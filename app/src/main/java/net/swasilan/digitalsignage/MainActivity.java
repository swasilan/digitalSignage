package net.swasilan.digitalsignage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    WebView simpleWebView;
    String url = "https://docs.google.com/presentation/d/e/2PACX-1vQWPgB5C3Veqx0LFOCumv_dIkmjYr5u5VsE9Fm4JNqdmV_GjEPBXCyVrPX3Giec9-gXf2mm-A1viCG6/pub?start=true&loop=true&delayms=5000";
    int refreshTime;
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;

        SharedPreferences sharedPref =
                PreferenceManager.getDefaultSharedPreferences(this);

        url = sharedPref.getString(SettingsActivity.KEY_PREF_URL, "https://www.google.ch" );
        refreshTime = Integer.parseInt(sharedPref.getString(SettingsActivity.KEY_PREF_REFRESH_TIME, "30"));

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        simpleWebView = (WebView) findViewById(R.id.simpleWebView);

        simpleWebView.setWebViewClient(new DigitalSignage());

        simpleWebView.setOnTouchListener(new View.OnTouchListener() {
            boolean settingsVisible = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(context, SettingsActivity.class);
                if(!settingsVisible) {
                    startActivity(i);
                }
                settingsVisible = !settingsVisible;
                return true;
            }
        });

        simpleWebView.getSettings().setJavaScriptEnabled(true);
        simpleWebView.loadUrl(url); // load a web page in a web view

        myTimer = new Timer();
        if(refreshTime > 0) {
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }

            }, 0, refreshTime * 60 * 1000);
        }
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }


    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.
            //Do something to the UI thread here
            simpleWebView.loadUrl(url); // load a web page in a web view
        }
    };

    private class DigitalSignage extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
