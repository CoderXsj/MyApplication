package com.cydai.cncx.launch;

import com.cydai.cncx.widget.CountDownTextView;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/10
 * Email : 497881309@qq.com
 */

public interface LoginContract {

    interface ILoginView{
        /**
         * 登陆进度的Dialog
         */
        void showProgressDialog();

        /**
         * 登陆进度的Dialog
         */
        void hiddenProgressDialog();

        /**
         * 显示原始的验证码Button
         */
        void showOriginalConfirmationButton();

        void showMessageDialog(String msg);

        /**
         * 更新原始验证码button
         */
        void updateConfirmationButton(long million);

        /**
         * 注册
         */
        void jump2registerActivity();

        /**
         * 跳转到MainActivity
         */
        void jump2mainActivity();
    }

    interface ILoginPresenter{
        /**
         * 请求验证码
         */
        void postConfirmationCode(String mobile,CountDownTextView countDownTextView);

        /**
         * 登陆
         * @param username 用户名
         * @param confirmationCode 验证码
         */
        void login(String username, String confirmationCode);
    }

    interface ILoginModule{
        /**
         *
         * @param username
         * @param confirmationCode
         * @return
         */
        Subscription login(String username, String confirmationCode,String imei);

        /**
         *
         * @param username  司机手机号码
         * @param type      请求者类型  0 司机  1乘客
         * @return
         */
        Subscription getConfirmationCode(String username,String type);
    }
}