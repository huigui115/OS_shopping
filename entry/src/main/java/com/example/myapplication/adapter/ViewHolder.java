package com.example.myapplication.adapter;

import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.agp.components.LayoutScatter;
import ohos.agp.components.Text;
import ohos.app.Context;

import java.util.HashMap;
import java.util.Map;

public class ViewHolder {
    private final Component convertView;
    private final Map<Integer, Component> views = new HashMap<>();

    private ViewHolder(Component convertView) {
        this.convertView = convertView;
        convertView.setTag(this);
    }

    public Component getConvertView() {
        return convertView;
    }

    public static ViewHolder
    getCommentViewHolder(Context context, Component convertView,
                         int resource) {
        if (convertView == null) {
            return new ViewHolder(LayoutScatter.getInstance(context).parse(resource, null, false));
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    public <T extends Component> T getView(int resId) {
        Component view = views.get(resId);
        if (view == null) {
            view = convertView.findComponentById(resId);
            views.put(resId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(int viewId, String text) {
        Text tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        Image image = getView(viewId);
        image.setPixelMap(resId);
        image.setScaleMode(Image.ScaleMode.STRETCH);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, Component.ClickedListener listener) {
        Component comp = getView(viewId);
        comp.setClickedListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, Component.TouchEventListener listener) {
        Component comp = getView(viewId);
        comp.setTouchEventListener(listener);
        return this;
    }
}
