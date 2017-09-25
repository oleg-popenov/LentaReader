package com.test.lentareader;

import android.app.Application;

import com.test.lentareader.network.RssService;
import com.test.lentareader.presenters.BasePresenter;
import com.test.lentareader.presenters.DetailsPresenter;
import com.test.lentareader.presenters.NewsListPresenter;
import com.test.lentareader.presenters.Presentable;
import com.test.lentareader.presenters.viewstate.DetailsViewState;
import com.test.lentareader.presenters.viewstate.NewsListViewState;
import com.test.lentareader.presenters.viewstate.ViewState;
import com.test.lentareader.utils.XMLDateTransformer;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ReaderApp extends Application {


    private NewsListPresenter newsListPresenter;
    private DetailsPresenter detailsPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        newsListPresenter = new NewsListPresenter();
        detailsPresenter = new DetailsPresenter();
    }

    public <T extends ViewState> BasePresenter bind(Presentable<T> view, Class<T> clazz) {
        if(NewsListViewState.class.equals(clazz)){
            newsListPresenter.setView((Presentable<NewsListViewState>) view);
            return newsListPresenter;
        } else if(DetailsViewState.class.equals(clazz)){
            detailsPresenter.setView((Presentable<DetailsViewState>) view);
            return detailsPresenter;
        } else {
            throw new IllegalArgumentException("Incorrect ViewState");
        }
    }
}
