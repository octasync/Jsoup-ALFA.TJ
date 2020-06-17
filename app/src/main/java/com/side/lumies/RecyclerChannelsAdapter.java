package com.side.lumies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerChannelsAdapter extends RecyclerView.Adapter<RecyclerChannelsAdapter.ViewHolder> {

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> channels = new ArrayList<>();
    ArrayList<String> views = new ArrayList<>();
    Context context;


    public MyAdapterListener onClickListener;


    public RecyclerChannelsAdapter(Context applicationContext, ArrayList<String> title, ArrayList<String> times,
                                   ArrayList<String> channels, ArrayList<String> views, MyAdapterListener listener) {

        this.context = applicationContext;
        this.title = title;
        this.times = times;
        this.channels = channels;
        this.views = views;
        onClickListener = listener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView imageChannel;
        public TextView description;
        public TextView dops;
        public TextView videos;
        public TextView subscribes;


        public ViewHolder(View itemView) {
            super(itemView);

            imageChannel = itemView.findViewById(R.id.image);
            dops = itemView.findViewById(R.id.dops2);
            description= itemView.findViewById(R.id.description);
            videos = itemView.findViewById(R.id.nameOfChannel);


        }
    }
    @Override
    public RecyclerChannelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view5, parent, false);

        RecyclerChannelsAdapter.ViewHolder viewHolder = new RecyclerChannelsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        int index = title.get(position).indexOf(' ');
        holder.description.setText(title.get(position).substring(0, index));

        if (views.get(position).substring(0, 1).equals("/")) { Picasso.get().load("http://mix.tj" + views.get(position)).into(holder.imageChannel); }

        else{ Picasso.get().load(views.get(position)).into(holder.imageChannel); }


        holder.videos.setText(channels.get(position)+" подписчиков");


        holder.dops.setText(times.get(position));




    }

    @Override
    public int getItemCount() {
        return views.size();
    }

    public interface MyAdapterListener {

        void itemClick(View v, int position, String name, String name3, String name4, String name5, String name6, String name7, String name8);
    }


    public String getSees(String s) {

        int num = Integer.parseInt(s);
        String l;

        int first = num/1000;


        if (first == 0){
            l = s;

            if (l.substring(l.length()-1).equals("2")){
                l = s+" просмотра";
            }
            else if (l.substring(l.length()-1).equals("3")){
                l = s+" просмотра";
            }
            else if (l.substring(l.length()-1).equals("1")){
                l = s+" просмотр";
            }
            else if (l.substring(l.length()-1).equals("4")){
                l = s+" просмотра";
            }
            else l = s+" просмотров";


        }
        else {
            l = first + " тыс. просмотров";
        }

        return l;
    }

    public String getClocks(String s){

        String vremya = s;
        String showV = "";
        if (vremya.substring(0,1).equals("В")){
            showV = "1 день назад";
        }
        else if (vremya.substring(0,1).equals("С")){
            //showV = "Сегодня";

            String todays = s;

            try {
                String CurrDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String PrvvDate= todays.substring(todays.length()-5);
                Date date1 = null;
                Date date2 = null;
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                date1 = df.parse(CurrDate);
                date2 = df.parse(PrvvDate);
                long diff = Math.abs(date1.getTime() - date2.getTime());
                long diffHours = diff/(60*60*1000);
                long diffMinutes = diff/(60*1000);

                if (diffHours>0){
                    if (diffHours==1){
                        showV = diffHours+" час назад";
                    }
                    else if (diffHours>1&&diffHours<5){
                        showV = diffHours+" часа назад";
                    }
                    else if (diffHours>4){
                        showV = diffHours+" часов назад";
                    }
                }
                else if (diffHours==0){
                    if (diffMinutes%10==2){
                        showV = diffMinutes+" минуты назад";
                    }
                    else if (diffMinutes%10==3){
                        showV = diffMinutes+" минуты назад";
                    }
                    else if (diffMinutes%10==4){
                        showV = diffMinutes+" минуты назад";
                    }
                    else if (diffMinutes==1){
                        showV = "Минуту назад";
                    }
                    else {
                        showV = diffMinutes+" минут назад";
                    }

                }





            }
            catch (Exception e1) {
                System.out.println("exception " + e1);
            }




        }
        else {
            try {
                String CurrDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
                String PrvvDate =  s;
                Date date1 = null;
                Date date2 = null;
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                date1 = df.parse(CurrDate);
                date2 = df.parse(PrvvDate);
                long diff = Math.abs(date1.getTime() - date2.getTime());
                long diffHours = diff/(60*60*1000);
                long diffMinutes = diff/(60*1000);
                long diffDays = diff / (24 * 60 * 60 * 1000);

                if (diffDays<5){
                    showV = diffDays+" дня назад";
                }
                else if (diffDays>=5&&diffDays<7){
                    showV = diffDays+" дней назад";
                }
                else if (diffDays>=7&&diffDays<=32){

                    if (diffDays/7==1){
                        showV = "Неделю назад";
                    }
                    else {
                        showV = diffDays/7+" недели назад";
                    }

                }






                else if (diffDays>=32&&diffDays<=366){
                    if (diffDays/30>1&&diffDays/30<5){
                        showV = diffDays/30+" месяца назад";
                    }
                    else if (diffDays/30==1){
                        showV = diffDays/30+" месяц назад";
                    }
                    else {
                        showV = diffDays / 30 + " месяцев назад";
                    }
                }
                else{
                    if (diffDays/366==1){
                        showV = "Год назад";
                    }
                    else if (diffDays/366<5){
                        showV = diffDays/366 + " года назад";
                    }
                    else {
                        showV = diffDays/366 + " лет назад";
                    }
                }





            } catch (Exception e1) {
                System.out.println("exception " + e1);
            }


        }



        return showV;
    }

}