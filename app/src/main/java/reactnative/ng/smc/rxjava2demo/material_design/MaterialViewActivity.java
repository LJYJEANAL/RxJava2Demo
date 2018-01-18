package reactnative.ng.smc.rxjava2demo.material_design;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.aesthetic.Aesthetic;
import com.afollestad.aesthetic.BottomNavBgMode;
import com.afollestad.aesthetic.BottomNavIconTextMode;
import com.afollestad.aesthetic.TabLayoutIndicatorMode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;

import reactnative.ng.smc.rxjava2demo.BaseActivity;
import reactnative.ng.smc.rxjava2demo.R;

public class MaterialViewActivity extends BaseActivity {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_view);

       /* // 经测试在代码里直接声明透明状态栏更有效 可是顶部需要预留出状态栏的高度
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
        }*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawable);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        // 在Toolbar上添加侧滑菜单切换按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.post(new Runnable() {
            @Override
            public void run() {
                Animator animation = ViewAnimationUtils.createCircularReveal(drawer,
                     0,
                        drawer.getHeight()/2,
                        0,
                        (float) Math.hypot(drawer.getWidth(), drawer.getHeight()));
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                animation.setDuration(1500).start();
            }
        });

//       final AppBarLayout appbar = (AppBarLayout) findViewById(R.id.appbar);
//        appbar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Animator animation = ViewAnimationUtils.createCircularReveal(appbar,
//                        appbar.getWidth()/2,
//                        appbar.getHeight()/2,
//                        0,
//                        (float) Math.hypot(appbar.getWidth(), appbar.getHeight()));
//                animation.setInterpolator(new AccelerateDecelerateInterpolator());
//                animation.setDuration(1500).start();
//            }
//        },0);
        /**
         * toolbar返回箭头的显示
         */
// 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
  /*      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使左上角图标是否显示，如果设成false，则没有程序图标，仅仅就个标题，否则，显示应用程序图标，对应id为 android.R.id.home，对应ActionBar.DISPLAY_SHOW_HOME
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);*/
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("Material");
        /**
         * 代码设置Title字体大小
         */
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.Toolbar_TitleText);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Toolbar_TitleText);
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        mCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.LEFT | Gravity.BOTTOM);////设置展开后标题的位置


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_camera:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.text_color_primary)
                                .colorAccentRes(R.color.text_color_primary)
                                .colorStatusBarAuto()
                                .colorNavigationBarRes(R.color.md_purple)
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                                .apply();
                        break;
                    case R.id.nav_gallery:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.md_red)
                                .colorAccentRes(R.color.md_amber)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
                                .colorNavigationBarRes(R.color.md_purple)
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                                .apply();
                        break;
                    case R.id.nav_slideshow:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.md_purple)
                                .colorAccentRes(R.color.md_purple)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
                                .textColorPrimaryRes(android.R.color.black)
                                .textColorPrimaryInverseRes(android.R.color.white)
                                .textColorSecondaryRes(R.color.md_blue)
                                .textColorSecondaryInverseRes(R.color.md_pink)
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                                .apply();
                        break;
                    case R.id.nav_manage:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.md_blue)
                                .colorAccentRes(R.color.md_pink)
                                .textColorPrimaryRes(android.R.color.black)
                                .textColorPrimaryInverseRes(android.R.color.white)
                                .textColorSecondaryRes(R.color.md_green)
                                .textColorSecondaryInverseRes(R.color.md_purple)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                                .apply();
                        break;
                    case R.id.nav_camera2:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.md_green)
                                .colorAccentRes(R.color.blue)
                                .textColorPrimaryRes(android.R.color.black)//  android:textColor="?android:textColorPrimary"
                                .textColorPrimaryInverseRes(android.R.color.white)
                                .textColorSecondaryRes(R.color.md_purple)
                                .textColorSecondaryInverseRes(R.color.md_pink)
                                .colorIconTitleActiveRes(R.color.violet)
                                .colorIconTitleInactiveRes(R.color.indicatorSelectColor)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
//                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY_DARK)
//                                .bottomNavigationIconTextMode(BottomNavIconTextMode.BLACK_WHITE_AUTO)
                                .apply();
                        break;
                    case R.id.nav_gallery2:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.md_pink)
                                .colorAccentRes(R.color.md_pink)
                                .textColorPrimaryRes(R.color.md_purple)
                                .textColorPrimaryInverseRes(android.R.color.white)
                                .textColorSecondaryRes(R.color.blue)
                                .textColorSecondaryInverseRes(R.color.md_green)
                                .colorIconTitleActiveRes(R.color.indicatorSelectColor)
                                .colorIconTitleInactiveRes(R.color.violet)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
//                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
//                                .bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                                .apply();
                        break;
                    case R.id.nav_share:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.colorwith)
                                .colorPrimaryRes(R.color.colorPrimarys)
                                .colorAccentRes(R.color.indicatorSelectColor)
                                .textColorPrimaryRes(R.color.colorAccents)//字体设置 android:textColor="?android:textColorPrimary"生效
                                .textColorPrimaryInverseRes(R.color.md_amber)
                                .textColorSecondaryRes(R.color.navigationBarColor)//设置字体颜色
                                .textColorSecondaryInverseRes(R.color.md_purple)
                                .tabLayoutIndicatorMode(
                                        TabLayoutIndicatorMode.ACCENT)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
//                                .navigationViewMode(NavigationViewMode.SELECTED_PRIMARY)
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                                .apply();
                        break;
                    case R.id.nav_send:
                        Aesthetic.get()
                                .colorWindowBackgroundRes(R.color.navigationBarColor)//View Backgrounds
                                .colorPrimaryRes(R.color.md_purple)
                                .colorAccentRes(R.color.indicatorSelectColor)
                                .textColorPrimaryRes(R.color.md_white)//字体设置 android:textColor="?android:textColorPrimary"生效
                                .textColorPrimaryInverseRes(R.color.md_e)
                                .textColorSecondaryRes(R.color.md_white)//设置字体颜色
                                .textColorSecondaryInverseRes(R.color.md_white)
                                .tabLayoutIndicatorMode(
                                        TabLayoutIndicatorMode.PRIMARY)
                                .colorStatusBarAuto()
                                .colorNavigationBarAuto()
//                                .navigationViewMode(NavigationViewMode.SELECTED_ACCENT)
                                .bottomNavigationBackgroundMode(BottomNavBgMode.PRIMARY)
                                .bottomNavigationIconTextMode(BottomNavIconTextMode.SELECTED_ACCENT)
                                .apply();
                        break;
                    case R.id.nav_palet:
                        startActivity(new Intent(MaterialViewActivity.this, PaletteActivity.class),
                                ActivityOptions.makeSceneTransitionAnimation(MaterialViewActivity.this)
                                        .toBundle());
                        break;
                }


                return true;
            }
        });
        initNetScrollView();

    }



    private Bitmap bitmap;

    private void initNetScrollView() {
        View scroll_layout = findViewById(R.id.scroll_layout);
        LinearLayout info_layout = (LinearLayout) scroll_layout.findViewById(R.id.info_layout);

        final ImageView img = (ImageView) scroll_layout.findViewById(R.id.img);
        final TextView related_info = (TextView) scroll_layout.findViewById(R.id.related_info);

        final CardView find_CardView = (CardView) scroll_layout.findViewById(R.id.find_CardView);
        final ImageView find_img = (ImageView) scroll_layout.findViewById(R.id.find_img);
        final TextView find_tv = (TextView) scroll_layout.findViewById(R.id.find_tv);
        final TextView find_info_tv = (TextView) scroll_layout.findViewById(R.id.find_info_tv);

        Glide.with(this).load("http://imgu.3gtv.net:9090/_file/section/20170905164716960.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                find_img.setImageBitmap(resource);
                bitmap = resource;
            }
        });
        find_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialViewActivity.this, ShareElementsActivity.class);

//                Bundle mBundle = new Bundle();
//
//                mBundle.putParcelable("resId", bitmap);
//                intent.putExtras(mBundle);
                ByteArrayOutputStream baos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte [] bitmapByte =baos.toByteArray();
                intent.putExtra("flag",1);
                intent.putExtra("bitmap", bitmapByte);
                intent.putExtra("related_info", find_info_tv.getText().toString());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MaterialViewActivity.this,
                        find_CardView, "shareName");
                startActivity(intent, options.toBundle());
            }
        });


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaterialViewActivity.this, ShareElementsActivity.class);
                intent.putExtra("resId", R.drawable.header);
                intent.putExtra("flag",0);
                intent.putExtra("related_info", related_info.getText().toString());
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MaterialViewActivity.this,
                        img, "shareName");
                startActivity(intent, options.toBundle());
            }
        });
        info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaterialViewActivity.this, TransitionActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(MaterialViewActivity.this)
                                .toBundle());
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar   snackbar = Snackbar.make(v, "helloworld", Snackbar.LENGTH_LONG);
                snackbar.setAction(
                        android.R.string.cancel,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //no-op
                            }
                        });
                snackbar.show();
            }
        });
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            String msg = "";
            switch (item.getItemId()) {
                case R.id.id_settings:
                    msg += "Click edit";
                    break;
//                case R.id.action_share:
//                    msg += "Click share";
//                    break;
//                case R.id.action_settings:
//                    msg += "Click setting";
//                    break;
//                case R.id.action_delete:
//                    msg += "Click delete";
//                    break;
            }

            if (!msg.equals("")) {
                Toast.makeText(MaterialViewActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_samples, menu);
        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search view example…");
        return true;
    }
}
