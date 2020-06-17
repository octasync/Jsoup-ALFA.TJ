package com.side.lumies;


import android.app.ProgressDialog;
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
import android.widget.Toast;

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
public class Two extends Fragment {

    int s;
    public int size = 0;
    RelativeLayout vsyo;
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


    int rand = 0;

    String[] hrefs = new String[12];
    String[] titles = new String[12];
    String[] imagesLink1 = new String[12];
    String[] channels = new String[12];
    String[] views = new String[12];
    String[] clocks = new String[12];

    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> imageLink1 = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> channel = new ArrayList<>();
    ArrayList<String> view = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    private ProgressDialog pDialog;
    RelativeLayout progressBar;
    ProgressBar progress_circular;


    String main = "https://alfa.tj/smart/category/0/";
    String viseoUrl = "";
    int page = 1;
    RelativeLayout ite1, item1, item2, item3, item4, item5;
    public boolean a = false;

    public Two() {

    }

    public void clear() {
        vsyo.setVisibility(View.GONE);
        href.clear();
        title.clear();
        imageLink1.clear();
        channel.clear();
        view.clear();
        clock.clear();
        progressBar.setVisibility(View.INVISIBLE);
        int size = mAdapter.getItemCount();
        mAdapter.notifyItemRangeRemoved(0, size);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        progressBar = v.findViewById(R.id.progressBar);
        progress_circular = v.findViewById(R.id.progress_circular);

        vsyo = v.findViewById(R.id.naetomvsyo);
        ite1 = v.findViewById(R.id.ite1);

        ite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                main = "https://alfa.tj/smart/category/0/";
                page = 1;
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        item1 = v.findViewById(R.id.item1);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/8/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        item2 = v.findViewById(R.id.item2);

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/9/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        item3 = v.findViewById(R.id.item3);

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/10/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        item4 = v.findViewById(R.id.item4);

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/12/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        item5 = v.findViewById(R.id.item5);

        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/13/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        RelativeLayout item6 = v.findViewById(R.id.item6);

        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/14/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });

        RelativeLayout item7 = v.findViewById(R.id.item7);

        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/15/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item8 = v.findViewById(R.id.item8);

        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/16/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item9 = v.findViewById(R.id.item9);

        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/17/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item10 = v.findViewById(R.id.item10);

        item10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/18/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item11 = v.findViewById(R.id.item11);

        item11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/19/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item12 = v.findViewById(R.id.item12);

        item12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/23/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item13 = v.findViewById(R.id.item13);

        item13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/24/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item14 = v.findViewById(R.id.item14);

        item14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/25/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item15 = v.findViewById(R.id.item15);

        item15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/26/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item16 = v.findViewById(R.id.item16);

        item16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/27/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item17 = v.findViewById(R.id.item17);

        item17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/28/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item18 = v.findViewById(R.id.item18);

        item18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/29/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item19 = v.findViewById(R.id.item19);

        item19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/30/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item20 = v.findViewById(R.id.item20);

        item20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/31/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item21 = v.findViewById(R.id.item21);

        item21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/33/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item22 = v.findViewById(R.id.item22);

        item22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/35/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item23 = v.findViewById(R.id.item23);

        item23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/36/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });
        RelativeLayout item24 = v.findViewById(R.id.item24);

        item24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                page = 1;
                main = "https://alfa.tj/smart/category/51/";
                new GetContacts().execute();
                page = 1;
                recyclerView.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);
                progress_circular.setVisibility(View.VISIBLE);
                ((MainActivity)getActivity()).appBarLayout.setExpanded(true,true);

            }
        });




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView = v.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.smoothScrollToPosition(0);
        new GetContacts().execute();

        mAdapter = new RecyclerAdapter2(getActivity().getApplicationContext(),href,  title,imageLink1, channel  ,view,  clock, new RecyclerAdapter2.MyAdapterListener() {
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

                if (!a&&size!=0){
                    new GetContacts().execute();
                    Log.d("oks", "ok");
                }
                else if (size==0&&!recyclerView.canScrollVertically(1)) {
                    vsyo.setVisibility(View.VISIBLE);
                    hideProgressView();

                }
                else if (!recyclerView.canScrollVertically(1)) {
                    showProgressView();
                    if (!a&&size!=0) {
                        new GetContacts().execute();
                    }
                }




            }
        });


        return v;
    }





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




                Document doc = Jsoup.connect(main+pageNum).get();
                Elements hrefElements = doc.select("div.moviePlayPlus");
                Elements hrefElements2 = doc.select("div.info");
                Elements hrefElements3 = doc.select("div.poster");
                Elements hrefElements4 = doc.select("span.year");
                Elements hrefElements5 = doc.select("div.info");
                Elements hrefElements6 = doc.select("div.movie-age");

                size = hrefElements6.size();
                Log.d("whaaaat", "doInBackground: "+size);

                for (int i = 0; i < hrefElements6.size(); i++) {

                    hrefs[i] = hrefElements.get(i).getElementsByTag("a").attr("href");
                    titles[i] = hrefElements2.get(i).getElementsByTag("h1").text();
                    imagesLink1[i] = hrefElements3.get(i).getElementsByTag("img").attr("src");
                    channels[i] = hrefElements4.get(i).text();
                    views[i] = hrefElements5.get(i).getElementsByTag("p").text();
                    clocks[i] = hrefElements6.get(i).text();




                }

                href.addAll(Arrays.asList(hrefs));
                title.addAll(Arrays.asList(titles));
                imageLink1.addAll(Arrays.asList(imagesLink1));
                channel.addAll(Arrays.asList(channels));
                view.addAll(Arrays.asList(views));
                clock.addAll(Arrays.asList(clocks));


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
            recyclerView.setVisibility(View.VISIBLE);
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