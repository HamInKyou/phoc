package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TitleList extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.titlelist, container, false);

        DatabaseQueryClass.Theme.getThemes(new DataListener() {
            @Override
            public void getData(Object data) {
                JsonElement ele = new JsonParser().parse(data.toString());
                JsonObject obj = ele.getAsJsonObject();
                Log.d("Theme", obj.toString());

                //String title = obj.get("theme").getAsString();
                //String comment = obj.get("content").getAsString();
                //String date= obj.get("createdAt").getAsString();
                //String imgUri = obj.get("img").getAsString();
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.titlelistRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final TitleListItemAdapter adapter = new TitleListItemAdapter();

        adapter.addItem(new TitleListItem("가을"));
        adapter.addItem(new TitleListItem("기말고사"));
        adapter.addItem(new TitleListItem("안드로이드"));
        adapter.addItem(new TitleListItem("겨울"));
        adapter.addItem(new TitleListItem("사진"));
        adapter.addItem(new TitleListItem("고양이"));
        adapter.addItem(new TitleListItem("강아지"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListenr(new OnTitleListitemClickListener() {
            @Override
            public void onItemClick(TitleListItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(6, null);
            }
        });

        return rootView;

    }
}
