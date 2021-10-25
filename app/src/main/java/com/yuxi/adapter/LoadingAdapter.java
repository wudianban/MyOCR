package com.yuxi.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.dylanc.loadinghelper.LoadingHelper;
import com.yuxi.myocr.R;

/**
 * 玉犀科技
 *
 * @author Li
 * 创建日期：2021/10/25 17:00
 * 描述：LoadingAdapter
 */
public class LoadingAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

    @NonNull
    @Override
    public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_loading_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

    }
}
