package com.example.da08.servernodejs;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Da08 on 2017. 7. 25..
 */

public interface Ibbs {
        public static final String SERVER = "http://192.168.10.253:8080/";

        @GET("bbs")
        public Observable<ResponseBody> read();
        @POST("bbs")
        public Observable<ResponseBody> write(@Body RequestBody body);
        @PUT("bbs")
        public void update(Bbs bbs);
        @DELETE("bbs")
        public void delete(Bbs bbs);
}



interface Iuser{

}