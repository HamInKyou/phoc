package com.example.phoc.TitleListActivity;

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
import com.example.phoc.MyFeedActivity.MyFeedItemAdapter;
import com.example.phoc.R;
import com.example.phoc.main;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TitleList extends Fragment{
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.titlelist, container, false);
        recyclerView = rootView.findViewById(R.id.titlelistRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final TitleListItemAdapter adapter = new TitleListItemAdapter();

        DatabaseQueryClass.Theme.getThemes(new DataListener() {
            @Override
            public void getData(Object data) {
                JsonElement ele = new JsonParser().parse(data.toString());
                JsonObject obj = ele.getAsJsonObject();
                Log.d("Theme", obj.toString());
                adapter.addItem(new TitleListItem(obj.get("name").getAsString()));
                setAdapterToView(adapter);
            }
        });

        adapter.setOnItemClickListenr(new OnTitleListitemClickListener() {
            @Override
            public void onItemClick(TitleListItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(6, null);

            }
        });
        return rootView;
    }
    private void setAdapterToView(final TitleListItemAdapter adapter){
        recyclerView.setAdapter(adapter);
    }
}
