package com.example.phoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phoc.DatabaseConnection.DataListener;
import com.example.phoc.DatabaseConnection.DatabaseQueryClass;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class SearchUser extends Fragment{

        ImageView searchBtn;
        EditText inputText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.searchuser, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.searchuserRecyclerView);

        searchBtn = rootView.findViewById(R.id.searchBtn);
        inputText = rootView.findViewById(R.id.editText);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputText.getText().toString().length() < 0)
                    return ;
                else {
                    DatabaseQueryClass.User.findSimilarUserByNickname(inputText.getText().toString(), new DataListener() {
                        @Override
                        public void getData(Object data) {
                            JsonElement ele = new JsonParser().parse(data.toString());
                            JsonObject obj = ele.getAsJsonObject();
                            Log.d("Similar", obj.toString());

                            //list 추가
                        }
                    });
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        final SearchUserItemAdapter adapter = new SearchUserItemAdapter();

        adapter.addItem(new SearchUserItem("김용후"));
        adapter.addItem(new SearchUserItem("재갈용후"));
        adapter.addItem(new SearchUserItem("안드용후"));
        adapter.addItem(new SearchUserItem("박용후"));
        adapter.addItem(new SearchUserItem("최용후"));
        adapter.addItem(new SearchUserItem("함용후"));
        adapter.addItem(new SearchUserItem("남궁용후"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnSearchUserItemClickListener() {
            @Override
            public void onItemClick(SearchUserItemAdapter.ViewHolder holder, View view, int position) {
                ((main) getActivity()).onFragmentSelected(7, null);
            }
        });
        return rootView;
    }
}
