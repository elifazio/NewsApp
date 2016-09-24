package br.com.clubedosaplicativos.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.volleyextensions.request.Jackson2Request;

import java.util.ArrayList;

import br.com.clubedosaplicativos.newsapp.adapter.NewsAdapter;
import br.com.clubedosaplicativos.newsapp.model.GoogleFeedResponse;
import br.com.clubedosaplicativos.newsapp.model.News;
import br.com.clubedosaplicativos.newsapp.util.VolleyUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lvNews;
    private String url = "https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=Android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lvNews = (ListView) this.findViewById(R.id.lvNews);

        loadNews();
    }

    private void loadNews() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

        Request<GoogleFeedResponse> request = new Jackson2Request<GoogleFeedResponse>(url, GoogleFeedResponse.class, new Response.Listener<GoogleFeedResponse>() {
            @Override
            public void onResponse(GoogleFeedResponse response) {
                MainActivity.this.createAdapter(response.getResponseData().getEntries());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error loading news: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        VolleyUtil.getInstance(this).addToRequestQueue(request);
    }

    private void createAdapter(ArrayList<News> objects) {
        NewsAdapter adapter = new NewsAdapter(this, objects);

        this.lvNews.setAdapter(adapter);
        this.lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News newsItem = (News) adapterView.getAdapter().getItem(i);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newsItem.getLink()));
                startActivity(intent);
            }
        });
    }
}
