package com.hupo.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hupo.bean.ListviewButton;
import com.hupo.followmind.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SideslipMenuAdapter extends ArrayAdapter<ListviewButton> {
    private int resource;
    public SideslipMenuAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListviewButton listviewButton = getItem(position);
        View view;
        //listview优化--缓存
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }else{
            view = convertView;
        }
        ImageView item_img = (ImageView) view.findViewById(R.id.item_img);
        TextView item_name = (TextView) view.findViewById(R.id.item_name);
        ImageView arrow = (ImageView) view.findViewById(R.id.arrow);
        item_img.setImageResource(listviewButton.getItem_image());
        item_name.setText(listviewButton.getItem_name());
        arrow.setImageResource(listviewButton.getArrows());
        return view;
    }
}
