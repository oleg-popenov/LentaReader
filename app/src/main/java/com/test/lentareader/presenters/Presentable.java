package com.test.lentareader.presenters;

import com.test.lentareader.presenters.viewstate.ViewState;

public interface Presentable<T extends ViewState>{
    public void render(T viewState);
}
