package com.example.phoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Myfeed extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myfeed, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.myFeedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        MyfeedItemAdapter adapter = new MyfeedItemAdapter();

        adapter.addItem(new MyfeedItem("가을","가을 날씨가 좋더라고요","2019-11-29"));
        adapter.addItem(new MyfeedItem("완성","드디어되네 씌바거","2019-11-29"));
        adapter.addItem(new MyfeedItem("최지웅","짱","2019-11-29"));

        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
