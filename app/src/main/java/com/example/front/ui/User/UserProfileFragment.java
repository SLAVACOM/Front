package com.example.front.ui.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.front.CONST.CONST;
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

public class UserProfileFragment extends Fragment {
    private TextView name;
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

        editBt = view.findViewById(R.id.floatBt_editProf);


        if (user.getSecond_name()!=null) name.append("\nФамилия:" +user.getSecond_name());
        name.setText("Имя: "+ user.getName()+"\nПочта: "+ user.getEmail());
        name.setText("Полное имя: "+ user.getFull_name());
        if (user.isCurator()){
            name.append("\nРоль администратора: да");
            } else {
            name.append("\nРоль администратора: нет");
            }
        name.append("\nНомер телефона: "+user.getPhone());
        name.append("\nБаланс: "+user.getPoints());
            if (user.getCard_id() != null && !user.getCard_id().isEmpty())
                name.append("\nНомер карты: " + user.getCard_id());

        else {
            if (user.isCurator()){
                name.append("\nРоль администратора: да");
            } else {
                name.append("\nРоль администратора: нет");
            }
                name.append("\nНомер телефона: "+user.getPhone());
                name.append("\nПочта: "+user.getEmail());
                name.append("\nБаланс: "+ user.getPoints());
            if (user.getCard_id() != null && !user.getCard_id().isEmpty())
                name.append("\nНомер карты: " + user.getCard_id());
        }


        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,new UserProfileEditFragment()).addToBackStack(null);
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