package com.shellyambar.thegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MyMusicService extends Service {

    private MediaPlayer mediaPlayer = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.soundscape);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }else{
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.soundscape);
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }








        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
        mediaPlayer.stop();

    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
