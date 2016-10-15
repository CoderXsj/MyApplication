package com.cydai.cncx.launch;

import com.cydai.cncx.widget.CountDownTextView;

import java.util.Map;

import rx.Subscription;

/**
 * Created by 薛世君
 * Date : 2016/10/11
 * Email : 497881309@qq.com
 */

public interface ValidateMobileContract {

    interface IValidateMobileView{
        /**
         * 显示原始的验证码Button
         */
        void showOriginalConfirmationButton();

        /**
         * 更新原始验证码button
         */
        void updateConfirmationButton(long million);

        /**
         * 跳转到认证车主界面
         */
        void jump2authentication(Map<String,String> params);

        void validateConfirmationCode();

        void showValidateFailedDialog();
    }

    interface IValidateMobilePresenter{
        /**
         * 请求验证码
         */
        void postConfirmationCode(String mobile,CountDownTextView countDownTextView);

        /**
         * 验证服务器的验证码
         */
        void validateConfirmationCode(String mobile,String confirmationCode);
    }

    interface IValidateMobileModule{
        /**
         * 获取手机号码
         * @param username  手机号码
         * @return
         */
        Subscription getConfirmationCode(String username);

        /**
         * 验证验证码
         * @param mobile
         * @param confirmationCode
         * @return
         */
        Subscription validateConfirmationCode(String mobile,String confirmationCode);
    }
}