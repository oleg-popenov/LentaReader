package com.test.lentareader.presenters;

import com.test.lentareader.presenters.viewstate.ViewState;

public class BasePresenter<T extends ViewState> {

    T state = null;

    private Presentable<T> view;

    Presentable<T> getView() {
        return view;
    };
    public void setView(Presentable<T> view){
        this.view = view;
        if(state != null){
            present(state);
        }
    }

    void present(T state){
        getView().render(state);
        this.state = state;
    }
}
