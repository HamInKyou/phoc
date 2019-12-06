package com.example.phoc.listView;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.phoc.R;

//<<<<<<< HEAD:app/src/main/java/com/example/phoc/listView/FeedItemAdapter.java
import java.util.ArrayList;
import android.content.Context;

public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemAdapter.ViewHolder> {
    ArrayList<FeedItem> items = new ArrayList<FeedItem>();
    private Context context;
    public FeedItemAdapter(Context context){
        this.context = context;
    }
//=======
//public class MyfeedItemAdapter extends RecyclerView.Adapter<MyfeedItemAdapter.ViewHolder>{
//    ArrayList<MyfeedItem> items = new ArrayList<MyfeedItem>();
//
//    public interface OnItemClickListener{
//        public  void onItemClick(View view, int position, int type);
//    }
//
//    private OnItemClickListener onItemClickListener;
//
//    public MyfeedItemAdapter(OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
//
//>>>>>>> dev:app/src/main/java/com/example/phoc/MyfeedItemAdapter.java
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.feed_item, viewGroup, false);

        return new ViewHolder(itemView, this.context);
    }

    @Override
//<<<<<<< HEAD:app/src/main/java/com/example/phoc/listView/FeedItemAdapter.java
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FeedItem item = items.get(position);
//=======
//    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
//        MyfeedItemAdapter.ViewHolder holder = (MyfeedItemAdapter.ViewHolder)viewHolder;
//        holder.title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //viewType1은 TextView인 title
//                onItemClickListener.onItemClick(v, position, 1);
//            }
//        });
//        MyfeedItem item = items.get(position);
//>>>>>>> dev:app/src/main/java/com/example/phoc/MyfeedItemAdapter.java
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView comment;
        TextView date;
        ImageView imgView;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;

            title = itemView.findViewById(R.id.fromMyFeed2ParticularTitle);
            comment = itemView.findViewById(R.id.inMyFeedComment);
            date = itemView.findViewById(R.id.inMyFeedDate);
            imgView = itemView.findViewById(R.id.imgView);
        }

        public void setItem(FeedItem item){
            title.setText(item.getTitle());
            comment.setText(item.getComment());
            date.setText(item.getDate());
            Log.d("Post", item.getImgUri());
            Uri uri = Uri.parse(item.getImgUri());
            Glide.with(context).load(uri).into(imgView);

        }
    }

    public void addItem(FeedItem item){
        items.add(item);
    }

    public void setItems(ArrayList<FeedItem> items){
        this.items = items;
    }

    public FeedItem getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, FeedItem item){
        items.set(position, item);
    }
}
