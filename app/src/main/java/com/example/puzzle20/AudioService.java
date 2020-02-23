package com.example.puzzle20;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class AudioService extends Service {

    static final int START = 3, PAUSE = 4;
    Boolean shouldPause = false;
    MediaPlayer loop;

    private void startLoop(){
        if(loop == null){
            loop = MediaPlayer.create(this, R.raw.pokemon);
        }
        if(!loop.isPlaying()){
            loop.setLooping(true);
            loop.start();
        }
    }

    private void start(){
        startLoop();
        shouldPause = false;
    }
    private void pause(){
        shouldPause = true;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(shouldPause) {
                            loop.pause();
                        }
                    }
                }, 100);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(getClass().getSimpleName(), "Creating service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(getClass().getSimpleName(), "Intent received");

        try {
            int actionDefault = 0;
            int action = actionDefault;

            if(intent != null){
                if(intent.hasExtra("action")){
                    action = intent.getIntExtra("action", actionDefault);
                }
            }

            switch (action) {
                case START:
                    start();
                    break;
                case PAUSE:
                    pause();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loop != null) loop.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
