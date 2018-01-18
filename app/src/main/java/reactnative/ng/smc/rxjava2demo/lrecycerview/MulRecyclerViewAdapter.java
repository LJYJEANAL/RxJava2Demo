package reactnative.ng.smc.rxjava2demo.lrecycerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.mvp.data.SectionContents;
import reactnative.ng.smc.rxjava2demo.util.Public;

/**
 * Created by Administrator on 2017/10/30.
 */

public class MulRecyclerViewAdapter extends RecyclerView.Adapter<MulRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<SectionContents> datas;
    public MulRecyclerViewAdapter(Context context) {
        this.context = context;

    }
    /**
     * 得到的总条数
     * @return
     */
    @Override
    public int getItemCount() {
        if (datas!=null){

            return datas.size();
        }else{
            return 0;
        }
    }
    public void setDatas(List<SectionContents> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * itemType 保存在holder找那个
     * holder根据positiom被缓存在cache中
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 相当于getView方法中创建view和Viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recyclerview, null);
        return new MyViewHolder(itemView);
    }

    /**
     * 相当月getview绑定数据部分
     * 数据和veiw的绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final  MyViewHolder holder, int position) {
        //根据位置得到对应的数据
      final   SectionContents sectionContents = datas.get(position);
        holder.tv_title.setText(sectionContents.getName());
//        Glide.with(context).load(sectionContents.getHorizontalPic()).into(holder.iv_icon);
        Glide.with(context).load(sectionContents.getHorizontalPic()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                holder.iv_icon.setImageBitmap(resource);
                sectionContents.setBitmap(resource);
            }
        });
    }
    public SectionContents getItem(int position){
        try {
            if (datas!=null&&datas.size()>position){
                return datas.get(position);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    public void addData(int position, SectionContents data) {
        datas.add(position, data);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_icon;
        private TextView tv_title;

        public MyViewHolder(final View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_icon.getLayoutParams().height= (int) ((Public.getScreenWidthPixels(context)/1.5)*0.5625);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemView, "点击 " + datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    /**
     * 点击recycleView某条的监听
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, String data);

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }
}
