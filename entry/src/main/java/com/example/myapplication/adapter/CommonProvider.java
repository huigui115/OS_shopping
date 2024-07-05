package com.example.myapplication.adapter;

import ohos.agp.components.BaseItemProvider;
import ohos.agp.components.Component;
import ohos.agp.components.ComponentContainer;
import ohos.app.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * CommonProvider
 */
public abstract class CommonProvider<T> extends BaseItemProvider {
    private final Context context;
    private final int layoutId;
    private List<T> data;

    public CommonProvider(List<T> data, Context context, int layoutId) {
        this.data = data;
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component,
                                  ComponentContainer parent) {
        ViewHolder holder = ViewHolder.getCommentViewHolder(context,
                component, layoutId);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }

    protected abstract void convert(ViewHolder holder, T item, int position);

    public void replace(Collection<T> dataList) {
        if (dataList == null) {
            return;
        }
        data = new ArrayList<>();
        data.addAll(dataList);
        notifyDataChanged();
    }
}
