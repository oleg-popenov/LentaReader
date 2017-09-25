package com.test.lentareader.network;

import com.test.lentareader.network.models.RSS;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssService {

    @GET("/rss/news")
    Call<RSS> loadNews();
}
