package com.example.front.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.front.R;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    TextView name, ferstname, email,phone,balance;
    ImageView imageView, qrCode;
    String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        url =  url+"SLAVCOM";

        View view = inflater.inflate(R.layout.fragment_user,container,false);
        name = view.findViewById(R.id.tv_prof_name);
        phone = view.findViewById(R.id.tv_prof_number);
        balance = view.findViewById(R.id.tv_prof_balance);
        email =view.findViewById(R.id.tv_prof_email);
        ferstname = view.findViewById(R.id.tv_prof_firstname);
        imageView = view.findViewById(R.id.imageViewProfile);
        qrCode = view.findViewById(R.id.iv_prof_QrCode);
        name.setText("Имя: "+"Святослав");
        phone.setText("Номер телефона: "+"89370136981");
        email.setText("Почта: "+ "slavacom121@gmail.com");
        ferstname.setText("Фамилия:" +"Иванов");
        balance.setText("Баланс: "+"777");
        loadQrCode(url);
        return view;
    }

    private void loadQrCode(String url){
        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(qrCode, new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

    }
}