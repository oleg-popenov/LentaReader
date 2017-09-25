package com.test.lentareader.utils;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.lentareader.R;
import com.test.lentareader.network.models.RSS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    public NewsAdapter(List<RSS.Channel.Item> list) {
        this.list = list;
    }

    private List<RSS.Channel.Item> list = new ArrayList<>();
    static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");

    public void setList(List<RSS.Channel.Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        RSS.Channel.Item item = list.get(position);
        holder.category.setText(item.getCategory());
        holder.time.setText(timeFormat.format(item.getPubDate()));
        holder.title.setText(item.getTitle());
        CharSequence day = DateUtils.getRelativeTimeSpanString(
                item.getPubDate().getTime(),
                System.currentTimeMillis(),
                DateUtils.DAY_IN_MILLIS);
        holder.day.setText(day);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder{
        TextView time;
        TextView day;
        TextView category;
        TextView title;

        public VH(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            day = itemView.findViewById(R.id.day);
            category = itemView.findViewById(R.id.category);
            title = itemView.findViewById(R.id.title);
        }
    }
}
