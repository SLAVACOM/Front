package com.example.front.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.ScannerActivity;
import com.example.front.data.EventJSON;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.scanner.CaptureAct;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventEditFragment extends Fragment {
    private EditText title, content;
    private Button qrBtn, nfcBtn;
    private EventJSON eventJSON;

    final static String TAG = "nfc_test";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_edit, container, false);
        eventJSON = DataBASE.EVENT_JSON_LIST.get(getArguments().getInt("pos"));
        title = view.findViewById(R.id.etv_edit_event_title);
        qrBtn = view.findViewById(R.id.scanner2);
        nfcBtn = view.findViewById(R.id.scanner);
        content = view.findViewById(R.id.etv_edit_event_content);
        title.setText(eventJSON.getTitle());
        content.setText(eventJSON.getPlace());
        nfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScannerActivity.class);
                intent.putExtra("post_id", eventJSON.getId());
                startActivityForResult(intent, 1);
            }
        });
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setPrompt("Отсканируйте QR код профиля");
                options.setBeepEnabled(true);
                options.setOrientationLocked(true);
                options.setCaptureActivity(CaptureAct.class);
                activityResultLauncher.launch(options);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String card = null;
        if (data != null) {
            card = data.getStringExtra("card_id");
        }
        if (resultCode == 1) {
            if (card == null || card.isEmpty()) error();
            else addParticipant(card);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) return;
        try{
            addParticipant(Integer.parseInt(result.getContents()));
        } catch (Throwable t) {
            t.printStackTrace();
            error();
        }
    });

    public Map<String,String> addParticipant(String participant_card_id){
        Map<String,String> e = new HashMap<>();
        e.put("participant_card_id", participant_card_id);
        addParticipant(e);
        return e;
    }
    public Map<String,String> addParticipant(Integer participant_id){
        Map<String,String> e = new HashMap<>();
        e.put("participant_id", participant_id == null ?  "0" : participant_id+"");
        addParticipant(e);
        return e;
    }
    public void addParticipant(Map<String,String> map) {
        Call<ResponseBody> addPointsNFC = RetrofitClient.getInstance().getApi().addEventParticipant("Bearer " + DataBASE.token, eventJSON.getId(), map);
        addPointsNFC.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()){
                    try {
                        Log.d(CONST.SERVER_LOG, "ADD Participant " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Пользователь или метка не найдены", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                error();
            }
        });
    }
    public void error() {
        Toast.makeText(getActivity(), "Метка не поддерживается", Toast.LENGTH_SHORT).show();
    }
}