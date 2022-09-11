package com.android.test;

/**
 * @author ihesen
 * @data 2019-08-05
 */
public interface Constants {


    interface Strings {
        //订单详情需要的订单code
        String DETAIL_ORDER_CODE = "detail_order_code";
    }

    interface Int{
        int TAKE_PHOTO_REQUEST_CODE = 0x100;
        int SELECT_PHOTO_REQUEST_CODE = 0x101;
    }

    interface Pay {
        String ALIPAY = "ALIPAY";
        String WECHAT = "WECHAT";

    }

    interface Ints {
        //公司列表当前生效公司标识，1-生效，2-不生效
        int COMPANY_ACTIVE = 1;
    }

    /**
     * 订单状态 1-待付款，2-已付款，3-已关闭
     */
    interface OrderStatus {
        //待付款
        int ORDER_PAY_WAIT = 1;
        //已付款
        int ORDER_PAY_COMPLETED = 2;
        //已关闭
        int ORDER_PAY_CLOSE = 3;
    }
}
