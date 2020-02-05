package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    public int clickCount = 0;
    /* access modifiers changed from: private */
    public CountDownTimer clickTimer = null;
    private ImageButton mHelpButton;
    private ImageButton mPlayButton;
    private ImageButton mSettingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mPlayButton = (ImageButton) findViewById(R.id.home_play_button);
        this.mPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, ControlPanelActivity.class));
            }
        });
    }
}
