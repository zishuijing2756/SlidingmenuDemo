package com.infzm.slidingmenu.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.infzm.slidingmenu.demo.fragment.AppleFragment;
import com.infzm.slidingmenu.demo.fragment.LeftFragment;
import com.infzm.slidingmenu.demo.fragment.SociologyFragment;
import com.infzm.slidingmenu.demo.fragment.SportsFragment;
import com.infzm.slidingmenu.demo.fragment.TechnologyFragment;
import com.infzm.slidingmenu.demo.fragment.WorldFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @date 2016.4.11
 * @author yangnana
 * @description 主界面
 */
public class MainActivity extends SlidingFragmentActivity implements OnClickListener {

    private FragmentManager     fragmentManager;
    private FragmentTransaction transaction;
    private ImageView           topButton;
    private TextView            topTextView;

    private WorldFragment       worldFragment;
    private SociologyFragment   sociologyFragment;
    private TechnologyFragment  technologyFragment;
    private SportsFragment      sportsFragment;
    private AppleFragment       appFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_main);

        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);
        topTextView = (TextView) findViewById(R.id.topTv);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        initSlidingMenu(savedInstanceState);
        setContentFragment(0, getString(R.string.international));
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        /** 设置左侧滑动菜单 */
        setBehindContentView(R.layout.menu_frame_left);
        transaction.replace(R.id.menu_frame, new LeftFragment()).commit();

        /** 实例化滑动菜单对象 */
        SlidingMenu sm = getSlidingMenu();
        /** 设置可以左右滑动的菜单 */
        sm.setMode(SlidingMenu.LEFT);
        /** 设置滑动阴影的宽度 */
        sm.setShadowWidthRes(R.dimen.shadow_width);
        /** 设置滑动菜单阴影的图像资源 */
        sm.setShadowDrawable(null);
        /** 设置滑动菜单视图的宽度 */
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        /** 设置渐入渐出效果的值 */
        sm.setFadeDegree(0.35f);
        /** 设置触摸屏幕的模式,这里设置为全屏 */
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        /** 设置下方视图的在滚动时的缩放比例 */
        sm.setBehindScrollScale(0.0f);

    }

    /**
     * 初始化内容
     * 
     * @param index
     * @param title
     */
    public void setContentFragment(int index, String title) {
        /** 开启一个Fragment事务 */
        transaction = fragmentManager.beginTransaction();

        /** 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况 */
        hideFragments(transaction);

        switch (index) {
            case 0:
                if (worldFragment == null) {
                    /** 如果MessageFragment为空，则创建一个并添加到界面上 */
                    worldFragment = new WorldFragment();
                    transaction.add(R.id.content_frame, worldFragment);
                } else {
                    /** 如果MessageFragment不为空，则直接将它显示出来 */
                    transaction.show(worldFragment);
                }
                break;
            case 1:
                if (sociologyFragment == null) {
                    sociologyFragment = new SociologyFragment();
                    transaction.add(R.id.content_frame, sociologyFragment);
                } else {
                    transaction.show(sociologyFragment);
                }
                break;
            case 2:
                if (technologyFragment == null) {
                    technologyFragment = new TechnologyFragment();
                    transaction.add(R.id.content_frame, technologyFragment);
                } else {
                    transaction.show(technologyFragment);
                }
                break;
            case 3:
                if (sportsFragment == null) {
                    sportsFragment = new SportsFragment();
                    transaction.add(R.id.content_frame, sportsFragment);
                } else {
                    transaction.show(sportsFragment);
                }
                break;
            case 4:
                if (appFragment == null) {
                    appFragment = new AppleFragment();
                    transaction.add(R.id.content_frame, appFragment);
                } else {
                    transaction.show(appFragment);
                }
                break;
        }

        transaction.commitAllowingStateLoss();
        getSlidingMenu().showContent();
        topTextView.setText(title);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     * 
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (worldFragment != null) {
            transaction.hide(worldFragment);
        }
        if (sociologyFragment != null) {
            transaction.hide(sociologyFragment);
        }
        if (technologyFragment != null) {
            transaction.hide(technologyFragment);
        }
        if (sportsFragment != null) {
            transaction.hide(sportsFragment);
        }
        if (appFragment != null) {
            transaction.hide(appFragment);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topButton:
                toggle();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
