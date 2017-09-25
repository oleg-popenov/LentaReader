package com.test.lentareader.presenters.viewstate;

import android.support.v7.widget.RecyclerView;

public class DetailsViewState extends ViewState {
    private RecyclerView.Adapter adapter;
    private String error;

    public DetailsViewState(RecyclerView.Adapter adapter, String error) {
        this.adapter = adapter;
        this.error = error;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public String getError() {
        return error;
    }
}
