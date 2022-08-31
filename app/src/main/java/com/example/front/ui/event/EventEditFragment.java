package com.example.front.ui.event;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.front.R;
import com.example.front.data.User;
import com.example.front.data.database.DataBASE;
import com.example.front.data.EventJSON;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.scanner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventEditFragment extends Fragment {
    private EditText title,content;
    private Button button;
    private User user;
    private EventJSON eventJSON;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_edit, container, false);
        eventJSON = DataBASE.EVENT_JSON_LIST.get(getArguments().getInt("pos"));
        title = view.findViewById(R.id.etv_edit_event_title);
        button = view.findViewById(R.id.scanner);

        content = view.findViewById(R.id.etv_edit_event_content);
        title.setText(eventJSON.getTitle());
        content.setText(eventJSON.getPlace());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CodeScanner();

            }
        });


        return view;
    }

    private void CodeScanner(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        activityResultLauncher.launch(options);
    }


    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents()!=null){
            try {
                Call<User> getUser = RetrofitClient.getInstance().getApi().getUser("Berar "+DataBASE.token,Integer.valueOf(result.getContents()));
                getUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user.setId(response.body().getId());
                    user.setFull_name(response.body().getFull_name());
                    user.setPoints(response.body().getPoints());

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
            }catch (Exception e){
                e.printStackTrace();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Пользователь "+user.getFull_name());
            builder.setMessage(result.getContents());
            builder.setPositiveButton("Добавить баллы", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Call<ResponseBody> setPoints = RetrofitClient.getInstance().getApi().setPoints("Bearer "+DataBASE.token,user.getId(),user.getPoints()+eventJSON.getPoints());
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("Не добовлять", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    });
}