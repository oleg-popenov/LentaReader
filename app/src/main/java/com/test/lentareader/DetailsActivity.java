package com.test.lentareader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.test.lentareader.presenters.DetailsPresenter;
import com.test.lentareader.presenters.Presentable;
import com.test.lentareader.presenters.viewstate.DetailsViewState;

public class DetailsActivity extends AppCompatActivity implements Presentable<DetailsViewState>{

    private static final String EXTRA_URL = "url";
    RecyclerView detailsList;
    DetailsPresenter presenter;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        url = getIntent().getStringExtra(EXTRA_URL);
        detailsList = findViewById(R.id.details_list);
        detailsList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = (DetailsPresenter) ((ReaderApp)getApplicationContext())
                .bind(this, DetailsViewState.class);
        presenter.loadPage(url);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter = null;
    }


    @Override
    public void render(DetailsViewState viewState) {
        detailsList.setAdapter(viewState.getAdapter());
        if(!TextUtils.isEmpty(viewState.getError())){
            Toast.makeText(this, viewState.getError(), Toast.LENGTH_LONG).show();
        }
    }

    public static void startActivity(Context context, String url){
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }
}
