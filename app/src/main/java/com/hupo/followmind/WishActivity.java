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

import com.hupo.bean.Foods;
import com.hupo.bean.Wish;
import com.hupo.tools.DepotListAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class WishActivity extends AppCompatActivity implements DepotListAdapter.onUpdateListener,DepotListAdapter.onDeleteListener{
    CustomListView item_wishs;
    List<String> list_name = new ArrayList<>();
    List<Double> list_money = new ArrayList<>();
    List<String> list_wishs = new ArrayList<>();
    ImageButton addButton;
    View alert_view;
    DepotListAdapter adapter;
    EditText editname;
    EditText editmoney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish);
        item_wishs = (CustomListView) findViewById(R.id.list_depot);
        //alert_view = View.inflate(FoodsActivity.this,R.layout.content_alert,null);

        //数据库操作&初始化LiteView
        LitePal.getDatabase();
        List<Wish> wishsAll = DataSupport.findAll(Wish.class);
        for(Wish wish : wishsAll){
            list_name.add(wish.getWish_name());
            list_money.add(wish.getMoney());
            list_wishs.add(wish.getWish_name()+"($"+wish.getMoney()+")");
        }

        item_wishs.initSlideMode(CustomListView.MOD_LEFT);
        adapter = new DepotListAdapter(WishActivity.this, item_wishs, list_wishs,this,this);
        item_wishs.setAdapter(adapter);

        //添加
        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FoodsActivity.this, "ok", Toast.LENGTH_SHORT).show();
                alert_view = LayoutInflater.from(WishActivity.this).inflate(R.layout.content_alert1,null);
                editname = (EditText) alert_view.findViewById(R.id.addWishname);
                editmoney = (EditText) alert_view.findViewById(R.id.addWishmoney);
                AlertDialog.Builder alert = new AlertDialog.Builder(WishActivity.this);
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String addName = editname.getText().toString();
                        double addMoney = Double.parseDouble(editmoney.getText().toString());
                        if(!addName.equals("") && !list_name.contains(addName)){
                            Wish newWish = new Wish();
                            newWish.setWish_name(addName);
                            newWish.setMoney(addMoney);
                            newWish.save();
                            list_name.add(addName);
                            list_money.add(addMoney);
                            list_wishs.add(addName+"($"+addMoney+")");
                            adapter.notifyDataSetChanged();
                            Toast.makeText(WishActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(WishActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setView(alert_view).show();
            }
        });
    }

    //删除
    @Override
    public void onDelete(int position) {
        list_wishs.remove(position);
        DataSupport.delete(Wish.class,position);
        adapter.notifyDataSetChanged();
    }

    //修改
    @Override
    public void onUpdate(final int position) {
        //positions = position;
        alert_view = LayoutInflater.from(WishActivity.this).inflate(R.layout.content_alert1,null);
        editname = (EditText) alert_view.findViewById(R.id.addWishname);
        editmoney = (EditText) alert_view.findViewById(R.id.addWishmoney);
        editmoney.setText("");
        editname.setHint(list_name.get(position));
        editmoney.setHint(list_money.get(position).toString());
        AlertDialog.Builder alert = new AlertDialog.Builder(WishActivity.this);
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String updateName = editname.getText().toString();
                String updateMoney = editmoney.getText().toString();
                double money = 0;
                if (updateName.equals("") && updateMoney.equals("")){
                    Toast.makeText(WishActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }else if (updateName.equals("") && !updateMoney.equals("")){
                    money = Double.parseDouble(updateMoney);
                    if (!list_money.contains(money)){
                        Wish newwish = new Wish();
                        newwish.setMoney(money);
                        newwish.updateAll("wish_name = ? and money = ?", list_name.get(position),list_money.get(position).toString());
                        list_money.set(position,money);
                        list_wishs.set(position,editname.getHint()+"($"+money+")");
                        adapter.notifyDataSetChanged();
                        Toast.makeText(WishActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WishActivity.this, "修改后的金额不能重复", Toast.LENGTH_SHORT).show();
                    }
                }else if (!updateName.equals("") && updateMoney.equals("")){
                    if (!list_name.contains(updateName)){
                        Wish newwish = new Wish();
                        newwish.setWish_name(updateName);
                        newwish.updateAll("wish_name = ? and money = ?", list_name.get(position),list_money.get(position).toString());
                        list_name.set(position,updateName);
                        list_wishs.set(position,updateName+"($"+editmoney.getHint()+")");
                        adapter.notifyDataSetChanged();
                        Toast.makeText(WishActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WishActivity.this, "修改后的名称不能重复", Toast.LENGTH_SHORT).show();
                    }
                }else if (!updateName.equals("") && !updateMoney.equals("")){
                    if (!list_name.contains(updateName)){
                        Wish newwish = new Wish();
                        newwish.setWish_name(updateName);
                        newwish.setMoney(money);
                        newwish.updateAll("wish_name = ? and money = ?", list_name.get(position),list_money.get(position).toString());
                        list_name.set(position,updateName);
                        list_money.set(position,money);
                        list_wishs.set(position,updateName+"($"+money+")");
                        adapter.notifyDataSetChanged();
                        Toast.makeText(WishActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WishActivity.this, "修改后的信息不能重复", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).setView(alert_view).show();
    }
}
