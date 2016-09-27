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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(this.mResource, null);
            holder = new ViewHolder();

            holder.listItemTitle = (TextView) convertView.findViewById(R.id.list_item_title);
            holder.listItemContent = (TextView) convertView.findViewById(R.id.list_item_content);
            holder.listItemLink = (TextView) convertView.findViewById(R.id.list_item_link);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        News newsItem = this.getItem(position);

        holder.listItemTitle.setText(Html.fromHtml(newsItem.getWebTitle()));
        holder.listItemContent.setText(Html.fromHtml(newsItem.getTrailText()));
        holder.listItemLink.setText(newsItem.getShortUrl());

        return convertView;
    }

    static class ViewHolder {
        TextView listItemTitle;
        TextView listItemContent;
        TextView listItemLink;
    }
}
