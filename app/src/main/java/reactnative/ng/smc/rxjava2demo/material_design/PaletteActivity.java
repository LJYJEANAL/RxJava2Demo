package reactnative.ng.smc.rxjava2demo.material_design;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import reactnative.ng.smc.rxjava2demo.R;

/**
 * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页

 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
 请求个数： 数字，大于0
 第几页：数字，大于0
 例：
 http://gank.io/api/data/Android/10/1
 http://gank.io/api/data/福利/10/1
 http://gank.io/api/data/iOS/20/2
 http://gank.io/api/data/all/20/2
 每日数据： http://gank.io/api/day/年/月/日

 例：
 http://gank.io/api/day/2015/08/06

 */

public class PaletteActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private Toolbar toolbar;
    private int[] colors;
    private View background;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setEnterTransition(explode);
        getWindow().setExitTransition(explode);
        setContentView(R.layout.activity_palette);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        background = findViewById(R.id.background);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        tablayout = (TabLayout) findViewById(R.id.tablayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(PaletteActivity.this);
            }
        });
        initData();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void initData() {

        List<Fragment> list_fragment = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        /**
         * 设置title
         */
        titles.add("首页");
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("优璇");


        /**
         * 设置fragment
         */
        colors = new int[]{R.color.md_red, R.color.accent_color, R.color.md_purple, R.color.md_green, R.color.md_lime, R.color.md_pink};
        final int[] drawableImag = new int[]{R.drawable.header, R.drawable.anim1, R.drawable.anim2, R.drawable.anim3, R.drawable.anim4, R.drawable.anim5, R.drawable.anim6};
        for (int i = 0; i < titles.size(); i++) {
            list_fragment.add(PalettFragment.newInstance(drawableImag[i]));

        }

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), list_fragment, titles);
        viewpager.setAdapter(fragmentPagerAdapter);

        tablayout.setupWithViewPager(viewpager);//必须设置 否侧tablayout无法显示出来
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                colorChange(drawableImag[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("信息", "onScrollChange： " + scrollX + "   -->:" + scrollY);
            }
        });
        colorChange(drawableImag[0]);
    }

    /**
     * 界面颜色的更改
     */
    private Palette.Swatch s, s1, s2, s3, s4, s5, s6;

    @SuppressLint("NewApi")
    private void colorChange(int imageId) {

        window = getWindow();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                s1 = palette.getVibrantSwatch();

                s2 = palette.getDarkVibrantSwatch();

                s3 = palette.getLightVibrantSwatch();

                s4 = palette.getMutedSwatch();

                s5 = palette.getDarkMutedSwatch();

                s6 = palette.getLightMutedSwatch();
                tablayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorwith));
//                    tablayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.colorwith)));
                tablayout.setTabTextColors(getResources().getColor(R.color.dark), getResources().getColor(R.color.colorwith));

                tablayout.setBackgroundColor(s4.getRgb());
                toolbar.setBackgroundColor(s4.getRgb());
                background.setBackgroundColor(s4.getRgb());
                window.setStatusBarColor(s4.getRgb());
                window.setNavigationBarColor(s4.getRgb());

                if (s1 != null) {

//                    tablayout.setSelectedTabIndicatorColor(s1.getRgb());

                    s1.getPopulation();

                }

                if (s2 != null) {

//                    tablayout.setTabTextColors(ColorStateList.valueOf(s2.getRgb()));

                }

                if (s3 != null) {

//                    tablayout.setBackgroundColor(s3.getRgb());

                }

                if (s4 != null) {

                }

               /* if (s5 != null) {

                    window.setStatusBarColor(s5.getRgb());

                }

                if (s6 != null) {

                    window.setNavigationBarColor(s6.getRgb());

                }*/
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.s1:
                    if (s1 != null) {
                        s = s1;
                    }

                    break;
                case R.id.s2:
                    if (s2 != null) {
                        s = s2;
                    }

                    break;
                case R.id.s3:
                    if (s3 != null) {
                        s = s3;
                    }


                    break;
                case R.id.s4:
                    if (s4 != null) {
                        s = s4;
                    }


                    break;
                case R.id.s5:
                    if (s5 != null) {
                        s = s5;
                    }


                    break;
                case R.id.s6:
                    if (s6 != null) {
                        s = s6;
                    }

                    break;
            }
            if (s!=null){
                tablayout.setBackgroundColor(s.getRgb());
                toolbar.setBackgroundColor(s.getRgb());
                background.setBackgroundColor(s.getRgb());
                window.setStatusBarColor(s.getRgb());
                window.setNavigationBarColor(s.getRgb());
            }

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change_color, menu);
        return true;
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
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

