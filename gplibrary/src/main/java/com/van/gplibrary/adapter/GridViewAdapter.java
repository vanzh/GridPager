package com.van.gplibrary.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.van.gplibrary.weight.GridPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 提供给用户继承实现的Adapter，对应GridPager的数据
 *
 * @param <T> Created by zp on 2017/3/24.
 */

public class GridViewAdapter<T> implements GridPager.IGridView {
    public List<T> mData = new ArrayList<>();
    public int resId;
    public Context mContext;

    public GridViewAdapter(Context context, List<T> data, int resId) {
        mContext = context;
        this.resId = resId;
        mData.addAll(data);
    }

    @Override
    public final int getCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public final T getItem(int position) {
        return null == mData ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public final void replaceAll(List<T> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }
}