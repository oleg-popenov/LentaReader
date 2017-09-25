package com.test.lentareader.presenters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.lentareader.network.RssService;
import com.test.lentareader.network.models.RSS;
import com.test.lentareader.presenters.viewstate.NewsListViewState;
import com.test.lentareader.utils.NewsAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsListPresenter extends BasePresenter<NewsListViewState> {

    public NewsListPresenter(RssService rssService) {
        this.rssService = rssService;
    }

    private RssService rssService;

    @Override
    public void setView(Presentable<NewsListViewState> view) {
        super.setView(view);
        if(state == null){
            update();
        }
    }

    public void update(){
        NewsListViewState newState = state != null ? state : new NewsListViewState(true,null,null);
        present(newState);
        rssService.loadNews().enqueue(new Callback<RSS>() {
                @Override
                public void onResponse(Call<RSS> call, Response<RSS> response) {
                    if ( response.isSuccessful() && response.code() == 200){
                        present(new NewsListViewState(false,
                                new NewsAdapter(response.body().getChannel().itemList), null));
                    }
                }

                @Override
                public void onFailure(Call<RSS> call, Throwable t) {
                    Log.e("RETROFIT", "ERRROR", t);
                    present(new NewsListViewState(false, null, "Network error"));
                }
            });

    }




}
