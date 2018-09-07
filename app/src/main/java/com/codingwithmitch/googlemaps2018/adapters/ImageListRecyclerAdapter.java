package com.codingwithmitch.googlemaps2018.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codingwithmitch.googlemaps2018.R;


import java.util.ArrayList;

public class ImageListRecyclerAdapter extends RecyclerView.Adapter<ImageListRecyclerAdapter.ViewHolder>{

    private ArrayList<Integer> mImages = new ArrayList<>();
    private ImageListRecyclerClickListener mImageListRecyclerClickListener;
    private Context mContext;

    public ImageListRecyclerAdapter(Context context, ArrayList<Integer> images, ImageListRecyclerClickListener imageListRecyclerClickListener) {
        mContext = context;
        mImages = images;
        mImageListRecyclerClickListener = imageListRecyclerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view, mImageListRecyclerClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.cwm_logo)
                .error(R.drawable.cwm_logo);

        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(mImages.get(position))
                .into(((ViewHolder)holder).image);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener
    {
        ImageView image;
        ImageListRecyclerClickListener mClickListener;

        public ViewHolder(View itemView, ImageListRecyclerClickListener clickListener) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            mClickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onImageSelected(getAdapterPosition());
        }
    }

    public interface ImageListRecyclerClickListener{
        void onImageSelected(int position);
    }
}
