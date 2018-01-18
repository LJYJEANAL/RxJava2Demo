package reactnative.ng.smc.rxjava2demo.view;

import reactnative.ng.smc.rxjava2demo.model.User;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
