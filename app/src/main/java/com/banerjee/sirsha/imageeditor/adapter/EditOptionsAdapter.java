package com.banerjee.sirsha.imageeditor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.activity.PhotoActivity;
import com.banerjee.sirsha.imageeditor.model.EditOptionModel;

import java.util.ArrayList;

/**
 * Created by Sirsha Banerjee on 19/1/18.
 */

public class EditOptionsAdapter  extends RecyclerView.Adapter<EditOptionsAdapter.EditOptionViewholder>{

    private ArrayList<EditOptionModel> arrEditOptionsList;
    private PhotoActivity photoActivity;

    public EditOptionsAdapter(ArrayList<EditOptionModel> arrEditOptionsList, PhotoActivity photoActivity){
        this.arrEditOptionsList = arrEditOptionsList;
        this.photoActivity = photoActivity;
    }

    @Override
    public EditOptionViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        final EditOptionViewholder basketViewHolder;
        final View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_edit_option_thumbnail, parent, false);
        basketViewHolder = new EditOptionViewholder(row);
        return basketViewHolder;
    }

    @Override
    public void onBindViewHolder(EditOptionViewholder holder, final int position) {
        holder.tvEditOptionName.setText(arrEditOptionsList.get(position).getEditOptionName());
        holder.ivEditOptionIcon.setImageResource(arrEditOptionsList.get(position).getEditOptionImageResourceID());
        holder.llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoActivity.onEditOptionClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrEditOptionsList.size();
    }

    public class EditOptionViewholder extends RecyclerView.ViewHolder{

        private ImageView ivEditOptionIcon;
        private TextView tvEditOptionName;
        private LinearLayout llParent;

        public EditOptionViewholder(View itemView) {
            super(itemView);
            ivEditOptionIcon = itemView.findViewById(R.id.row_edit_option_thumbnail_ivEditOptionIcon);
            tvEditOptionName = itemView.findViewById(R.id.row_edit_option_thumbnail_tvEditOptionName);
            llParent = itemView.findViewById(R.id.row_edit_option_thumbnail_llParent);
        }
    }

    public interface EditOptionClick{
        public void onEditOptionClick(int position);
    }
}
