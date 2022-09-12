package com.example.front.ui.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.front.R;
import com.example.front.data.database.DataBASE;
import com.example.front.retrofit.RetrofitClient;
import com.example.front.retrofit.call.ValidateCallback;
import com.example.front.retrofit.responses.ValidationResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddEventFragment extends Fragment {

    private EditText title, place, point;
    private TextView date;
    private Button add;
    private Calendar dateAndTime = Calendar.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        date = view.findViewById(R.id.textView9);
        title = view.findViewById(R.id.etv_title_event);
        place = view.findViewById(R.id.etv_title_event2);
        point = view.findViewById(R.id.etv_points_event4);
        add = view.findViewById(R.id.bt_add);
        date.setOnClickListener((view1 -> {
            setDate(view1);
        }));
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String date = format.format(dateAndTime.getTime());

                Call<ResponseBody> addEvent = RetrofitClient.getInstance().getApi().addEvent("Bearer " + DataBASE.token, title.getText().toString(), place.getText().toString(), date,point.getText().toString());
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
}