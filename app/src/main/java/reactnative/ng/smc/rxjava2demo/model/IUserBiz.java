package reactnative.ng.smc.rxjava2demo.model;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
