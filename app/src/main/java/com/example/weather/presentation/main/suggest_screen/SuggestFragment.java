package com.example.weather.presentation.main.suggest_screen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.example.weather.utils.OnCityChangeListener;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class SuggestFragment extends BaseMainFragment implements SuggestView, SuggestAdapter.OnPlaceClickListener {

    @BindView(R.id.suggestRecyclerView)
    RecyclerView suggestRecycler;

    @BindView(R.id.suggestEditText)
    EditText suggestEditText;

    @BindView(R.id.containerEditText)
    FrameLayout editTextContainer;

    @BindView(R.id.suggestProgressBar)
    ProgressBar suggestProgressBar;

    @BindView(R.id.detailsProgressBar)
    ProgressBar detailsProgressBar;

    @Inject
    SuggestPresenter presenter;

    @Inject
    SuggestAdapter adapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        compositeDisposable.add(RxTextView
                .textChanges(suggestEditText)
                .debounce(600, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text ->  {
                    String query = text.toString().trim();
                    if (!query.equals("")) presenter.getSuggestions(query);
                    else hideRecyclerData();
                })
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        adapter.setOnPaceClickListener(null);
    }

    @Override
    public void hideLoad() {
        suggestProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoad() {
        suggestProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDetailsLoad() {
        detailsProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showDetailsLoad() {
        detailsProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hides only RecyclerView content.
     */
    @Override
    public void hideRecyclerData() {
        suggestRecycler.setVisibility(View.GONE);
    }

    /**
     * Shows only RecyclerView content.
     */
    @Override
    public void showRecyclerData() {
        suggestRecycler.setVisibility(View.VISIBLE);
    }

    /**
     * Hides whole container.
     */
    @Override
    public void hideContainerData() {
        suggestRecycler.setVisibility(View.GONE);
        editTextContainer.setVisibility(View.GONE);
    }

    /**
     * Shows whole container.
     */
    @Override
    public void showContainerData() {
        suggestRecycler.setVisibility(View.VISIBLE);
        editTextContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuggestionList(SuggestViewModel suggestViewModel) {
        adapter.setData(suggestViewModel.getPredictions());
    }

    @Override
    public void showError() {
        Toast.makeText(getContext().getApplicationContext(), R.string.suggest_error_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void receivePlaceDetails() {
        if (!(getActivity() instanceof OnCityChangeListener)) {
            throw new IllegalStateException("Activity must implement OnCityChangeListener.");
        }
        ((OnCityChangeListener) getActivity()).cityChanged();
    }

    @OnClick(R.id.iconClear)
    void onClearClick() {
        suggestEditText.setText("");
    }

    @Override
    public void placeClicked(String placeId) {
        presenter.getPlaceDetails(placeId);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        suggestRecycler.setLayoutManager(layoutManager);
        suggestRecycler.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
    }
}
