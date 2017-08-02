package com.hupo.followmind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hupo.bean.Events;
import com.hupo.bean.Foods;
import com.hupo.bean.ListviewButton;
import com.hupo.bean.Sentences;
import com.hupo.bean.Wish;
import com.hupo.tools.SideslipMenuAdapter;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    BoomMenuButton menuButton;
    ListView sideslipMenu;
    ListviewButton listviewButton;
    List<ListviewButton> sm_Items = new ArrayList<>();
    List<String> list_food = new ArrayList<>();
    List<Wish> list_wish;
    List<String> list_sentence = new ArrayList<>();
    List<String> list_event = new ArrayList<>();
    TextView outcome;
    TextView outsentence;
    TextView outtime;
    Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        outcome = (TextView) findViewById(R.id.outcome);
        outsentence = (TextView) findViewById(R.id.sentence);
        outtime = (TextView) findViewById(R.id.time);

        //数据库操作
        LitePal.getDatabase();
        /*Foods food1 = new Foods();
        food1.setFood_name("食物DEMO1");
        food1.save();
        Foods food2 = new Foods();
        food2.setFood_name("食物DEMO2");
        food2.save();
        Wish wish1 = new Wish();
        wish1.setWish_name("红轴机械键盘");
        wish1.setMoney(109);
        wish1.save();
        Wish wish2 = new Wish();
        wish2.setWish_name("心愿DEMO2");
        wish2.setMoney(20000);
        wish2.save();
        Events events1 = new Events();
        events1.setEvent_text("约好朋友五黑");
        events1.save();
        Events events2 = new Events();
        events2.setEvent_text("日程DEMO2");
        events2.save();
        Sentences sentences1 = new Sentences();
        sentences1.setSentence_text("吔屎啦你");
        sentences1.save();*/

        //获取数据库的数据
        //食物
        List<Foods> list_foodAll = DataSupport.findAll(Foods.class);
        for(Foods food : list_foodAll){
            list_food.add(food.getFood_name());
        }
        //心愿
        list_wish = DataSupport.findAll(Wish.class);
        //日程
        List<Events> list_eventAll = DataSupport.findAll(Events.class);
        for (Events events : list_eventAll){
            list_event.add(events.getEvent_text());
        }
        //句子
        List<Sentences> list_sentenceAll = DataSupport.findAll(Sentences.class);
        for(Sentences sentences : list_sentenceAll){
            list_sentence.add(sentences.getSentence_text());
        }

        //bmb初始化
        menuButton = (BoomMenuButton) findViewById(R.id.bmb);
        assert menuButton != null;
        menuButton.setButtonEnum(ButtonEnum.Ham);
        menuButton.setPiecePlaceEnum(PiecePlaceEnum.HAM_3);
        menuButton.setButtonPlaceEnum(ButtonPlaceEnum.HAM_3);
        //BoomMenuButton添加数据
        for (int i = 0; i < menuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            String outcomeString = null;
                            switch (index){
                                //吃什么
                                case 0:
                                    if (list_food.size() == 0){
                                        Toast.makeText(MainActivity.this, "你的美食仓暂无数据", Toast.LENGTH_SHORT).show();
                                        break;
                                    }else {
                                        outcomeString = list_food.get(random.nextInt(list_food.size()));
                                        outcome.setText(outcomeString);
                                        //outcomeString = "";
                                        break;
                                    }
                                //买什么
                                case 1:
                                    if (list_wish.size() == 0){
                                        Toast.makeText(MainActivity.this, "你的心愿单暂无数据", Toast.LENGTH_SHORT).show();
                                        break;
                                    }else {
                                        int come_index = random.nextInt(list_wish.size());
                                        outcomeString = list_wish.get(come_index).getWish_name();
                                        outcome.setText(outcomeString);
                                        //outcomeString = "";
                                        break;
                                    }
                                //做什么
                                case 2:
                                    if (list_event.size() == 0){
                                        Toast.makeText(MainActivity.this, "你的日程本暂无数据", Toast.LENGTH_SHORT).show();
                                        break;
                                    }else {
                                        outcomeString = list_event.get(random.nextInt(list_event.size()));
                                        outcome.setText(outcomeString);
                                        //outcomeString = "";
                                        break;
                                    }
                            }
                        }
                    })
                    .normalText(getText()).textSize(15)
                    .normalImageRes(getImage());
            menuButton.addBuilder(builder);
        }
        //其他控件的初始化&操作
        if(list_sentence.size() != 0){
            outsentence.setText(list_sentence.get(random.nextInt(list_sentence.size())));
        }else{
            outsentence.setText("事了拂袖去，深藏功与名");
        }
        //获取日期星期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String week[] = new String[]{"Error","日","一","二","三","四","五","六"};
        outtime.setText(simpleDateFormat.format(new Date()) +"  "+ "星期" + week[calendar.DAY_OF_WEEK]);

        //MENU - LiteView初始化
        sideslipMenu = (ListView) findViewById(R.id.list_item);
        initListviewButton();
        SideslipMenuAdapter adapter = new SideslipMenuAdapter(MainActivity.this,R.layout.sideslipmenu_item,sm_Items);
        sideslipMenu.setAdapter(adapter);
        sideslipMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listviewButton = sm_Items.get(i);
                switch(i){
                    case 0:
                        Intent toFood = new Intent(MainActivity.this,FoodsActivity.class);
                        startActivity(toFood);
                        break;
                    case 1:
                        Intent toWish = new Intent(MainActivity.this,WishActivity.class);
                        startActivity(toWish);
                        break;
                    case 2:
                        Intent toEvent = new Intent(MainActivity.this,EventActivity.class);
                        startActivity(toEvent);
                        break;
                    case 3:
                        Intent toSentence = new Intent(MainActivity.this,SentenceActivity.class);
                        startActivity(toSentence);
                        break;
                }
            }
        });


    }
    //BoomMenuButton值
    private static int[] BoomMenuButtonImages = new int[]{
        R.drawable.eat_w,
        R.drawable.buy_w,
        R.drawable.doing_w
    };
    private static String[] BoomMenuButtonNames = new String[]{
      "吃什么","买什么","做什么"
    };
    private static int textindex = 0;
    private static int imageindex = 0;
    static String getText(){
        if(textindex >= BoomMenuButtonNames.length)
            textindex = 0;
        return BoomMenuButtonNames[textindex++];
    }
    static int getImage(){
        if(imageindex >= BoomMenuButtonImages.length)
            imageindex = 0;
        return BoomMenuButtonImages[imageindex++];
    }
    void initListviewButton(){
        //ListviewButton home = new ListviewButton(R.drawable.home_menu,"主页",R.drawable.arrows);
        ListviewButton eat_menu = new ListviewButton(R.drawable.eat_menu,"我的美食仓",R.drawable.arrows);
        ListviewButton gift_menu = new ListviewButton(R.drawable.gift_menu,"我的心愿单",R.drawable.arrows);
        ListviewButton doing_menu = new ListviewButton(R.drawable.doing_menu,"我的日程本",R.drawable.arrows);
        ListviewButton sentence_menu = new ListviewButton(R.drawable.sentence_menu,"我的句子库",R.drawable.arrows);
        //sm_Items.add(home);
        sm_Items.add(eat_menu);
        sm_Items.add(gift_menu);
        sm_Items.add(doing_menu);
        sm_Items.add(sentence_menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //重新获取数据库的数据
        LitePal.getDatabase();
        //食物
        List<Foods> list_foodAll = DataSupport.findAll(Foods.class);
        for(Foods food : list_foodAll){
            list_food.add(food.getFood_name());
        }
        //心愿
        list_wish = DataSupport.findAll(Wish.class);
        //日程
        List<Events> list_eventAll = DataSupport.findAll(Events.class);
        for (Events events : list_eventAll){
            list_event.add(events.getEvent_text());
        }
        //句子
        List<Sentences> list_sentenceAll = DataSupport.findAll(Sentences.class);
        for(Sentences sentences : list_sentenceAll){
            list_sentence.add(sentences.getSentence_text());
        }
    }
}
