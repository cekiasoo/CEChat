package com.ce.cechat.ui.dynamic;

import android.view.View;

import com.ce.cechat.R;
import com.ce.cechat.app.BaseFragment;

/**
 * @author CE Chen
 *
 * 动态
 *
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends BaseFragment {


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment SettingFragment.
     */
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }
}
