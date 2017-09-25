package com.test.lentareader;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.test.lentareader.network.models.RSS;
import com.test.lentareader.presenters.NewsListPresenter;
import com.test.lentareader.presenters.Presentable;
import com.test.lentareader.presenters.viewstate.NewsListViewState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements Presentable<NewsListViewState>,
                                        SwipeRefreshLayout.OnRefreshListener {
    RecyclerView newsList;
    SwipeRefreshLayout swipeLayout;
    NewsListPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsList = findViewById(R.id.news_list);
        newsList.setLayoutManager(new LinearLayoutManager(this));
        swipeLayout = findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = (NewsListPresenter) ((ReaderApp)getApplicationContext())
                .bind(this, NewsListViewState.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter = null;
    }

    @Override
    public void render(NewsListViewState viewState) {
        swipeLayout.setRefreshing(viewState.isLoading());
        newsList.setAdapter(viewState.getAdapter());
        if(!TextUtils.isEmpty(viewState.getError())){
            Toast.makeText(this, viewState.getError(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        if(presenter != null){
            presenter.update();
        }
    }
}
