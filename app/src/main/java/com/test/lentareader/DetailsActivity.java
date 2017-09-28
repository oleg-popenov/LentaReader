package com.test.lentareader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.test.lentareader.presenters.DetailsPresenter;
import com.test.lentareader.presenters.Presentable;
import com.test.lentareader.presenters.viewstate.DetailsViewState;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements Presentable<DetailsViewState> {

    private static final String EXTRA_TITLE = "title";
    private static final String EXTRA_URL = "urls";
    RecyclerView detailsList;
    DetailsPresenter presenter;
    ArrayList<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        urls = getIntent().getStringArrayListExtra(EXTRA_URL);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        toolbar.setTitle(title.toUpperCase());
        detailsList = findViewById(R.id.details_list);
        detailsList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = (DetailsPresenter) ((ReaderApp) getApplicationContext())
                .bind(this, DetailsViewState.class);
        presenter.loadPage(urls);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter = null;
    }


    @Override
    public void render(DetailsViewState viewState) {
        detailsList.setAdapter(viewState.getAdapter());
        if (!TextUtils.isEmpty(viewState.getError())) {
            Toast.makeText(this, viewState.getError(), Toast.LENGTH_LONG).show();
        }
    }

    public static void startActivity(Context context, String title, ArrayList<String> urls) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putStringArrayListExtra(EXTRA_URL, urls);
        context.startActivity(intent);
    }
}
