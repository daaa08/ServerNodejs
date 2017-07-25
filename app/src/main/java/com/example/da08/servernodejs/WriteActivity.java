package com.example.da08.servernodejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class WriteActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editAuthor;
    private EditText editContent;
    private Button btnInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initView();

        btnInput.setOnClickListener(
                v ->{
                    String title = editTitle.getText().toString();
                    String author = editAuthor.getText().toString();
                    String content = editContent.getText().toString();

                    postData(title, author,content);

                }
        );

    }

    private void postData(String title, String author, String content){
        // 0 입력할 객체 생성
        Bbs bbs = new Bbs();
        bbs.title = title;
        bbs.author = author;
        bbs.content = content;

        // 입력값이 null일때
        if(bbs.title == null || bbs.title.equals("") || bbs.title.equals(null)
                || bbs.author == null || bbs.author.equals("") || bbs.author.equals(null)
                || bbs.content == null || bbs.content.equals("") || bbs.content.equals(null)){

            Toast.makeText(getBaseContext(),"빈 칸을 채워주세요",Toast.LENGTH_SHORT).show();

        }else {  // 입력값이 모두 입력되었을때

            // 1 레트로핏 생성
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Ibbs.SERVER)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            // 2 서비스 연결
            Ibbs myServer = client.create(Ibbs.class);
            // 3 서비스의 특정 함수 호출 -> Observable 생성
            Gson gson = new Gson();
            // bbs 객체를 수동으로 전달하기 위해서는 bbs객체를 수동으로 변환하고 requestBody에 미디어타입과 String으로 변환된 데이터를 담아서 전송
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),
                    gson.toJson(bbs)
            );
            Observable<ResponseBody> observable = myServer.write(body);
            // 4 subscribe 등록
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            responseBody -> {
                                String result = responseBody.string();  // 결과코드를 넘겨서 처리

                            /*
                            1. 정상적으로 새 글을 입력한 후 데이터를 전송하고 종료
                            2. 새글을 전송하지 않고 화면을 그냥 종료

                            위의 두 가지를 구분해서 결과값을 호출한 MainActivity로 넘겨서 처리
                             */
                                finish();

                            }
                    );
        }

    }

    private void initView() {
        editTitle = (EditText) findViewById(R.id.editTitle);
        editAuthor = (EditText) findViewById(R.id.editAuthor);
        editContent = (EditText) findViewById(R.id.editContent);
        btnInput = (Button) findViewById(R.id.btnInput);
    }
}
