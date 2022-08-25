package com.example.front.retrofit;

import com.example.front.retrofit.maper.TitleAndDescription;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/login/")
    Call<JsonObject> login(@Field("email")String email, @Field("password") String password);


    @Headers({"Accept: application/json"})
    Call<ResponseBody> registration(@Field("email") String email, @Field("name") String name,@Field("second_name")String second_name,@Field("accept") String accept,
                                    @Field("phone") String phone,@Field("last_name") String last_name);

    @POST("api/auth/logout/")
    Call<ResponseBody> logout(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/reset")
    Call<ResponseBody> resetPassword(@Field("email") String email);

    @POST("api/auth/profile")
    Call<ObjectResponse<User>> getProfile(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @POST("api/auth/profile")
    @Headers({"Accept: application/json"})
    Call<JsonObject> editProfile(@Header("Authorization") String authHeader,@Field("_method") String method, @Field("name") String name, @Field("second_name") String second_name,
                                      @Field("last_name")String last_name, @Field("password") String password, @Field("password_confirmation") String password_confirmation);

    @GET("api/user/event")
    Call<ListRESPONSE<HistoryJSON>> getEventHistory(@Header("Authorization") String authHeader);











    @FormUrlEncoded
    @POST("api/post")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> addNews(@Header("Authorization") String authHeader, @Body TitleAndDescription titleAndDescription);


    @FormUrlEncoded
    @POST("api/post/{{post_id}}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> editNews(@Header("Authorization") String authHeader,@Field("_method") String method,@Field("title") String title, @Field("description") String description,@Field("post_photos[0]") File file,@Field("post_photos[1]") File file1,@Field("delete_photos[0]") int id);


    @DELETE("api/post/{post_id}")
    Call<ResponseBody> deleteNews(@Header("Authorization") String authHeader,@Path("post_id") int id);

    @GET("api/post")
    Call<ListRESPONSE<NewsJSON>> getNewsListREs();
    @GET("api/post")
    Call<JsonObject> getNewsList();













    @FormUrlEncoded
    @POST("api/user/post")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> addUserRespons(@Header("Authorization") String authHeader,@Field("title") String title,@Field("description") String description);


    @FormUrlEncoded
    @POST("api/user/post/{{user_post_id}}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> editUserRespons(@Header("Authorization") String authHeader,@Field("_method") String method,@Field("title") String title, @Field("description") String description,@Field("post_photos[0]") File file,@Field("post_photos[1]") File file1,@Field("delete_photos[0]") int id);

    @DELETE("api/user/post/{{user_post_id}}")
    Call<ResponseBody> deleteUserRespons(@Header("Authorization") String authHeader);

    @POST("api/user/post/{{user_post_id}}/accept")
    Call<ResponseBody> userResponsStatusExecution(@Header("Authorization") String authHeader);

    @POST("api/user/post/{{user_post_id}}/confirm")
    Call<ResponseBody> userResponsStatusExecuted(@Header("Authorization") String authHeader);

    @POST("api/user/post/{{user_post_id}}/dislike")
    Call<ResponseBody> userResponsDislike(@Header("Authorization") String authHeader);

    @POST("api/user/post/{{user_post_id}}/like")
    Call<ResponseBody> userResponsLike(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @GET("api/user/post/")
    Call<ResponseBody> getResponsList(@Header("Authorization") String authHeader,@Field("mode") String mode);
















    @FormUrlEncoded
    @POST("api/event")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addEvent(@Field("title") String title, @Field("place") String place, @Field("date") String date, @Field("points") String points);

    @FormUrlEncoded
    @POST("api/event/{{event_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addEvent(@Field("title") String title, @Field("place") String place, @Field("date") String date, @Field("points") String points,@Field("_method") String method);

    @DELETE("api/event/{event_id}")
    Call<ResponseBody> deleteEvent(@Header("Authorization") String token, @Path("event_id") int id);

    @GET("api/event")
    Call<JsonObject> getEventList();


    @FormUrlEncoded
    @POST("api/bus/event")
    @Headers({"Accept: application/json"})
    Call<JsonObject> addBusEvent(@Header("Authorization") String token,@Field("title") String title,@Field("place")String place,@Field("time")String time);

    @DELETE("api/bus/event/{bus_event_id}")

    Call<JsonObject> deleteBusEvent(@Header("Authorization") String token,@Path("bus_event_id") int id);

    @GET("api/bus/event")
    Call<ListRESPONSE<BusJSON>> getBusList();



















    @FormUrlEncoded
    @POST("api/request")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addRequest(@Field("text") String text, @Field("role") int role, @Field("type") int i);

    @FormUrlEncoded
    @POST("api/request/{{request_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addPhotoToRequest(@Field("_method") String _method, @Field("post_files[0]") File post_files,@Field("post_files[1]")File file2);

    @FormUrlEncoded
    @POST("api/request/{{request_id}}/messages")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addMessageToRequest(@Field("text") String text);

    @GET("api/request/{{request_id}}/messages")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> getRequestForMessages();

    @DELETE("api/request/{{request_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> geleteRequest();

    @GET("api/post")
    Call<ResponseBody> getRequestList();









    @FormUrlEncoded
    @POST
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addFile(@Field("title")File title,@Field("file")File file);

    @GET("api/file/{{file_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> downloadFile();

    @DELETE("api/file/{{file_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteFille();

    @GET("api/file")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getFileList();








    @FormUrlEncoded
    @POST("api/mapObject")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addMapObject(@Field("name")String name,@Field("color") String color,@Field("coords[0]") float coords1,@Field("coords[1]") float coords2, @Field("type") String type,@Field("points")int points);

    @FormUrlEncoded
    @POST("api/mapObject/{{map_object_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> editMapObject(@Field("name")String name,@Field("color") String color,@Field("coords[0]") float coords1,@Field("coords[1]") float coords2, @Field("type") String type,@Field("points")int points);

    @DELETE("api/mapObject/{{map_object_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteMapObject();

    @GET("api/mapObject")
    Call<ListRESPONSE<MapObject>> getMapObject();








    @FormUrlEncoded
    @POST("api/auth/profile")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> edit_profile(@Field("_method") String method,@Field("name") String name,@Field("second_name")String second_name,@Field("last_name")String last_name,
                                    @Field("password") String password, @Field("password_confirmation")String password_confirmation);


    @POST("api/user/{user_id}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> editUserList(@Header("Authorization") String token, @Path("user_id") int user_id,@Body String method,@Body String email,@Body String name, @Body String second_name, @Body int phone,@Body String last_name,@Body int blocked, @Body int curator, @Body int point,@Body int card_id);

    @GET("api/user/3")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getUserData();


    @GET("api/user")
    Call<JsonObject> getUsers(@Header("Authorization") String heder);
}
