package reactnative.ng.smc.rxjava2demo.mvp.presenter;

import android.os.Handler;
import android.util.Log;

import io.reactivex.disposables.Disposable;
import reactnative.ng.smc.rxjava2demo.mvp.model.Listener;
import reactnative.ng.smc.rxjava2demo.mvp.model.MvpNetManager;
import reactnative.ng.smc.rxjava2demo.mvp.view.IUserView;
import reactnative.ng.smc.rxjava2demo.network.data.ContentInfo;

/**
 * Created by Administrator on 2017/10/12.
 */

public class IUserPrestener {
    private IUserView iUserView;
    private Handler mHandler = new Handler();

    public IUserPrestener(IUserView iUserView) {
        this.iUserView = iUserView;
    }

    public void getData() {
        iUserView.showLoading();
        iUserView.clearAll();
        MvpNetManager.getInstance().getSecationContentCall2(5055, 0, 20, new Listener<Disposable, ContentInfo>() {
            @Override
            public void onCallBack(Disposable disposable, ContentInfo reply) {
                iUserView.disPosable(disposable);
            }

            @Override
            public void onSuccess(final ContentInfo value) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder s = new StringBuilder();
                        for (int i = 0; i < value.getResults().size(); i++) {
                            s.append(value.getResults().get(i).getName() + "\n");

                        }
                        iUserView.showData(s.toString());
                        iUserView.hideLoading();
                    }
                }, 5000);


            }

            @Override
            public void onFailed(String reply) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserView.showFailedError();
                    }
                });
            }
        });
    }
}
