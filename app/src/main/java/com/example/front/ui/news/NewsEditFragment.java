package com.example.front.ui.news;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.TextEditActivity;
import com.example.front.data.Photo;
import com.example.front.data.ServerItemResponse;
import com.example.front.data.News;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Api;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.call.ValidateCallback;
import com.example.front.retrofit.responses.ValidationResponse;
import com.example.front.ui.components.AppEditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsEditFragment extends Fragment {

    public static final int PHOTO_REQUEST_CODE = 10;
    public static final int TEXT_REQUEST_CODE = 11;
    private AppEditText title, description;
    private Button saveBtn, deleteBtn, addimage;
    private FloatingActionButton deletePhotoBtn;
    private ViewPager viewPager;
    private News item;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        int pos = arguments == null ? -1 : arguments.getInt("pos", -1);
        if (pos != -1) {
            item = DataBASE.NEWS_JSON_LIST.get(pos);
        } else {
            item = new News();
        }

        View view = inflater.inflate(R.layout.fragment_news_edit, container, false);
        title = view.findViewById(R.id.etv_news_edit_title);
        description = view.findViewById(R.id.etv_news_edit_content);

        saveBtn = view.findViewById(R.id.buttonEdit);
        ((GradientDrawable) saveBtn.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.accept));
        viewPager = view.findViewById(R.id.viewPager);
        updatePhotos();
        addimage = view.findViewById(R.id.bt_newsedit_addimage);
        ((GradientDrawable) addimage.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.accept));
        deletePhotoBtn = view.findViewById(R.id.deletePhoto);
        deleteBtn = view.findViewById(R.id.buttonDel);
        ((GradientDrawable) deleteBtn.getBackground()).setColor(ContextCompat.getColor(getContext(), R.color.like));
        title.setText(item.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description.getEt().setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            description.getEt().setText(Html.fromHtml(item.getDescription()));
        }
        description.getEt().setOnTouchListener((view12, motionEvent) -> {
            if (motionEvent.getAction() != MotionEvent.ACTION_UP) return false;
            Intent intent = new Intent(getContext(), TextEditActivity.class);
            intent.putExtra("text", item.getDescription());
            startActivityForResult(intent, TEXT_REQUEST_CODE);
            return  false;
        });
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, PHOTO_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int photoID = item.getPhotos().get(viewPager.getCurrentItem()).getId();
                if (photoID == 0) {
                    item.getPhotos().remove(viewPager.getCurrentItem());
                    item.getAdapter().notifyDataSetChanged();
                    updatePhotos();
                    return;
                }
                Call<JSONObject> deletePhoto = RetrofitClient.getInstance().getApi().editNewsDeletePhoto("Bearer " + DataBASE.token, item.getId(), "PUT", photoID);
                deletePhoto.enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                        if (response.isSuccessful()) {
                            item.getPhotos().remove(viewPager.getCurrentItem());
                            item.getAdapter().notifyDataSetChanged();
                            viewPager.setVisibility(View.GONE);
                            viewPager.invalidate();
                            viewPager.setVisibility(View.VISIBLE);
                            viewPager.setCurrentItem(Math.min(viewPager.getCurrentItem(), item.getPhotos().size() - 1));
                            Toast.makeText(getContext(), "Фото удалено", Toast.LENGTH_SHORT).show();
                            if (item.getPhotos().size() == 0) {
                                viewPager.setVisibility(View.GONE);
                            }
                        } else onError();
                    }

                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {
                        t.printStackTrace();
                        onError();
                    }
                });
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ResponseBody> delete = RetrofitClient.getInstance().getApi().deleteNews("Bearer " + DataBASE.token, item.getId());
                delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), String.valueOf(response.message()), Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else onError();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        onError();
                    }
                });
            }
        });


        saveBtn.setOnClickListener(view1 -> saveNews());


        return view;
    }

    private void updatePhotos() {
        if (item.getPhotos().size() > 0) {
            item.initAdapter(getContext());
            viewPager.setAdapter(item.getAdapter());
            viewPager.setVisibility(View.VISIBLE);
        } else {
            viewPager.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Context context = getContext();
                String path = RealPathUtil.getRealPath(context, uri);
                if (path != null) {
                    File file = new File(path);
                    String name = "post_photos[0]";
                    RequestBody filebody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    List<MultipartBody.Part> photos = new ArrayList<>();
                    photos.add(MultipartBody.Part.createFormData(name, file.getName(), filebody));
                    item.getPhotos().add(new Photo(file.getAbsolutePath()));
                    item.getAdapter().notifyDataSetChanged();
                    if (item.getId() == 0) {
                        updatePhotos();
                    } else {
                        saveNews(photos, false);
                    }
                }
            } else if (requestCode == TEXT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                item.setDescription(data.getStringExtra("text").replaceAll("(<br */>)+$", ""));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description.getEt().setText(Html.fromHtml(item.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    description.getEt().setText(Html.fromHtml(item.getDescription()));
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    public void onError() {
        Toast.makeText(getContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show();
    }

    public void saveNews() {
        saveNews(null, true);
    }
    public void saveNews(List<MultipartBody.Part> photos, boolean close) {
        Call<ServerItemResponse<News>> saveNewsPost;
        final String id = item.getId() > 0 ? item.getId()+"" : "";
        Api r = RetrofitClient.getInstance().getApi();
        String header = "Bearer " + DataBASE.token;
        boolean addPhoto = false;
        if (photos == null || id.isEmpty()) {
            if (id.isEmpty()) {
                saveNewsPost = r.addNews(header, title.getText(), item.getDescription());
                if (!item.getPhotos().isEmpty()) {
                    photos = item.getPhotosMultipart();
                }
            } else {
                saveNewsPost = r.editNews(header, id, title.getText(), item.getDescription());
            }
        } else {
            addPhoto = photos.size() > 0;
            saveNewsPost = r.editNews(header, id, "PUT", title.getText(), item.getDescription(), photos);
        }
        List<MultipartBody.Part> finalPhotos = photos;
        boolean finalAddPhoto = addPhoto;
        saveNewsPost.enqueue(new ValidateCallback<ServerItemResponse<News>>() {
            @Override
            public void onSuccess(Call<ServerItemResponse<News>> call, Response<ServerItemResponse<News>> response) {
                Log.d(CONST.SERVER_LOG, response.body().toString());
                if (finalAddPhoto) Toast.makeText(getContext(), "Фото загружено", Toast.LENGTH_SHORT).show();
                viewPager.setVisibility(View.GONE);
                item = response.body().getData();
                NewsEditFragment.this.getView().invalidate();
                if (item.getPhotos().size() == 0 && finalPhotos.isEmpty()) {
                    viewPager.setVisibility(View.GONE);
                } else {
                    viewPager.setVisibility(View.VISIBLE);
                }
                item.initAdapter(getContext());
                viewPager.setAdapter(item.getAdapter());
                if(id.isEmpty() && finalPhotos != null) {
                    saveNews(finalPhotos, close);
                    return;
                }
                if (!close) return;
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }

            @Override
            public void on422(Call<ServerItemResponse<News>> call, Response<ServerItemResponse<News>> response, ValidationResponse errors) {
                String error = errors.getError("title");
                if (error != null) title.setError(error);
                error = errors.getError("description");
                if (error != null) description.setError(error);
            }

            @Override
            public Context getContext() {
                return NewsEditFragment.this.getContext();
            }
        });
    }
}