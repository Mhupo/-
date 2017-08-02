package com.hupo.followmind;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hupo.bean.Foods;
import com.hupo.tools.DepotListAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/24.
 */

public class FoodsActivity extends AppCompatActivity implements DepotListAdapter.onDeleteListener,DepotListAdapter.onUpdateListener{
    CustomListView item_foods;
    List<String> list_foods = new ArrayList<>();
    ImageButton addButton;
    View alert_view;
    DepotListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        item_foods = (CustomListView) findViewById(R.id.list_depot);
        //alert_view = View.inflate(FoodsActivity.this,R.layout.content_alert,null);

        //数据库操作&初始化LiteView
        LitePal.getDatabase();
        List<Foods> foodsAll = DataSupport.findAll(Foods.class);
        for(Foods food : foodsAll){
            list_foods.add(food.getFood_name());
        }

        item_foods.initSlideMode(CustomListView.MOD_LEFT);
        adapter = new DepotListAdapter(FoodsActivity.this,item_foods,list_foods,this,this);
        item_foods.setAdapter(adapter);

        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FoodsActivity.this, "ok", Toast.LENGTH_SHORT).show();
                alert_view = LayoutInflater.from(FoodsActivity.this).inflate(R.layout.content_alert,null);
                AlertDialog.Builder alert = new AlertDialog.Builder(FoodsActivity.this);
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                        String addText = editText.getText().toString();
                        if(!addText.equals("") && !list_foods.contains(addText)){
                            Foods newFood = new Foods();
                            newFood.setFood_name(addText);
                            newFood.save();
                            list_foods.add(addText);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(FoodsActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FoodsActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setView(alert_view).show();
            }
        });
    }

    @Override
    public void onDelete(int position) {
        list_foods.remove(position);
        DataSupport.delete(Foods.class,position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(final int position) {
        //positions = position;
        alert_view = LayoutInflater.from(FoodsActivity.this).inflate(R.layout.content_alert,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(FoodsActivity.this);
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                String updateText = editText.getText().toString();
                if(!updateText.equals("") && !list_foods.contains(updateText)){
                    Foods newfood = new Foods();
                    newfood.setFood_name(updateText);
                    newfood.updateAll("food_name = ?",list_foods.get(position));
                    list_foods.set(position,updateText);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(FoodsActivity.this, "成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(FoodsActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                }
            }
        }).setView(alert_view).show();
    }
}
