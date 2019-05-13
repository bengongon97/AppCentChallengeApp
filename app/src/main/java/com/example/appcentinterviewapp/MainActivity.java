package com.example.appcentinterviewapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.example.appcentinterviewapp.Networking.RetrofitClientInstance;
import com.example.appcentinterviewapp.Networking.RippleAPIService;
import com.example.appcentinterviewapp.Parser.FlickrPhotoReceiver;
import com.google.gson.Gson;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.R.layout;

public class MainActivity extends AppCompatActivity implements EntranceAdapter.OnItemClickListener{

    FlickrPhotoReceiver myReceiver;
    RecyclerView entrance;
    EntranceAdapter entAdapter;
    int[] lastVisibleItem = new int[2];
    Integer page = 1;
    final String method = "flickr.photos.getRecent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrance = findViewById(R.id.mainRecycler);

        final RippleAPIService service = RetrofitClientInstance.getRetrofitInstance().create(RippleAPIService.class);
        callRequest(service);

        entrance.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                 int pastVisiblesItems, visibleItemCount, totalItemCount;

                if(dy > 0) //check for scroll down
                {
                    StaggeredGridLayoutManager mLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    mLayoutManager.findLastCompletelyVisibleItemPositions(lastVisibleItem);

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPositions(null)[0];

                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            getNextPage();
                        }
                }
            }
        });
    }

    private void getNextPage() {
        page++;
        RippleAPIService service = RetrofitClientInstance.getRetrofitInstance().create(RippleAPIService.class);
        Call<FlickrPhotoReceiver> call = service.willGetPhotos(method,"3b358615b0511c9184faf9f966ccb0ec","20",page+"", "json", "1");

        call.enqueue(new Callback<FlickrPhotoReceiver>() {

            @Override
            public void onResponse(Call<FlickrPhotoReceiver> call, Response<FlickrPhotoReceiver> response) {

                if (response.isSuccessful()){
                    FlickrPhotoReceiver newRows = response.body();
                    entAdapter.appendNewRows(newRows, page, myReceiver.getPhotos().getPhoto().size());
                }
                else
                    Toast.makeText(MainActivity.this,"unsuccessful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FlickrPhotoReceiver> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onItemClick(int position) {
        Intent intent = new Intent(this, PictureShowerActivity.class);

        Gson gson = new Gson();
        String receiverString = gson.toJson(myReceiver);

        intent.putExtra("picContent", receiverString);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    public void informationProcessor(FlickrPhotoReceiver myReceiver){


        entrance.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        entAdapter = new EntranceAdapter(MainActivity.this, myReceiver);

        ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(entAdapter);
        scaleInAnimationAdapter.setFirstOnly(false);
        scaleInAnimationAdapter.setDuration(500);
        scaleInAnimationAdapter.setInterpolator(new OvershootInterpolator());
        entrance.setAdapter(scaleInAnimationAdapter);

        entAdapter.setOnItemClickListener(MainActivity.this);

    }

    public void callRequest(RippleAPIService service) {

        Call<FlickrPhotoReceiver> call = service.willGetPhotos(method,"3b358615b0511c9184faf9f966ccb0ec","20","1", "json", "1");

        call.enqueue(new Callback<FlickrPhotoReceiver>() {

            @Override
            public void onResponse(Call<FlickrPhotoReceiver> call, Response<FlickrPhotoReceiver> response) {

                if (response.isSuccessful()) {
                    myReceiver = response.body();
                    informationProcessor(myReceiver);
                } else {
                    Toast.makeText(MainActivity.this, "No results found.\n Please try again.",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<FlickrPhotoReceiver> call, Throwable t) {
                // call failed
                Toast.makeText(MainActivity.this, "Oops!It is " + t.getCause().toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
