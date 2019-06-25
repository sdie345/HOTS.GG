package kr.swote.hotsgg.views.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.datas.HeroData;

public class HeroResultActivity extends AppCompatActivity {
    View profile;
    View allSkills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_result);
        profile = findViewById(R.id.hero_profile);
        allSkills = findViewById(R.id.hero_total_skills);

        Intent intent = getIntent(); // 넘어온 intent 확인
        HeroData heroData = new Gson().fromJson(intent.getStringExtra("heroData"), HeroData.class); // 넘어온 string intent 변환
    }
}
