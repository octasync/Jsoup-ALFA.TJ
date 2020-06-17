package com.side.lumies;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Five extends Fragment {


    public Five() {
        // Required empty public constructor
    }
    private EditText username;
    private EditText password;
    Button btn_login;
    private String username1, password1;
    private View v;

    CoordinatorLayout entered;
    RelativeLayout enter, registr, forgot_password;
    TextView textView, email;
    CircleImageView circleImageView;

    ImageView edit, more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         v = inflater.inflate(R.layout.fragment_five, container, false);

        final RelativeLayout name = (RelativeLayout) v.findViewById(R.id.play);
        final RelativeLayout text = (RelativeLayout)v. findViewById(R.id.rela);

        final RelativeLayout text2 = (RelativeLayout)v. findViewById(R.id.relajon);

        final AppBarLayout layout = (AppBarLayout) v.findViewById(R.id.app_bar);
        layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                text.setAlpha(1.0f - Math.abs(verticalOffset / (float) appBarLayout.getTotalScrollRange()*1.8f));

                final int scrollRange = appBarLayout.getTotalScrollRange();

                float offsetFactor = (float) (-verticalOffset) / (float) scrollRange;

                float scaleFactor = 1F - offsetFactor * .6F ;
                name.setScaleX(scaleFactor);
                name.setScaleY(scaleFactor);




            }
        });

        textView = v.findViewById(R.id.text);
        circleImageView = v.findViewById(R.id.firstWord);
        email = v.findViewById(R.id.email);
        more = v.findViewById(R.id.more);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        edit  =v.findViewById(R.id.edit);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mix.tj/person/edcrfv"));
                startActivity(browserIntent);
            }
        });


        registr = v.findViewById(R.id.registr);

        registr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mix.tj/go/register"));
                startActivity(browserIntent);
            }
        });

        forgot_password = v.findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mix.tj/go/lostpassword"));
                startActivity(browserIntent);

            }
        });

        entered = v.findViewById(R.id.entered);
        enter = v.findViewById(R.id.enter);

        username = v.findViewById(R.id.username);
        password = v.findViewById(R.id.password);
        btn_login = v.findViewById(R.id.btn_login);



        SharedPreferences prefs = getActivity().getSharedPreferences("My_pref", MODE_PRIVATE);
        username1 = prefs.getString("username", "");//"No name defined" is the default value.
        password1= prefs.getString("password", "");//"No name defined" is the default value.

        ((MainActivity)getActivity()).username11 = username1;
        ((MainActivity)getActivity()).password11 = password1;



        if (username1.equals("")){
            Firstable(username1, password1);
        }
        else{
            Secondable(username1, password1);
        }


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("My_pref", MODE_PRIVATE).edit();
                editor.putString("username", "");
                editor.putString("password", "");
                editor.apply();
                username1 = "";
                password1 = "";
                Firstable(username1, password1);
                Toast.makeText(getActivity(), "Выход из аккаунта успешно выполнено", Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = username.getText().toString();
                password1 = password.getText().toString();
                try {

                    Connection.Response loginForm = Jsoup.connect("https://hello.tj/index.php/auth")
                            .method(Connection.Method.GET)
                            .execute();

                    Document document = Jsoup.connect("https://hello.tj/index.php/auth")
                            .followRedirects(true)
                            .data("mobile4", "985602001")
                            .data("password2", "planeta...333")
                            .data("checkbox1", "1")
                            .data("enter_button", "1")
                            .cookies(loginForm.cookies())
                            .post();
                    Log.d("TAGssssss", "onClick: "+document);

/*
                    Element hrefElements2 = document.select("div.userSmallMenuName").first();

                    String s = hrefElements2.text();
                    Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
                    if (s!=null){
                        Toast.makeText(getActivity(), "Неправильно введён логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Вход выполнен!", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("My_pref", MODE_PRIVATE).edit();
                        editor.putString("username", username1);
                        editor.putString("password", password1);
                        editor.apply();
                        ((MainActivity)getActivity()).username11 = username1;
                        ((MainActivity)getActivity()).password11 = password1;
                        Secondable(username1, password1);


                    }
 */


                } catch (
                        IOException e) {
                    e.getMessage();
                }


            }
        });




        return v;
    }
    String name, image, emails;

    public void Secondable(String s1, String s2){
        entered.setVisibility(View.VISIBLE);
        enter.setVisibility(View.GONE);
        String s;
        try {

            Connection.Response loginForm = Jsoup.connect("https://hello.tj/index.php/auth")
                    .method(Connection.Method.GET)
                    .execute();

            Document document = Jsoup.connect("https://hello.tj/index.php/auth")
                    .data("mobile2", username1)
                    .data("password2", password1)
                    .data("checkbox1", "1")
                    .data("enter_button", "1")
                    .cookies(loginForm.cookies())
                    .post();

                Element hrefElements2 = document.select("div.userSmallMenuName").first();

            String s3 = hrefElements2.text();
            if (s3!=null){
                Toast.makeText(getActivity(), "Неправильно введён логин или пароль", Toast.LENGTH_SHORT).show();
                Firstable(s1, s2);
            }
            else{
                Toast.makeText(getActivity(), "Вход выполнен!", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("My_pref", MODE_PRIVATE).edit();
                editor.putString("username", username1);
                editor.putString("password", password1);
                editor.apply();
            }

/*
            Document doc = Jsoup.connect().get();

        Elements hrefElements = doc.select("div.channel-title.dark-div");
        Elements hrefElements22 = doc.select("div.channel-banner");
        Element hrefElements3 = doc.select("div.prev-post").first();


        name = hrefElements.get(0).getElementsByTag("h1").text();
        image = hrefElements22.get(0).getElementsByTag("img").attr("src");
        emails = hrefElements3.getElementsByTag("label").text();


*/


        } catch (
                IOException e) {
            e.getMessage();
        }

/*
        int index = emails.indexOf(' ');
        emails = emails.substring(index+1, emails.length());
        int index2 = emails.indexOf(' ');
        emails = emails.substring(0, index2);

        textView.setText(name);
        email.setText(emails);

        if (image.substring(0, 1).equals("/")) {
            Picasso.get().load("http://mix.tj" + image).into(circleImageView);
        } else {
            Picasso.get().load(image).into(circleImageView);
        }
*/
    }
    public void Firstable(String s1, String s2){
        entered.setVisibility(View.GONE);
        enter.setVisibility(View.VISIBLE);


    }



}
