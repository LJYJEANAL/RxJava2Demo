package reactnative.ng.smc.rxjava2demo.material_design;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import reactnative.ng.smc.rxjava2demo.R;

/**
 * Created by Administrator on 2017/12/15.
 */

public class PalettFragment extends Fragment {
   private int drawableImag;
    private View view;

    public static PalettFragment newInstance(int drawableImag) {
        PalettFragment fragment = new PalettFragment();
        fragment.drawableImag=drawableImag;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){

            view = inflater.inflate(R.layout.fragment_palett, container, false);
            ImageView viewById = (ImageView)view.findViewById(R.id.img);
            viewById.setImageResource(drawableImag);
        }

        return view;
    }

}
