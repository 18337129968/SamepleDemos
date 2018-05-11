package com.hfxief.utils;


import com.hfxief.R;
import com.hfxief.app.BaseApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: MaitianErrorHandler
 * @Description: 网络错误信息
 * @date 2016/9/1 10:57
 * @auther xie
 */
public class MaitianErrorHandler {

    // ---- Http ----
    private static final String MSG_NET_EOF = BaseApplication.getInstance().getString(R.string.msg_net_eof);
    private static final String MSG_NET_OFF = BaseApplication.getInstance().getString(R.string.msg_net_off);
    private static final String MSG_SOCKET_TIME_OUT = BaseApplication.getInstance().getString(R.string.msg_socket_timeout);
    private static final String MSG_CONN_TIME_OUT = BaseApplication.getInstance().getString(R.string.msg_connection_timeout);
    private static final String MSG_CHECK_NET = BaseApplication.getInstance().getString(R.string.msg_check_net);
    private static final String MSG_NONE_ERROR = BaseApplication.getInstance().getString(R.string.msg_none_error);
    private static final String MSG_BAD_REQUEST = BaseApplication.getInstance().getString(R.string.msg_bad_request);
    private static final String MSG_UNKNOWNS_HOST = BaseApplication.getInstance().getString(R.string.msg_unknow_host);
    private static final String MSG_SYNTAX_ERROR = BaseApplication.getInstance().getString(R.string.msg_syntax_error);
    private static final String MSG_CLIENT_ERROR = BaseApplication.getInstance().getString(R.string.msg_client_error);
    private static final String MSG_SERVER_ERROR = BaseApplication.getInstance().getString(R.string.msg_server_error);
    private static final String MSG_CONNECT_ERROR = BaseApplication.getInstance().getString(R.string.msg_connect_error);
    private static final String MSG_PERMISSION_DENY = BaseApplication.getInstance().getString(R.string.msg_perssion_deny);
    private static final String MSG_CLIENT_TIMEOUT = BaseApplication.getInstance().getString(R.string.msg_client_timeout);
    private static final String MSG_GATEWAY_TIMEOUT = BaseApplication.getInstance().getString(R.string.msg_gateway_timeout);

    // ---- Download ----
    private static final String MSG_DOWNLOAD_ENTITY = BaseApplication.getInstance().getString(R.string.msg_download_entity);
    private static final String MSG_DOWNLOAD_URL = BaseApplication.getInstance().getString(R.string.msg_download_url);

    // ---- Database ----
    private static final String MSG_DB_CREATE_FAILURE = BaseApplication.getInstance().getString(R.string.msg_db_create_failure);
    private static final String MSG_DB_DELETE_FAILURE = BaseApplication.getInstance().getString(R.string.msg_db_delete_failure);

    public static final String NET_OFF = "0";
    public static final String CHECK_NET = "1";
    public static final String CLIENT_ERROR = "2";
    public static final String SERVER_ERROR = "3";
    public static final String NONE_ERROR = "4";
    public static final String NET_EOF = "5";
    public static final String BAD_REQUEST = "6";
    public static final String SOCKET_TIME_OUT = "7";
    public static final String UNKNOWNS_HOST = "8";
    public static final String CONN_TIME_OUT = "9";
    public static final String SYNTAX_ERROR = "10";
    public static final String CONNECT_ERROR = "11";
    public static final String PERMISSION_DENY = "12";
    public static final String CLIENT_TIMEOUT = "13";
    public static final String GATEWAY_TIMEOUT = "14";
    public static final String DOWNLOAD_ENTITY = "15";
    public static final String DOWNLOAD_URL = "16";
    public static final String DB_CREATE_FAILURE = "17";
    public static final String DB_DELETE_FAILURE = "18";

    public static Map<String, String> EMS = new HashMap<>();

    static {
        EMS.put(NET_OFF, MSG_NET_OFF);
        EMS.put(CHECK_NET, MSG_CHECK_NET);
        EMS.put(CLIENT_ERROR, MSG_CLIENT_ERROR);
        EMS.put(SERVER_ERROR, MSG_SERVER_ERROR);
        EMS.put(NONE_ERROR, MSG_NONE_ERROR);
        EMS.put(NET_EOF, MSG_NET_EOF);
        EMS.put(BAD_REQUEST, MSG_BAD_REQUEST);
        EMS.put(SOCKET_TIME_OUT, MSG_SOCKET_TIME_OUT);
        EMS.put(UNKNOWNS_HOST, MSG_UNKNOWNS_HOST);
        EMS.put(CONN_TIME_OUT, MSG_CONN_TIME_OUT);
        EMS.put(SYNTAX_ERROR, MSG_SYNTAX_ERROR);
        EMS.put(CONNECT_ERROR, MSG_CONNECT_ERROR);
        EMS.put(PERMISSION_DENY, MSG_PERMISSION_DENY);
        EMS.put(CLIENT_TIMEOUT, MSG_CLIENT_TIMEOUT);
        EMS.put(GATEWAY_TIMEOUT, MSG_GATEWAY_TIMEOUT);

        EMS.put(DOWNLOAD_ENTITY, MSG_DOWNLOAD_ENTITY);
        EMS.put(DOWNLOAD_URL, MSG_DOWNLOAD_URL);

        EMS.put(DB_CREATE_FAILURE, MSG_DB_CREATE_FAILURE);
        EMS.put(DB_DELETE_FAILURE, MSG_DB_DELETE_FAILURE);
    }
}
