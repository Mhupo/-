package com.hupo.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hupo.followmind.CustomListView;

import com.hupo.followmind.FoodsActivity;
import com.hupo.followmind.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class DepotListAdapter extends BaseAdapter {
    private List<String> stringList;
    private Context context;
    private LayoutInflater layoutInflater;
    private CustomListView depotListView;
    private onDeleteListener deleteListener;
    private onUpdateListener updateListener;


    public interface onDeleteListener{
        public void onDelete(int position);
    }
    public interface onUpdateListener{
        public void onUpdate(int position);
    }

    public DepotListAdapter(Context context, CustomListView depotListView,  List<String> stringList, onDeleteListener deleteListener, onUpdateListener updateListener) {
        this.context = context;
        this.depotListView = depotListView;
        this.stringList = stringList;
        this.layoutInflater = LayoutInflater.from(context);
        this.deleteListener = deleteListener;
        this.updateListener = updateListener;
    }


    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int i) {
        return stringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null){
            view = layoutInflater.inflate(R.layout.depot_item,null);
            viewHolder.itemName = view.findViewById(R.id.item_name);
            viewHolder.deleteButton = view.findViewById(R.id.deleteButton);
            viewHolder.updateButton = view.findViewById(R.id.updateButton);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        String itemName = stringList.get(i);
        viewHolder.itemName.setText(itemName);
        viewHolder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListener.onUpdate(i);
                depotListView.slideBack();
            }
        });
        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteListener.onDelete(i);
                depotListView.slideBack();
            }
        });

        return view;
    }
    //性能优化
    public static class ViewHolder{
        public TextView itemName;
        public RelativeLayout deleteButton;
        public RelativeLayout updateButton;
    }
}
