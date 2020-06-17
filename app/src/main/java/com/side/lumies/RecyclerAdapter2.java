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

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> href = new ArrayList<>();
    ArrayList<String> imagesLink = new ArrayList<>();
    ArrayList<String> channel = new ArrayList<>();
    ArrayList<String> view = new ArrayList<>();
    ArrayList<String> clock = new ArrayList<>();
    Context context;


    public MyAdapterListener onClickListener;


    public RecyclerAdapter2(Context applicationContext,  ArrayList<String> href,ArrayList<String> title,ArrayList<String> imagesLink, ArrayList<String> channel,
                            ArrayList<String> view,ArrayList<String> clock,MyAdapterListener listener) {

        this.context = applicationContext;
        this.href = href;
        this.title = title;
        this.channel = channel;
        this.imagesLink = imagesLink;
        this.view = view;
        this.clock = clock;
        onClickListener = listener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView idTextView;

        public CircleImageView imageChannel;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public TextView time;
        public TextView age;

        public TextView nameOfChannel;


        public ViewHolder(View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.image);
            age = itemView.findViewById(R.id.age);
            imageChannel = itemView.findViewById(R.id.image2);
            titleTextView = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.timeLapse);
            descriptionTextView = itemView.findViewById(R.id.description);

            nameOfChannel = itemView.findViewById(R.id.nameOfChannel);





        }
    }
    @Override
    public RecyclerAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        RecyclerAdapter2.ViewHolder viewHolder = new RecyclerAdapter2.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        Picasso.get().load(imagesLink.get(position)).into(holder.idTextView);

        holder.time.setText(channel.get(position));

        holder.age.setText(clock.get(position));

        holder.titleTextView.setText(view.get(position));
        holder.descriptionTextView.setText(title.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.itemClick(view, position,  title.get(position), href.get(position), channel.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public interface MyAdapterListener {

        void itemClick(View v, int position, String name, String name2, String name3);
    }




}