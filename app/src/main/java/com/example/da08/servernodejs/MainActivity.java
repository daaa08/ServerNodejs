package com.example.da08.servernodejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnWrite;
    private RecyclerView recyclerView;
    private List<Bbs> datas = new ArrayList<>();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        lambdaTest();
        initView();
        datas = new ArrayList<>();
        adapter = new RecyclerAdapter(this,datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    private void lambdaTest() {
//        new Thread(
//                () -> Log.i("Labmda", "-===========================-")
//        ).start();
//    }

    private void initView() {
        btnWrite = (Button) findViewById(R.id.btnWrite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }
}
