package reactnative.ng.smc.rxjava2demo.presenter;

import android.os.Handler;

import reactnative.ng.smc.rxjava2demo.model.IUserBiz;
import reactnative.ng.smc.rxjava2demo.model.OnLoginListener;
import reactnative.ng.smc.rxjava2demo.model.User;
import reactnative.ng.smc.rxjava2demo.model.UserBiz;
import reactnative.ng.smc.rxjava2demo.view.IUserLoginView;

/**
 * Created by Administrator on 2017/9/7.
 */

public class UserLoginPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private Handler mHandler = new Handler();

    public UserLoginPresenter(IUserLoginView userLoginView) {
        this.userLoginView = userLoginView;
        this.userBiz = new UserBiz();
    }

    public void login() {
        userLoginView.showLoading();
        userBiz.login(userLoginView.getUserName(), userLoginView.getPassword(), new OnLoginListener() {
            @Override
            public void loginSuccess(final User user) {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.toMainActivity(user);
                        userLoginView.hideLoading();
                    }
                });
            }

            @Override
            public void loginFailed() {
                //需要在UI线程执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.showFailedError();
                        userLoginView.hideLoading();
                    }
                });

            }
        });
    }

    public void clear() {
        userLoginView.clearUserName();
        userLoginView.clearPassword();
    }
}
