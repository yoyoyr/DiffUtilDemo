package com.example.pby.diffutildemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {

    private final List<Bean> mDataList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private Button mClickButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerAdapter = new RecyclerAdapter(mDataList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        addData(4, mDataList);
        mRecyclerAdapter.notifyDataSetChanged();


        mClickButton = findViewById(R.id.click);
        mClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });
    }

    private void refreshData() {
        final List<Bean> oldDataList = new ArrayList<>();
        final List<Bean> newDataList = new ArrayList<>();

        // deep copy
        for (int i = 0; i < mDataList.size(); i++) {
            oldDataList.add(mDataList.get(i).deepCopy());
        }
        // change
        for (int i = mDataList.size() - 1; i >= 0; i--) {
            newDataList.add(mDataList.get(i));
        }

//        newDataList.forEach(new Consumer<Bean>() {
//            @Override
//            public void accept(Bean bean) {
//                System.out.println("addd " + bean.toString());
//            }
//        });
//
//        oldDataList.forEach(new Consumer<Bean>() {
//            @Override
//            public void accept(Bean bean) {
//                System.out.println("old " + bean.toString());
//            }
//        });
        // diffUtil
        RecyclerItemCallback recyclerItemCallback = new RecyclerItemCallback(oldDataList, newDataList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(recyclerItemCallback);

        diffResult.dispatchUpdatesTo(mRecyclerAdapter);
    }

    private void addData(int count, List<Bean> list) {
        for (int i = 0; i < count; i++) {
            Bean bean = new Bean(UUID.randomUUID().toString(), "content = " + i, Color.parseColor(ColorUtils.generateRandomColor()));
            list.add(bean);
        }
    }
}
