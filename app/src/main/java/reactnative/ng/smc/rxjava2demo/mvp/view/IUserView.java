package reactnative.ng.smc.rxjava2demo.mvp.view;

import io.reactivex.disposables.Disposable;

/**
 * view 中用户操作方法
 */

public  interface IUserView {

    void showLoading();

    void hideLoading();

    void showFailedError();

    void showData(String data);

    void clearAll();

    void disPosable(Disposable disposable);
}
