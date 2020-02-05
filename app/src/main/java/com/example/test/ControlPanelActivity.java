package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bwin.airtoplay.RTSPPlayerView;
import com.bwin.airtoplay.RTSPPlayerView.Callback;
import com.bwin.airtoplay.RTSPPlayerView.RTSPPlayerStatus;

import java.io.File;
import java.util.Arrays;
import java.util.Timer;


public class ControlPanelActivity extends AppCompatActivity{
    private static final String TAG = "ControlPanelActivity";
    public int lastFramerate;
    public int currentFramerate;
    public Timer mFramerateTimer;
    public RTSPPlayerView mRTSPPlayerView;
    public ImageButton mBackButton;
    public ImageButton mTakePhotoButton;
    public ImageButton mRecordVideoButton;
    public SoundPool mSoundPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        this.mRTSPPlayerView = (RTSPPlayerView) findViewById(R.id.control_panel_rtspPlayerView);
        this.mRTSPPlayerView.addCallback(new Callback() {
            public void rtspPlayerStatusChanged(int status) {
                switch (status) {
                    case 1:
                        ControlPanelActivity.this.currentFramerate = 0;
                        ControlPanelActivity.this.lastFramerate = 0;
                        if (ControlPanelActivity.this.mRTSPPlayerView.isShown()) {
                            ControlPanelActivity.this.mRTSPPlayerView.startPlaying();
                            return;
                        } else {
                            ControlPanelActivity.this.mRTSPPlayerView.stopPlaying();
                            return;
                        }
                    case 2:
                        ControlPanelActivity.this.currentFramerate = ControlPanelActivity.this.currentFramerate + 1;
                        return;
                    case 3:
                        if (ControlPanelActivity.this.mFramerateTimer != null) {
                            ControlPanelActivity.this.mFramerateTimer.cancel();
                            ControlPanelActivity.this.mFramerateTimer = null;
                        }
                        ControlPanelActivity.this.mRTSPPlayerView.initPlaying();
                        return;
                    default:
                        Log.i(ControlPanelActivity.TAG, "RTSPPlayerView Callback: Why run into here?");
                        return;
                }
            }
            public void onReceiveData(byte[] data) {
                Log.d(ControlPanelActivity.TAG, new String(data) + Arrays.toString(data));
            }
        });
        this.mRTSPPlayerView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.i(ControlPanelActivity.TAG, "surfaceCreated");
                ControlPanelActivity.this.mRTSPPlayerView.initPlaying();
            }

            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.i(ControlPanelActivity.TAG, "surfaceChanged:(" + i + ": " + i1 + "," + i2 + ")");
            }
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.i(ControlPanelActivity.TAG, "surfaceDestroyed");
                ControlPanelActivity.this.mRTSPPlayerView.stopPlaying();
            }
        });

        this.mBackButton = findViewById(R.id.control_panel_back_button);
        this.mBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ControlPanelActivity.this.finish();
            }
        });
        this.mTakePhotoButton =  findViewById(R.id.control_panel_take_photo_button);
        this.mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ControlPanelActivity.this.mRTSPPlayerView.status == RTSPPlayerStatus.DECODING) {
                    String photoFilePath = Utilities.getRandomPhotoFilePath();
                    String toastText = ControlPanelActivity.this.getResources().getString(R.string.control_panel_alert_save_photo_fail);
                    ControlPanelActivity.this.mRTSPPlayerView.saveScreenshot(photoFilePath);
                    if (photoFilePath != null && ControlPanelActivity.this.mRTSPPlayerView.saveScreenshot(photoFilePath) >= 0) {
                        if (new File(photoFilePath).exists()) {
                            toastText = ControlPanelActivity.this.getResources().getString(R.string.control_panel_alert_save_photo_success) + photoFilePath;
                        }

                        ControlPanelActivity.this.mediaScan(photoFilePath);
                    }
                    Toast.makeText(ControlPanelActivity.this, toastText, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void mediaScan(String fullFilePath) {
        File file = new File(fullFilePath);
        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{file.getAbsolutePath()}, null, new OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.v("MediaScanWork", "file " + path + " was scanned seccessfully: " + uri);
            }
        });
    }

}
