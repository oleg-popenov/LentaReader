package com.test.lentareader.presenters;

import com.test.lentareader.network.DetailsService;
import com.test.lentareader.network.models.Page;
import com.test.lentareader.presenters.viewstate.DetailsViewState;
import com.test.lentareader.utils.DetailsAdapter;
import com.test.lentareader.utils.PageConverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsPresenter extends BasePresenter<DetailsViewState> {

    private final DetailsService detailsService;

    public DetailsPresenter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lenta.ru")
                .addConverterFactory(PageConverter.FACTORY)
                .build();
        detailsService = retrofit.create(DetailsService.class);
    }

    @Override
    public void setView(Presentable<DetailsViewState> view) {
        super.setView(view);
    }

    public void loadPage(String url){
        detailsService.loadTopic(url).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    present(new DetailsViewState(new DetailsAdapter(response.body()), null));
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                present(new DetailsViewState(null, "Network error"));
            }
        });
    }

}
