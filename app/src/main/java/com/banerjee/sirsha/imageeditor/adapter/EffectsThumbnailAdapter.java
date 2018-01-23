package com.banerjee.sirsha.imageeditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.activity.EffectsActivity;
import com.banerjee.sirsha.imageeditor.model.FilterThumbnailModel;

import java.util.ArrayList;

/**
 * Created by Sirsha Banerjee on 22/1/18.
 */

public class EffectsThumbnailAdapter extends RecyclerView.Adapter<EffectsThumbnailAdapter.EffectsThumbnailViewHolder> {

    private ArrayList<FilterThumbnailModel> arrEffectsThumbnailList;
    private EffectsActivity effectsActivity;

    public EffectsThumbnailAdapter(ArrayList<FilterThumbnailModel> arrEffectsThumbnailList, EffectsActivity effectsActivity) {
        this.arrEffectsThumbnailList = arrEffectsThumbnailList;
        this.effectsActivity = effectsActivity;
    }

    @Override
    public EffectsThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final EffectsThumbnailViewHolder effectsThumbnailViewHolder;
        final View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_thumnail, parent, false);
        effectsThumbnailViewHolder = new EffectsThumbnailViewHolder(row);
        return effectsThumbnailViewHolder;
    }

    @Override
    public void onBindViewHolder(EffectsThumbnailViewHolder holder, final int position) {
        holder.tvFilterThumbnailName.setText(arrEffectsThumbnailList.get(position).getFilterName());
        holder.ivFilterThumbnail.setImageBitmap(arrEffectsThumbnailList.get(position).getFilterBitmap());
        holder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effectsActivity.onEffectOptionCLick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrEffectsThumbnailList.size();
    }

    public class EffectsThumbnailViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFilterThumbnailName;
        private ImageView ivFilterThumbnail;
        private LinearLayout llParent;

        public EffectsThumbnailViewHolder(View itemView) {
            super(itemView);
            tvFilterThumbnailName = itemView.findViewById(R.id.row_filter_thumbnail_tvFilterName);
            ivFilterThumbnail = itemView.findViewById(R.id.row_filter_thumbnail_ivFilter);
            llParent = itemView.findViewById(R.id.row_filter_thumbnail_llParent);
        }
    }

    public interface EffectsThumbnailClick {
        void onEffectOptionCLick(int position);
    }
}
