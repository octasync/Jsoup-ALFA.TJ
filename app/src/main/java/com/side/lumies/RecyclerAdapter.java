package com.side.lumies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> channels = new ArrayList<>();
    ArrayList<String> views = new ArrayList<>();
    ArrayList<String> clocks = new ArrayList<>();
    Context context;


    public MyAdapterListener onClickListener;


    public RecyclerAdapter(Context applicationContext, ArrayList<String> id, ArrayList<String> title, ArrayList<String> description, ArrayList<String> times,
                           ArrayList<String> channels, ArrayList<String> views, ArrayList<String> clocks, MyAdapterListener listener) {

        this.context = applicationContext;
        this.id = id;
        this.title = title;
        this.description = description;
        this.times = times;
        this.channels = channels;
        this.views = views;
        this.clocks = clocks;
        onClickListener = listener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView idTextView;

        public CircleImageView imageChannel;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView time;

        public TextView nameOfChannel;


        public ViewHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.image);

            imageChannel = itemView.findViewById(R.id.image2);
            titleTextView = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.timeLapse);
            descriptionTextView = itemView.findViewById(R.id.description);

            nameOfChannel = itemView.findViewById(R.id.nameOfChannel);





        }
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        RecyclerAdapter.ViewHolder viewHolder = new RecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {




                String s = description.get(position).substring(0, 1);
                if (s.equals("/")) {
                    Picasso.get().load("http://mix.tj" + description.get(position)).into(holder.idTextView);
                } else {
                    Picasso.get().load(description.get(position)).into(holder.idTextView);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.itemClick(view, position, id.get(position), title.get(position), channels.get(position), getSees(views.get(position)),
                                getClocks(clocks.get(position)), views.get(position), clocks.get(position));
                    }
                });
                holder.titleTextView.setText(channels.get(position) + "  ·  " + getSees(views.get(position)) + "  ·  " + getClocks(clocks.get(position)));
                holder.time.setText(times.get(position));
                holder.descriptionTextView.setText(title.get(position));



    }

    @Override
    public int getItemCount() {
        return id.size();
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