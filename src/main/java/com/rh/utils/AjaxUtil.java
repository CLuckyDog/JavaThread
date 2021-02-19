/*
 * @(#)AjaxUtil.java 2009-5-19
 *
 * Copyright 2009 LINKAGE, Inc. All rights reserved.
 * LINKAGE PROPRIETARY/CONFIDtheENTIAL. Use is subject to license terms.
 */
package com.rh.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @description [Do the function simple description]
 * 
 * @author chenwei
 * @date 2009-5-22
 * @version 1.0.0
 * @since 1.0
 */
public class AjaxUtil 
{
	
    /**
     * 将对象转化为JSON字符串
     * @author chenwei
     * @date 2009-5-22
     * @param object
     * @return
     */
    public static String convert2JSONString(Object object) {
    	String jsonString = null;
        // 日期值处理器
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class,
            new JsonDateValueProcessor());
        if (object != null) 
        {
            if (object instanceof Collection || object instanceof Object[])
            {
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();
            } 
            else 
            {
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();
            }
        }
        return jsonString == null ? "''" : jsonString;
    }

    /**
     * 返回AJAX调用结果
     * 
     * @author chenwei
     * @date 2009-5-22
     * @param response
     * @param outputString
     * @throws IOException
     */
    public static void ajaxOutput(HttpServletResponse response, String outputString) throws IOException {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(outputString);
        response.getWriter().flush();
        
    }
    
    /**
     * 
     * 返回页面传递过来的值map
     * 
     * @author chenwei
     * @date 2009-5-22
     * @version 1.0.0
     * @param request
     * @return
     */
    public static Map ajaxGetInput(HttpServletRequest request)
    {
        return request.getParameterMap();
    }

}
