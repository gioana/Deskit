package com.deskit.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deskit.R;
import com.deskit.listeners.OnItemClickListener;
import com.deskit.model.ListItem;
import com.deskit.model.Resource;

import java.util.List;

public class ResourceAdapter extends ItemAdapter {

    public ResourceAdapter(List<Resource> itemList, OnItemClickListener itemClickListener) {
        super(itemList, itemClickListener);
    }

    @Override
    public ResourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_list_resource_item, parent, false);

        return new ResourceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResourceViewHolder resourceViewHolder = (ResourceViewHolder) holder;

        Resource resource = (Resource) itemList.get(position);
        resourceViewHolder.title.setText(resource.getTitle());
        resourceViewHolder.professorTextView.setText(resource.getProfessor());
        resourceViewHolder.detailsTextView.setText(resource.getDetails());
        resourceViewHolder.authorTextView.setText(resource.getAuthor());

        holder.bind((ListItem) itemList.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private static class ResourceViewHolder extends ViewHolder {
        TextView professorTextView;
        TextView detailsTextView;
        TextView authorTextView;
        TextView shareTextView;
        TextView saveOfflineTextView;

        ResourceViewHolder(View itemView) {
            super(itemView);

            professorTextView = (TextView) itemView.findViewById(R.id.professor_text_view);
            detailsTextView = (TextView) itemView.findViewById(R.id.details_text_view);
            authorTextView = (TextView) itemView.findViewById(R.id.author_text_view);
            shareTextView = (TextView) itemView.findViewById(R.id.share_text_view);
            saveOfflineTextView = (TextView) itemView.findViewById(R.id.save_offline_text_view);
        }
    }
}
