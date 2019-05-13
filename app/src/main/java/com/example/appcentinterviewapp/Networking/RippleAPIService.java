package com.example.appcentinterviewapp.Networking;

import com.example.appcentinterviewapp.Parser.FlickrPhotoReceiver;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RippleAPIService {
    // For photos
    @GET("rest/")
    Call<FlickrPhotoReceiver> willGetPhotos(@Query("method") String method,@Query("api_key") String api_key,
                                            @Query("per_page") String per_page, @Query("page") String page,
                                            @Query("format") String format, @Query("nojsoncallback") String nojsoncallback);
   /* @GET("rest/")
    Call<FlickrPhotoReceiver> willGetPhotos(@Query("method") String method,@Query("api_key") String api_key);*/


    //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=3b358615b0511c9184faf9f966ccb0ec&per_page=20&page=1&format=json&nojsoncallback=1
}
