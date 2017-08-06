package com.example.weather.presentation.main.detail_screen;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;

import javax.inject.Inject;

/**
 * Created on 8/6/2017.
 */

public class DetailFragment extends BaseMainFragment {

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Inject
    DetailPresenter presenter;

    @Override
    protected int provideLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity()).getActivityComponent().plusFragmentComponent().inject(this);
    }
}
