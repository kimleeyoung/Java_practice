package com.kly.rss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.stanfy.gsonxml.GsonXml;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NewsAdapter.OnNewsClickListener{

    XmlParserCreator parserCreator = new XmlParserCreator() {
        @Override
        public XmlPullParser createParser() {
            try {
                return XmlPullParserFactory.newInstance().newPullParser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

    ArrayList<Item> newsList=new ArrayList<>();
    NewsAdapter newsAdapter=new NewsAdapter(this,newsList);
    RecyclerView newsListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListRV=findViewById(R.id.newslist_rv);
        newsListRV.setAdapter(newsAdapter);

        newsAdapter.setOnNewsClickListener(this);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        newsListRV.setLayoutManager(layoutManager);

        Handler handler=new Handler();

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String xml=response.body().string();
                Log.d("xml",xml);
                GsonXml gsonXml = new GsonXmlBuilder()
                        .setXmlParserCreator(parserCreator)
                        .setSameNameLists(true)
                        .create();
                Rss rss=gsonXml.fromXml(xml,Rss.class);
                for(Item item:rss.channel.item){
                    //Log.d("item",item.getTitle());
                    Log.d("item",item.getEnclosure().getUrl());
                    newsList.add(item);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    @Override
    public void onNewsClick(Item item) {
        Log.d("item",item.getTitle());

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(item.getLink()));
        startActivity(intent);
    }
}

//옵저버 패턴
//묵시적 호출