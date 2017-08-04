package com.example.weather.presentation.main.suggest_screen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SuggestFragment extends BaseMainFragment implements SuggestView, SuggestAdapter.OnPlaceClickListener {

    @BindView(R.id.suggestRecyclerView)
    RecyclerView suggestRecycler;

    @BindView(R.id.suggestEditText)
    EditText suggestEditText;

    @BindView(R.id.suggestProgressBar)
    ProgressBar progressBar;

    @Inject
    SuggestPresenter presenter;

    @Inject
    SuggestAdapter adapter;

    private Disposable editTextSubscription;

    public static SuggestFragment newInstance() {
        return new SuggestFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_suggest;
    }

    @Nullable
    @Override
    protected String provideToolbarTitle() {
        return getString(R.string.suggest_toolbar_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        adapter.setOnPaceClickListener(this);
        editTextSubscription = RxTextView
                .textChanges(suggestEditText)
                .debounce(600, TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text ->  {
                    String query = text.toString().trim();
                    if (!query.equals("")) presenter.getSuggestions(query, true);
                    else hideData();
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextSubscription.dispose();
        adapter.setOnPaceClickListener(null);
    }

    @Override
    public void hideLoad() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoad() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideData() {
        suggestRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showData() {
        suggestRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionList(SuggestViewModel suggestViewModel) {
        adapter.setData(suggestViewModel.getPredictions());
    }

    @OnClick(R.id.iconClear)
    void onClearClick() {
        suggestEditText.setText("");
    }

    @Override
    public void placeClicked(String placeId) {
        Log.d("SuggestView", placeId);
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity()).getActivityComponent().plusFragmentComponent().inject(this);
    }

    private void initRecyclerView() {
        suggestRecycler.setAdapter(adapter);
        suggestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
