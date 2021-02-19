/*
 * Copyright (c) 2016, Asiainfo-Linkage. All rights reserved.<br>
 * Asiainfo-Linkage PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.rh.utils;

import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <b>��Ȩ��</b>Copyright (c) 2016 ����.<br>
 * <b>���̣�</b>epgstb<br>
 * <b>�ļ���</b>HttpUtil.java<br>
 * <b>�����ˣ�</b> zhaoyj	71919<br>
 * <b>����ʱ�䣺</b>2016-3-23 ����2:47:19<br>
 * <p>
 * <b>������Լ����װhttpclient.</b><br>
 * ������Լ����װhttpclient.<br>
 * </p>
 *
 * @author zhaoyj
 * @see [�����/����]
 * @since [��Ʒ/ģ��汾] 
 */
public class HttpUtil {
	
	// �Ƿ��ѹر�
	private boolean isShutdown = false;
	// Ĭ���ַ�����ΪUTF-8
	private static final String DEFAULT_CONTENT_CHARSET = "UTF-8";
	// 
	private static final String DEFAULT_ELEMENT_CHARSET = "UTF-8";
	
	//private static final String DEFAULT_IMAGE_FOLDER = "e:/upload/news";
	//private static final String DEFAULT_IMAGE_FOLDER = "/temp";
	
	private HttpClient httpclient;
	
	private HttpRequestBase request;
	
	public HttpUtil() {
		httpclient = new DefaultHttpClient();
		this.useCharset(DEFAULT_CONTENT_CHARSET);
		httpclient.getParams().setParameter(
				HttpProtocolParams.HTTP_ELEMENT_CHARSET, DEFAULT_ELEMENT_CHARSET);
		httpclient.getParams().setParameter(
				HttpProtocolParams.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
	}
	
	/**
	 * ʹ��HTTP����ʽ
	 */
	public HttpUtil useHttps() {
		try {
            TrustManager easyTrustManager = new X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];  //To change body of implemented methods use File | Settings | File Templates.
                }
            };

			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, new TrustManager[] { easyTrustManager }, new java.security.SecureRandom());

			SSLSocketFactory ssf = new SSLSocketFactory(sslcontext);
			httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, ssf));
		} catch (Exception e) {
		}
		
		return this;
	}
	
	/**
	 * ʹ��HTTP��GET��ʽ��������
	 */
	public HttpUtil useHttpGet() {
		this.request = new HttpGet();
		return this;
	}
	
	/**
	 * ʹ��HTTP��POST��ʽ��������
	 */
	public HttpUtil useHttpPost() {
		this.request = new HttpPost();
		return this;
	}
	
	/**
	 * ʹ���ض�������Ϊ�����ַ�����
	 * @param charset
	 * @return
	 */
	public HttpUtil useCharset(String charset) {
		httpclient.getParams().setParameter(
				HttpProtocolParams.HTTP_CONTENT_CHARSET, charset);
		return this;
	}
	
	/**
	 * ���ó�ʱ
	 * 
	 * @param connectionTimeout The timeout until a connection is established. A value of zero means the timeout is not used.
	 * @param soTimeout The default socket timeout (SO_TIMEOUT) in milliseconds which is the timeout for waiting for data. A timeout value of zero is interpreted as an infinite timeout. 
	 * @param connectionManagerTimeout The timeout in milliseconds used when retrieving an HTTP connection from the HTTP connection manager. 0 means to wait indefinitely. 
	 * @return
	 */
	public HttpUtil timeout(Integer connectionTimeout, Integer soTimeout, Long connectionManagerTimeout) {
		if (connectionTimeout != null) {
			httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connectionTimeout);
		}
		if (soTimeout != null) {
			httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, soTimeout);
		}
		if (connectionManagerTimeout != null) {
			httpclient.getParams().setParameter("http.connection-manager.timeout", connectionManagerTimeout);
		}
		return this;
	}
	
	/**
	 * ����POST����
	 * @param params
	 */
	public void postParams(Map<String, String> params) {
		if (request instanceof HttpPost) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try{
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			}
			catch(UnsupportedEncodingException ex){
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * ����POST����
	 * @param strParams
	 */
	public void postParams(String strParams) {
		if (request instanceof HttpPost) {
			try{
				((HttpPost) request).setEntity(new StringEntity(strParams, "UTF-8"));
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ȡhttp POST�������
	 * @param req
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getPostParams(HttpServletRequest req) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = req.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // ����������δ����ڳ�������ʱʹ�á����mysign��sign�����Ҳ����ʹ����δ���ת��
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");
            params.put(name, valueStr);
        }
        return params;
	}

	/**
	 * ��ȡ����url���ַ������
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getResponseText(String url) {
		if (isShutdown()) return null;
		String text = null;
		try {
			if (request != null) {
				request.setURI(new URI(url));
			} else {
				request = new HttpGet(url); // Ĭ��ʹ��GET��ʽ����
			}
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			text = httpclient.execute(request, responseHandler);
		} catch (Exception e) {
			if (!request.isAborted()) request.abort();
			e.printStackTrace();
			return null;
		} finally {
			request.abort();
		}
		return text;
	}
	
	public String getResponseText(String url, String strCharset) {
		if (isShutdown()) return null;
		String text = null;
		InputStream is = null;
		try {
			if (request != null) {
				request.setURI(new URI(url));
			} else {
				request = new HttpGet(url); // Ĭ��ʹ��GET��ʽ����
			}
			HttpResponse response = httpclient.execute(request);
			if (response.getStatusLine().getStatusCode() != 200) {  
				if (!request.isAborted()) request.abort();  
	            return null;
	        }
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				is = entity.getContent();
				text = new String(IOUtils.toByteArray(is), strCharset);
				return text;
			}
		} catch (Exception e) {
			if (!request.isAborted()) request.abort();
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			request.abort();
		}
		return null;
	}
	

	public HttpClient getHttpClient() {
		return httpclient;
	}
	
	public void shutdown() {
		httpclient.getConnectionManager().shutdown();
		isShutdown = true;
	}
	
	public boolean isShutdown() {
		return isShutdown;
	}
	
	public static void main(String[] args) {
	    String URL="http://117.27.128.186:35309/itv-open/";
	    String appId="10000006";
	    String timestamp=DateUtil.getNowTimeStamp();
        String auth=MD5.digest(appId+timestamp);
	    String paramInfo=DES3Util.encryptMsg("itvAccount=iptv7819126038","PRBSQBQHMO1T3DZPOLWCI7ILTN9F6V5OIX8SWYZVGTDNC67H");
        URL+="appId="+appId+"&timestamp="+timestamp+"&paramInfo="+paramInfo+"&auth="+auth;

		try {
            String sbBack = HttpUtil.sendGet(URL);
            JSONObject jsonObject = JSONObject.fromObject(sbBack);
            String areaCode = (String) jsonObject.get("areaCode");
        } catch (HttpResponseException e) {
			e.printStackTrace();
		}

	}
	
	/**
     * ��ָ��URL����GET����������
     * @param url ���������URL
     * @return URL ������Զ����Դ����Ӧ���
     */
    public static String sendGet(String url) throws HttpResponseException {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setConnectTimeout(2000);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
			System.out.println("--------------");
        } catch (HttpResponseException e){
			System.out.println(e);
		}

//        catch (SocketTimeoutException e1){
//			System.out.println("����GET��������쳣��" + e1);
//			result="";
//			String message = e1.getMessage();
//			Throwable cause = e1.getCause();
//			getExceptionAllinformation(e1);
//		}
        catch (Exception e2) {
				throw new HttpResponseException(404, "Not Found");

		}
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return result;
    }

    /**
     * ��ָ�� URL ����POST����������
	 * @param url	��������� URL
     * @param param	����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
     * @return ������Զ����Դ����Ӧ���
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(param);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("���� POST ��������쳣��"+e);
            e.printStackTrace();
        }
        //ʹ��finally�����ر��������������
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

	//1��
	public String getTrace(Throwable t) {
		StringWriter stringWriter= new StringWriter();
		PrintWriter writer= new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer= stringWriter.getBuffer();
		System.out.println(buffer.toString());
		return buffer.toString();
	}

	//2��
	public static String getExceptionAllinformation(Exception ex){
		String sOut = "";
		StackTraceElement[] trace = ex.getStackTrace();
		for (StackTraceElement s : trace) {
			sOut += "\tat " + s + "\r\n";
		}
		System.out.println(sOut);
		return sOut;
	}

	//3��
	public static String getExceptionAllinformation_01(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		System.out.println(ret);
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
		}
		return ret;
	}

	//4��
	private static String toString_02(Throwable e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		System.out.println(sw.toString());
		return sw.toString();
	}
}
