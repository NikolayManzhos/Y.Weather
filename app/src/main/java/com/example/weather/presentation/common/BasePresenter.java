package com.example.weather.presentation.common;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<View, Router> {
    private View view;
    private Router router;
    private CompositeDisposable compositeDisposable;

    public abstract void onAttach();

    public abstract void onDetach();

    public void setView(View view) {
        this.view = view;
        if (view != null) compositeDisposable = new CompositeDisposable();
        else compositeDisposable.clear();
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
