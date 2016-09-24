package br.com.clubedosaplicativos.newsapp.model;

import java.util.ArrayList;

/**
 * Created by elifa on 24/09/2016.
 */
public class GoogleFeedResponseData {
    private String query;
    private ArrayList<News> entries;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public ArrayList<News> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<News> entries) {
        this.entries = entries;
    }
}
