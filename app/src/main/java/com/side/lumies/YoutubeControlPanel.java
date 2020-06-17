package com.side.lumies;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import org.salient.artplayer.AbsControlPanel;
import org.salient.artplayer.MediaPlayerManager;
import org.salient.artplayer.Utils;
import org.salient.artplayer.VideoView;
import org.salient.artplayer.ui.VideoGestureListener;


public class YoutubeControlPanel extends AbsControlPanel implements SeekBar.OnSeekBarChangeListener {

    private final String TAG = YoutubeControlPanel.class.getSimpleName();

    private int mWhat;
    private int mExtra;
    protected GestureDetector mGestureDetector;

    private ImageView startCenterIv;
    private SeekBar bottom_seek_progress;
    private View layout_bottom;
    private View layout_top;
    private TextView current;
    private TextView total;
    public TextView zerone;
    private ProgressBar loading;
    ImageView downIv;
    private ImageView video_cover;
    private ImageView fullScreenIv;
    private LinearLayout errorLayout;
    private TextView tvAlert;
    private TextView tvConfirm;
    private TextView tvTitle;
    private LinearLayout llOperation;
    private LinearLayout llProgressTime;
    public ImageView ScreenSize;

    ImageView minusten, plusten, looping, backgroundViewPlayer;

    TextView choose_player;
    AdView adView2;


    View minErrorIv;
    FrameLayout backgroundLayout;

    public YoutubeControlPanel(Context context) {
        super(context);
    }

    public YoutubeControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YoutubeControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageView getBackgroundViewPlayer(){return backgroundViewPlayer;}

    public ImageView getFullScreenIv() {
        return fullScreenIv;
    }

    public ImageView getDownIv() { return downIv; }

    public ImageView getLooping(){return looping;}

    public ImageView getStartCenterIv(){
        return startCenterIv;
    }

    public TextView getZerone(){return zerone;}

    public TextView getChoose_player(){return choose_player;}

    public ImageView getPlusten(){return plusten;}

    public ImageView getMinusten(){return minusten;}

    public AdView getAdView2(){return adView2;}



    @Override
    protected int getResourceId() {
        return R.layout.layout_youtube_control_panel;
    }




    @Override
    protected void init(Context context) {
        super.init(context);
        minErrorIv = findViewById(R.id.minErrorIv);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        startCenterIv = findViewById(R.id.startCenterIv);
        bottom_seek_progress = findViewById(R.id.bottom_seek_progress);
        layout_bottom = findViewById(R.id.layout_bottom);
        layout_top = findViewById(R.id.layout_top);
        current = findViewById(R.id.current);
        total = findViewById(R.id.total);
        loading = findViewById(R.id.loading);
        downIv = findViewById(R.id.downIv);
        choose_player = findViewById(R.id.choose_player);
        choose_player.setOnClickListener(this);
        video_cover = findViewById(R.id.video_cover);
        errorLayout = findViewById(R.id.errorLayout);
        tvAlert = findViewById(R.id.tvAlert);
        tvConfirm = findViewById(R.id.tvConfirm);
        fullScreenIv = findViewById(R.id.fullScreenIv);
        tvTitle = findViewById(R.id.tvTitle);
        ScreenSize = findViewById(R.id.screensize);
        zerone = findViewById(R.id.zerone);
        minusten = findViewById(R.id.minusten);
        plusten = findViewById(R.id.plusten);
        looping = findViewById(R.id.looping);
        looping.setOnClickListener(this);
        backgroundViewPlayer = findViewById(R.id.backgroundViewPlayer);
        backgroundViewPlayer.setOnClickListener(this);
        adView2 = findViewById(R.id.adView2);



        llOperation = findViewById(R.id.llOperation);
        llProgressTime = findViewById(R.id.llProgressTime);

        minusten.setOnClickListener(this);
        plusten.setOnClickListener(this);
        downIv.setOnClickListener(this);
        ScreenSize.setOnClickListener(this);
        bottom_seek_progress.setOnSeekBarChangeListener(this);
        startCenterIv.setOnClickListener(this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTarget == null) return;

                if (MediaPlayerManager.instance().getPlayerState() == MediaPlayerManager.PlayerState.ERROR) {
                    return;
                }
                if (layout_bottom.getVisibility() != VISIBLE) {
                    showUI(startCenterIv, layout_bottom, layout_top, bottom_seek_progress, minusten, plusten);
                    bgAlphaToBlack();
                } else {
                    hideUI(layout_top, layout_bottom, startCenterIv, bottom_seek_progress, minusten, plusten);
                    bgAlphaToTrans();
                }
                if (MediaPlayerManager.instance().getPlayerState() == MediaPlayerManager.PlayerState.PREPARING) {
                    hideUI(bottom_seek_progress, startCenterIv);
            }
            }
        });
        final VideoGestureListener videoGestureListener = new VideoGestureListener(this);
        mGestureDetector = new GestureDetector(getContext(), videoGestureListener);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGestureDetector.onTouchEvent(event)) return true;
                return videoGestureListener.onTouch(v, event);
            }
        });
        bgAlphaToBlack();
    }

    int pubs = 20000;
    void showOrHide(int status) {

    }

    void bgAlphaToBlack() {
        ObjectAnimator.ofFloat(backgroundLayout, "alpha", backgroundLayout.getAlpha(), 0.8f)
                .setDuration(300).start();
    }

    void bgAlphaToTrans() {
        ObjectAnimator.ofFloat(backgroundLayout, "alpha", backgroundLayout.getAlpha(), 0f)
                .setDuration(300).start();
    }

    @Override
    public void onStateError() {
        tvAlert.setText("Не удалось загрузить видео");
        tvConfirm.setText("Повторите попытку позже");
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mTarget != null) {
                    hideUI(errorLayout);
                    mTarget.start();
                }
            }
        });
    }

    @Override
    public void onStateIdle() {
        hideUI(startCenterIv, layout_bottom, layout_top, loading, errorLayout, bottom_seek_progress, minusten, plusten);
        bgAlphaToTrans();
        showUI(video_cover);
        //startCenterIv.setBackgroundResource(R.drawable.play_white);

    }

    @Override
    public void onStatePreparing() {
        showUI(loading);
        hideUI(startCenterIv);

    }

    @Override
    public void onStatePrepared() {
        hideUI(loading);
        if  (layout_bottom.getVisibility()==VISIBLE) {
            showUI(startCenterIv);
        }


    }

    @Override
    public void onStatePlaying() {
        startCenterIv.setBackgroundResource(R.drawable.pause_white);
    }

    @Override
    public void onStatePaused() {
        startCenterIv.setBackgroundResource(R.drawable.play_white);
    }

    @Override
    public void onStatePlaybackCompleted() {
        startCenterIv.setBackgroundResource(R.drawable.play_white);
        Log.d("chabr", "onStatePlaybackCompleted: ok");
    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onBufferingUpdate(int progress) {
        if (progress != 0)
            bottom_seek_progress.setSecondaryProgress(progress);

        Log.d("ohmy", "onBufferingUpdate:"+progress);
    }

    @Override
    public void onInfo(int what, int extra) {
        mWhat = what;
        mExtra = extra;
    }

    @Override
    public void onProgressUpdate(final int progress, final long position, final long duration) {
        post(new Runnable() {
            @Override
            public void run() {
                bottom_seek_progress.setProgress(progress);
                current.setText(Utils.stringForTime(position));
                total.setText(Utils.stringForTime(duration));

                Log.d("alooo", "run: "+progress+"  "+position+"  "+duration);

            }
        });
    }

    @Override
    public void onEnterSecondScreen() {
        if (mTarget != null && mTarget.getWindowType() == VideoView.WindowType.FULLSCREEN) {
            hideUI(fullScreenIv);
        }
        zerone.setVisibility(VISIBLE);
        showUI(downIv);
        SynchronizeViewState();
    }

    @Override
    public void onExitSecondScreen() {
        if (mTarget != null && mTarget.getWindowType() != VideoView.WindowType.TINY) {
            hideUI(downIv);
        }

        zerone.setVisibility(GONE);
        showUI(fullScreenIv);
        SynchronizeViewState();
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStartTrackingTouch [" + this.hashCode() + "] ");
        MediaPlayerManager.instance().cancelProgressTimer();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStopTrackingTouch [" + this.hashCode() + "] ");
        MediaPlayerManager.instance().startProgressTimer();
        ViewParent vpup = getParent();
        while (vpup != null) {
            vpup.requestDisallowInterceptTouchEvent(false);
            vpup = vpup.getParent();
        }
        if (MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PLAYING &&
                MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PAUSED)
            return;
        long time = (long) (seekBar.getProgress() * 1.00 / 100 * MediaPlayerManager.instance().getDuration());
        MediaPlayerManager.instance().seekTo(time);
        Log.i(TAG, "seekTo " + seekBar.getProgress() +"   "+MediaPlayerManager.instance().getDuration() + " [" + this.hashCode() + "] ");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long duration = MediaPlayerManager.instance().getDuration();
            current.setText(Utils.stringForTime(progress / 100 * duration));
        }
    }

    //显示WiFi状态提醒
    public void showWifiAlert() {
        hideUI(startCenterIv, layout_bottom, layout_top, loading);
        showUI(errorLayout);
        tvAlert.setText("Is in non-WIFI");
        tvConfirm.setText("continue");
        tvConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTarget != null) {
                    hideUI(errorLayout);
                    mTarget.start();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.startCenterIv) {
            if (mTarget == null) {
                Log.d("chbri", "onClick: ss");
                return;
            }
            if (mTarget.isCurrentPlaying() && MediaPlayerManager.instance().isPlaying()) {

                Log.d("chbri", "onClick: kmri");
                mTarget.pause();
            } else {
                if (!Utils.isNetConnected(getContext())) {
                    onStateError();
                    return;
                }
                if (!Utils.isWifiConnected(getContext())) {
                    showWifiAlert();
                    return;
                }

                Log.d("chbri", "onClick: sumri");
                mTarget.start();
            }

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    public void SynchronizeViewState() {
        if (MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PLAYING
                && MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PAUSED) {
            showUI(startCenterIv);
        } else {
            hideUI(startCenterIv);
        }

    }


}
