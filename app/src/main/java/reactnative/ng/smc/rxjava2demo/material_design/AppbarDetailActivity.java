package reactnative.ng.smc.rxjava2demo.material_design;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import reactnative.ng.smc.rxjava2demo.BaseActivity;
import reactnative.ng.smc.rxjava2demo.R;
import reactnative.ng.smc.rxjava2demo.util.Public;

/**
 * 设置状态栏透明 需在CollapsingToolbarLayout设置折叠时工具栏布局的颜色以及把折叠时状态栏的颜色设置成透明
 * app:statusBarScrim="@android:color/transparent"
 * 同时在背景处设置 android:fitsSystemWindows="true"
 */
public class AppbarDetailActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabLayout sliding_tabs;
    private ViewPager viewpager;
    private ImageView ivImage;
    private ActionBar actionBar;

    private float totalHeight;      //总高度
    private float toolBarHeight;    //toolBar高度
    private float offSetHeight;     //总高度 -  toolBar高度  布局位移值
    private float llHeight;         //搜索框高度

    private float llHeightOffScale;     //高度差比值
    private float llOffDistance;        //距离差
    private float llOffDistanceScale;   //距离差比值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbar_detail);

     // 经测试在代码里直接声明透明状态栏更有效 可是顶部需要预留出状态栏的高度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else {
                WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        }

        initView();
        initData();

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        _offSetChanged();
        sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        Bitmap bitmap = getIntent().getParcelableExtra("bitmap");
        ivImage.setImageBitmap(bitmap);
        Log.i("信息",getIntent().getStringExtra("sharedTv"));
        toolbar.setTitle("");
        toolbar.setTitleTextAppearance(this, R.style.Toolbar_TitleText);
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        mCollapsingToolbarLayout.setTitle(getIntent().getStringExtra("sharedTv"));



        //去掉标题
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        // 给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAfterTransition(AppbarDetailActivity.this);
            }
        });

        ViewCompat.setTransitionName(ivImage, "sharedImage");

    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_samples, menu);
        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search view example…");
        return true;
    }*/
    /**
     * 监听滑动
     */
    private void _offSetChanged() {
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        Public.getStatusBarHeight(AppbarDetailActivity.this);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    totalHeight = appBarLayout.getMeasuredHeight();
                    toolBarHeight = toolbar.getMeasuredHeight();
                    offSetHeight = totalHeight - toolBarHeight - Public.getStatusBarHeight(AppbarDetailActivity.this);
                    Log.e("信息", "状态栏高度：" + Public.getStatusBarHeight(AppbarDetailActivity.this) +
                            "   totalHeight:" + totalHeight + "  toolBarHeight:" + toolBarHeight + "--offSetHeight:" + offSetHeight);

                }
                Log.i("信息", ((-verticalOffset) > offSetHeight / 3)+" ---"+(((-verticalOffset) > offSetHeight / 3) && ((-verticalOffset) <= (offSetHeight * 2) / 3))+" sdsd");
                if ((-verticalOffset) <= offSetHeight  / 3) {
                    //显示
                    Log.e("信息","显示");
                            actionBar.setDisplayHomeAsUpEnabled(true);
                } else {
                    Log.e("信息","隐藏");
                    //隐藏
                    actionBar.setDisplayHomeAsUpEnabled(false);
//                    if (toolbar.getNavigationIcon() != null) {
//                        toolbar.getNavigationIcon().setAlpha((int) toolbar.getAlpha());
//                    }

                }
                Log.i("信息", "ap:" + "onOffsetChanged:" + verticalOffset);

            }
        });
    }

    private void initData() {
        List<Fragment> list_fragment = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        /**
         * 设置title
         */
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视频");
        titles.add("健康");
        titles.add("励志");
        titles.add("图文");
        titles.add("本地");
        titles.add("动漫");
        titles.add("搞笑");
        titles.add("精选");

        /**
         * 设置fragment
         */

        for (int i = 0; i < titles.size(); i++) {
            list_fragment.add( HotFragment.newInstance());
        }
//        tab.addTab(tab.newTab().setText(list_title.get(0)));
//        tab.addTab(tab.newTab().setText(list_title.get(1)));
//        tab.addTab(tab.newTab().setText(list_title.get(2)));
//        tab.addTab(tab.newTab().setText(list_title.get(3)));

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), list_fragment, titles);
        viewpager.setAdapter(fragmentPagerAdapter);

        sliding_tabs.setupWithViewPager(viewpager);//必须设置 否侧tablayout无法显示出来
    }


    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> list;
        private List<String> list_title;

        public FragmentAdapter(FragmentManager supportFragmentManager, List<Fragment> list_fragment, List<String> list_title) {
            super(supportFragmentManager);
            this.list = list_fragment;
            this.list_title = list_title;
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("信息","getItem:"+list.size());
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }
    }
}
