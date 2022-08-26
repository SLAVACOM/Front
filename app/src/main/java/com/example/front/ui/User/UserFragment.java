package com.example.front.ui.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front.R;
import com.example.front.data.DataData;
import com.example.front.retrofit.User;
import com.example.front.ui.appeal.AppealAddFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    private TextView name, lastname,secondname, curator,cardId,email,phone,balance;
    private FloatingActionButton editBt;
    private ImageView qrCode;
    private String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        User user= DataData.user;
        url =  url+user.getId();

        View view = inflater.inflate(R.layout.fragment_user,container,false);
        name = view.findViewById(R.id.tv_prof_name);
        phone = view.findViewById(R.id.tv_prof_number);
        balance = view.findViewById(R.id.tv_prof_balance);
        email =view.findViewById(R.id.tv_prof_email);
        secondname = view.findViewById(R.id.tv_prof_second_name);
        lastname = view.findViewById(R.id.tv_prof_last_name);
        cardId = view.findViewById(R.id.tv_prof_card_id);
        qrCode = view.findViewById(R.id.iv_prof_QrCode);
        curator = view.findViewById(R.id.tv_prof_role);
        editBt = view.findViewById(R.id.floatBt_editProf);


        name.setText("Имя: "+ user.getName());
        email.setText("Почта: "+ user.getEmail());
        if (user.getSecond_name()!=null) secondname.setText("Фамилия:" +user.getSecond_name());
        if (user.getLast_name()!=null){
            lastname.setText("Фамилия:" +user.getLast_name());
            if (user.isCurator()){
                lastname.setText("Роль администратора: да");
            } else {
                lastname.setText("Роль администратора: нет");
            }
            phone.setText("Номер телефона: "+user.getPhone());
            balance.setText("Баланс: "+user.getPoints());
            if (user.getCard_id() != null && !user.getCard_id().isEmpty())
                cardId.setText("Номер карты: " + user.getCard_id());


        }
        else {
            if (user.isCurator()){
                lastname.setText("Роль администратора: да");
            } else {
                lastname.setText("Роль администратора: нет");
            }
            curator.setText("Номер телефона: "+user.getPhone());
            phone.setText("Почта: "+user.getEmail());
            email.setText("Баланс: "+ user.getPoints());
            if (user.getCard_id() != null && !user.getCard_id().isEmpty())
                balance.setText("Номер карты: " + user.getCard_id());
        }
        loadQrCode(url);


        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new UserEditFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void loadQrCode(String url){

//        Picasso.with(getContext()).load(url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(qrCode, new com.squareup.picasso.Callback(){
//
//            @Override
//            public void onSuccess() {
//
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });

    }
}