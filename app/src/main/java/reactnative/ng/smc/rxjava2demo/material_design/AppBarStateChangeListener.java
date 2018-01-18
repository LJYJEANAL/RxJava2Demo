package reactnative.ng.smc.rxjava2demo.material_design;

import android.support.design.widget.AppBarLayout;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/13.
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {
    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.e("信息","onOffsetChanged："+i);
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED,i);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED,i);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE,i);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state,int i);
}
/*
使用
*  appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state,int i) {
                Log.e("信息",Math.abs(i)+"--》高度："+appBarLayout.getTotalScrollRange());
                if( state == State.EXPANDED ) {//展开状态
                    Log.i("信息","展开状态"+i);
//                    toolbar.setNavigationIcon(R.drawable.bt_title_back_selector);

                }else if(state == State.COLLAPSED){  //折叠状态
                    Log.i("信息","折叠状态"+i);
                    if (actionBar!=null){
                        actionBar.setDisplayHomeAsUpEnabled(false);
                    }


//                    toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
                }else { //中间状态
                    Log.i("信息","中间状态"+i);
                    if (actionBar!=null){
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }

                    if (Math.abs(i)>= appBarLayout.getTotalScrollRange()){
                    }
//                    toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

                }
            }
        });*/


