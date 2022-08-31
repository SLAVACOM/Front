package com.example.front.ui.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front.CONST.CONST;
import com.example.front.LoginActivity;
import com.example.front.MainActivity;
import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.data.User;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.responses.ObjectResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {
    private TextView name, lastname,secondname, curator,cardId,email,phone,balance;
    private FloatingActionButton editBt;
    private ImageView qrCode;
    private String url = "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        User user= DataBASE.user;

        url =  url+user.getId();
        qrCode = view.findViewById(R.id.iv_prof_QrCode);
        Picasso.get().load(url).into(qrCode);

        name = view.findViewById(R.id.tv_prof_name);
        phone = view.findViewById(R.id.tv_prof_number);
        balance = view.findViewById(R.id.tv_prof_balance);
        email =view.findViewById(R.id.tv_prof_email);
        secondname = view.findViewById(R.id.tv_prof_second_name);
        lastname = view.findViewById(R.id.tv_prof_last_name);
        cardId = view.findViewById(R.id.tv_prof_card_id);
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


        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new UserListEditFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Call<ObjectResponse<User>> getProfileData = RetrofitClient.getInstance().getApi().getProfile("Bearer " + DataBASE.token);
        getProfileData.enqueue(new Callback<ObjectResponse<User>>() {
            @Override
            public void onResponse(Call<ObjectResponse<User>> call, Response<ObjectResponse<User>> response) {
                if (response.isSuccessful()) {
                    DataBASE.user = response.body().getData();
                    Log.d(CONST.SERVER_LOG, "USERS " + DataBASE.user);
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse<User>> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }


}