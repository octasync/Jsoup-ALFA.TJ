package com.side.lumies;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.salient.artplayer.MediaPlayerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class One extends Fragment {




    Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";


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


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Parcelable recyclerViewState;


    int rand = 0;

    String[] hrefs = new String[24];
    String[] titles = new String[24];
    String[] imagesLink1 = new String[24];
    String[] times = new String[24];
    String[] channels = new String[24];
    String[] views = new String[24];
    String[] clocks = new String[24];
    String[] icons = new String[24];

    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> imageLink1 = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> channel = new ArrayList<>();
    ArrayList<String> view = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    ArrayList<String> icon = new ArrayList<>();

    private ProgressDialog pDialog;
    RelativeLayout progressBar;
    ProgressBar progress_circular;


    String main = "http://mix.tj/cat/all/top?page=";
    String viseoUrl = "";
    int page = 1;

    public One() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);






        progressBar = v.findViewById(R.id.progressBar);
        progress_circular = v.findViewById(R.id.progress_circular);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView = v.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);
        new GetContacts().execute();


        mAdapter = new RecyclerAdapter(getActivity().getApplicationContext(), href, title, imageLink1, time, channel, view, clock,new RecyclerAdapter.MyAdapterListener() {
            @Override
            public void itemClick(View v, int position, String name, String name3, String name4, String views, String name6, String name7, String name8) {

                MediaPlayerManager.instance().pause();
                ((MainActivity) getActivity()).url = name;



                //((MainActivity) getActivity()).VideoUrlStream = name9;

                ((MainActivity) getActivity()).firstDate = views;
                ((MainActivity) getActivity()).secondDate = name7;

            }
        });

        ((MainActivity)getActivity()).logogogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recyclerView.smoothScrollToPosition(0);

            }
        });

        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!a) {
                   new GetContacts().execute();

                }
                if (!recyclerView.canScrollVertically(1)&&!a) {
                    showProgressView();
                    new GetContacts().execute();

                }
                if (!recyclerView.canScrollVertically(1)) {
                    showProgressView();

                }

            }
        });


        return v;
    }

    public boolean a = false;

    class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            a = true;

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);


            String pageNum = String.valueOf(page);

            try {


                Connection.Response loginForm = Jsoup.connect("http://mix.tj/go/enter")
                        .method(Connection.Method.GET)
                        .execute();

                Document document = Jsoup.connect("http://mix.tj/go/enter")
                        .data("login_name", "gulov2001@list.ru")
                        .data("login_password", "planeta...333")
                        .data("login", "submit")
                        .data("send_btn", "")
                        .cookies(loginForm.cookies())
                        .post();
                System.out.println(document);


                Document doc = Jsoup.connect(main + pageNum).get();
                Elements hrefElements = doc.select("div.picture-content");
                Elements hrefElements2 = doc.select("div.content");
                Elements hrefElements3 = doc.select("div.mixx-note.ct-time.font-size-1");
                Elements hrefElements4 = doc.select("a.author.mixx-info.font-size-1");
                Elements hrefElements5 = doc.select("div.view.mixx-info.font-size-1");
                Elements hrefElements6 = doc.select("time.entry-date.updated");


                for (int i = 0; i < 24; i++) {

                        hrefs[i] = hrefElements.get(i).getElementsByTag("a").attr("href");
                        titles[i] = hrefElements2.get(i).getElementsByTag("h3").text();
                    imagesLink1[i] = hrefElements.get(i).getElementsByTag("img").attr("data-src");
                    times[i] = hrefElements3.get(i).getElementsByTag("span").text();
                    channels[i] = hrefElements4.get(i).getElementsByTag("span").text();
                    views[i] = hrefElements5.get(i).getElementsByTag("span").text();
                    clocks[i] = hrefElements6.get(i).text();




                }

                href.addAll(Arrays.asList(hrefs));
                title.addAll(Arrays.asList(titles));
                imageLink1.addAll(Arrays.asList(imagesLink1));
                time.addAll(Arrays.asList(times));
                channel.addAll(Arrays.asList(channels));
                view.addAll(Arrays.asList(views));
                clock.addAll(Arrays.asList(clocks));
                icon.addAll(Arrays.asList(icons));


            } catch (IOException e) {
                e.getMessage();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            page++;
            progress_circular.setVisibility(View.GONE);
            a = false;
            hideProgressView();
            mAdapter.notifyDataSetChanged();


        }


    }

    public void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }




}