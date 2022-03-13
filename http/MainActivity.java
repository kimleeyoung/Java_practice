package com.kly.http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText LinkEt=findViewById(R.id.link_et);
        Button searchBtn=findViewById(R.id.search_btn);
        TextView htmlTv=findViewById(R.id.html_tv);
        Handler handler=new Handler();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link=LinkEt.getText().toString();
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder().url(link).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String html=response.body().string();
                        Log.d("html",html);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                htmlTv.setText(html);
                            }
                        });
                    }
                });
            }
        });
    }
}