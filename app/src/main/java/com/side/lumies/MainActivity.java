package com.side.lumies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;

import org.jsoup.Connection;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

/**
 * Чуть-чуть грязно, бывает...
 */

public class MainActivity extends AppCompatActivity{

    RecyclerView.Adapter mAdapter;

    String password11 = "", username11 = "";

    public VideoView mVideoView;
    public String VideoUrlStream = "";

    String search_url = "";

    private TextView mTextMessage;
    private SectionsPageAdapter mSectionsPageAdapter;

    ViewPager mViewPager, search_view;
    MenuItem prevMenuItem;

    public String viseoUrl = "";

    public int i = 0;
    TextView counter;
    public String url = "";

    ImageView search, back;

    Boolean isCollapsed = false;


    String firstDate;
    String secondDate;
    RelativeLayout planeta;
    AppBarLayout appBarLayout;
    ImageView logogogo;

    ImageView sort;

    RelativeLayout setting_size;

    RelativeLayout nav2;

    Button btn_save;






    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Two());
        adapter.addFragment(new SearchFragment());

        viewPager.setAdapter(adapter);
    }

    String bools = "first";
    String bools2 = "first";
    String bools3 = "first";

    int firstable = 80, secondable = 1000;

    int size = 80;

    private void setupViewPager2(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment());
        viewPager.setAdapter(adapter);
    }


    TextView progress;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("size_of_bar", MODE_PRIVATE);
        size = prefs.getInt("username", 80);

        firstable = size;

        nav2 = findViewById(R.id.nav3);

        appBarLayout = findViewById(R.id.appbar);
        planeta = findViewById(R.id.planeta);
        sort = findViewById(R.id.sort);
        setting_size = findViewById(R.id.settings_size);

        TickSeekBar listenerSeekBar = findViewById(R.id.listener);
        listenerSeekBar.setMax(150);
        listenerSeekBar.setMin(-50);

        listenerSeekBar.setProgress(firstable+0.0f);
        progress = (TextView) findViewById(R.id.firstest);
        progress.setText(listenerSeekBar.getProgress()+"");

        listenerSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                progress.setText(seekParams.progress+"");
                firstable = seekParams.progress;
                SharedPreferences.Editor editor = getSharedPreferences("size_of_bar", MODE_PRIVATE).edit();
                editor.putInt("username", firstable);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {
                progress.setText(seekBar.getProgress()+"");

                firstable = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {
                progress.setText(seekBar.getProgress()+"");


            }
        });



        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setting_size.setVisibility(View.VISIBLE);


            }
        });


        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("size_of_bar", MODE_PRIVATE).edit();
                editor.putInt("username", firstable);
                editor.apply();
                setting_size.setVisibility(View.GONE);
            }
        });



        logogogo = (ImageView)findViewById(R.id.logogogo);
        search_view = findViewById(R.id.search_view);
        search_view.setCurrentItem(0);
        search_view.setOffscreenPageLimit(2);
        setupViewPager2(search_view);


        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewPager.setCurrentItem(1);

            }
        });









        mTextMessage = (TextView) findViewById(R.id.message);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                if (position==0){
                    nav2.setVisibility(View.VISIBLE);
                    appBarLayout.setExpanded(true, true);
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            appBarLayout.setVisibility(View.VISIBLE);
                        }
                    }, 250);
                }
                else if (position==1){
                    nav2.setVisibility(View.VISIBLE);

                    appBarLayout.setExpanded(false, true);
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            appBarLayout.setVisibility(View.GONE);
                        }
                    }, 250);

                }


            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }








    public boolean checkEngine  = false;

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem()==1){
            mViewPager.setCurrentItem(0);
        }
        else {
            onBackPressed();


        }
        if (MediaPlayerManager.instance().backPress()) {
            return;
        }



    }



    int countSeasons = 0;
    int countSeries = 0;
    int[] mass;

    String[] titles ;

    ArrayList<String> title = new ArrayList<>();

    class GetContacts2 extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            title.clear();



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);


/*

            try {




                Document doc = Jsoup.connect("https://alfa.tj/smart/player/"+url).get();
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

*/





            try {




                Document doc = Jsoup.connect("https://alfa.tj/smart/player/"+url).get();

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


        }


    }


    public void GoCheck(){
        new FindVideoUrl().execute();
    }

    class FindVideoUrl extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);

            try {




                Document doc2 = Jsoup.connect("https://alfa.tj/smart/player/"+url).get();

                Element link = doc2.select("source").first();

                if (link == null) {
                    Elements linksOnPage = doc2.select("script");
                    Matcher matcher = null;

                    Pattern pattern = Pattern.compile("advert.videoSource = '(.+?)';");

                    for (Element element : linksOnPage) {
                        for (DataNode node : element.dataNodes()) {
                            matcher = pattern.matcher(node.getWholeData());
                            while (matcher.find()) {
                                VideoUrlStream = matcher.group(1);
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
            if (!VideoUrlStream.equals("{full_link_to_video}")) {
            }
            else{
                new GetContacts2().execute();
            }
        }


    }

    public void dialogIcon(String s1, String s2){


        new BottomDialog.Builder(this)
                .setTitle(s1)
                .setContent(s2)
                .setCancelable(true)
                .setIcon(R.drawable.ic_play_circle_filled_black_24dp)
                .setPositiveText("OK")
                .setPositiveTextColorResource(android.R.color.white)
                .setPositiveBackgroundColorResource(R.color.colorAccent)
                .onPositive(new BottomDialog.ButtonCallback() {
                    @Override
                    public void onClick(@NonNull BottomDialog bottomDialog) {

                    }
                }).show();
    }





}
