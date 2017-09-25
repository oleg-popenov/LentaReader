package com.test.lentareader.presenters.viewstate;

import android.support.v7.widget.RecyclerView;

public class NewsListViewState extends ViewState {
    private boolean loading;
    private RecyclerView.Adapter adapter;
    private String error;

    public NewsListViewState(boolean loading, RecyclerView.Adapter adapter, String error) {
        this.loading = loading;
        this.adapter = adapter;
        this.error = error;
    }

    public boolean isLoading() {
        return loading;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public String getError() {
        return error;
    }
}
