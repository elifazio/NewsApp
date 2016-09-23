package br.com.clubedosaplicativos.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.volleyextensions.request.Jackson2Request;

import br.com.clubedosaplicativos.newsapp.Util.VolleyUtil;
import br.com.clubedosaplicativos.newsapp.model.GuardianResponse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lvNews;
    private String url = "https://content.guardianapis.com/search?api-key=426fa373-b826-4fca-8e48-d47514fa6b9b";

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

        Request<GuardianResponse> request = new Jackson2Request<GuardianResponse>(url, GuardianResponse.class, mapper, new Response.Listener<GuardianResponse>() {
            @Override
            public void onResponse(GuardianResponse response) {
                Log.d(TAG, "onResponse: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyUtil.getInstance(this).addToRequestQueue(request);

    }
}
