package com.side.lumies;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;


public class Four extends Fragment {

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    public int size = 0;
    public boolean a = false;
    int s;
    RelativeLayout vsyo;
    Parcelable savedRecyclerLayoutState;
    int rand = 0;
    String[] hrefs;
    String[] titles;
    String[] imagesLink1;
    String[] clocks;
    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> imageLink1 = new ArrayList<>();
    ArrayList<String> channel = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    RelativeLayout progressBar;
    ProgressBar progress_circular;
    private RecyclerView recyclerView;
    private RecyclerAdapter4 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public Four() {

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    public void clear() {
        vsyo.setVisibility(View.GONE);
        href.clear();
        title.clear();
        imageLink1.clear();
        channel.clear();
        //  view.clear();
        clock.clear();
        progressBar.setVisibility(View.INVISIBLE);
        int size = mAdapter.getItemCount();
        mAdapter.notifyItemRangeRemoved(0, size);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_four, container, false);
        progressBar = v.findViewById(R.id.progressBar);
        progress_circular = v.findViewById(R.id.progress_circular);

        vsyo = v.findViewById(R.id.naetomvsyo);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView = v.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);
        new Size().execute();

        mAdapter = new RecyclerAdapter4(getActivity().getApplicationContext(), href, title, imageLink1, channel, clock, clock, new RecyclerAdapter4.MyAdapterListener() {
            @Override
            public void itemClick(View v, int position, String name, String name2, String name3) {

                Intent intent = new Intent(getActivity(), VideoViewActivity.class);
                intent.putExtra("hrefff", name2);
                startActivity(intent);

            }
        });


        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    vsyo.setVisibility(View.VISIBLE);
                    hideProgressView();

                }

            }
        });


        return v;
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    class Size extends AsyncTask<Void, Void, Void> {

        Document doc2;
        int size = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            a = true;

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

                String sessionId = res.cookie("sessionVal");

                Log.d("TAGsssssss", "doInBackground: " + sessionId);

                doc2 = Jsoup.connect("http://alfa.tj/smart/watched").cookie("sessionVal", sessionId).get();
                Elements userName = doc2.select("tr");
                size = userName.size();


            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hrefs = new String[size - 1];
            titles = new String[size - 1];
            imagesLink1 = new String[size - 1];
            clocks = new String[size - 1];
            new GetContacts().execute();
        }


    }

    class GetContacts extends AsyncTask<Void, Void, Void> {

        Document doc2;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            a = true;

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

                String sessionId = res.cookie("sessionVal");

                Log.d("TAGsssssss", "doInBackground: " + sessionId);

                doc2 = Jsoup.connect("http://alfa.tj/smart/watched").cookie("sessionVal", sessionId).get();
                Elements userName = doc2.select("tr");


                Elements hrefElements = doc2.select("th");
                Elements hrefElements2 = doc2.select("td");
                Elements hrefElements3 = doc2.select("tr");
                // Elements hrefElements4 = doc2.select("span.year");
                // Elements hrefElements5 = doc2.select("div.info");
                Elements hrefElements6 = doc2.select("td");

                size = hrefElements6.size();

                for (int i = 0; i < userName.size() - 1; i++) {

                    hrefs[i] = hrefElements.get(i + 4).getElementsByTag("a").attr("href");
                    titles[i] = hrefElements2.get(i * 3).text();
                    imagesLink1[i] = hrefElements3.get(i + 1).getElementsByTag("img").attr("src");
                    clocks[i] = hrefElements6.get(i * 3 + 2).text();


                }

                href.addAll(Arrays.asList(hrefs));
                title.addAll(Arrays.asList(titles));
                imageLink1.addAll(Arrays.asList(imagesLink1));
                // channel.addAll(Arrays.asList(channels));
                // view.addAll(Arrays.asList(views));
                clock.addAll(Arrays.asList(clocks));


            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progress_circular.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            a = false;
            hideProgressView();
            mAdapter.notifyDataSetChanged();


        }


    }


}