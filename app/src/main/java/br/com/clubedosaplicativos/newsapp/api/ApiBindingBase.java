package br.com.clubedosaplicativos.newsapp.api;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by elifa on 27/09/2016.
 */
public class ApiBindingBase {

    private static final String TAG = ApiBindingBase.class.getSimpleName() ;

    protected URL createURL(String url) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (Exception e) {
            Log.e(TAG, "There may be a problem with the url provided", e);
        }
        return urlObject;
    }

    protected String makeGetHTTPRequest(URL url) {
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
}
