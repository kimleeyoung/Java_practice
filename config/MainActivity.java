package com.kly.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        count=pref.getInt("count",0);
        Log.d("MainActivity",count+"");

        count++;

        SharedPreferences.Editor editor=pref.edit();
        editor.putInt("count",count);
        editor.commit();

        Button configBtn=findViewById(R.id.config_btn);
        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ConfigActivity.class);
                startActivity(intent);
            }
        });

        Button alarmBtn=findViewById(R.id.alarm_btn);
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isMessageAlarm=pref.getBoolean("message_alarm",false);
                Log.d("MainActivity",""+isMessageAlarm);

                if(isMessageAlarm){
                    Toast.makeText(MainActivity.this,"메세지가 왔습니다",Toast.LENGTH_SHORT).show();
                    boolean isSound=pref.getBoolean("alarm_sound",false);
                    if(isSound){
                        MediaPlayer mediaPlayer=null;
                        String sound=pref.getString("sound","카톡");
                        if(sound.equals("카톡")){
                            mediaPlayer=mediaPlayer.create(MainActivity.this,R.raw.katok);
                        }
                        else if(sound.equals("카톡왔숑")){
                            mediaPlayer=mediaPlayer.create(MainActivity.this,R.raw.katok2);
                        }
                        else if(sound.equals("오바마 카카오톡")){
                            mediaPlayer=mediaPlayer.create(MainActivity.this,R.raw.kakao_obama);
                        }
                        mediaPlayer.start();
                    }
                }
            }
        });
    }
}
