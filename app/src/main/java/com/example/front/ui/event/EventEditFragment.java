package com.example.front.ui.event;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.front.BuildConfig;
import com.example.front.R;
import com.example.front.ScannerActivity;
import com.example.front.data.EventJSON;
import com.example.front.data.User;
import com.example.front.data.database.DataBASE;
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
    private Button button,nfc;
    private User user;
    private EventJSON eventJSON;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    final static String TAG = "nfc_test";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_edit, container, false);
        eventJSON = DataBASE.EVENT_JSON_LIST.get(getArguments().getInt("pos"));
        title = view.findViewById(R.id.etv_edit_event_title);
        button = view.findViewById(R.id.scanner2);
        nfc = view.findViewById(R.id.scanner);
        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ScannerActivity.class);
                intent.putExtra("post_id",eventJSON.getId());
                startActivity(intent);
            }
        });
        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        if (nfcAdapter == null) {
            Toast.makeText(getContext(), "NO NFC Capabilities",
                    Toast.LENGTH_SHORT).show();
        }

        pendingIntent = PendingIntent.getActivity(getContext(), 0, new Intent(getContext(), this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


        content = view.findViewById(R.id.etv_edit_event_content);
        title.setText(eventJSON.getTitle());
        content.setText(eventJSON.getPlace());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setPrompt("");
                options.setBeepEnabled(true);
                options.setOrientationLocked(true);
                options.setCaptureActivity(CaptureAct.class);
                activityResultLauncher.launch(options);
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        assert nfcAdapter != null;

        nfcAdapter.enableForegroundDispatch(getActivity(), pendingIntent, null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }




    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            assert tag != null;
            byte[] payload = detectTagData(tag).getBytes();

        }
    }
    private String detectTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        long card_id =toDec(id);
        Toast.makeText(getContext(),""+card_id, Toast.LENGTH_SHORT).show();
        return String.valueOf(card_id);
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = 0; i < bytes.length; ++i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }


    ActivityResultLauncher<ScanOptions> activityResultLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents()!=null){
            Call<ResponseBody> setPoints = RetrofitClient.getInstance().getApi().addPointsQrCode("Bearer "+DataBASE.token,eventJSON.getId(),"PUT",Integer.valueOf(result.getContents()));
            setPoints.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code()==200){
                        Toast.makeText(getContext(), "Успешно", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    });
}