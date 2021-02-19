package com.rh.utils;

// ���ദ��hashֵ�ֱ�
public class Hash163 {
    private final static int TABLE_COUNT = 12;// �ֱ���

    private final static int USEE_TABLE_COUNT = 10;// �û���ر��10��
    
    private final static int FAVR_MARK_TABLE_COUNT = 100;// �ղغ���ǩ3.0���100�� 

    public final static String USERINFO_TABLE_HEAD = "I_USERINFO";// �û���Ϣ��

    public final static String USERCDN_TABLE_HEAD = "I_USERCDN";// �û�����������

    public final static String IPTVINFO_TABLE_HEAD = "I_IPTVINFO";// IPTV������Ϣ��

    public final static String IPTVLOGIN_TABLE_HEAD = "I_IPTVLOGIN";// IPTV�û���¼��Ϣ��
    
    public final static String MULLOGIN_TABLE_HEDA = "I_MULLOGIN";// �����û���¼��Ϣ��

    public final static String SUBSCRIBEINFO_TABLE_HEAD = "I_SUBSCRIBEINFO";// ���¶�����ϵ��

    public final static String TEMPSUBSCRIPTION_TABLE_HEAD = "I_TEMPSUBSCRIPTION";// �ǰ��¶�����ϵ��


    public final static String MOBILEINFO_TABLE_HEAD = "I_MOBILEINFO";// MOBILE������Ϣ

    public final static String PCINFO_TABLE_HEAD = "I_PCINFO";// PC������Ϣ

    public final static String FAVORITEINFO_TABLE_HEAD = "I_FAVORITEINFO";// �ղ���Ϣ

    public final static String MEETINGINFO_TABLE_HEAD = "I_MEETINGINFO";// ����������Ϣ

    public final static String SOFTCLIENTINFO_TABLE_HEAD = "I_SOFTCLIENTINFO";// ���ն�������Ϣ
    
    public final static String SOFTCLIENTONLINEINFO_TABLE_HEAD = "I_SOFTCLIENTONLINEINFO";// ���ն�������Ϣ
    
    public final static String IPTVACCOUNT_TABLE_HEAD = "I_IPTVACCOUNT";// �����˻���
    
    public final static String GAMESACCOUNT_TABLE_HEAD = "I_GAMES_ACCOUNT";// ��Ϸ�˺ű�
    
    public final static String EXCHARGE_TABLE_HEAD = "A_EXCHANGE_DET";// ����TVB��ֵ�嵥��
    
    public final static String IPTVBINDUNIONPAY_TABLE_HEAD = "A_UNIONPAY_IPTVBIND"; // �����û�����������
    
    public final static String UNIONPAYMSG_TABLE_HRED = "A_UNIONPAYMSG"; //�����������ױ��ģ����·ֱ�
    
    public final static String PHONESMSCODE_TABLE_HRED = "A_PHONESMSCODE"; //�����������ױ��ģ����·ֱ�
    
    public final static String FAVORITE_TABLE_HEAD = "A_FAVORITE";// �ղ���Ϣ3.0
    
    public final static String BOOKMARK_TABLE_HEAD = "A_BOOKMARK";// ��ǩ��Ϣ3.0
    
    public final static String APPFAVORITE_TABLE_HEAD = "A_APPFAVORITE";// Ӧ���ղ���Ϣ
    
    public final static String APPSCORERECORD_TABLE_HEAD = "A_APPSCORERECORD";// Ӧ���ղ���Ϣ
    
    public final static String AMS_USERINFO = "A_USERINFO"; //0101�û�������Ϣ
    
    public final static String AMS_GAMESACCOUNT = "A_GAMESACCOUNT";//��Ϸ�˻���Ϣ
    public final static String AMS_READINGACCOUNT = "A_READINGACCOUNT";//�Ķ��˻���Ϣ
    public final static String AMS_EDUCATIONACCOUNT = "A_EDUCATIONACCOUNT";//�����˻���Ϣ
    public final static String AMS_KALAOKACCOUNT = "A_KALAOKACCOUNT";//����OK�˻���Ϣ
    public final static String AMS_LIVEACCOUNT = "A_LIVEACCOUNT";//�����˻���Ϣ

    
 
    public final static String ACCOUNT_LOCK_TABLE_HEAD = "A_ACCOUNTLOCK";// ͯ�������
    public final static String ACCOUNT_LOCK_HISTORY_TABLE_HEAD = "A_ACCOUNTLOCKHISTORY";// ͯ�������޸ļ�¼��
    
    public final static String A_ACCOUNTPHONE_TABLE_HEAD ="A_ACCOUNTPHONE";//���ֻ���
    
    public final static String HISTORYINFO_TABLE_HEAD = "A_HISTORYINFO";// ������ʷ��Ϣ��
    
    public  final static String STBBLACKACCOUNT_TABLE_HEAD ="A_STBBLACKACCOUNT";//�����û���������Ϣ
    public final static String STBGRAYACCOUNT_TABLE_HEAD = "A_STBGRAYACCOUNT"; //������������
    
    public final static String A_PRODUCTCARDINFO_TABLE_HEAD = "A_PRODUCTCARDINFO"; //��Ʒ����Ϣ��
    public final static String A_PRODUCTCARDBIND_TABLE_HEAD = "A_PRODUCTCARDBIND"; //��Ʒ�����ΰ󶨱�
    public final static String A_PRODUCTCARDHISTORY_TABLE_HEAD = "A_PRODUCTCARDHISTORY"; //��Ʒ��ʹ�ü�¼��
    public final static String A_PRODUCTCARDBATCH_TABLE_HEAD = "A_PRODUCTCARDBATCH"; //��Ʒ�����α�
    
    public final static String QUESTIONNATE_TABLE_HEAD = "A_QUESTIONNATE";// �ʾ�����
    public  final static String A_SELFSERVICESACCOUNT_TABLE_HEAD="A_SELFSERVICESACCOUNT";//�Է����������ͨ���û���
    public static final String A_USERCHECKRESULT_TABLE_HEAD = "A_USERCHECKRESULT";//ͯ������ʹ�ü�¼��
    
    //add by zhaoyj 71919
    //��Ϣ������ر�
    public static final String A_M_USERNORMALMESSAGEVIEW_TABLE_HEAD = "A_M_USERNORMALMESSAGEVIEW";//�û���ͨ��Ϣ������
    public static final String A_M_USERNORMALMESSAGE_TABLE_HEAD = "A_M_USERNORMALMESSAGE";//�û���ͨ��Ϣ��
    public static final String A_M_USERFORCEDMESSAGE_TABLE_HEAD = "A_M_USERFORCEDMESSAGE";//�û�ǿ����Ϣ��
    
    //���Ż��ֶһ���¼��
    public static final String A_POINTRECORD_TABLE_HEAD = "A_POINTRECORD";
    
    //�㶫ͳһ����������Ϣ��
    public static final String A_GDORDERINFO_TABLE_HEAD = "A_GDORDERINFO";
    
    //add by zhaoyj 71919 20151020
    // �Ĵ��ļ��ȳ齱��
    public static final String T_A_LOTTERY_TABLE_HEAD = "T_A_LOTTERY";
    
    //�û�֧��ȯ�˻���
    public static final String A_USERPAYMENTVOUCHERACCOUNT_TABLE_HEAD = "A_USERPAYMENTVOUCHERACCOUNT";
    //�û�֧��ȯ��ȡ��¼��
    public static final String A_USERPAYMENTVOUCHEROBTAIN_TABLE_HEAD = "A_USERPAYMENTVOUCHEROBTAIN";
    
    //�û�֧��ȯ���Ѽ�¼��
    public static final String A_USERPAYVOUCHERCONSUMPTION_TABLE_HEAD = "A_USERPAYVOUCHERCONSUMPTION";
    
    
    // �Ĵ�517��ɱ��¼��
    public static final String T_A_MAYACTIVITYRECODE_TABLE_HEAD = "T_A_MAYACTIVITYRECODE";
    
    // �Ĵ�������ɱ��¼��
    public static final String T_A_DRAGONBOATRECORD_TABLE_HEAD = "T_A_DRAGONBOATRECORD";
    /**
     * ��ö�����ϵ����
     * 
     * @param userName
     * @return
     * @throws Exception
     */
    public static String getSubscribeInfoTableName(String userName)
        throws Exception {
        return getTableName2(userName, SUBSCRIBEINFO_TABLE_HEAD);
    }

    /**
     * ��ö�����ϵ����
     * 
     * @param userName
     * @return
     * @throws Exception
     */
    public static String getTempSubscribeInfoTableName(String userName)
        throws Exception {
        return getTableName2(userName, TEMPSUBSCRIPTION_TABLE_HEAD);
    }

    /**
     * ����嵥����
     * 
     * @param cdate
     * @return
     */
    public static String getDetTableName(java.util.Date cdate) {
        // ȡ�嵥
        String detMonth = DateUtil.getMonth(cdate);
        String tableName = "I_USERDET" + detMonth;
        return tableName;
    }

    /**
     * ��öһ���ʷ��¼����
     * 
     * @param cdate
     * @return
     */
    public static String getExchangeHistoryTableName(java.util.Date cdate) {
        String detMonth = DateUtil.getMonth(cdate);
        String tableName = "I_EXCHANGEHISTORY" + detMonth;
        return tableName;
    }

    /**
     * ����嵥���ݱ���
     * 
     * @param cdate
     * @return
     */
    public static String getDetBakTableName(java.util.Date cdate) {
        // ȡ�嵥
        String detMonth = DateUtil.getMonth(cdate);
        String tableName = "I_USERDETBAK" + detMonth;
        return tableName;
    }

    /**
     * ����û�����
     * 
     * @return java.lang.String
     * @param userName
     *            java.lang.String
     */
    public static String USERHash(String userName, String tableHead)
        throws Exception {
        return getTableName2(userName, tableHead);
    }

    /**
     * �����û��������ر���. ���� ����: (2002-6-6 11:18:12)
     * 
     * @return java.lang.String
     * @param str
     *            java.lang.String
     * @param tableHead
     *            java.lang.String
     */
    public static String getTableName(String str, String tableHead)
        throws Exception {
        if (tableHead == null || tableHead.equals(""))
            throw new Exception(
                "Exception in method getTableName of class Hash163:param tableHead is null");
        return tableHead + sHash(str);
    }

    public static String getTableName2(String str, String tableHead)
        throws Exception {
        if (tableHead == null || tableHead.equals(""))
            throw new Exception(
                "Exception in method getTableName of class Hash163:param tableHead is null");
        return tableHead + sHash2(str);
    }

    // �����û������HASHֵ
    static public int hash(String str) {
        int l_power[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
            113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int s_sum, i;
        s_sum = 0;
        for (i = 0; i < str.length(); i++ ) {
            s_sum += str.charAt(i) * l_power[i];
        }
        return (s_sum % TABLE_COUNT);
    }

    // �����û������HASHֵ
    static public int hash2(String str) {
        int l_power[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
            113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int s_sum, i;
        s_sum = 0;
        for (i = 0; i < str.length(); i++ ) {
            s_sum += str.charAt(i) * l_power[i];
        }
        return (s_sum % USEE_TABLE_COUNT);
    }

    /**
     * �˴����뷽������. ���� ����: (2002-6-7 10:49:11)
     * 
     * @return java.lang.String
     * @param str
     *            java.lang.String
     */
    public static String sHash(String str)
        throws Exception {
        int sum = -1;
        String ret = "";
        if (str == null || str.length() > 40)
            throw new Exception(
                "Exception in method sHash of class Hash163:str is null or lenth > 40");
        sum = hash(str.trim());
        if ( (sum < 0) || (sum > TABLE_COUNT - 1))
            throw new Exception(
                "Exception in method sHash of class Hash163:error");
        if (sum < 10)
            ret = "0" + sum;
        else if (sum >= 10)
            ret = String.valueOf(sum);
        return (ret);
    }

    public static String sHash2(String str)
        throws Exception {
        int sum = -1;
        String ret = "";
        if (str == null || str.length() > 40)
            throw new Exception(
                "Exception in method sHash of class Hash163:str is null or lenth > 40");
        sum = hash2(str.trim());
        if ( (sum < 0) || (sum > USEE_TABLE_COUNT - 1))
            throw new Exception(
                "Exception in method sHash of class Hash163:error");
        if (sum < 10)
            ret = "0" + sum;
        else if (sum >= 10)
            ret = String.valueOf(sum);
        return (ret);
    }

    // ��ȡԭʼ��hashֵ
    static public int hashValue(String str) {
        int l_power[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
            113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int s_sum, i;
        s_sum = 0;
        for (i = 0; i < str.length(); i++ ) {
            s_sum += str.charAt(i) * l_power[i];
        }
        return s_sum;
    }
    
    
    /**
     * ����û�����
     * 
     * @return java.lang.String
     * @param userName java.lang.String
     *      ��100�ű�
     */
    public static String USERHash3(String userName, String tableHead)
            throws Exception {
            return getTableName3(userName, tableHead);
        }
    public static String getTableName3(String str, String tableHead)
            throws Exception {
            if (tableHead == null || tableHead.equals(""))
                throw new Exception(
                    "Exception in method getTableName of class Hash163:param tableHead is null");
            return tableHead + sHash3(str);
        }
    

    public static String sHash3(String str)
        throws Exception {
        int sum = -1;
        String ret = "";
        if (str == null || str.length() > 40)
            throw new Exception(
                "Exception in method sHash of class Hash163:str is null or lenth > 40");
        sum = hash3(str.trim());
        if ( (sum < 0) || (sum > FAVR_MARK_TABLE_COUNT - 1))
            throw new Exception(
                "Exception in method sHash of class Hash163:error");
        if (sum < 10)
            ret = "0" + sum;
        else if (sum >= 10)
            ret = String.valueOf(sum);
        return (ret);
    }

    // �����û������HASHֵ
    static public int hash3(String str) {
        int l_power[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43,
            47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109,
            113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        int s_sum, i;
        s_sum = 0;
        for (i = 0; i < str.length(); i++ ) {
            s_sum += str.charAt(i) * l_power[i];
        }
        return (s_sum % FAVR_MARK_TABLE_COUNT);
    }

    public static void main(String[] args) throws Exception {
        String loginAccount="iptv7716624032";
//        String tableNo = Hash163.USERHash(loginAccount, Hash163.AMS_USERINFO);
        String tableNo= Hash163.USERHash(loginAccount, Hash163.SUBSCRIBEINFO_TABLE_HEAD);
        System.out.println(tableNo);
    }
}
