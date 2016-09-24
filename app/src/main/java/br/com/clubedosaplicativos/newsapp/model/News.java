package br.com.clubedosaplicativos.newsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by elifa on 22/09/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class News {
    private String title;
    private String contentSnippet;
    private String link;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentSnippet() {
        return contentSnippet;
    }

    public void setContentSnippet(String contentSnippet) {
        this.contentSnippet = contentSnippet;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
