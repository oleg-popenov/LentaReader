package com.test.lentareader.presenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.test.lentareader.R;
import com.test.lentareader.ReaderApp;
import com.test.lentareader.network.RssService;
import com.test.lentareader.network.models.RSS;
import com.test.lentareader.presenters.viewstate.NewsListViewState;
import com.test.lentareader.utils.NewsAdapter;
import com.test.lentareader.utils.XMLDateTransformer;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsListPresenter extends BasePresenter<NewsListViewState> {

    public static final String TAG = NewsListPresenter.class.getName();

    private RssService rssService;
    private Context context;

    public NewsListPresenter(Context context) {
        this.context = context;
        RegistryMatcher rm = new RegistryMatcher();
        rm.bind(Date.class, new XMLDateTransformer());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(rm)))
                .build();
        rssService = retrofit.create(RssService.class);
    }

    @Override
    public void setView(Presentable<NewsListViewState> view) {
        super.setView(view);
        if (state == null) {
            update();
        }
    }

    public void update() {
        NewsListViewState newState = state != null ? state : new NewsListViewState(true, null, null);
        present(newState);
        rssService.loadNews().enqueue(new Callback<RSS>() {
            @Override
            public void onResponse(Call<RSS> call, Response<RSS> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    present(new NewsListViewState(false,
                            new NewsAdapter(response.body().getChannel().itemList), null));
                }
            }

            @Override
            public void onFailure(Call<RSS> call, Throwable t) {
                Log.e(TAG, null, t);
                present(new NewsListViewState(false, null, context.getString(R.string.network_error_msg)));
            }
        });

    }


}
