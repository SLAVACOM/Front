package com.example.front.retrofit;

import com.example.front.data.Appeal;
import com.example.front.data.BusJSON;
import com.example.front.data.EventJSON;
import com.example.front.data.HistoryJSON;
import com.example.front.data.ListRESPONSE;
import com.example.front.data.MapObject;
import com.example.front.data.News;
import com.example.front.data.ResponsLibrary;
import com.example.front.retrofit.responses.ObjectResponse;
import com.example.front.data.RequestTypeJSON;
import com.example.front.data.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Api {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/login/")
    Call<JsonObject> login(@Field("email")String email, @Field("password") String password);

    @Headers({"Accept: application/json"})
    @POST("api/auth/signup/")
    Call<ResponseBody> registration(@Body User user);

    @POST("api/auth/logout/")
    Call<ResponseBody> logout(@Header("Authorization") String authHeader);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST("api/auth/reset")
    Call<ResponseBody> resetPassword(@Field("email") String email);

    @POST("api/auth/profile")
    Call<ObjectResponse<User>> getProfile(@Header("Authorization") String authHeader);

    @POST("api/auth/profile")
    @Headers({"Accept: application/json"})
    Call<JsonObject> editProfile(@Header("Authorization") String authHeader,@Field("_method") String method, @Field("name") String name, @Field("second_name") String second_name,
                                 @Field("last_name")String last_name, @Field("password") String password, @Field("password_confirmation") String password_confirmation);

    @POST("api/user/{user_id}")
    @Headers({"Accept: application/json"})
    Call<JsonObject> editProfile(@Header("Authorization") String authHeader,@Path("user_id") int id,@Query("_method") String method ,@Query("blocked") int block);

    @GET("api/user/event")
    Call<ListRESPONSE<HistoryJSON>> getEventHistory(@Header("Authorization") String authHeader);





    @GET("api/type")
    Call<ListRESPONSE<RequestTypeJSON>> getRequestTypes();

    @POST("/api/type")
    @Headers({"Accept: application/json"})
    Call<ObjectResponse<RequestTypeJSON>> addRequestType(@Header("Authorization") String authHeader,@Query("name") String name);

    @DELETE("api/type/{type_id}")
    Call<ResponseBody> deleteRequest(@Header("Authorization") String authHeader,@Path("type_id") int id);





    @POST("api/post")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> addNews(@Header("Authorization") String authHeader, @Query("title") String title,@Query("description") String description);


    @Multipart
    @POST("api/post/{post_id}")
    @Headers({"Accept: application/json"})
    Call<JSONObject> editNews(@Header("Authorization") String authHeader,@Path("post_id") int postID, @Query("_method") String put,@Query("title") String title, @Query("description") String description, @Part MultipartBody.Part  post_photos );

    @POST("api/post/{post_id}")
    @Headers({"Accept: application/json"})
    Call<JSONObject> editNewsNoPhoto(@Header("Authorization") String authHeader,@Path("post_id") int postID, @Query("_method") String put,@Query("title") String title, @Query("description") String description);

    @POST("api/post/{post_id}")
    @Headers({"Accept: application/json"})
    Call<JSONObject> editNewsDeletePhoto(@Header("Authorization") String authHeader,@Path("post_id") int postID, @Query("_method") String put,@Query("delete_photos[0]") int photoID);


    @DELETE("api/post/{post_id}")
    Call<ResponseBody> deleteNews(@Header("Authorization") String authHeader,@Path("post_id") int id);


    @GET("api/post")
    Call<ListRESPONSE<News>> getNewsList();


    @FormUrlEncoded
    @POST("api/user/post")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> addUserRespons(@Header("Authorization") String authHeader,@Field("title") String title,@Field("description") String description);


    @FormUrlEncoded
    @POST("api/user/post/{{user_post_id}}")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> editUserRespons(@Header("Authorization") String authHeader,@Field("_method") String method,@Field("title") String title, @Field("description") String description,@Field("post_photos[0]") File file,@Field("post_photos[1]") File file1,@Field("delete_photos[0]") int id);

    @DELETE("api/user/post/{user_post_id}")
    Call<ResponseBody> deleteUserRespons(@Header("Authorization") String authHeader,@Path("user_post_id") int id);

    @POST("api/user/post/{user_post_id}/accept")
    Call<ResponseBody> userResponsStatusExecution(@Header("Authorization") String authHeader,@Path("user_post_id") int id);

    @POST("api/user/post/{user_post_id}/confirm")
    Call<ResponseBody> userResponsStatusExecuted(@Header("Authorization") String authHeader,@Path("user_post_id") int id);

    @POST("api/user/post/{user_post_id}/dislike")
    Call<ResponseBody> userResponsDislike(@Header("Authorization") String authHeader,@Path("user_post_id") int id);

    @POST("api/user/post/{user_post_id}/like")
    Call<ResponseBody> userResponsLike(@Header("Authorization") String authHeader,@Path("user_post_id") int id);

    @GET("api/user/post/")
    Call<ListRESPONSE<Appeal>> getAppeals(@Header("Authorization") String authHeader, @Query("mode") String mode, @Query("page") String page);





    @FormUrlEncoded
    @POST("api/bus/event")
    @Headers({"Accept: application/json"})
    Call<JsonObject> addBusEvent(@Header("Authorization") String token,@Field("title") String title,@Field("place")String place,@Field("time")String time);

    @DELETE("api/bus/event/{bus_event_id}")

    Call<JsonObject> deleteBusEvent(@Header("Authorization") String token,@Path("bus_event_id") int id);

    @GET("api/bus/event")
    Call<ListRESPONSE<BusJSON>> getBusList();























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





    @PUT("api/auth/profile")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> editProfile(@Header("Authorization") String token,@Body User user);


    @Headers({"Accept: application/json"})
    @PUT("api/user/{user_id}")
    Call<ResponseBody> editUser(@Header("Authorization") String token, @Path("user_id") int user_id, @Body User user);

    @GET("api/user")
    Call<ListRESPONSE<User>> getUsers(@Header("Authorization") String heder);


    @GET("api/event")
    Call<ListRESPONSE<EventJSON>> getEventList();

    @POST("api/event")
    @Headers({"Accept: application/json"})
    Call<ResponseBody> addEvent(@Header("Authorization") String token,@Query("title") String title, @Query("place")String place,@Query("date") String date,@Query("points") String points);

    @Headers({"Accept: application/json"})
    @PUT("api/event/{event_id}")
    Call<ResponseBody> addEventParticipant(@Header("Authorization") String heder, @Path("event_id") int event_id, @Body Map<String,String> body);

    @GET("api/request/?role=128")
    Call<ListRESPONSE<ResponsLibrary>> getLibRespons(@Header("Authorization") String heder);
    @GET("api/request/?role=1024")
    Call<ListRESPONSE<ResponsLibrary>> getAdminRespons(@Header("Authorization") String heder);

    @POST("api/request/?role=128")
    Call<ResponseBody> addReqLib(@Header("Authorization") String heder, @Query("text")String body,@Query("type") int idtype);




}
