package com.test.lentareader.utils;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.lentareader.DetailsActivity;
import com.test.lentareader.R;
import com.test.lentareader.network.models.RSS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {
    private Map<String, Set<String>> linksByCategory = new HashMap<>();
    public static final String TIME_FORMAT = "hh:mm";

    public NewsAdapter(List<RSS.Channel.Item> list) {
        this.list = list;

        for (RSS.Channel.Item item : this.list) {
            Set<String> links = linksByCategory.get(item.getCategory());
            if (links == null) {
                links = new HashSet<>();
                linksByCategory.put(item.getCategory(), links);
            }
            links.add(item.getLink());
        }
    }

    private List<RSS.Channel.Item> list = new ArrayList<>();
    private static SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final RSS.Channel.Item item = list.get(position);
        holder.category.setText(item.getCategory());
        holder.time.setText(timeFormat.format(item.getPubDate()));
        holder.title.setText(item.getTitle());
        CharSequence day = DateUtils.getRelativeTimeSpanString(
                item.getPubDate().getTime(),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS);
        holder.day.setText(day);
        final ArrayList<String> urls = new ArrayList<>();
        Set<String> otherInCategory = linksByCategory.get(item.getCategory());
        urls.add(item.getLink());
        otherInCategory.remove(item.getLink());
        urls.addAll(otherInCategory);
        otherInCategory.add(item.getLink());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsActivity.startActivity(view.getContext(), item.getCategory(), urls);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        View view;
        TextView time;
        TextView day;
        TextView category;
        TextView title;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            time = itemView.findViewById(R.id.time);
            day = itemView.findViewById(R.id.day);
            category = itemView.findViewById(R.id.category);
            title = itemView.findViewById(R.id.title);
        }
    }
}
