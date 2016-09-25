package br.com.clubedosaplicativos.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.clubedosaplicativos.newsapp.adapter.NewsAdapter;
import br.com.clubedosaplicativos.newsapp.model.GuardianResponse;
import br.com.clubedosaplicativos.newsapp.model.News;
import br.com.clubedosaplicativos.newsapp.task.GuardianNewsAsyncTaskLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<GuardianResponse> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String API_KEY = "5c601e9d-2a56-4082-a2c8-068363f5fdc3";
    private static final String SEARCH_STRING = "android";
    private static final String SHOW_FIELDS = "trailText,shortUrl";
    private static final int NEWS_LOADER_ID = 1;
    private String BASE_URL = "https://content.guardianapis.com/search";
    private ListView lvNews;
    private TextView tvNoResults;
    private NewsAdapter mNewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.tvNoResults = (TextView) findViewById(R.id.tvNoResults);
        this.lvNews = (ListView) this.findViewById(R.id.lvNews);
        this.lvNews.setEmptyView(this.tvNoResults);
        this.mNewsAdapter = new NewsAdapter(this);
        this.lvNews.setAdapter(this.mNewsAdapter);
        this.lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News newsItem = (News) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newsItem.getWebUrl()));
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
    }

    private void loadNews(List<News> newsList) {
        this.mNewsAdapter.clear();
        if (newsList != null) this.mNewsAdapter.addAll(newsList);
    }

    @Override
    public Loader<GuardianResponse> onCreateLoader(int id, Bundle args) {
        Uri baseUrl = Uri.parse(BASE_URL);
        Uri.Builder urlBuilder = baseUrl.buildUpon();

        urlBuilder.appendQueryParameter("api-key", API_KEY);
        urlBuilder.appendQueryParameter("show-fields", SHOW_FIELDS);
        urlBuilder.appendQueryParameter("q", SEARCH_STRING);

        return new GuardianNewsAsyncTaskLoader(this, urlBuilder.build().toString());
    }

    @Override
    public void onLoadFinished(Loader<GuardianResponse> loader, GuardianResponse data) {
        this.loadNews(data.getResults());
    }

    @Override
    public void onLoaderReset(Loader<GuardianResponse> loader) {
        this.mNewsAdapter.clear();
    }
}
