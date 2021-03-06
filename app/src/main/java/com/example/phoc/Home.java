package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Home extends Fragment implements View.OnClickListener {

    Button fromHome2MakeFeedBtn;
    TextView todayTheme;
    String titleName;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);

        fromHome2MakeFeedBtn = rootView.findViewById(R.id.fromHome2MakeFeedBtn);
        todayTheme = rootView.findViewById((R.id.todayTheme));

        fromHome2MakeFeedBtn.setOnClickListener(this);
        todayTheme.setOnClickListener(this);

        DatabaseQueryClass.Theme.getTodayTheme(new DataListener() {
            @Override
            public void getData(Object data, String id) {
                String json = new Gson().toJson(data);
                JsonElement element = new JsonParser().parse(json);
                JsonObject jobj = element.getAsJsonObject();

                String name = element.getAsJsonObject().get("name").getAsString();
                todayTheme.setText(jobj.get("name").toString());
                titleName = jobj.get("name").toString();
                int lastPoint = titleName.length();
                titleName = titleName.substring(1, lastPoint-1);
            }
        });


        return rootView;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if(v==fromHome2MakeFeedBtn) {
            bundle.putString("titleName", titleName);
            ((main) getActivity()).onFragmentSelected(5, bundle);
        }
        else if(v==todayTheme) {
            bundle.putString("theme", titleName);
            ((main) getActivity()).onFragmentSelected(6, bundle);
        }
    }
}
