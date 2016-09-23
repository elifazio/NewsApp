package br.com.clubedosaplicativos.newsapp.adapter;

import android.content.Context;
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

    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
        this.mResource = R.layout.news_list_item;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(this.mResource, null);
        }

        TextView listitemlink = (TextView) convertView.findViewById(R.id.list_item_link);
        TextView listitempublishdate = (TextView) convertView.findViewById(R.id.list_item_publish_date);
        TextView listitemsectionName = (TextView) convertView.findViewById(R.id.list_item_sectionName);
        TextView listitemtitle = (TextView) convertView.findViewById(R.id.list_item_title);

        News newsItem = this.getItem(position);

        listitemlink.setText(newsItem.getWebUrl());
        listitempublishdate.setText(newsItem.getWebPublicationDate());
        listitemsectionName.setText(newsItem.getSectionName());
        listitemtitle.setText(newsItem.getTitle());

        return convertView;
    }
}
