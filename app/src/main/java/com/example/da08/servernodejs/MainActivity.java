package com.example.da08.servernodejs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnWrite;
    private RecyclerView recyclerView;
    private List<Bbs> datas = new ArrayList<>();
    private RecyclerAdapter adapter;

    public final int REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        datas = new ArrayList<>();
        adapter = new RecyclerAdapter(this,datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loader();

    }

    private void initView() {
        btnWrite = (Button) findViewById(R.id.btnWrite);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                 호출시 startActivity를 사용하면 onResume 처리를 따로 해줘야한다 때문에 다른걸 사용해서 호출해보기
                 */
                Intent intent  =new Intent(MainActivity.this,WriteActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            this.datas.clear();
            loader();
        }
    }

    private void loader(){
        // 1 레트로핏 생성
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Ibbs.SERVER)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        // 2 서비스 연결
        Ibbs myServer = client.create(Ibbs.class);
        // 3 서비스의 특정 함수 호출 -> Observable 생성
        Observable<ResponseBody> observable = myServer.read();

        // 4 subscribe 등록
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseBody -> {
                            // 데이터를 꺼내고
                            String jsonString = responseBody.string();
//                            Log.e("retrofit", "datas:"+responseBody.string());
                            Gson gson = new Gson();
//                            Type type = new TypeToken<List<Bbs>>(){}.getType(); // 컨버팅하기 위한 타입 지정
//                            gson.fromJson(jsonString, Bbs.class);
                            Bbs datas[] = gson.fromJson(jsonString, Bbs[].class);
                            // 아답터에 세팅
                            for(Bbs bbs : datas){
                                this.datas.add(bbs);
                            }
                            // 아답터 갱신
                            adapter.notifyDataSetChanged();
                        }
                );
    }


}
