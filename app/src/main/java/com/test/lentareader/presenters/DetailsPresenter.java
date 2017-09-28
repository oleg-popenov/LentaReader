package com.test.lentareader.presenters;

import android.os.AsyncTask;
import android.util.Log;

import com.test.lentareader.network.DetailsService;
import com.test.lentareader.network.models.Page;
import com.test.lentareader.presenters.viewstate.DetailsViewState;
import com.test.lentareader.utils.DetailsAdapter;
import com.test.lentareader.utils.PageConverter;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailsPresenter extends BasePresenter<DetailsViewState> {

    private final DetailsService detailsService;
    public static final String TAG = DetailsPresenter.class.getName();

    public DetailsPresenter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(PageConverter.FACTORY)
                .build();
        detailsService = retrofit.create(DetailsService.class);
    }

    @Override
    public void setView(Presentable<DetailsViewState> view) {
        super.setView(view);
    }

    public void loadPage(final List<String> urls) {
        final DetailsAdapter adapter = new DetailsAdapter();
        present(new DetailsViewState(adapter, null));

        new AsyncTask<Void, Page, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (String url : urls) {
                    try {
                        Response<Page> response = detailsService.loadTopic(url).execute();
                        if (response.isSuccessful() && response.code() == 200) {
                            publishProgress(response.body());
                        } else {
                            Log.e(TAG, "Failed to load: " + url);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Page... values) {
                super.onProgressUpdate(values);
                adapter.addPage(values[0]);
            }
        }.execute();
    }

}
