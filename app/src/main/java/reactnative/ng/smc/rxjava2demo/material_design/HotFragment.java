package reactnative.ng.smc.rxjava2demo.material_design;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.disposables.Disposable;
import reactnative.ng.smc.rxjava2demo.R;

public class HotFragment extends Fragment {

    private Snackbar snackbar;
    private Disposable isDarkSubscription;

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }


}
