package com.test.lentareader.network;


import com.test.lentareader.network.models.Page;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface DetailsService {

    @GET
    Call<Page> loadTopic(@Url String link);
}
