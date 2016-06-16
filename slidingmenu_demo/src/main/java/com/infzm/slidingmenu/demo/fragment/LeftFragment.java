package com.infzm.slidingmenu.demo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.infzm.slidingmenu.demo.MainActivity;
import com.infzm.slidingmenu.demo.R;

/**
 * @date 2016/4/11
 * @author yangnana
 * @description 侧边栏菜单
 */
public class LeftFragment extends Fragment implements OnClickListener {

    private View world;
    private View sociologyView;
    private View technologyView;
    private View sportsView;
    private View appleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_menu, null);
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        world = view.findViewById(R.id.tv_world);
        sociologyView = view.findViewById(R.id.tv_sociology);
        technologyView = view.findViewById(R.id.tv_technology);
        sportsView = view.findViewById(R.id.tv_sports);
        appleView = view.findViewById(R.id.tv_apple);

        world.setOnClickListener(this);
        sociologyView.setOnClickListener(this);
        technologyView.setOnClickListener(this);
        sportsView.setOnClickListener(this);
        appleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title = null;
        int index = 0;
        switch (v.getId()) {
            case R.id.tv_world: // 国际
                title = getString(R.string.international);
                index = 0;
                break;
            case R.id.tv_sociology:// 社会
                title = getString(R.string.sociology);
                index = 1;
                break;
            case R.id.tv_technology: // 科技
                title = getString(R.string.technology);
                index = 2;
                break;
            case R.id.tv_sports: // 体育
                title = getString(R.string.sports);
                index = 3;
                break;
            case R.id.tv_apple: // 苹果
                title = getString(R.string.apple);
                index = 4;
                break;

            default:
                break;
        }

        MainActivity fca = (MainActivity) getActivity();
        fca.setContentFragment(index, title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
