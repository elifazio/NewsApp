package br.com.clubedosaplicativos.newsapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import br.com.clubedosaplicativos.newsapp.api.GuardianApiBinding;
import br.com.clubedosaplicativos.newsapp.model.GuardianResponse;

/**
 * Created by elifa on 24/09/2016.
 */
public class GuardianNewsAsyncTaskLoader extends AsyncTaskLoader<GuardianResponse> {

    private String mUrl;

    public GuardianNewsAsyncTaskLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public GuardianResponse loadInBackground() {
        GuardianApiBinding guardianApiBinding = new GuardianApiBinding(this.mUrl);
        return guardianApiBinding.fetchNewsArticle();
    }
}
