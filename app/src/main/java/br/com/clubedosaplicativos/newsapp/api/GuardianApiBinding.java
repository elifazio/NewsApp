package br.com.clubedosaplicativos.newsapp.api;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import br.com.clubedosaplicativos.newsapp.model.GuardianResponse;
import br.com.clubedosaplicativos.newsapp.model.News;

/**
 * Created by elifa on 24/09/2016.
 */
public class GuardianApiBinding {

    private static final String TAG = GuardianApiBinding.class.getSimpleName();
    private String mUrl;

    public GuardianApiBinding(String url) {
        this.mUrl = url;
    }

    private URL createURL(String url) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (Exception e) {
            Log.e(TAG, "There may be a problem with the url provided", e);
        }
        return urlObject;
    }

    private String makeGetHTTPRequest(URL url) {
        String response = "";
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                response = this.readResponseFromStream(inputStream);
            } else {
                Log.e(TAG, "Error conneting with reponse code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro getting the JSON.", e);
        }
        return response;
    }

    private String readResponseFromStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (stream != null) {
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public GuardianResponse fetchNewsArticle() {
        URL url = this.createURL(this.mUrl);
        String json = this.makeGetHTTPRequest(url);

        return this.parseGuardianJsonResponse(json);
    }

    private GuardianResponse parseGuardianJsonResponse(String json) {
        GuardianResponse response = new GuardianResponse();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonResponseObject = jsonObject.getJSONObject("response");
            JSONArray jsonResultsArray = jsonResponseObject.getJSONArray("results");

            this.fillGuardianObject(response, jsonResponseObject);
            this.fillGuardianResultsArray(response, jsonResultsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void fillGuardianObject(GuardianResponse response, JSONObject jsonResponseObject) {
        try {
            response.setCurrentPage(jsonResponseObject.getInt("currentPage"));
            response.setOrderBy(jsonResponseObject.getString("orderBy"));
            response.setPages(jsonResponseObject.getInt("pages"));
            response.setPageSize(jsonResponseObject.getInt("pageSize"));
            response.setStartIndex(jsonResponseObject.getInt("startIndex"));
            response.setTotal(jsonResponseObject.getInt("total"));
            response.setUserTier(jsonResponseObject.getString("userTier"));
            response.setStatus(jsonResponseObject.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillGuardianResultsArray(GuardianResponse response, JSONArray jsonResultsArray) {
        try {
            List<News> newsList = new ArrayList<>();
            for (int i = 0; i < jsonResultsArray.length(); i++) {
                JSONObject jsonResultItem = jsonResultsArray.getJSONObject(i);
                News newsItem = new News();
                newsItem.setApiUrl(jsonResultItem.getString("apiUrl"));
                newsItem.setId(jsonResultItem.getString("id"));
                newsItem.setIsHosted(jsonResultItem.getString("isHosted"));
                newsItem.setSectionId(jsonResultItem.getString("sectionId"));
                newsItem.setSectionName(jsonResultItem.getString("sectionName"));
                newsItem.setWebPublicationDate(jsonResultItem.getString("webPublicationDate"));
                newsItem.setWebTitle(jsonResultItem.getString("webTitle"));
                newsItem.setWebUrl(jsonResultItem.getString("webUrl"));
                newsItem.setType(jsonResultItem.getString("type"));

                JSONObject jsonFieldsObject = jsonResultItem.getJSONObject("fields");
                newsItem.setTrailText(jsonFieldsObject.getString("trailText"));
                newsItem.setShortUrl(jsonFieldsObject.getString("shortUrl"));

                newsList.add(newsItem);
            }
            response.setResults(newsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
