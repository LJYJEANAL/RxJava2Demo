package reactnative.ng.smc.rxjava2demo.lrecycerview;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.why168.LoopViewPagerLayout;
import com.github.why168.listener.OnBannerItemClickListener;
import com.github.why168.listener.OnLoadImageViewListener;
import com.github.why168.modle.BannerInfo;
import com.github.why168.modle.IndicatorLocation;
import com.github.why168.modle.LoopStyle;

import java.util.ArrayList;
import java.util.List;

import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.base.BaseActivity;
import reactnative.ng.smc.rxjava2demo.material_design.AppbarDetailActivity;
import reactnative.ng.smc.rxjava2demo.material_design.ShareImgActivity;
import reactnative.ng.smc.rxjava2demo.mvp.data.SectionContents;
import reactnative.ng.smc.rxjava2demo.mvp.data.SectionInfo;
import reactnative.ng.smc.rxjava2demo.mvp.model.Listener;
import reactnative.ng.smc.rxjava2demo.mvp.model.MvpNetManager;
import reactnative.ng.smc.rxjava2demo.util.Public;

public class MulActivity extends BaseActivity {

    private LoopViewPagerLayout mLoopViewPagerLayout;
    private LRecyclerView mRecyclerView;
    private List<SectionInfo> sectionInfoList;
    /**
     * 去除banner的
     */
    private List<SectionInfo> sectionInfoList1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private MulRecyclerViewAdapter mulRadapter;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(MulActivity.this, R.layout.activity_mul, null);
        super.setContentView(view);
        initUI();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main_refresh, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.menu_refresh) {
            mRecyclerView.refresh();
        }
        return true;
    }

    private void initUI() {
        getTitleView().setText("首页");
        getTitleView().setTextSize(20);
        Toolbar toolbar = getToobar();
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_color));
      final   View rootView=getContentView();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Animator animation = ViewAnimationUtils.createCircularReveal(rootView,
                                rootView.getWidth()/2,
                                rootView.getHeight(),
                                (float) Math.hypot(rootView.getWidth(), rootView.getHeight()),
                                0);
                        animation.setInterpolator(new AccelerateDecelerateInterpolator());
                        animation.setDuration(1500).start();
//                        finish();
                    }},0);
            }
        });

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);
//设置头部加载颜色
        mRecyclerView.setHeaderViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
//设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.colorAccent, R.color.dark, android.R.color.white);
//设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mRecyclerView.setArrowImageView(R.drawable.ic_loading_rotate);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.LineScalePulseOutRapid);

//        GridLayoutManager manager = new GridLayoutManager(MulActivity.this, 2);
//        mRecyclerView.setLayoutManager(manager);

//        setLayoutManager
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //防止item位置互换
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(layoutManager);

        mulRadapter = new MulRecyclerViewAdapter(MulActivity.this);

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mulRadapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.setLoadMoreEnabled(true);
        DividerDecoration divider = new DividerDecoration.Builder(MulActivity.this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.colorwith)
                .build();
//根据需要选择使用GridItemDecoration还是SpacesItemDecoration
        GridItemDecoration Gridivider = new GridItemDecoration.Builder(MulActivity.this)
                .setHorizontal(R.dimen.dp_4)
                .setVertical(R.dimen.default_divider_padding)
                .setColorResource(R.color.colorwith)
                .build();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(Gridivider);

        mLoopViewPagerLayout = (LoopViewPagerLayout) LayoutInflater.from(this).inflate(R.layout.layout_banner_header, (ViewGroup) findViewById(android.R.id.content), false);
        mLoopViewPagerLayout.getLayoutParams().height = (int) (Public.getScreenWidthPixels(this) * 0.5625);
        mLoopViewPagerLayout.setLoop_ms(2000);//轮播的速度(毫秒)
        mLoopViewPagerLayout.setLoop_duration(800);//滑动的速率(毫秒)
        mLoopViewPagerLayout.setLoop_style(LoopStyle.Depth);//轮播的样式-默认empty
        mLoopViewPagerLayout.setIndicatorLocation(IndicatorLocation.Center);//设置小数点位置
//        mLoopViewPagerLayout.setNormalBackground(R.drawable.indicator_normal_background);//默认指示器颜色
        mLoopViewPagerLayout.setSelectedBackground(R.drawable.indicator_selected_background);//选中指示器颜色
        mLoopViewPagerLayout.initializeData(this);//初始化数据
        mLRecyclerViewAdapter.addHeaderView(mLoopViewPagerLayout);
        View footView = LayoutInflater.from(this).inflate(R.layout.sample_footer, null);
//        mLRecyclerViewAdapter.addFooterView(footView);
        mLoopViewPagerLayout.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }

            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                Log.i("信息", "onLoadImageView：" + ((SectionContents) parameter).getHorizontalPic());
                Glide.with(MulActivity.this)
                        .load(((SectionContents) parameter).getHorizontalPic())
                        .into(imageView);
            }
        });
        mLoopViewPagerLayout.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                Toast.makeText(MulActivity.this, "点击事件:" + ((SectionContents) banner.get(index).data).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //点击事件
        mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;
                if (position%2==0){
                    intent = new Intent(MulActivity.this, ShareImgActivity.class);
                }else{
                    intent = new Intent(MulActivity.this, AppbarDetailActivity.class);
                }

                Bitmap bitmap = mulRadapter.getItem(position).getBitmap();
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("bitmap", bitmap);
                intent.putExtras(mBundle);
                intent.putExtra("sharedTv", mulRadapter.getItem(position).getName());
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MaterialViewActivity.this,
//                       Pair.create(View ,String ) );
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MulActivity.this,
                        view, "sharedImage");
                startActivity(intent, options.toBundle());


            }
        });
        //长按事件
        mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MulActivity.this, "长按事件:" + mulRadapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                requestData(10);
            }
        });

        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("信息", "onRefresh");
                requestData(3);
            }
        });
        //刷新 需要先设置刷新的监听才能设置
        mRecyclerView.refresh();
        //强制刷新
//        mRecyclerView.forceToRefresh();
        /**
         * mRecyclerView滑动
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    /**
     * 服务器端一共多少条数据
     */
    private int TOTAL_COUNTER_SIZE;//如果服务器没有返回总数据或者总页数，这里设置为最大值比如10000，什么时候没有数据了根据接口返回判断
    /**
     * 每一页展示多少条数据
     */
    private static final int REQUEST_COUNT = 10;


    private void requestData(final int limit) {
        new Thread() {

            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.arg1 = limit;
                message.what = -1;
                mHandler.sendMessage(message);
//                mHandler.sendEmptyMessage(-1);
                //模拟一下网络请求失败的情况
//                if(NetworkUtils.isNetAvailable(MulActivity.this)) {
//                    Message message= mHandler.obtainMessage();
//                    message.arg1=limit;
//                    message.what=-1;
//                    mHandler.sendMessage(message);
//
////                    mHandler.sendEmptyMessage(-1);
//                } else {
//                    mHandler.sendEmptyMessage(-3);
//                }
            }
        }.start();
    }

    //    private PreviewHandler mHandler = new PreviewHandler(this);
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case -1:
                    Log.e("信息", "handleMessage:");
                    int limit = msg.arg1;
                    getNetDatas(limit);
                    break;
                case -3:
                    mRecyclerView.refreshComplete(REQUEST_COUNT);
                    mLRecyclerViewAdapter.notifyDataSetChanged();
                    mRecyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                        @Override
                        public void reload() {
                            requestData(msg.arg1);
                        }
                    });

                    break;
                default:
                    break;
            }
        }
    };


    private void getNetDatas(int limit) {
        final ArrayList<BannerInfo> bannerData = new ArrayList<>();
        MvpNetManager.getInstance()._getRecommendCall(5042, 0, limit, new Listener<Boolean, List<SectionInfo>>() {
            @Override
            public void onCallBack(Boolean aBoolean, List<SectionInfo> reply) {
                if (aBoolean) {
                    sectionInfoList = reply;
                    //初始化banner数据
                    if (sectionInfoList != null) {
                        if (sectionInfoList.get(0) != null) {
                            List<SectionContents> bannerList = sectionInfoList.get(0).getSectionContents();
                            if (bannerList != null) {

                                for (SectionContents bannerInfo : bannerList) {
                                    bannerData.add(new BannerInfo(bannerInfo, bannerInfo.getName()));
                                }
                            }
                        }
                        sectionInfoList1 = sectionInfoList;
                        sectionInfoList1.remove(0);

                        List<SectionContents> datas = new ArrayList<SectionContents>();
                        if (sectionInfoList1.size() > 0) {
                            for (int i = 0; i < sectionInfoList1.size(); i++) {
                                for (int j = 0; j < sectionInfoList1.get(i).getSectionContents().size(); j++) {
                                    datas.add(sectionInfoList1.get(i).getSectionContents().get(j));
                                }
                            }

                        }
                        Log.e("信息", "TOTAL_COUNTER_SIZE:" + TOTAL_COUNTER_SIZE);
                        if (TOTAL_COUNTER_SIZE == datas.size()) {
                            mRecyclerView.setNoMore(true);//没有更多数据时候设置不可上拉
                        } else {
                            TOTAL_COUNTER_SIZE = datas.size();
                        }
                        mLoopViewPagerLayout.setLoopData(bannerData);
                        mulRadapter.setDatas(datas);
                        mRecyclerView.refreshComplete(REQUEST_COUNT);
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailed(String reply) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoopViewPagerLayout.startLoop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLoopViewPagerLayout.stopLoop();
    }
}
