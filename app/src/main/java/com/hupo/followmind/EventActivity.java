package com.hupo.followmind;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hupo.bean.Events;
import com.hupo.bean.Foods;
import com.hupo.tools.DepotListAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class EventActivity extends AppCompatActivity implements DepotListAdapter.onDeleteListener,DepotListAdapter.onUpdateListener{
    CustomListView item_events;
    List<String> list_events = new ArrayList<>();
    ImageButton addButton;
    View alert_view;
    DepotListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        item_events = (CustomListView) findViewById(R.id.list_depot);
        //alert_view = View.inflate(FoodsActivity.this,R.layout.content_alert,null);

        //数据库操作&初始化LiteView
        LitePal.getDatabase();
        List<Events> eventsAll = DataSupport.findAll(Events.class);
        for(Events events : eventsAll){
            list_events.add(events.getEvent_text());
        }

        item_events.initSlideMode(CustomListView.MOD_LEFT);
        adapter = new DepotListAdapter(EventActivity.this, item_events, list_events,this,this);
        item_events.setAdapter(adapter);

        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FoodsActivity.this, "ok", Toast.LENGTH_SHORT).show();
                alert_view = LayoutInflater.from(EventActivity.this).inflate(R.layout.content_alert,null);
                AlertDialog.Builder alert = new AlertDialog.Builder(EventActivity.this);
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                        String addText = editText.getText().toString();
                        if(!addText.equals("") && !list_events.contains(addText)){
                            Events newEvent = new Events();
                            newEvent.setEvent_text(addText);
                            newEvent.save();
                            list_events.add(addText);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(EventActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EventActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setView(alert_view).show();
            }
        });
    }

    @Override
    public void onDelete(int position) {
        list_events.remove(position);
        DataSupport.delete(Events.class,position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(final int position) {
        //positions = position;
        alert_view = LayoutInflater.from(EventActivity.this).inflate(R.layout.content_alert,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(EventActivity.this);
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                String updateText = editText.getText().toString();
                if(!updateText.equals("") && !list_events.contains(updateText)){
                    Events newevents = new Events();
                    newevents.setEvent_text(updateText);
                    newevents.updateAll("event_text = ?", list_events.get(position));
                    list_events.set(position,updateText);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(EventActivity.this, "成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EventActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                }
            }
        }).setView(alert_view).show();
    }
}
