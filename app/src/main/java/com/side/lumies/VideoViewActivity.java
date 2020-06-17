package com.side.lumies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.salient.artplayer.MediaPlayerManager;
import org.salient.artplayer.ScaleType;
import org.salient.artplayer.SystemMediaPlayer;
import org.salient.artplayer.VideoView;
import org.salient.artplayer.exo.ExoPlayer;
import org.salient.artplayer.ijk.IjkPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;


public class VideoViewActivity extends AppCompatActivity {


    private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
    private long lastPressTime;
    private boolean mHasDoubleClicked = false;
    boolean hideandsick = false;
    VideoView videoView;
    int s = 10;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
     NewControlPanel controlPanel;
    String videoUrlStream = "", urla = "";
    RelativeLayout btn1, btn2;
    RelativeLayout progressBar;
    ScrollView scrollView;
    TextView mainText, raiting, yearss, seasons, buget, descript, starring, director;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = (VideoView) findViewById(R.id.video_view);

        parent_view = findViewById(R.id.parent_view);

        parent_view.setPadding(0, getStatusBarHeight(), 0, 0);
        btn1 =  findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clipData = android.content.ClipData.newPlainText("Text Label", videoUrlStream);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"Ссылка скопирована!",Toast.LENGTH_SHORT).show();

            }
        });
        btn2 =  findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrlStream));
                startActivity(browserIntent);
            }
        });

        scrollView = findViewById(R.id.scrolling);
        raiting = findViewById(R.id.raiting);
        yearss = findViewById(R.id.yearss);
        seasons = findViewById(R.id.seasons);
        buget = findViewById(R.id.buget);
        descript = findViewById(R.id.descript);
        starring = findViewById(R.id.starring);
        director = findViewById(R.id.director);
        mainText = findViewById(R.id.MainText);

        controlPanel = new NewControlPanel(this);


        controlPanel.getIvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullScreenChange();
            }
        });

        MediaPlayerManager.instance().releasePlayerAndView(this);

        MediaPlayerManager.instance().setMediaPlayer(new SystemMediaPlayer());

        progressBar = findViewById(R.id.progress);


        controlPanel.getPlusTenDouble().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long pressTime = System.currentTimeMillis();

                if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                    MediaPlayerManager.instance().pause();
                    MediaPlayerManager.instance().seekTo(MediaPlayerManager.instance().getCurrentPositionWhenPlaying()+9000);
                    MediaPlayerManager.instance().seekTo(MediaPlayerManager.instance().getCurrentPositionWhenPlaying()+3000);
                    MediaPlayerManager.instance().isPlaying();
                    mHasDoubleClicked = true;
                    controlPanel.ShowUiAll();
                    controlPanel.HideUiAll();
                    MediaPlayerManager.instance().start();
                    ObjectAnimator.ofFloat(controlPanel.getPlusTenss(), "alpha", controlPanel.getPlusTenss().getAlpha(), 0.8f).setDuration(150).start();
                    ObjectAnimator.ofFloat(controlPanel.getPlusTenDouble(), "alpha", controlPanel.getPlusTenDouble().getAlpha(), 0.8f).setDuration(150).start();

                    controlPanel.HideUiAll();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ObjectAnimator.ofFloat(controlPanel.getPlusTenss(), "alpha", controlPanel.getPlusTenss().getAlpha(), 0f)
                                    .setDuration(150).start();
                            ObjectAnimator.ofFloat(controlPanel.getPlusTenDouble(), "alpha", controlPanel.getPlusTenDouble().getAlpha(), 0f)
                                    .setDuration(150).start();
                        }
                    }, 250);


                }
                else {
                    mHasDoubleClicked = false;
                    Handler myHandler = new Handler() {
                        public void handleMessage(Message m) {
                            if (!mHasDoubleClicked) {
                                if (hideandsick){
                                    controlPanel.ShowUiAll();
                                    hideandsick = false;

                                }
                                else if (controlPanel.getIvRight().getVisibility()==View.VISIBLE){
                                    controlPanel.HideUiAll();
                                    hideandsick = true;
                                }
                                else{
                                    controlPanel.ShowUiAll();
                                    hideandsick = true;
                                }
                            }
                        }
                    };
                    Message m = new Message();
                    myHandler.sendMessageDelayed(m,DOUBLE_PRESS_INTERVAL);
                }

                lastPressTime = pressTime;
            }
        });
        controlPanel.getMinusTenDouble().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long pressTime = System.currentTimeMillis();


                if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                    MediaPlayerManager.instance().seekTo(MediaPlayerManager.instance().getCurrentPositionWhenPlaying()-10000);
                    mHasDoubleClicked = true;
                    ObjectAnimator.ofFloat(controlPanel.getMinesTenss(), "alpha", controlPanel.getMinesTenss().getAlpha(), 0.8f)
                            .setDuration(150).start();
                    ObjectAnimator.ofFloat(controlPanel.getMinusTenDouble(), "alpha", controlPanel.getMinusTenDouble().getAlpha(), 0.8f)
                            .setDuration(150).start();

                    controlPanel.HideUiAll();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ObjectAnimator.ofFloat(controlPanel.getMinesTenss(), "alpha", controlPanel.getMinesTenss().getAlpha(), 0f)
                                    .setDuration(150).start();

                            ObjectAnimator.ofFloat(controlPanel.getMinusTenDouble(), "alpha", controlPanel.getMinusTenDouble().getAlpha(), 0f)
                                    .setDuration(150).start();

                            controlPanel.HideUiAll();
                        }
                    }, 250);
                }
                else {
                    mHasDoubleClicked = false;
                    Handler myHandler = new Handler() {
                        public void handleMessage(Message m) {
                            if (!mHasDoubleClicked) {

                                if (hideandsick){
                                    controlPanel.ShowUiAll();
                                    hideandsick = false;

                                }
                                else if (controlPanel.getIvRight().getVisibility()==View.VISIBLE){
                                    controlPanel.HideUiAll();
                                    hideandsick = true;
                                }
                                else{
                                    controlPanel.ShowUiAll();
                                    hideandsick = true;
                                }
                            }
                        }
                    };
                    Message m = new Message();
                    myHandler.sendMessageDelayed(m,DOUBLE_PRESS_INTERVAL);
                }

                lastPressTime = pressTime;
            }
        });

        videoView.setControlPanel(controlPanel);



        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            int is = 0;
            urla =(String) b.get("hrefff");
            for (int i = 0 ; i<5;i++){
                is = urla.indexOf('/');
                urla = urla.substring(is+1, urla.length());

            }
            new FindVideoUrl().execute();
        }


        initOrientationListener();



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView = findViewById(R.id.recycler_view2);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);

        mAdapter = new RecyclerAdapter3(getApplicationContext(),  title, new RecyclerAdapter3.MyAdapterListener() {
            @Override
            public void itemClick(View v, int position, String name, String name2) {

                controlPanel.getZerone().setText(name2);
                String save = name;
                int ii1, ii2, ii3, ii4;
                int i1 = name.indexOf('\'');
                name = name.substring(i1+1, name.length());
                int i2 = name.indexOf('\'');
                name = name.substring(i2+1, name.length());
                int i3 = name.indexOf('\'');
                name = name.substring(i3+1, name.length());
                int i4 = name.indexOf('\'');
                name = name.substring(i4+1, name.length());

                videoUrlStream = save.substring(i1+1, i1+i2+1);
                playVideos();


            }
        });






    }

    public void playVideos(){
        MediaPlayerManager.instance().releaseMediaPlayer();
        MediaPlayerManager.instance().setScreenScale(ScaleType.DEFAULT);
        videoView.setUp(videoUrlStream);
        videoView.start();
        MediaPlayerManager.instance().start();
    }
    RelativeLayout parent_view;



    @Override
    public void onBackPressed() {
        if (parent_view.getPaddingTop()==0){
            fullScreenChange();
        }
        else {
            if (MediaPlayerManager.instance().backPress()) {
                return;
            }
            this.finish();
            MediaPlayerManager.instance().releaseMediaPlayer();
            videoView.pause();
            controlPanel = null;
        }
    }



    @Override
    protected void onPause() {
        videoView.pause();
        super.onPause();
    }




    OrientationEventListener mOrientationListener;
    boolean secondCheck = false;
    private int isRotate;
    private long orientationListenerDelayTime = 0;
    boolean isPortraitToLandscape = false;
    boolean isLandscapeToPortrait = false;
    boolean chceck = true;

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    private void initOrientationListener() {
        if (mOrientationListener != null) {
            return;
        }
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int orientation) {

               // Toast.makeText(getApplicationContext(), "LAnd", Toast.LENGTH_SHORT).show();

                Log.d("jezza", "onOrientationChanged: "+orientation);

                try {
                    isRotate = Settings.System.getInt(getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }
                if (isRotate == 0) return;


                if (orientation >= 260 && orientation <= 280 && System.currentTimeMillis() - orientationListenerDelayTime > 1000) {

                    if (chceck){
                        isPortraitToLandscape = true;
                        chceck = false;

                    }
                    if (isPortraitToLandscape) {

                        secondCheck = true;
                        isPortraitToLandscape = false;
                        View decorView = getWindow().getDecorView();
                        new MarginViewWrapper(videoView).setHeight(DensityUtil.getScreenW(VideoViewActivity.this));
                        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
                        parent_view.setPadding(0, 0, 0, 0);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    }
                    orientationListenerDelayTime = System.currentTimeMillis();
                } else if (orientation >= 70 && orientation <= 90 && System.currentTimeMillis() - orientationListenerDelayTime > 1000) {

                    if (chceck){
                        isPortraitToLandscape = true;
                        chceck = false;
                    }
                    if (isPortraitToLandscape) {
                        secondCheck = true;
                        isPortraitToLandscape = false;
                        View decorView = getWindow().getDecorView();
                        new MarginViewWrapper(videoView).setHeight(DensityUtil.getScreenW(VideoViewActivity.this));
                        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                        decorView.setSystemUiVisibility(uiOptions);
                        parent_view.setPadding(0, 0, 0, 0);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

                    }
                    orientationListenerDelayTime = System.currentTimeMillis();
                }
            }
        };
        if (mOrientationListener.canDetectOrientation()) {
            mOrientationListener.enable();
        } else {
            mOrientationListener.disable();
        }

    }


    float mTopViewOriginalWidth;
    float mTopOriginalHeight;


    void fullScreenChange() {
        if (isLandscape()) {
            isLandscapeToPortrait = true;

            parent_view.setPadding(0, getStatusBarHeight(), 0, 0);
            chceck = true;

            new MarginViewWrapper(videoView).setHeight(DensityUtil.dip2px(this, 202));
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        } else {

            parent_view.setPadding(0, 0, 0, 0);
            View decorView = getWindow().getDecorView();
            new MarginViewWrapper(videoView).setHeight(DensityUtil.getScreenW(this));
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            isPortraitToLandscape = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }



    public Boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    class MarginViewWrapper {
        private ViewGroup.MarginLayoutParams params;
        private View viewWrapper;

        MarginViewWrapper(View view) {
            this.viewWrapper = view;
            params = (ViewGroup.MarginLayoutParams) viewWrapper.getLayoutParams();
            if (params instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) params).gravity = Gravity.START;
            }
        }


        int getWidth() {
            return params.width < 0 ? (int) mTopViewOriginalWidth : params.width;
        }

        int getHeight() {
            return params.height < 0 ? (int) mTopOriginalHeight : params.height;
        }

        void setWidth(float width) {
            if (width == mTopViewOriginalWidth) {
                params.width = -1;
                params.setMargins(0, 0, 0, 0);
            } else
                params.width = (int) width;

            viewWrapper.setLayoutParams(params);
        }

        void setHeight(float height) {
            params.height = (int) height;
            viewWrapper.setLayoutParams(params);
        }

        void setMarginTop(int m) {
            params.topMargin = m;
            viewWrapper.setLayoutParams(params);
        }

        void setMarginBottom(int m) {
            params.bottomMargin = m;
            viewWrapper.setLayoutParams(params);
        }

        int getMarginTop() {
            return params.topMargin;
        }

        void setMarginRight(int mr) {
            params.rightMargin = mr;
            viewWrapper.setLayoutParams(params);
        }

        void setMarginLeft(int mr) {
            params.leftMargin = mr;
            viewWrapper.setLayoutParams(params);
        }

        int getMarginRight() {
            return params.rightMargin;
        }

        int getMarginLeft() {
            return params.leftMargin;
        }

        int getMarginBottom() {
            return params.bottomMargin;
        }
    }










    int countSeasons = 0;
    int countSeries = 0;
    int[] mass;

    String[] titles ;
    int size = 0;

    ArrayList<String> title = new ArrayList<>();

    String nameRussian, nameEnglish, year, budzhet, imdb, kinopoisk, opisaniye, shanri, derectors, persons;

    class GetContacts2 extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            title.clear();



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);




            try {




                Document doc = Jsoup.connect("http://alfa.tj/smart/player/"+urla).get();
                Elements hrefElements = doc.select("div.card");
                countSeasons = hrefElements.size();
                Elements hrefElements2 = doc.select("div.col-2.movieListPlay");
                countSeries = hrefElements2.size();

                mass = new int [countSeasons];

                for (int i = 0 ; i<countSeasons; i++){
                    Elements hrefElements3 = doc.select("div.card").get(i).getElementsByTag("a");

                    int s = hrefElements3.size();

                    mass[i] = s/2;

                }



            } catch (IOException e) {
                e.getMessage();
            }






            try {




                Document doc = Jsoup.connect("http://alfa.tj/smart/player/"+urla).get();

                Elements hrefElements = doc.select("div.col-2.movieListPlay");

                size = hrefElements.size();
                titles = new String[size];
                Log.d("whaaaat", "doInBackground: "+size);

                for (int i = 0; i < hrefElements.size(); i++) {

                    titles[i] = hrefElements.get(i).getElementsByTag("a").attr("href");

                }


                title.addAll(Arrays.asList(titles));





            } catch (IOException e) {
                e.getMessage();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            recyclerView.setAdapter(mAdapter);
            new GetContacts3().execute();


            if (countSeasons%10==1){
                seasons.setText(countSeasons+" сезон");
            }
            else if (countSeasons%10==2){
                seasons.setText(countSeasons+" сезона");
            }
            else if (countSeasons%10==3){
                seasons.setText(countSeasons+" сезона");
            }
            else if (countSeasons%10==4){
                seasons.setText(countSeasons+" сезона");
            }
            else if (countSeasons%10==5){
                seasons.setText(countSeasons+" сезонов");
            }
            else {
                seasons.setText(countSeasons+" сезонов");
            }



            String name = titles[0];
            int i1 = name.indexOf('\'');
            name = name.substring(i1+1, name.length());
            int i2 = name.indexOf('\'');
            name = name.substring(i2+1, name.length());
            int i3 = name.indexOf('\'');
            name = name.substring(i3+1, name.length());
            int i4 = name.indexOf('\'');
            name = name.substring(i4+1, name.length());


            if (controlPanel!=null) {
                controlPanel.getZerone().setText(titles[0].substring(i1 + i2 + i3 + 3, i1 + i2 + i3 + i4 + 3));
            }
            videoUrlStream = titles[0].substring(i1+1, i1+i2+1);
            playVideos();

        }


    }


    class GetContacts3 extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);



            try {

                Document doc = Jsoup.connect("http://alfa.tj/smart/about/"+urla).get();

                Elements hrefElements2 = doc.select("div.col-sm-12.col-lg-8");
                Elements hrefElements3 = doc.select("div.col-7");
                Elements hrefElements4 = doc.select("div#movieDescription");
                Elements hrefElements5 = doc.select("div#movieCast");
                Elements hrefElements6 = doc.select("div#movieDirectors");



                nameRussian = hrefElements2.first().getElementsByTag("h2").text();
                nameEnglish = hrefElements2.first().getElementsByTag("h6").text();
                year = hrefElements3.get(0).text();
                budzhet = hrefElements3.get(1).text();
                imdb = hrefElements3.get(2).text();
                kinopoisk = hrefElements3.get(3).text();
                opisaniye = hrefElements4.first().text();
                persons = hrefElements5.get(0).text();
                derectors = hrefElements6.get(1).text();


            } catch (IOException e) {
                e.getMessage();
            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            float rate = Float.parseFloat(kinopoisk);
            if (!nameEnglish.equals("")) {
                mainText.setText(nameRussian + " / " + nameEnglish);
            }
            else{
                mainText.setText(nameRussian);
            }

            int ice = 0, count = 0;
            String save = persons;
            for (int i = 0 ; i<3;i++){
                ice = save.indexOf(',');
                count+=ice;
                save = save.substring(ice+1, save.length());
            }

            starring.setText(persons.substring(0, count+2));


            int ice2 = 0, count2 = 0;
            String save2 = derectors;
            ice2 = save2.indexOf(',');

            director.setText(derectors.substring(0, ice2));


            yearss.setText(year);
            buget.setText(budzhet);
            raiting.setText(rate*10+"%"+" Рейтинг");
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            descript.setText(opisaniye.substring(10, opisaniye.length()));
            if (controlPanel!=null) {
                controlPanel.getZerone().setText(nameRussian);
            }
        }


    }

    class FindVideoUrl extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);

            try {




                Document doc2 = Jsoup.connect("http://alfa.tj/smart/player/"+urla).get();

                Element link = doc2.select("source").first();

                if (link == null) {
                    Elements linksOnPage = doc2.select("script");
                    Matcher matcher = null;

                    Pattern pattern = Pattern.compile("advert.videoSource = '(.+?)';");

                    for (Element element : linksOnPage) {
                        for (DataNode node : element.dataNodes()) {
                            matcher = pattern.matcher(node.getWholeData());
                            while (matcher.find()) {
                                videoUrlStream = matcher.group(1);
                            }
                        }
                    }
                }


            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!videoUrlStream.equals("{full_link_to_video}")) {
                playVideos();
                new GetContacts3().execute();
                seasons.setText("Фильм");
            }
            else{
                title.clear();
                new GetContacts2().execute();
            }

        }


    }



}
