package com.rh.utils;

// 此类处理按hash值分表
public class Hash163 {
    private final static int TABLE_COUNT = 12;// 分表数

    private final static int USEE_TABLE_COUNT = 10;// 用户相关表分10张
    
    private final static int FAVR_MARK_TABLE_COUNT = 100;// 收藏和书签3.0表分100张 

    public final static String USERINFO_TABLE_HEAD = "I_USERINFO";// 用户信息表

    public final static String USERCDN_TABLE_HEAD = "I_USERCDN";// 用户能力关联表

    public final static String IPTVINFO_TABLE_HEAD = "I_IPTVINFO";// IPTV能力信息表

    public final static String IPTVLOGIN_TABLE_HEAD = "I_IPTVLOGIN";// IPTV用户登录信息表
    
    public final static String MULLOGIN_TABLE_HEDA = "I_MULLOGIN";// 三屏用户登录信息表

    public final static String SUBSCRIBEINFO_TABLE_HEAD = "I_SUBSCRIBEINFO";// 包月定购关系表

    public final static String TEMPSUBSCRIPTION_TABLE_HEAD = "I_TEMPSUBSCRIPTION";// 非包月定购关系表


    public final static String MOBILEINFO_TABLE_HEAD = "I_MOBILEINFO";// MOBILE能力信息

    public final static String PCINFO_TABLE_HEAD = "I_PCINFO";// PC能力信息

    public final static String FAVORITEINFO_TABLE_HEAD = "I_FAVORITEINFO";// 收藏信息

    public final static String MEETINGINFO_TABLE_HEAD = "I_MEETINGINFO";// 会议能力信息

    public final static String SOFTCLIENTINFO_TABLE_HEAD = "I_SOFTCLIENTINFO";// 软终端能力信息
    
    public final static String SOFTCLIENTONLINEINFO_TABLE_HEAD = "I_SOFTCLIENTONLINEINFO";// 软终端在线信息
    
    public final static String IPTVACCOUNT_TABLE_HEAD = "I_IPTVACCOUNT";// 积分账户表
    
    public final static String GAMESACCOUNT_TABLE_HEAD = "I_GAMES_ACCOUNT";// 游戏账号表
    
    public final static String EXCHARGE_TABLE_HEAD = "A_EXCHANGE_DET";// 湖南TVB充值清单表
    
    public final static String IPTVBINDUNIONPAY_TABLE_HEAD = "A_UNIONPAY_IPTVBIND"; // 江苏用户绑定银联卡表
    
    public final static String UNIONPAYMSG_TABLE_HRED = "A_UNIONPAYMSG"; //江苏银联交易报文（按月分表）
    
    public final static String PHONESMSCODE_TABLE_HRED = "A_PHONESMSCODE"; //江苏银联交易报文（按月分表）
    
    public final static String FAVORITE_TABLE_HEAD = "A_FAVORITE";// 收藏信息3.0
    
    public final static String BOOKMARK_TABLE_HEAD = "A_BOOKMARK";// 书签信息3.0
    
    public final static String APPFAVORITE_TABLE_HEAD = "A_APPFAVORITE";// 应用收藏信息
    
    public final static String APPSCORERECORD_TABLE_HEAD = "A_APPSCORERECORD";// 应用收藏信息
    
    public final static String AMS_USERINFO = "A_USERINFO"; //0101用户基本信息
    
    public final static String AMS_GAMESACCOUNT = "A_GAMESACCOUNT";//游戏账户信息
    public final static String AMS_READINGACCOUNT = "A_READINGACCOUNT";//阅读账户信息
    public final static String AMS_EDUCATIONACCOUNT = "A_EDUCATIONACCOUNT";//教育账户信息
    public final static String AMS_KALAOKACCOUNT = "A_KALAOKACCOUNT";//卡拉OK账户信息
    public final static String AMS_LIVEACCOUNT = "A_LIVEACCOUNT";//生活账户信息

    
 
    public final static String ACCOUNT_LOCK_TABLE_HEAD = "A_ACCOUNTLOCK";// 童锁密码表
    public final static String ACCOUNT_LOCK_HISTORY_TABLE_HEAD = "A_ACCOUNTLOCKHISTORY";// 童锁密码修改记录表
    
    public final static String A_ACCOUNTPHONE_TABLE_HEAD ="A_ACCOUNTPHONE";//绑定手机表
    
    public final static String HISTORYINFO_TABLE_HEAD = "A_HISTORYINFO";// 定购历史信息表
    
    public  final static String STBBLACKACCOUNT_TABLE_HEAD ="A_STBBLACKACCOUNT";//大厅用户黑名单信息
    public final static String STBGRAYACCOUNT_TABLE_HEAD = "A_STBGRAYACCOUNT"; //大厅灰名单表
    
    public final static String A_PRODUCTCARDINFO_TABLE_HEAD = "A_PRODUCTCARDINFO"; //产品卡信息表
    public final static String A_PRODUCTCARDBIND_TABLE_HEAD = "A_PRODUCTCARDBIND"; //产品卡批次绑定表
    public final static String A_PRODUCTCARDHISTORY_TABLE_HEAD = "A_PRODUCTCARDHISTORY"; //产品卡使用记录表
    public final static String A_PRODUCTCARDBATCH_TABLE_HEAD = "A_PRODUCTCARDBATCH"; //产品卡批次表
    
    public final static String QUESTIONNATE_TABLE_HEAD = "A_QUESTIONNATE";// 问卷调查表
    public  final static String A_SELFSERVICESACCOUNT_TABLE_HEAD="A_SELFSERVICESACCOUNT";//自服务大厅可视通话用户表
    public static final String A_USERCHECKRESULT_TABLE_HEAD = "A_USERCHECKRESULT";//童锁密码使用记录表
    
    //add by zhaoyj 71919
    //消息盒子相关表
    public static final String A_M_USERNORMALMESSAGEVIEW_TABLE_HEAD = "A_M_USERNORMALMESSAGEVIEW";//用户普通消息概览表
    public static final String A_M_USERNORMALMESSAGE_TABLE_HEAD = "A_M_USERNORMALMESSAGE";//用户普通消息表
    public static final String A_M_USERFORCEDMESSAGE_TABLE_HEAD = "A_M_USERFORCEDMESSAGE";//用户强制消息表
    
    //电信积分兑换记录表
    public static final String A_POINTRECORD_TABLE_HEAD = "A_POINTRECORD";
    
    //广东统一订购订单信息表
    public static final String A_GDORDERINFO_TABLE_HEAD = "A_GDORDERINFO";
    
    //add by zhaoyj 71919 20151020
    // 四川四季度抽奖表
    public static final String T_A_LOTTERY_TABLE_HEAD = "T_A_LOTTERY";
    
    //用户支付券账户表
    public static final String A_USERPAYMENTVOUCHERACCOUNT_TABLE_HEAD = "A_USERPAYMENTVOUCHERACCOUNT";
    //用户支付券领取记录表
    public static final String A_USERPAYMENTVOUCHEROBTAIN_TABLE_HEAD = "A_USERPAYMENTVOUCHEROBTAIN";
    
    //用户支付券消费记录表
    public static final String A_USERPAYVOUCHERCONSUMPTION_TABLE_HEAD = "A_USERPAYVOUCHERCONSUMPTION";
    
    
    // 四川517秒杀记录表
    public static final String T_A_MAYACTIVITYRECODE_TABLE_HEAD = "T_A_MAYACTIVITYRECODE";
    
    // 四川端午秒杀记录表
    public static final String T_A_DRAGONBOATRECORD_TABLE_HEAD = "T_A_DRAGONBOATRECORD";
    /**
     * 获得订购关系表名
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
     * 获得订购关系表名
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
     * 获得清单表名
     * 
     * @param cdate
     * @return
     */
    public static String getDetTableName(java.util.Date cdate) {
        // 取清单
        String detMonth = DateUtil.getMonth(cdate);
        String tableName = "I_USERDET" + detMonth;
        return tableName;
    }

    /**
     * 获得兑换历史记录表名
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
     * 获得清单备份表名
     * 
     * @param cdate
     * @return
     */
    public static String getDetBakTableName(java.util.Date cdate) {
        // 取清单
        String detMonth = DateUtil.getMonth(cdate);
        String tableName = "I_USERDETBAK" + detMonth;
        return tableName;
    }

    /**
     * 获得用户表名
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
     * 根据用户名获得相关表名. 创建 日期: (2002-6-6 11:18:12)
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

    // 根据用户名获得HASH值
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

    // 根据用户名获得HASH值
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
     * 此处插入方法描述. 创建 日期: (2002-6-7 10:49:11)
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

    // 获取原始的hash值
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
     * 获得用户表名
     * 
     * @return java.lang.String
     * @param userName java.lang.String
     *      分100张表
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

    // 根据用户名获得HASH值
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
        String loginAccount="839416402630@HAITV";
//        System.out.println(Hash163.sHash2("IPTV2021032434823618"));
//        String tableNo = Hash163.USERHash(loginAccount, Hash163.IPTVINFO_TABLE_HEAD);// BMS用户信息表
        String tableNo= Hash163.USERHash(loginAccount, Hash163.SUBSCRIBEINFO_TABLE_HEAD);//BMS 订购信息表
//        String tableNo = Hash163.USERHash(loginAccount, Hash163.AMS_USERINFO);//AMS用户信息表
        System.out.println(tableNo);
       String tableName = Hash163.USERHash(loginAccount, Hash163.SUBSCRIBEINFO_TABLE_HEAD);
        System.out.println(tableName);
    }
}
