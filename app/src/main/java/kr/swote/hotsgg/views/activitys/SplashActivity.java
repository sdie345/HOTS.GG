package kr.swote.hotsgg.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import kr.swote.hotsgg.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashHandler(), 1000); // 1초 후에 hd handler 실행  1000ms = 1초

    }

    private class splashHandler implements Runnable {
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, MainaActivity로 이동
            SplashActivity.this.finish(); // 로딩페이지 Activity stack에서 제거
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_in); // 페이지 전환 효과
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
