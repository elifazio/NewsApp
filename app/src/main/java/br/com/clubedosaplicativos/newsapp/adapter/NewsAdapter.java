package br.com.clubedosaplicativos.newsapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.clubedosaplicativos.newsapp.R;
import br.com.clubedosaplicativos.newsapp.model.News;

/**
 * Created by elifa on 23/09/2016.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    private final int mResource;

    public NewsAdapter(Context context) {
        super(context, R.layout.news_list_item);
        this.mResource = R.layout.news_list_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(this.mResource, null);
        }

        TextView listItemTitle = (TextView) convertView.findViewById(R.id.list_item_title);
        TextView listItemContent = (TextView) convertView.findViewById(R.id.list_item_content);
        TextView listItemLink = (TextView) convertView.findViewById(R.id.list_item_link);

        News newsItem = this.getItem(position);

        listItemTitle.setText(Html.fromHtml(newsItem.getWebTitle()));
        listItemContent.setText(Html.fromHtml(newsItem.getTrailText()));
        listItemLink.setText(newsItem.getShortUrl());

        return convertView;
    }
}
