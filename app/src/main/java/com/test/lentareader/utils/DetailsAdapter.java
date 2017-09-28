package com.test.lentareader.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.lentareader.R;
import com.test.lentareader.network.models.Page;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.VH> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<VM> models = new ArrayList<>();

    private VM getItem(int position) {
        return models.get(position);
    }

    public void addPage(Page value) {
        models.add(new VM(value.getTitleImage(), value.getTitle()));

        for (Page.Item item : value.getItems()) {
            models.add(new VM(item));
            notifyItemInserted(models.size() - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).isHeader ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        int resId = viewType == TYPE_HEADER ? R.layout.details_header : R.layout.details_item;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(resId, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Context context = holder.view.getContext();
        VM item = getItem(position);
        if (!TextUtils.isEmpty(item.image)) {
            holder.image.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(item.image)
                    .into(holder.image);
        } else {
            holder.image.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.text)) {
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(Utils.fromHtml(item.text));
            holder.text.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            holder.text.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    static class VM {

        public VM(String image, String text) {
            this.isHeader = true;
            this.image = image;
            this.text = text;
        }

        public VM(Page.Item item) {
            this.isHeader = false;
            this.image = item.getImage();
            this.text = item.getText();
        }

        boolean isHeader;
        String image;
        String text;
    }

    static class VH extends RecyclerView.ViewHolder {
        View view;
        ImageView image;
        TextView text;

        public VH(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
        }
    }

}
