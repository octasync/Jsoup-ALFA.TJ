package com.side.lumies.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.side.lumies.R;
import com.side.lumies.SplashActivity;


public class IntroActivity extends AppCompatActivity {


    boolean s = false;

    @NonNull
    private final ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public
        void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

        }

        @Override
        public
        void onPageSelected(final int position) {
        }

        @Override
        public
        void onPageScrollStateChanged(final int state) {

        }
    };


    Button buttonPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ViewPagerIndicator viewPagerIndicator = (ViewPagerIndicator)findViewById(R.id.view_pager_indicator);

        final ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);

        buttonPanel = (Button)findViewById(R.id.buttonPanel);

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Frag1());
        adapter.addFragment(new Frag2());
        adapter.addFragment(new Frag3());
        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);

        viewPagerIndicator.setupWithViewPager(mViewPager);
        viewPagerIndicator.addOnPageChangeListener(mOnPageChangeListener);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    buttonPanel.setText("ПРОДОЛЖИТЬ");
                }
                else if (position==1){
                    buttonPanel.setText("ПРОДОЛЖИТЬ");
                }
                else if (position==2){

                    buttonPanel.setText("Начать");
                }


            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        buttonPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem()==0){
                    mViewPager.setCurrentItem(1);
                }
                else if (mViewPager.getCurrentItem()==1){
                    mViewPager.setCurrentItem(2);
                    buttonPanel.setText("Начать");
                }
                else if (mViewPager.getCurrentItem()==2){
                    if (s) {
                        Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
                        intent.putExtra("getChoose", "true");
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
                        intent.putExtra("getChoose", "false");
                        startActivity(intent);
                    }
                }
            }
        });



    }



}
