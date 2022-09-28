package com.example.front.ui.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import com.example.front.retrofit.Retrofit;
import com.example.front.retrofit.responses.ObjectResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileFragment extends Fragment {
    private FloatingActionButton editBt;
    private ImageView qrCode;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);

        String url = CONST.SERVER_URl + "/storage/" + DataBASE.user.getQr();
        qrCode = view.findViewById(R.id.iv_prof_QrCode);
        Picasso.get().load(url)
                .placeholder(R.drawable.ic_person)
                .into(qrCode);
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        qrCode.setMinimumWidth(dm.widthPixels * 3 / 4);
        qrCode.setMinimumHeight(dm.widthPixels * 3 / 4);

        view.findViewById(R.id.name);

        editBt = view.findViewById(R.id.floatBt_editProf);

        printUser(DataBASE.user);
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, new UserProfileEditFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public void printUser(User user) {
        ((TextView) view.findViewById(R.id.surname)).setText(user.getSecond_name());
        ((TextView) view.findViewById(R.id.name)).setText(user.getName());
        ((TextView) view.findViewById(R.id.lastname)).setText(user.getLast_name());
        ((TextView) view.findViewById(R.id.phone)).setText(user.getPhone());
        ((TextView) view.findViewById(R.id.email)).setText(user.getEmail());
        ((TextView) view.findViewById(R.id.address)).setText((!user.getAddress().isEmpty() ? user.getAddress() : "не указан"));
        ((TextView) view.findViewById(R.id.profile_points_tv)).setText(user.getPoints()+"");
        ((TextView) view.findViewById(R.id.card_id)).setText(("не выдана"));
        if (user.getCard_id() != null && !user.getCard_id().isEmpty())
            ((TextView) view.findViewById(R.id.card_id)).setText(user.getCard_id());
    }

    @Override
    public void onResume() {
        super.onResume();
        Call<ObjectResponse<User>> getProfileData = Retrofit.getInstance().getApi().getProfile("Bearer " + DataBASE.token);
        getProfileData.enqueue(new Callback<ObjectResponse<User>>() {
            @Override
            public void onResponse(Call<ObjectResponse<User>> call, Response<ObjectResponse<User>> response) {
                if (response.isSuccessful()) {
                    User reponseUser = response.body().getData();
                    DataBASE.user = reponseUser;
                    Log.d(CONST.SERVER_LOG, "USERS " + DataBASE.user);
                    printUser(reponseUser);
                }
            }

            @Override
            public void onFailure(Call<ObjectResponse<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}