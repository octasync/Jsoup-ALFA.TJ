package com.side.lumies;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.salient.artplayer.AbsControlPanel;
import org.salient.artplayer.MediaPlayerManager;
import org.salient.artplayer.Utils;
import org.salient.artplayer.VideoView;
import org.salient.artplayer.ui.VideoGestureListener;

public class NewControlPanel extends AbsControlPanel implements SeekBar.OnSeekBarChangeListener {

    private final String TAG = org.salient.artplayer.ui.ControlPanel.class.getSimpleName();

    private final long autoDismissTime = 5000;
    private int mWhat;
    private int mExtra;
    protected GestureDetector mGestureDetector;

    ImageView start;
    private SeekBar bottom_seek_progress;
    private View layout_bottom;
    private View layout_top;
    FrameLayout backgroundLayout;
    RelativeLayout plusTenDouble, minusTenDouble;
    private TextView current;
    private TextView total;
    private ProgressBar loading;
    ImageView ivRight;
    private LinearLayout llAlert;
    private TextView tvAlert;
    private TextView tvConfirm;
    private TextView tvTitle;
    private LinearLayout llOperation;
    TextView zerone;

    ImageView minesTenss, plusTenss;


    public String titreExtension;


    public ImageView getMinesTenss() {
        return minesTenss;
    }

    public ImageView getPlusTenss() {
        return plusTenss;
    }
    public ImageView getIvRight() {
        return ivRight;
    }

    private Runnable mDismissTask = new Runnable() {
        @Override
        public void run() {
            if (MediaPlayerManager.instance().getCurrentVideoView() == mTarget && MediaPlayerManager.instance().isPlaying()) {
                hideUI(layout_bottom, layout_top, start);
                bgAlphaToTrans();
            }
        }
    };
    void bgAlphaToBlack() {
        ObjectAnimator.ofFloat(backgroundLayout, "alpha", backgroundLayout.getAlpha(), 0.8f)
                .setDuration(300).start();
    }

    void bgAlphaToTrans() {
        ObjectAnimator.ofFloat(backgroundLayout, "alpha", backgroundLayout.getAlpha(), 0f)
                .setDuration(300).start();
    }



    public NewControlPanel(Context context) {
        super(context);
    }

    public NewControlPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewControlPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getResourceId() {
        return R.layout.new_control_panel_layout;
    }

    public TextView getZerone() {
        return zerone;
    }

    public RelativeLayout getPlusTenDouble() {
        return plusTenDouble;
    }

    public RelativeLayout getMinusTenDouble() {
        return minusTenDouble;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        start = findViewById(R.id.center);
        bottom_seek_progress = findViewById(R.id.bottom_seek_progress);
        layout_bottom = findViewById(R.id.layout_bottom);
        layout_top = findViewById(R.id.layout_top);
        current = findViewById(R.id.current);
        total = findViewById(R.id.total);
        loading = findViewById(R.id.loading);
        backgroundLayout = findViewById(R.id.backgroundLayout);
        llAlert = findViewById(R.id.llAlert);
        tvAlert = findViewById(R.id.tvAlert);
        tvConfirm = findViewById(R.id.tvConfirm);
        ivRight = findViewById(R.id.ivRight);
        tvTitle = findViewById(R.id.tvTitle);
        zerone = findViewById(R.id.zerone);
        llOperation = findViewById(R.id.llOperation);

        plusTenDouble = findViewById(R.id.plustenDouble);
        minusTenDouble = findViewById(R.id.minustenDouble);

        plusTenss = findViewById(R.id.plusTenss);
        minesTenss = findViewById(R.id.minesTenss);

        plusTenDouble.setOnClickListener(this);
        minusTenDouble.setOnClickListener(this);


        ivRight.setOnClickListener(this);
        bottom_seek_progress.setOnSeekBarChangeListener(this);
        start.setOnClickListener(this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTarget == null) return;
                if (!mTarget.isCurrentPlaying()) {
                    return;
                }
                if (MediaPlayerManager.instance().getPlayerState() == MediaPlayerManager.PlayerState.PLAYING) {
                    cancelDismissTask();
                    if (layout_bottom.getVisibility() != VISIBLE) {
                        showUI(layout_bottom, layout_top, start);
                        bgAlphaToBlack();
                    } else {
                        hideUI(layout_top, layout_bottom, start);
                        bgAlphaToTrans();
                    }
                    startDismissTask();
                }
            }
        });
        final VideoGestureListener videoGestureListener = new VideoGestureListener(this);
        mGestureDetector = new GestureDetector(getContext(), videoGestureListener);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mGestureDetector.onTouchEvent(event))
                    return true;
                return videoGestureListener.onTouch(v, event);
            }
        });
    }


    public void ShowUiAll(){
        showUI(start, layout_top, layout_bottom);
        bgAlphaToBlack();
    }
    public void HideUiAll(){
        hideUI(start, layout_top, layout_bottom);
        bgAlphaToTrans();
    }


    @Override
    public void onStateError() {
        hideUI(start, layout_top, layout_bottom, loading);
        showUI(llAlert);
        //MediaPlayerManager.instance().releaseMediaPlayer();
        tvAlert.setText("Ошибка 404");
        tvConfirm.setText("Повторить");
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTarget != null) {
                    hideUI(llAlert);
                    mTarget.start();
                }
            }
        });
    }

    @Override
    public void onStateIdle() {
        hideUI(layout_bottom, layout_top, loading, llAlert, start);
        showUI(loading);
        start.setBackgroundResource(R.drawable.play_white);
        //if (mTarget!=null && mTarget.get)
        SynchronizeViewState();
    }

    @Override
    public void onStatePreparing() {
        showUI(loading);
        hideUI(start);
        hideUI(plusTenDouble);
        hideUI(minusTenDouble);
    }

    @Override
    public void onStatePrepared() {
        hideUI(loading);
        showUI(start);
        showUI(plusTenDouble);
        showUI(minusTenDouble);
    }

    @Override
    public void onStatePlaying() {
        start.setBackgroundResource(R.drawable.pause_white);
        showUI(layout_bottom, layout_top);
        hideUI( loading, llOperation);
        startDismissTask();
    }

    @Override
    public void onStatePaused() {
        start.setBackgroundResource(R.drawable.play_white);
        showUI(start, layout_bottom, layout_top);
        bgAlphaToBlack();
        hideUI( loading, llOperation);
    }

    @Override
    public void onStatePlaybackCompleted() {
        start.setBackgroundResource(R.drawable.play_white);
        hideUI(layout_bottom, loading);
        showUI(start);
        if (mTarget.getWindowType() == VideoView.WindowType.FULLSCREEN || mTarget.getWindowType() == VideoView.WindowType.TINY) {
            showUI(layout_top);
        }
    }

    @Override
    public void onSeekComplete() {

    }

    @Override
    public void onBufferingUpdate(int progress) {
        if (progress != 0) bottom_seek_progress.setSecondaryProgress(progress);
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
            }
        });
    }

    @Override
    public void onEnterSecondScreen() {
        if (mTarget != null && mTarget.getWindowType() == VideoView.WindowType.FULLSCREEN) {
            hideUI(ivRight);
        }
        SynchronizeViewState();
    }

    @Override
    public void onExitSecondScreen() {
        if (mTarget != null && mTarget.getWindowType() != VideoView.WindowType.TINY) {
        }
        showUI(ivRight);
        SynchronizeViewState();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStartTrackingTouch [" + this.hashCode() + "] ");
        MediaPlayerManager.instance().cancelProgressTimer();
        cancelDismissTask();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    long checkeded = 0;

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStopTrackingTouch [" + this.hashCode() + "] ");
        MediaPlayerManager.instance().startProgressTimer();
        startDismissTask();
        ViewParent vpup = getParent();
        while (vpup != null) {
            vpup.requestDisallowInterceptTouchEvent(false);
            vpup = vpup.getParent();
        }
        if (MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PLAYING &&
                MediaPlayerManager.instance().getPlayerState() != MediaPlayerManager.PlayerState.PAUSED)
            return;
        long time = seekBar.getProgress() * MediaPlayerManager.instance().getDuration() / 100;
        MediaPlayerManager.instance().seekTo(checkeded);
        Log.i(TAG, "seekTo " + Utils.stringForTime(time) + " [" + this.hashCode() + "] ");
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            long duration = MediaPlayerManager.instance().getDuration();
            current.setText(Utils.stringForTime(progress * duration / 100));
            checkeded = progress * duration / 100;
        }
    }

    //显示WiFi状态提醒
    public void showWifiAlert() {
        hideUI(start, layout_bottom, layout_top, loading);
        showUI(llAlert);
        tvAlert.setText("Is in non-WIFI");
        tvConfirm.setText("continue");
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTarget != null) {
                    hideUI(llAlert);
                    mTarget.start();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        cancelDismissTask();
        int id = v.getId();
        if (id == R.id.ivLeft) {
            if (mTarget == null) return;
            if (mTarget.getWindowType() == VideoView.WindowType.FULLSCREEN) {
                mTarget.exitFullscreen();
            } else if (mTarget.getWindowType() == VideoView.WindowType.TINY) {
                mTarget.exitTinyWindow();
            }
        } else if (id == R.id.ivRight) {
            if (mTarget == null) return;
            if (mTarget.getWindowType() != VideoView.WindowType.FULLSCREEN) {
                //new VideoView
                VideoView videoView = new VideoView(getContext());
                //set parent
                videoView.setParentVideoView(mTarget);
                videoView.setUp(mTarget.getDataSourceObject(), VideoView.WindowType.FULLSCREEN, mTarget.getData());
                videoView.setControlPanel(new org.salient.artplayer.ui.ControlPanel(getContext()));
                //start fullscreen
                videoView.startFullscreen(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                //MediaPlayerManager.instance().startFullscreen(videoView, ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }

        }  else if (id == R.id.center) {
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
        startDismissTask();
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
        if (MediaPlayerManager.instance().isMute()) {
        } else {
        }
        if (mTarget != null && mTarget.getParentVideoView() != null && mTarget.getParentVideoView().getControlPanel() != null) {
            TextView title = mTarget.getParentVideoView().getControlPanel().findViewById(R.id.tvTitle);
            tvTitle.setText(title.getText() == null ? "" : title.getText());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelDismissTask();
    }

    void startDismissTask() {
        cancelDismissTask();
        postDelayed(mDismissTask, autoDismissTime);
    }

     void cancelDismissTask() {
        Handler handler = getHandler();
        if (handler != null && mDismissTask != null) {
            handler.removeCallbacks(mDismissTask);
        }
    }

}
