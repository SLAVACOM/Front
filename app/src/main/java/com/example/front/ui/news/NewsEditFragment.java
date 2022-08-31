package com.example.front.ui.news;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.News;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsEditFragment extends Fragment {

    private EditText title,content;
    private Button button,deleteBt,addimage;

    private String path;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news_edit, container, false);
        title = view.findViewById(R.id.etv_news_edit_title);
        content = view.findViewById(R.id.etv_news_edit_content);
        button = view.findViewById(R.id.buttonEdit);
        addimage = view.findViewById(R.id.bt_newsedit_addimage);
        News item = DataBASE.NEWS_JSON_LIST.get(getArguments().getInt("pos"));
        deleteBt = view.findViewById(R.id.buttonDel);
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> delete = RetrofitClient.getInstance().getApi().deleteNews("Bearer " + DataBASE.token,Integer.valueOf(item.getId()));
                delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(),String.valueOf(response.message()), Toast.LENGTH_SHORT).show();
                        if (response.code()==200) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


        title.setText(item.getTitle());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (path!=null){
                    File file =new File(path);
                    String name= "post_photos["+item.getPhotos().size()+"]";
                    RequestBody filebody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData(name,file.getName(),filebody);
                    Call<JSONObject> saveNewsPost = RetrofitClient.getInstance().getApi().editNews("Bearer "+ DataBASE.token,item.getId(),"PUT",title.getText().toString(),content.getText().toString(),body);
                    saveNewsPost.enqueue(new Callback<JSONObject>() {

                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            Log.d(CONST.SERVER_LOG,response.toString());
                            if (response.code()==200){
                                Toast.makeText(getContext(), "Успешно изменено", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack();
                            }
                        }
                        @Override
                        public void onFailure(Call<JSONObject> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    Call<JSONObject> saveNewsPostNoPhoto = RetrofitClient.getInstance().getApi().editNewsNoPhoto("Bearer "+ DataBASE.token,item.getId(),"PUT",title.getText().toString(),content.getText().toString());
                    saveNewsPostNoPhoto.enqueue(new Callback<JSONObject>() {
                        @Override
                        public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                            if (response.code()==200){
                                Toast.makeText(getContext(), "Успешно изменено", Toast.LENGTH_SHORT).show();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.popBackStack();
                            }
                        }

                        @Override
                        public void onFailure(Call<JSONObject> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            content.setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            content.setText(Html.fromHtml(item.getDescription()));
        }


        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent,10);
                } else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10&&resultCode== Activity.RESULT_OK) {

            Uri uri = data.getData();
            Context context= getContext();
            path =RealPathUtil.getRealPath(context,uri);


        }
    }
}