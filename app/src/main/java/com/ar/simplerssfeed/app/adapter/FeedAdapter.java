package com.ar.simplerssfeed.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.simplerssfeed.R;
import com.ar.simplerssfeed.app.model.Post;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {
    private ArrayList<Post> data;
    private LayoutInflater inflater;
    private Context contextz;
    private AdapterView.OnItemClickListener onItemClickListener;
    private SimpleDateFormat sdf;
    private Locale id = new Locale("ID", "ID");

    public FeedAdapter(Context context) {
        this.contextz = context;
        inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
        sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy | HH:mm", id);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<Post> getListFeed() {
        return data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.item_feed, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Post post = data.get(position);
        holder.position = position;

        final long identity = System.currentTimeMillis();
        holder.identity = identity;

        Picasso.with(contextz).
                load(post.getAuthor().
                getAvatar_URL()).into(holder.img_profile);

        holder.txt_item_feed_date.setText(sdf.format(post.getDate()));
        holder.txt_item_feed_title.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        int position;
        TextView txt_item_feed_date, txt_item_feed_title;
        ImageView img_profile;
        long identity;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            img_profile = (ImageView) itemView.findViewById(R.id.img_profile);

            txt_item_feed_date = (TextView) itemView.findViewById(R.id.txt_item_feed_date);
            txt_item_feed_title = (TextView) itemView.findViewById(R.id.txt_item_feed_title);

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(null, v, position, 0);
            }
        }
    }
}
