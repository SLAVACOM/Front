package com.example.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.HistoryJSON;
import com.example.front.retrofit.User;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.MyViewHolder> {

    public static ClickListener clickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(position);

    }

    @Override
    public int getItemCount() {
        return DataData.USERS_LIST.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView id, points,fullname,email,phone,adress,status;
        Button button;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_user_id);
            points = itemView.findViewById(R.id.textView13);
            fullname = itemView.findViewById(R.id.textView10);
            email = itemView.findViewById(R.id.textView11);
            phone = itemView.findViewById(R.id.textView12);
            adress = itemView.findViewById(R.id.textView14);
            button =  itemView.findViewById(R.id.bt_userlist_block);
            itemView.setOnLongClickListener(this);
        }

        public void bindView(int position){
            User user = DataData.USERS_LIST.get(position);
            id.setText("ID пользователя: "+user.getId());
            points.setText(user.getPoints()+" баллов");
            email.setText("Почта: "+user.getEmail());
            fullname.setText("ФИО: "+user.getFull_name());
            phone.setText("Телефон: "+user.getPhone());
            adress.setText("Адрес: "+user.getAddress());
            if (user.getBlocked()==0)
                button.setText("Заблокировать");
            else button.setText("Разблоировать");
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(),view);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }


    }
    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterUserList.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
