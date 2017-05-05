package com.deskit.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deskit.R;
import com.deskit.listeners.OnItemClickListener;
import com.deskit.model.ListItem;

import java.util.List;

public class ItemAdapter<T> extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    protected List<T> itemList;

    protected OnItemClickListener itemClickListener;

    public ItemAdapter(List<T> itemList, OnItemClickListener itemClickListener) {
        this.itemList = itemList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(((ListItem)itemList.get(position)).getTitle());
        holder.subTitle.setText(((ListItem)itemList.get(position)).getSubTitle());

        holder.bind((ListItem) itemList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        TextView subTitle;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_text_view);
            subTitle = (TextView) itemView.findViewById(R.id.subtitle_text_view);
        }

        void bind(final ListItem listItem, final OnItemClickListener itemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(listItem);
                }
            });
        }
    }
}
