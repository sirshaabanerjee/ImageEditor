package com.banerjee.sirsha.imageeditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.activity.FilterActivity;
import com.banerjee.sirsha.imageeditor.model.FilterThumbnailModel;

import java.util.ArrayList;

/**
 * Created by Sirsha Banerjee on 24/1/18.
 */

public class FilterThumbnailAdapter extends RecyclerView.Adapter<FilterThumbnailAdapter.FilterThumbnailViewHolder>{

    private ArrayList<FilterThumbnailModel> arrFilterThumbnailList;
    private FilterActivity effectsActivity;

    public FilterThumbnailAdapter(ArrayList<FilterThumbnailModel> arrFilterThumbnailList, FilterActivity effectsActivity) {
        this.arrFilterThumbnailList = arrFilterThumbnailList;
        this.effectsActivity = effectsActivity;
    }

    @Override
    public FilterThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final FilterThumbnailViewHolder filterThumbnailViewHolder;
        final View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_thumnail, parent, false);
        filterThumbnailViewHolder = new FilterThumbnailViewHolder(row);
        return filterThumbnailViewHolder;
    }

    @Override
    public void onBindViewHolder(FilterThumbnailViewHolder holder, final int position) {
        holder.tvFilterThumbnailName.setText(arrFilterThumbnailList.get(position).getFilterName());
        holder.ivFilterThumbnail.setImageBitmap(arrFilterThumbnailList.get(position).getFilterBitmap());
        holder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effectsActivity.onFilterOptionCLick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrFilterThumbnailList.size();
    }

    public class FilterThumbnailViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFilterThumbnailName;
        private ImageView ivFilterThumbnail;
        private LinearLayout llParent;

        public FilterThumbnailViewHolder(View itemView) {
            super(itemView);
            tvFilterThumbnailName = itemView.findViewById(R.id.row_filter_thumbnail_tvFilterName);
            ivFilterThumbnail = itemView.findViewById(R.id.row_filter_thumbnail_ivFilter);
            llParent = itemView.findViewById(R.id.row_filter_thumbnail_llParent);
        }
    }

    public interface FilterThumbnailClick {
        void onFilterOptionCLick(int position);
    }
}
