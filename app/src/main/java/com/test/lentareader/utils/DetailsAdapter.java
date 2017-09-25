package com.test.lentareader.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.lentareader.R;
import com.test.lentareader.network.models.Page;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.VH> {

    private static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

    private Page page;

    public DetailsAdapter(Page page) {
        this.page = page;


    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
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
        if(position == 0) {
            bindHeader(holder);
        } else {
            holder.image.setVisibility(View.GONE);
            String text = page.getItems().get(position - 1).getText();
            holder.text.setText(Utils.fromHtml(text));
            holder.text.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void bindHeader(VH holder) {
        Context context = holder.view.getContext();
        if(!TextUtils.isEmpty(page.getTitleImage())){
            Picasso
                    .with(context)
                    .load(page.getTitleImage())
                    .into(holder.image);
        }

        holder.text.setText(page.getTitle());
    }

    @Override
    public int getItemCount() {
        return page.getItems().size()+1;
    }

    static class VH extends RecyclerView.ViewHolder{
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
