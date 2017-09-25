package com.test.lentareader;

import android.app.Application;
import android.util.Log;

import com.test.lentareader.network.RssService;
import com.test.lentareader.network.models.RSS;
import com.test.lentareader.presenters.BasePresenter;
import com.test.lentareader.presenters.NewsListPresenter;
import com.test.lentareader.presenters.Presentable;
import com.test.lentareader.presenters.viewstate.NewsListViewState;
import com.test.lentareader.presenters.viewstate.ViewState;
import com.test.lentareader.utils.XMLDateTransformer;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ReaderApp extends Application {

    Retrofit retrofit;
    RssService rssService;
    NewsListPresenter newsListPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        RegistryMatcher rm = new RegistryMatcher();
        rm.bind(Date.class, new XMLDateTransformer());
        retrofit = new Retrofit.Builder()
                .baseUrl("https://lenta.ru")
                .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(rm)))
                .build();
        rssService = retrofit.create(RssService.class);

        newsListPresenter = new NewsListPresenter(rssService);
    }

    public <T extends ViewState> BasePresenter bind(Presentable<T> view, Class<T> clazz) {
        if(NewsListViewState.class.equals(clazz)){
            newsListPresenter.setView((Presentable<NewsListViewState>) view);
            return newsListPresenter;
        } else {
            throw new IllegalArgumentException("Incorrect ViewState");
        }
    }

//    public void bindView(Object o){
//        if(o instanceof NewsActivity){
//            final NewsActivity newsActivity = (NewsActivity) o;
//            rssService.loadNews().enqueue(new Callback<RSS>() {
//                @Override
//                public void onResponse(Call<RSS> call, Response<RSS> response) {
//                    if ( response.isSuccessful() && response.code() == 200){
//                        newsActivity.setNewsList(response.body().getChannel().itemList);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<RSS> call, Throwable t) {
//                    Log.e("RETROFIT", "ERRROR", t);
//                }
//            });
//
//        }
//
//    }
}
