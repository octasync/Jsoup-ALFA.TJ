package com.side.lumies;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.warkiz.tickseekbar.OnSeekChangeListener;
import com.warkiz.tickseekbar.SeekParams;
import com.warkiz.tickseekbar.TickSeekBar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.salient.artplayer.MediaPlayerManager;
import org.salient.artplayer.VideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

/**
 * Чуть-чуть грязно, бывает...
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public BottomNavigationView navigation;
    public VideoView mVideoView;
    public String VideoUrlStream = "";
    public String viseoUrl = "";
    public int i = 0;
    public String url = "";
    public boolean checkEngine = false;
    RecyclerView.Adapter mAdapter;
    String password11 = "", username11 = "";
    String search_url = "";
    ViewPager mViewPager, search_view;
    MenuItem prevMenuItem;
    TextView counter;
    ImageView search, back;
    Boolean isCollapsed = false;
    String firstDate;
    String secondDate;
    RelativeLayout planeta;
    FrameLayout podstilka;
    AppBarLayout appBarLayout;
    ImageView logogogo;
    ImageView sort;
    RelativeLayout setting_size;
    RelativeLayout nav2;
    Button btn_save;

    String bools = "first";
    String bools2 = "first";
    String bools3 = "first";
    int firstable = 80, secondable = 1000;
    int size = 80;
    TextView progress;
    int countSeasons = 0;
    int countSeries = 0;
    int[] mass;
    String[] titles;
    ArrayList<String> title = new ArrayList<>();
    private TextView mTextMessage;
    private SectionsPageAdapter mSectionsPageAdapter;

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Two());
        adapter.addFragment(new SearchFragment());
        adapter.addFragment(new Three());
        adapter.addFragment(new Four());
        adapter.addFragment(new Five());

        viewPager.setAdapter(adapter);
    }

    private void setupViewPager2(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment());
        viewPager.setAdapter(adapter);
    }

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

        listenerSeekBar.setProgress(firstable + 0.0f);
        progress = findViewById(R.id.firstest);
        progress.setText(listenerSeekBar.getProgress() + "");

        listenerSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                progress.setText(seekParams.progress + "");
                firstable = seekParams.progress;
                SharedPreferences.Editor editor = getSharedPreferences("size_of_bar", MODE_PRIVATE).edit();
                editor.putInt("username", firstable);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(TickSeekBar seekBar) {
                progress.setText(seekBar.getProgress() + "");

                firstable = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(TickSeekBar seekBar) {
                progress.setText(seekBar.getProgress() + "");


            }
        });

        GoCheck();

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


        logogogo = findViewById(R.id.logogogo);
        search_view = findViewById(R.id.search_view);
        search_view.setCurrentItem(0);
        search_view.setOffscreenPageLimit(2);
        setupViewPager2(search_view);

        podstilka = findViewById(R.id.podstilka);

        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewPager.setCurrentItem(1);

            }
        });


        mVideoView = findViewById(R.id.videoView);


        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {


                if (position == 0) {
                    appBarLayout.setExpanded(true, true);
                    navigation.getMenu().getItem(0).setChecked(true);
                    appBarLayout.setVisibility(View.VISIBLE);
                }
                else if (position == 1) {
                    navigation.getMenu().getItem(1).setChecked(true);
                    appBarLayout.setExpanded(false, true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            appBarLayout.setVisibility(View.GONE);
                        }
                    }, 250);

                }
                else if (position == 2) {
                    appBarLayout.setExpanded(true, true);
                    navigation.getMenu().getItem(2).setChecked(true);
                    appBarLayout.setVisibility(View.VISIBLE);
                }
                else if (position == 3) {
                    appBarLayout.setExpanded(true, true);
                    navigation.getMenu().getItem(3).setChecked(true);
                    appBarLayout.setVisibility(View.VISIBLE);
                }
                else if (position == 4) {
                    appBarLayout.setExpanded(false, true);
                    navigation.getMenu().getItem(4).setChecked(true);
                    appBarLayout.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            mViewPager.setCurrentItem(1);
        } else {
            onBackPressed();


        }
        if (MediaPlayerManager.instance().backPress()) {
            return;
        }


    }

    public void GoCheck() {
        new Registering().execute();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.navigation_home:
                appBarLayout.setExpanded(true, true);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.navigation_dashboard:
                appBarLayout.setExpanded(false, true);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.navigation_notifications:
                appBarLayout.setExpanded(true, true);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.podborki:
                appBarLayout.setExpanded(true, true);
                mViewPager.setCurrentItem(3);
                break;
            case R.id.user_profile:
                appBarLayout.setExpanded(false, true);
                mViewPager.setCurrentItem(4);

                break;
        }

        return true;
    }

    static class Registering extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);

            try {
                Connection.Response res = Jsoup.connect("https://hello.tj/index.php/auth")
                        .data("mobile2", "985602001")
                        .data("password2", "planeta...333")
                        .data("checkbox1", "1")
                        .data("enter_button", "1")
                        .method(Connection.Method.POST)
                        .execute();

                Document doc = res.parse();
                String sessionId = res.cookie("sessionVal");
                Log.d("TAGsssssss", "doInBackground: " + sessionId);

                Document doc2 = Jsoup.connect("http://alfa.tj/")
                        .cookie("sessionVal", sessionId)
                        .get();

                Log.d("Googlegg", "doInBackground: " + doc2.text());

/*
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
*/

            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
         /*
            if (!VideoUrlStream.equals("{full_link_to_video}")) {
                Toast.makeText(getApplicationContext(), VideoUrlStream, Toast.LENGTH_LONG).show();
                playVideo();
            }
            else{
                new GetContacts2().execute();
            }

          */
        }


    }

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


                Document doc = Jsoup.connect("https://alfa.tj/smart/player/" + url).get();

                Elements hrefElements = doc.select("div.col-2.movieListPlay");

                size = hrefElements.size();
                titles = new String[size];
                Log.d("whaaaat", "doInBackground: " + size);

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


}
