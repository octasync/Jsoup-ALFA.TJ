package com.side.lumies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.ViewHolder> {

    ArrayList<String> title = new ArrayList<>();
    Context context;


    public MyAdapterListener onClickListener;


    public RecyclerAdapter3(Context applicationContext, ArrayList<String> title, MyAdapterListener listener) {

        this.context = applicationContext;
        this.title = title;
        onClickListener = listener;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;


        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);





        }
    }
    @Override
    public RecyclerAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view;


        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view4, parent, false);

        RecyclerAdapter3.ViewHolder viewHolder = new RecyclerAdapter3.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        String save = title.get(position);
        int ii1, ii2, ii3, ii4;
        final int i1 = save.indexOf('\'');
        save = save.substring(i1+1, save.length());
        final int i2 = save.indexOf('\'');
        save = save.substring(i2+1, save.length());
        final int i3 = save.indexOf('\'');
        save = save.substring(i3+1, save.length());
        final int i4 = save.indexOf('\'');
        save = save.substring(i4+1, save.length());


            holder.titleTextView.setText(title.get(position).substring(i1+i2+i3+3,  i1+i2+i3+i4+3));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.itemClick(view, position,  title.get(position), title.get(position).substring(i1+i2+i3+3,  i1+i2+i3+i4+3));

            }
        });



    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public interface MyAdapterListener {

        void itemClick(View v, int position, String name, String name3);


    }

}