package com.example.weather.presentation.common;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<View, Router> {
    private View view;
    private Router router;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public abstract void onAttach();

    public abstract void onDetach();

    public void setView(View view) {
        if (view == null) compositeDisposable.clear();
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Router getRouter() {
        return router;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }
}
