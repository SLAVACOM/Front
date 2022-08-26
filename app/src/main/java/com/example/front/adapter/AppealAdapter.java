package com.example.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.RequestTypeJSON;


public class AppealAdapter extends RecyclerView.Adapter {

    public static ClickListener clickListener;
    public Context context;

    public AppealAdapter(Context context) {
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
        return DataData.REQUEST_TYPEJSON_LIST.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.tv_request_content);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position){
            RequestTypeJSON appeal = DataData.REQUEST_TYPEJSON_LIST.get(position);
            content.setText("ID" + appeal.getId()+"\nОбращение: "+appeal.getName());
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);

        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onItemLongClick(getAdapterPosition(),view);

            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        AppealAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
