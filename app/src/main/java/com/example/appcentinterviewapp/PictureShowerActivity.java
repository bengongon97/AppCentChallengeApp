package com.example.appcentinterviewapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.appcentinterviewapp.Parser.FlickrPhotoReceiver;
import com.google.gson.Gson;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class PictureShowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_shower);

        ImageView imageItself = findViewById(R.id.imageItself);

        Intent mIntent = getIntent();
        int position = mIntent.getIntExtra("position", 0);

        Intent intent = getIntent();
        String jsonString = intent.getStringExtra("picContent");

        Gson gson = new Gson();
        final FlickrPhotoReceiver myReceiver = gson.fromJson(jsonString, FlickrPhotoReceiver.class);

        int farm = myReceiver.getPhotos().getPhoto().get(position).getFarm();
        String server_id = myReceiver.getPhotos().getPhoto().get(position).getServer();
        String id = myReceiver.getPhotos().getPhoto().get(position).getId();
        String secret = myReceiver.getPhotos().getPhoto().get(position).getSecret();

        String URLConstructor = "https://farm" + farm + ".staticflickr.com/" + server_id + "/" + id + "_" + secret + ".jpg";

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));

        builder.build().load(URLConstructor)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(imageItself);
    }
}
