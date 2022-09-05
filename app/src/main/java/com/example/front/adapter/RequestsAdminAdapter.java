package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.ResponsLibrary;
import com.example.front.data.database.DataBASE;


public class RequestsAdminAdapter extends RecyclerView.Adapter {

    public static ClickListener clickListener;
    public Context context;

    public RequestsAdminAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_appeal,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DataBASE.REQUEST_ADMIN_LIST.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_request_content);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position){
            ResponsLibrary appeal = DataBASE.REQUEST_ADMIN_LIST.get(position);
            content.setText("Требование: " + appeal.getText()+"\nПользователь: "+appeal.getUser().getFull_name()+"\nОпубликовано: " +appeal.getCreated_at().replaceAll("T"," ").replaceAll(".000000Z",""));

        }



        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(),view);

            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RequestsAdminAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemLongClick(int position, View v);
    }
}
