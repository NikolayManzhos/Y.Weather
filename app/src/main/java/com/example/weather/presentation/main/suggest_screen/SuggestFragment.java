package com.example.weather.presentation.main.suggest_screen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SuggestFragment extends BaseMainFragment implements SuggestView {

    @BindView(R.id.suggestRecyclerView)
    RecyclerView suggestRecycler;

    @BindView(R.id.suggestEditText)
    EditText suggestEditText;

    @Inject
    SuggestPresenter presenter;

    @Inject
    SuggestAdapter adapter;

    private Disposable editTextSubsription;

    public static SuggestFragment newInstance() {
        return new SuggestFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_suggest;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
        editTextSubsription = RxTextView
                .textChanges(suggestEditText)
                .debounce(600, TimeUnit.MILLISECONDS)
                .skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text ->  {
                    String query = text.toString().trim();
                    if (!query.equals("")) presenter.getSuggestions(query, true);
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        editTextSubsription.dispose();
    }

    @Override
    public void hideLoad() {}

    @Override
    public void showLoad() {}

    @Override
    public void showSuggestionList(SuggestViewModel suggestViewModel) {
        Log.d("SuggestView", String.valueOf(adapter.getItemCount()));
        suggestRecycler.setAdapter(adapter);
        suggestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setData(suggestViewModel.getPredictions());
        Log.d("SuggestView", String.valueOf(adapter.getItemCount()));
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
