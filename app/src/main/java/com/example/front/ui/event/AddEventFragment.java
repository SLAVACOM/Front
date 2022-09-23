package com.example.front.ui.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.front.CONST.CONST;
import com.example.front.R;
import com.example.front.ScannerActivity;
import com.example.front.data.EventJSON;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.Retrofit;
import com.example.front.retrofit.call.ValidateCallback;
import com.example.front.retrofit.responses.ValidationResponse;
import com.example.front.scanner.CaptureAct;
import com.example.front.ui.components.AppEditText;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventFragment extends Fragment {

    private AppEditText title, place, point;
    private TextView date, frag_title;
    private Button add;
    private Button qrBtn, nfcBtn;
    private Calendar dateAndTime = Calendar.getInstance();
    EventJSON event;
    public void checkEvent() {
        if (event == null || (DataBASE.user.getRole() & CONST.CURATOR_ROLE) == 0 && DataBASE.user.getRole() < CONST.ADMIN_ROLE) {
            qrBtn.setVisibility(View.INVISIBLE);
            nfcBtn.setVisibility(View.INVISIBLE);
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date c= sdf.parse(event.getDate());
            dateAndTime.setTime(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        title.setText(event.getTitle());
        place.setText(event.getPlace());
        point.setText(""+event.getPoints());
        frag_title.setText(R.string.edit_event_fragment_title);
        add.setText(R.string.save_title);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        Bundle arguments = getArguments();
        event = arguments != null ? DataBASE.EVENT_JSON_LIST.get(arguments.getInt("pos")) : null;
        date = view.findViewById(R.id.textView9);
        title = view.findViewById(R.id.etv_title_event);
        place = view.findViewById(R.id.etv_title_event2);
        point = view.findViewById(R.id.etv_points_event4);
        qrBtn = view.findViewById(R.id.scanner2);
        nfcBtn = view.findViewById(R.id.scanner);
        frag_title = view.findViewById(R.id.textView7);
        add = view.findViewById(R.id.bt_add);
        checkEvent();
        date.setOnClickListener((view1 -> {
            setDate(view1);
        }));
        nfcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScannerActivity.class);
                intent.putExtra("post_id", event.getId());
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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String date = format.format(dateAndTime.getTime());

                Call<ResponseBody> addEvent;
                if (event == null) {
                    addEvent= Retrofit.getInstance().getApi().addEvent("Bearer " + DataBASE.token, title.getText().toString(), place.getText().toString(), date,point.getText().toString());
                } else {
                    event.setTitle(title.getText().toString());
                    event.setPlace(place.getText().toString());
                    event.setPoints(point.getText().toString().length() > 0 ? Integer.parseInt(point.getText().toString()) : 0);
                    event.setDate(date);
                    addEvent= Retrofit.getInstance().getApi().editEvent("Bearer " + DataBASE.token, event.getId(), event);
                }
                addEvent.enqueue(new ValidateCallback<ResponseBody>() {
                    @Override
                    public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }

                    @Override
                    public void on422(Call<ResponseBody> call, Response<ResponseBody> response, ValidationResponse errors) {
                        String e = errors.getError("title");
                        if (e!= null) title.setError(e);
                        e = errors.getError("place");
                        if (e!= null) place.setError(e);
                        e = errors.getError("points");
                        if (e!= null) point.setError(e);
                        e = errors.getError("date");
                        if (e!= null) AddEventFragment.this.date.setError(e);

                    }

                    @Override
                    public Context getContext() {
                        return getActivity();
                    }
                });
            }
        });
        setInitialDateTime();
        return view;
    }

    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime() {
        new TimePickerDialog(getContext(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        date.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    TimePickerDialog.OnTimeSetListener t = (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        setInitialDateTime();
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            dateAndTime.set(Calendar.YEAR, y);
            dateAndTime.set(Calendar.MONTH, m);
            dateAndTime.set(Calendar.DAY_OF_MONTH, d);
            setTime();
        }

    };

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
        Call<ResponseBody> addPointsNFC = Retrofit.getInstance().getApi().addEventParticipant("Bearer " + DataBASE.token, event.getId(), map);
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