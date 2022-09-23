package com.example.front.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.data.Appeal;
import com.example.front.data.Photo;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.ui.components.AppEditText;
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
        Button likeBtn, confirmBtn, deleteBtn;
        TextView name,content,theme;
        ViewPagerCarouselView viewPager;
        Appeal appeal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            likeBtn = itemView.findViewById(R.id.bt_like);
            confirmBtn = itemView.findViewById(R.id.bt_status);
            deleteBtn = itemView.findViewById(R.id.bt_delete);
            theme = itemView.findViewById(R.id.tv_appeal_theme);
            name = itemView.findViewById(R.id.tv_appeal_name);
            content = itemView.findViewById(R.id.tv_appeal_content);
            viewPager = itemView.findViewById(R.id.viewPager);
            viewPager.setVisibility(View.GONE);
        }

        @SuppressLint("ResourceAsColor")
        public void bindView(int position){
            appeal = DataBASE.APPEALS_LIST.get(position);
            theme.setText("Автор: "+appeal.getAuthor().getFull_name());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(Html.fromHtml(appeal.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                content.setText(Html.fromHtml(appeal.getDescription()));
            }
            name.setText("Опубликовано: " + appeal.getDate());
            likeBtn.setText(String.valueOf(appeal.getLikes()));
            if (appeal.getUser_like()==1) {
                likeBtn.setBackgroundResource(R.drawable.chip);
                ((GradientDrawable) likeBtn.getBackground()).setColor(ContextCompat.getColor(context,R.color.like));
                likeBtn.setTextColor(ContextCompat.getColor(context,R.color.white));
            }
            deleteBtn.setBackgroundResource(R.drawable.chip);
            ((GradientDrawable) deleteBtn.getBackground()).setColor(ContextCompat.getColor(context,R.color.like));
            ((GradientDrawable) confirmBtn.getBackground()).setColor(ContextCompat.getColor(context,R.color.accept));

            int confirmVisible = View.INVISIBLE;
            if ((DataBASE.user.isUser()) && DataBASE.user.getId() == appeal.getUser_id() && appeal.getState() == CONST.APPEAL_PROCESSED) {//житель может пожтвердить, если статус -"выполнено"
                confirmVisible = View.VISIBLE;
                confirmBtn.setText(R.string.close_btn_title);
            }

            if (DataBASE.user.isAdmin() && appeal.getState() == CONST.APPEAL_OPENED) {//админ может подвердитть только новые обращения
                confirmVisible = View.VISIBLE;
                confirmBtn.setText(R.string.confirm_btn_title);
            }
            confirmBtn.setVisibility(confirmVisible);

            int deleteVisible = View.INVISIBLE;
            if (DataBASE.user.isAdmin() && (appeal.getState() == CONST.APPEAL_OPENED || appeal.getState() == CONST.APPEAL_CONFIRMED)
                || DataBASE.user.isUser() && DataBASE.user.getId() == appeal.getUser_id()) {
                deleteVisible = View.VISIBLE;
            }
            deleteBtn.setVisibility(deleteVisible);



            ArrayList<Photo> photos = appeal.getPhotos();
            if (photos.size()> 0) {
                NewsPhotosViewPagerAdapter adapter_news = new NewsPhotosViewPagerAdapter(context, photos);
                viewPager.setData(((AppCompatActivity)context).getSupportFragmentManager(), photos, 5000);
                viewPager.setVisibility(View.VISIBLE);
            }
            setListeners(position);
        }

        public void  setListeners(int position) {

            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    LinearLayout dialoglayout = (LinearLayout) inflater.inflate(R.layout.dialog_comment, null);
                    final AppEditText comm = new AppEditText(context);
                    comm.setHint(R.string.comment_title);
                    dialoglayout.addView(comm);
                    builder.setView(dialoglayout);

                    builder.setTitle("Подтверждение");
                    builder.setPositiveButton("Потвердить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, comm.getText().toString(), Toast.LENGTH_SHORT).show();;
                            Call<ResponseBody> accept;
                            if (DataBASE.user.isAdmin()) accept = Retrofit.getInstance().getApi().userResponsStatusExecution("Bearer "+DataBASE.token,appeal.getId(), comm.getText().toString());
                            else accept = Retrofit.getInstance().getApi().userResponsStatusExecuted("Bearer "+DataBASE.token,appeal.getId());
                            accept.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(context, "Потверждено", Toast.LENGTH_SHORT).show();
                                        confirmBtn.setVisibility(View.INVISIBLE);
                                    } else {
                                        onError();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    t.printStackTrace();
                                    onError();
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
            deleteBtn.setOnClickListener(new View.OnClickListener() {
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
                            Call<ResponseBody> accept = Retrofit.getInstance().getApi().deleteUserRespons("Bearer "+DataBASE.token,appeal.getId());
                            accept.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.isSuccessful()){
                                        DataBASE.APPEALS_LIST.remove(position);
                                        Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show();
                                        notifyDataSetChanged();
                                    } else {
                                        onError();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    t.printStackTrace();
                                    onError();
                                }
                            });
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });
            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseBody> likeReq = Retrofit.getInstance().getApi().appealLikeAcation("Bearer "+DataBASE.token,appeal.getId(), appeal.getUser_like()!=1 ? "like" : "dislike");
                    likeReq.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){
                                likeBtn.setBackgroundResource(R.drawable.chip);
                                if (appeal.getUser_like() != 1) {
                                    appeal.setUser_like(1);
                                    ((GradientDrawable) likeBtn.getBackground()).setColor(ContextCompat.getColor(context,R.color.like));
                                    likeBtn.setTextColor(ContextCompat.getColor(context,R.color.white));
                                    appeal.setLikes(appeal.getLikes()+1);
                                }else {
                                    appeal.setUser_like(0);
                                    ((GradientDrawable) likeBtn.getBackground()).setColor(ContextCompat.getColor(context,R.color.default_chip));
                                    likeBtn.invalidate();
                                    appeal.setLikes(appeal.getLikes()-1);
                                    likeBtn.setTextColor(ContextCompat.getColor(context,R.color.chip_text));
                                }
                                likeBtn.setText(String.valueOf(appeal.getLikes()));
                                likeBtn.invalidate();
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
        }

        @Override
        public void onClick(View view) {
            AppealsAdapter.clickListener.onItemClick(getAdapterPosition(), view);
        }
        public void  onError(){
            Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_SHORT).show();
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
