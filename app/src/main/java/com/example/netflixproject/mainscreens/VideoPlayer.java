package com.example.netflixproject.mainscreens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.netflixproject.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class VideoPlayer extends AppCompatActivity {
    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    //private static final String FILE_URL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().hide();


        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo.getTypeName().equals("MOBILE")){
            Toast.makeText(VideoPlayer.this, "Turn on wifi", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Turn on WIFI");
            builder.setMessage("Please turn on your WIFI connection to continue.");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }

        else if(networkInfo==null||!networkInfo.isConnected()||!networkInfo.isAvailable()){
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("No Internet Connection");
            builder.setMessage("Please turn on your internet connection to continue.");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    recreate();
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        }
        else
        {
            playerView=findViewById(R.id.exoplayer);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            String url= getIntent().getStringExtra("url");
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(this,trackSelector);
            playerView.setPlayer(simpleExoPlayer);
            DataSource.Factory dataSourceFactory=new DefaultDataSourceFactory(this, Util.getUserAgent(this,"Netflix"));
            MediaSource mediaSource=new ExtractorMediaSource.Factory(dataSourceFactory ).createMediaSource(Uri.parse(url));
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        simpleExoPlayer.release();
//    }
}
