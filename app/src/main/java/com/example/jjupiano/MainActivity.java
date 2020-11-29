package com.example.jjupiano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    //녹음 파일 경로 지정
    static final String RECORD_FILE = "/sdcard/recorded.mp4";

    MediaPlayer player;
    MediaRecorder recorder;
    SoundPool pool;

    int d, ds, re, res, m, f, fs, s, ss, ra, ras, si, dh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //가로 화면으로 고정(ContentView가 호출되기전 호출되어야함)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        //피아노 소리 변수랑 연동
        pool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        d = pool.load(this, R.raw.d, 1);
        ds = pool.load(this,R.raw.dos, 1);
        re = pool.load(this,R.raw.re,1);
        res = pool.load(this,R.raw.res,1);
        m = pool.load(this,R.raw.mi,1);
        f = pool.load(this,R.raw.fa,1);
        fs = pool.load(this,R.raw.fas,1);
        s = pool.load(this,R.raw.sol,1);
        ss = pool.load(this,R.raw.sols,1);
        ra = pool.load(this,R.raw.ra,1);
        ras = pool.load(this,R.raw.ras,1);
        si = pool.load(this,R.raw.si,1);
        dh = pool.load(this,R.raw.doh,1);
    }

    public void sound(View v){//피아노 소리
        switch(v.getId()){
            case R.id.btn1: //도
                pool.play(d,1,1,0,0,2);
                break;
            case R.id.btn2: //레
                pool.play(re,1,1,0,0,2);
                break;
            case R.id.btn3: //미
                pool.play(m,1,1,0,0,2);
                break;
            case R.id.btn4: //파
                pool.play(f,1,1,0,0,2);
                break;
            case R.id.btn5: //솔
                pool.play(s,1,1,0,0,2);
                break;
            case R.id.btn6: //라
                pool.play(ra,1,1,0,0,2);
                break;
            case R.id.btn7: //시
                pool.play(si,1,1,0,0,2);
                break;
            case R.id.btn8: //높은 도
                pool.play(dh, 1,1,0,0,2);
                break;
            case R.id.b_btn1: //도#
                pool.play(ds,1,1,0,0,2);
                break;
            case R.id.b_btn2: //레#
                pool.play(res,1,1,0,0,2);
                break;
            case R.id.b_btn3: //파#
                pool.play(fs,1,1,0,0,2);
                break;
            case R.id.b_btn4: //솔#
                pool.play(ss,1,1,0,0,2);
                break;
            case R.id.b_btn5: //라#
                pool.play(ras,1,1,0,0,2);
                break;
        }
    }

    public void change(View v){ //draw버튼 누르면 작곡 화면으로 변경
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(intent);
    }

    public void fin(View v){ //fin 버튼을 누르면 어플 종료
        Toast.makeText(getApplicationContext(), "피아노 어플을 종료합니다.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void record(View v){ //녹음
        if(recorder != null){
            recorder.stop();
            recorder.release();
            recorder=null;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setOutputFile(RECORD_FILE);

        try {
            Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_LONG).show();

            recorder.prepare();
            recorder.start();
        }catch (Exception ex){
            Log.e("AudioRecorder", "prepare() failed. ", ex);
        }
    }

    public void play(View v){ //녹음파일 재생
        if(player != null){ //player가 null이 아니면 player를 중지시키고 녹음 시작
            player.stop();
            player.release();
            player=null;
        }
        Toast.makeText(getApplicationContext(), "녹음파일을 재생합니다.", Toast.LENGTH_LONG).show();

        try{
            player = new MediaPlayer();

            //저장한 녹음 파일 불러와서 재생
            player.setDataSource(RECORD_FILE);
            player.prepare();
            player.start();
        }catch (Exception e){
            Log.e("AudioRecorder", "Audio play faild", e);
        }
    }

    public void stop(View v){ //녹음 스탑
        if(recorder == null)
            return; //녹음 중인 레코드가 없으면 return

        recorder.stop();
        recorder.release(); //기존에 있던 녹음 파일 삭제
        recorder = null;

        Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.",  Toast.LENGTH_LONG).show();
    }
}