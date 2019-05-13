package com.example.appcentinterviewapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appcentinterviewapp.Parser.FlickrPhotoReceiver;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class EntranceAdapter extends RecyclerView.Adapter<EntranceAdapter.EntranceView>{

    public interface OnItemClickListener {
        void onItemClick (int position);
    }

    private EntranceAdapter.OnItemClickListener onItemClickListener;
    private Context context;
    private FlickrPhotoReceiver myReceiver;

    EntranceAdapter(Context context, FlickrPhotoReceiver myReceiver) {
        this.context = context;
        this.myReceiver = myReceiver;
    }

    void setOnItemClickListener(EntranceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class EntranceView extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView picTitle;
        ImageView picItself;

        private EntranceView(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            picTitle = itemView.findViewById(R.id.picTitle);
            picItself = itemView.findViewById(R.id.picItself);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(R.id.picItself);
            }
        }
    }

    void appendNewRows(FlickrPhotoReceiver newRows, int pageno, int newItemCount) {
        myReceiver.getPhotos().getPhoto().addAll(newRows.getPhotos().getPhoto());
        this.notifyItemRangeInserted(20*(pageno-1) ,20);
    }


    @NonNull
    @Override
    public EntranceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.in_main, parent, false);
        return new EntranceView(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EntranceView holder, final int position) {

        String title  = myReceiver.getPhotos().getPhoto().get(holder.getAdapterPosition()).getTitle();


        //holder.picTitle.setText(position +""); UNCOMMENT THIS CODE TO SEE HOW MANY PICTURES ARE BUFFERED.
        holder.picTitle.setText(title);

        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        int farm = myReceiver.getPhotos().getPhoto().get(holder.getAdapterPosition()).getFarm();
        String server_id = myReceiver.getPhotos().getPhoto().get(holder.getAdapterPosition()).getServer();
        String id = myReceiver.getPhotos().getPhoto().get(holder.getAdapterPosition()).getId();
        String secret = myReceiver.getPhotos().getPhoto().get(holder.getAdapterPosition()).getSecret();

        String URLConstructor = "https://farm" + farm + ".staticflickr.com/" + server_id + "/" + id + "_" + secret + ".jpg";

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));

        builder.build().load(URLConstructor)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.picItself);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myReceiver.getPhotos().getPhoto().size();
    }


}
