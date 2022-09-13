package com.example.front.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.Photo;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.ui.components.ViewPagerCarouselView;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AppealsAdapter extends RecyclerView.Adapter {

    public Context context;
    public RecyclerView rv;
    public AppealsAdapter(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
    }


    private static ClickListener clickListener;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appeal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder)holder).bindView(position);
        }
    }

    @Override
    public int getItemCount() {
        return DataBASE.APPEALS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Button like, status, delete;
        TextView name,content,theme;
        ViewPagerCarouselView viewPager;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.bt_like);
            status = itemView.findViewById(R.id.bt_status);
            delete = itemView.findViewById(R.id.bt_delete);
            theme = itemView.findViewById(R.id.tv_appeal_theme);
            name = itemView.findViewById(R.id.tv_appeal_name);
            content = itemView.findViewById(R.id.tv_appeal_content);
            viewPager = itemView.findViewById(R.id.viewPager);
            viewPager.setVisibility(View.GONE);
        }

        @SuppressLint("ResourceAsColor")
        public void bindView(int position){
            Appeal appeal = DataBASE.APPEALS_LIST.get(position);
            theme.setText("Автор: "+appeal.getAuthor().getFull_name());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(appeal.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                content.setText(Html.fromHtml(appeal.getDescription()));
            }
            status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Подтверждение");
                    builder.setPositiveButton("Потвердить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Call<ResponseBody> accept = RetrofitClient.getInstance().getApi().userResponsStatusExecution("Bearer "+DataBASE.token,appeal.getId());
                            accept.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code()==200){
                                        Toast.makeText(context, "Потверждено", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                            dialogInterface.cancel();
                        }
                    });
                    builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });
            name.setText("Опубликовано: " + appeal.getDate());
            like.setText(String.valueOf(appeal.getLikes()));
            if (appeal.getUser_like()==1)
                like.setBackgroundResource(R.drawable.ic_like);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Удалить?");
                    builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Call<ResponseBody> accept = RetrofitClient.getInstance().getApi().deleteUserRespons("Bearer "+DataBASE.token,appeal.getId());
                            accept.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code()==200){
                                        DataBASE.APPEALS_LIST.remove(position);
                                        Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();

                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (appeal.getUser_like()!=1){
                        Call<ResponseBody> likeReq = RetrofitClient.getInstance().getApi().userResponsLike("Bearer "+DataBASE.token,appeal.getId());
                        likeReq.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    like.setBackgroundResource(R.drawable.ic_like);
                                    appeal.setUser_like(1);
                                    appeal.setLikes(appeal.getLikes()+1);
                                    like.setText(String.valueOf(appeal.getLikes()));
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    } else {
                        Call<ResponseBody> dislike =RetrofitClient.getInstance().getApi().userResponsDislike("Bearer "+DataBASE.token,appeal.getId());
                        dislike.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.code()==200){
                                    like.setBackgroundResource(R.drawable.ic_dislike);
                                    appeal.setLikes(appeal.getLikes()-1);
                                    appeal.setUser_like(0);
                                    like.setText(String.valueOf(appeal.getLikes()));
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                }
            });
            ArrayList<Photo> photos = appeal.getPhotos();
            if (photos.size()> 0) {
                NewsPhotosViewPagerAdapter adapter_news = new NewsPhotosViewPagerAdapter(context, photos);
                viewPager.setData(((AppCompatActivity)context).getSupportFragmentManager(), photos, 5000);
                viewPager.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            AppealsAdapter.clickListener.onItemClick(getAdapterPosition(), view);
        }
    }


    public void setClickListener(AppealsAdapter.ClickListener clickListener){
        AppealsAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position,View view);
        void onItemLongClick(int position,View view);
    }


}
