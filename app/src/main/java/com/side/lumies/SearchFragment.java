package com.side.lumies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;
import static android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

public class SearchFragment extends Fragment implements RecyclerAdapterSearch.ItemClickListener {

    ImageView back, search;
    EditText search_edit_frame;
    String[] hrefs;
    String[] titles;
    String[] imagesLink1;
    String[] times;
    String[] channels;
    String[] views;
    String[] clocks;
    String[] video_streams;
    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> imageLink1 = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> channel = new ArrayList<>();
    ArrayList<String> view = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    ArrayList<String> video_stream = new ArrayList<>();
    ArrayList<String> searchd_elem = new ArrayList<>();
    RecyclerAdapterSearch adapter;
    ArrayList<String> animalNames = new ArrayList<>();
    boolean process = false;
    RelativeLayout progressBar, vsyo;
    ProgressBar progress_circular;
    String search_url;
    int size = -1;
    int page = 1;
    ConstraintLayout main2;
    RecyclerView recyclerView2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public SearchFragment() {

    }

    @Override
    public void onItemClick(View view, String s) {
        search_edit_frame.setText(s);
        search_edit_frame.setSelection(s.length());
        clear();
        search_url = s;
        search_url = search_url.replace(" ", "%20");
        Toast.makeText(getActivity(), search_url, Toast.LENGTH_SHORT).show();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        page = 1;
        new LapLapi().execute();
        recyclerView2.setVisibility(View.GONE);
        progress_circular.setVisibility(View.VISIBLE);
        main2.setVisibility(View.VISIBLE);
    }


    @Override
    public void onItemClick2(View view, String s) {
        search_edit_frame.setText(s);
        search_edit_frame.setSelection(s.length());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        main2 = (ConstraintLayout) v.findViewById(R.id.main2);
        back = (ImageView) v.findViewById(R.id.back);
        search = (ImageView) v.findViewById(R.id.search);
        search_edit_frame = (EditText) v.findViewById(R.id.search_edit_frame);
        vsyo = (RelativeLayout) v.findViewById(R.id.naetomvsyo);


        SharedPreferences sharedPrefs2 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson2 = new Gson();
        String json2 = sharedPrefs2.getString("Tags", "");
        Type type = new TypeToken<List<String>>() {
        }.getType();
        ArrayList<String> arrayList = gson2.fromJson(json2, type);


        if (arrayList != null) {
            animalNames = arrayList;
        }

        recyclerView2 = v.findViewById(R.id.recycler_view2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView2.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerAdapterSearch(getActivity(), animalNames);
        adapter.setClickListener(this);


        recyclerView2.setAdapter(adapter);

        search_edit_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress_circular.setVisibility(View.INVISIBLE);
                main2.setVisibility(View.INVISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);
            }
        });


        search_edit_frame.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (search_edit_frame.getText().toString().length() != 0) {
                        animalNames.add(search_edit_frame.getText().toString());
                        adapter.notifyDataSetChanged();
                        recyclerView2.smoothScrollToPosition(adapter.getItemCount());
                        if (animalNames.size() > 40) {
                            animalNames.remove(animalNames.size() - 41);
                        }
                    }

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(animalNames);
                    editor.putString("Tags", json);
                    editor.commit();


                    clear();
                    search_url = search_edit_frame.getText().toString();
                    search_url = search_url.replace(" ", "%20");
                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                    page = 1;
                    new LapLapi().execute();
                    recyclerView2.setVisibility(View.GONE);
                    progress_circular.setVisibility(View.VISIBLE);
                    main2.setVisibility(View.VISIBLE);



                    return true;
                }
                return false;
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (main2.getVisibility() == View.VISIBLE) {
                    recyclerView2.setVisibility(View.VISIBLE);
                    progress_circular.setVisibility(View.INVISIBLE);
                    main2.setVisibility(View.INVISIBLE);
                } else {
                    ((MainActivity) getActivity()).mViewPager.setCurrentItem(1);
                }
            }
        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (search_edit_frame.getText().toString().length() != 0) {
                    animalNames.add(search_edit_frame.getText().toString());
                    adapter.notifyDataSetChanged();
                    recyclerView2.smoothScrollToPosition(adapter.getItemCount());
                    if (animalNames.size() > 40) {
                        animalNames.remove(animalNames.size() - 41);
                    }
                }

                SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPrefs.edit();
                Gson gson = new Gson();

                String json = gson.toJson(animalNames);

                editor.putString("Tags", json);
                editor.commit();
                clear();

                search_url = search_edit_frame.getText().toString();
                search_url = search_url.replace(" ", "%20");
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                page = 1;
                new LapLapi().execute();
                recyclerView2.setVisibility(View.GONE);
                progress_circular.setVisibility(View.VISIBLE);
                main2.setVisibility(View.VISIBLE);



            }
        });


        progressBar = v.findViewById(R.id.progressBar);
        progress_circular = v.findViewById(R.id.progress_circular);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = v.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVerticalScrollBarEnabled(false);


        mAdapter = new RecyclerAdapter2(getActivity().getApplicationContext(), href, title, imageLink1, channel, view, clock, new RecyclerAdapter2.MyAdapterListener() {
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


    public void clear() {
        vsyo.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        int size = mAdapter.getItemCount();
        mAdapter.notifyItemRangeRemoved(0, size);
        href.clear();
        title.clear();
        imageLink1.clear();
        time.clear();
        channel.clear();
        view.clear();
        clock.clear();
        video_stream.clear();
    }

    public void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    class LapLapi2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            process = true;


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);


            try {


                Document doc = Jsoup.connect("http://alfa.tj/index.php/smart/search?q=" + search_url).get();
//                Toast.makeText(getContext(), search_url, Toast.LENGTH_SHORT).show();


                Elements hrefElements = doc.select("div.moviePlayPlus");
                Elements hrefElements2 = doc.select("div.info");
                Elements hrefElements3 = doc.select("div.poster");
                Elements hrefElements4 = doc.select("span.year");
                Elements hrefElements5 = doc.select("div.info");
                Elements hrefElements6 = doc.select("div.movie-age");

                size = hrefElements6.size();
                Log.d("whaaaat", "doInBackground: " + size + " " + search_url);

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
            process = false;
            progress_circular.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            hideProgressView();
            mAdapter.notifyDataSetChanged();


        }


    }

    class LapLapi extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            process = true;


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE);

            try {

                Document doc = Jsoup.connect("http://alfa.tj/index.php/smart/search?q=" + search_url).get();
                Elements hrefElements6 = doc.select("div.movie-age");
                size = hrefElements6.size();


            } catch (IOException e) {
                e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            hrefs = new String[size];
            titles = new String[size];
            imagesLink1 = new String[size];
            times = new String[size];
            channels = new String[size];
            views = new String[size];
            clocks = new String[size];
            new LapLapi2().execute();

        }


    }


}