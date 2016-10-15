package com.cydai.cncx.common;

/**
 * Created by 薛世君
 * Date : 2016/10/8
 * Email : 497881309@qq.com
 */
public class Constants {
    //驾驶证
    public final static int DRIVER_LICENSE = 0;
    //司机idcard
    public final static int DRIVER_ID_CARD = 1;
    //行驶证
    public final static int DRIVING_LICENSE= 2;

    public final static String BASE_HOST = "http://120.76.138.252:3222";

    public final static String BASE_URL = BASE_HOST + "/api/";

    public final static String SHARE_MENU = "SHARE_PREFERENCE";

    public final static String SP_FIRST_ENTER = "isFirstEnter";

    public final static String SP_HAVE_LOGIN = "isLogin";

    public final static String SP_ACCESS_TOKEN = "Access_Token";

    public final static String STATUS_SUCCESS = "success";

    public final static String STATUS_FAILURE = "failure";

    public final static String STATUS_ERROR = "error";

    public final static int STATUS_ORDER_GET = 0;

    public final static int STATUS_ORDER_GETTING = 1;

    public final static int REQUEST_ID_CARD = 1001;

    //请求驾驶证
    public final static int REQUEST_DRIVER_LICENSE = 1002;

    //请求行驶证
    public final static int REQUEST_DRIVING_LICENSE = 1003;

    public final static String INTENT_DRIVER_ID_CARD_IMAGE = "DriverIdCardImage";

    public final static String INTENT_DRIVER_LICENSE_IMAGE = "DriverLicenseImage";

    public final static String INTENT_DRIVER_LICENSE = "DriverLicense";

    public final static String INTENT_DRIVER_NAME = "DriverName";

    public final static String INTENT_DRIVER_DATE = "DriverDate";

    public final static String INTENT_DRIVER_MOBILE = "DriverMobile";
}