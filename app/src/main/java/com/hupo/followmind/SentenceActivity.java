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
import com.hupo.bean.Sentences;
import com.hupo.tools.DepotListAdapter;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class SentenceActivity extends AppCompatActivity implements  DepotListAdapter.onUpdateListener,DepotListAdapter.onDeleteListener{
    CustomListView item_sentences;
    List<String> list_sentences = new ArrayList<>();
    ImageButton addButton;
    View alert_view;
    DepotListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence);
        item_sentences = (CustomListView) findViewById(R.id.list_depot);
        //alert_view = View.inflate(FoodsActivity.this,R.layout.content_alert,null);

        //数据库操作&初始化LiteView
        LitePal.getDatabase();
        List<Sentences> sentenceAll = DataSupport.findAll(Sentences.class);
        for(Sentences sentences : sentenceAll){
            list_sentences.add(sentences.getSentence_text());
        }

        item_sentences.initSlideMode(CustomListView.MOD_LEFT);
        adapter = new DepotListAdapter(SentenceActivity.this, item_sentences, list_sentences,this,this);
        item_sentences.setAdapter(adapter);

        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FoodsActivity.this, "ok", Toast.LENGTH_SHORT).show();
                alert_view = LayoutInflater.from(SentenceActivity.this).inflate(R.layout.content_alert,null);
                AlertDialog.Builder alert = new AlertDialog.Builder(SentenceActivity.this);
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                        String addText = editText.getText().toString();
                        if(!addText.equals("") && !list_sentences.contains(addText)){
                            Sentences newSentence = new Sentences();
                            newSentence.setSentence_text(addText);
                            newSentence.save();
                            list_sentences.add(addText);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(SentenceActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SentenceActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                        }
                    }
                }).setView(alert_view).show();
            }
        });
    }

    @Override
    public void onDelete(int position) {
        list_sentences.remove(position);
        DataSupport.delete(Sentences.class,position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUpdate(final int position) {
        //positions = position;
        alert_view = LayoutInflater.from(SentenceActivity.this).inflate(R.layout.content_alert,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(SentenceActivity.this);
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) alert_view.findViewById(R.id.addText);
                String updateText = editText.getText().toString();
                if(!updateText.equals("") && !list_sentences.contains(updateText)){
                    Sentences newsentence = new Sentences();
                    newsentence.setSentence_text(updateText);
                    newsentence.updateAll("sentence_text = ?", list_sentences.get(position));
                    list_sentences.set(position,updateText);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(SentenceActivity.this, "成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SentenceActivity.this, "ERROR：输入为空或与之前的重复", Toast.LENGTH_LONG).show();
                }
            }
        }).setView(alert_view).show();
    }
}
