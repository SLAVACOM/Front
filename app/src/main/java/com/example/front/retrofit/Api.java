package com.example.front.retrofit;

import java.io.File;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/login/")
    Call<ResponseBody> login(@Field("email")String email,@Field("password") String password);


    @Headers({"Accept: application/json"})
    Call<ResponseBody> registration(@Field("email") String email, @Field("name") String name,@Field("second_name")String second_name,@Field("accept") String accept,
                                    @Field("phone") String phone,@Field("last_name") String last_name);

    @POST("api/auth/logout/")
    @Headers("Authorization: Bearer {{token}")
    Call<ResponseBody> logout();

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/reset")
    Call<ResponseBody> reset_password(@Field("email") String email);

    @Headers("Authorization: Bearer {{token}}")
    @POST("api/auth/profile")
    Call<ResponseBody> view_profil_data();

    @FormUrlEncoded
    @POST("api/auth/profile")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> editProfile(@Field("_method") String method, @Field("name") String name, @Field("second_name") String second_name,
                                   @Field("last_name")String last_name, @Field("password") String password,@Field("password_confirmation") String password_confirmation);

    @GET("api/user/event")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getEventHistory();











    @FormUrlEncoded
    @POST("api/post")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addNews(@Field("title") String title, @Field("description") String description);


    @FormUrlEncoded
    @POST("api/post/{{post_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> editNews(@Field("_method") String method,@Field("title") String title, @Field("description") String description,@Field("post_photos[0]") File file,@Field("post_photos[1]") File file1,@Field("delete_photos[0]") int id);


    @DELETE("api/post/{{post_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteNews();

    @GET("api/post")
    Call<ResponseBody> getNewsList();













    @FormUrlEncoded
    @POST("api/user/post")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addUserRespons(@Field("title") String title,@Field("description") String description);


    @FormUrlEncoded
    @POST("api/user/post/{{user_post_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> editUserRespons(@Field("_method") String method,@Field("title") String title, @Field("description") String description,@Field("post_photos[0]") File file,@Field("post_photos[1]") File file1,@Field("delete_photos[0]") int id);

    @DELETE("api/user/post/{{user_post_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteUserRespons();

    @POST("api/user/post/{{user_post_id}}/accept")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> userResponsStatusExecution();

    @POST("api/user/post/{{user_post_id}}/confirm")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> userResponsStatusExecuted();

    @POST("api/user/post/{{user_post_id}}/dislike")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> userResponsDislike();

    @POST("api/user/post/{{user_post_id}}/like")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> userResponsLike();

    @FormUrlEncoded
    @GET("api/user/post/")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getResponsList(@Field("mode") String mode);
















    @FormUrlEncoded
    @POST("api/event")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addEvent(@Field("title") String title, @Field("place") String place, @Field("date") String date, @Field("points") String points);

    @FormUrlEncoded
    @POST("api/event/{{event_id}}")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addEvent(@Field("title") String title, @Field("place") String place, @Field("date") String date, @Field("points") String points,@Field("_method") String method);

    @DELETE("api/event/{{event_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteEvent();

    @GET("api/event")
    Call<ResponseBody> getEventList();



    @POST("api/bus/event")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> addBusEvent(@Field("title") String title,@Field("place")String place,@Field("time")String time);

    @DELETE("api/bus/event/{{bus_event_id}}")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> deleteBusEvent();

    @GET("api/type")
    Call<ResponseBody> getBusList();



















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
    Call<ResponseBody> getMapObject();








    @FormUrlEncoded
    @POST("api/auth/profile")
    @Headers({"Accept: application/json","Authorization: Bearer {{token}}"})
    Call<ResponseBody> edit_profile(@Field("_method") String method,@Field("name") String name,@Field("second_name")String second_name,@Field("last_name")String last_name,
                                    @Field("password") String password, @Field("password_confirmation")String password_confirmation);


    @GET("api/user/3")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getUserData();


    @GET("api/user")
    @Headers({"Authorization: Bearer {{token}}"})
    Call<ResponseBody> getUsers();
}
