package reactnative.ng.smc.rxjava2demo.lrecycerview;

import android.content.Context;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.mvp.data.SectionContents;

/**
 * Created by Administrator on 2017/10/31.
 */

public class DataAdapter extends ListBaseAdapter<SectionContents> {
    public DataAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_recyclerview;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {

    }
}
