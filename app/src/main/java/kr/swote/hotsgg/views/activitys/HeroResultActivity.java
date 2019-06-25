package kr.swote.hotsgg.views.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.datas.Ability;
import kr.swote.hotsgg.functions.datas.HeroData;
import kr.swote.hotsgg.functions.datas.HeroicAbility;
import kr.swote.hotsgg.functions.datas.OtherAbility;
import kr.swote.hotsgg.functions.datas.Trait;

public class HeroResultActivity extends AppCompatActivity {
    View profile;
    View allSkills;
    LinearLayout traitLayout;
    LinearLayout abilityLayout;
    LinearLayout heroicAbilityLayout;
    LinearLayout otherAbilityLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_result);
        profile = findViewById(R.id.hero_profile);
        allSkills = findViewById(R.id.hero_total_skills);
        //하위 스킬 인플레이트 시킬 리니어 레이아웃 찾아옴
        traitLayout = allSkills.findViewById(R.id.traits);
        abilityLayout = allSkills.findViewById(R.id.ability);
        heroicAbilityLayout = allSkills.findViewById(R.id.heroic_ability);
        otherAbilityLayout = allSkills.findViewById(R.id.other_ability);
        Intent intent = getIntent(); // 넘어온 intent 확인
        HeroData heroData = new Gson().fromJson(intent.getStringExtra("heroData"), HeroData.class); // 넘어온 string intent 변환

        //hero profile 작성란
        //영웅 이름
        TextView name = profile.findViewById(R.id.name);
        name.setText(heroData.getName());
        //영웅 타이틀
        TextView title = profile.findViewById(R.id.title);
        title.setText(heroData.getTitle());
        //영웅 초상화
        ImageView portrait = profile.findViewById(R.id.portrait);
        setBitmap(portrait, heroData.getCardPortrait());
        //영웅 역할
        TextView role = profile.findViewById(R.id.role);
        role.setText(heroData.getRole().getName());
        //영웅 타입
        TextView type = profile.findViewById(R.id.type);
        type.setText(heroData.getType().getName());
        //영웅 난이도
        TextView difficulty = profile.findViewById(R.id.difficulty);
        difficulty.setText(heroData.getDifficulty());
        //영웅 세계관
        TextView franchise = profile.findViewById(R.id.franchise);
        franchise.setText(heroData.getFranchise());
        //영웅 설명
        TextView description = profile.findViewById(R.id.description);
        description.setText(heroData.getDescription());
        //영웅 짧은 설명
        TextView short_description = profile.findViewById(R.id.short_description);
        short_description.setText(heroData.getShortDescription());


        //고유 스킬
        Trait trait = heroData.getTrait();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View trait_skill = inflater.inflate(R.layout.hero_skill, traitLayout, false);
        //스킬 아이콘
        ImageView trait_skillImage = trait_skill.findViewById(R.id.skill_image);
        setBitmap(trait_skillImage, trait.getIcon());
        //스킬 제목
        TextView trait_skillTitle = trait_skill.findViewById(R.id.skill_title);
        trait_skillTitle.setText(trait.getName());
        //스킬 설명
        TextView trait_skillDescription = trait_skill.findViewById(R.id.skill_description);
        trait_skillDescription.setText(trait.getDescription());

        //부모 뷰에 붙임
        traitLayout.addView(trait_skill);
        //일반 스킬
        List<Ability> abilities = heroData.getAbilities();
        for (Ability ability : abilities) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View skill = layoutInflater.inflate(R.layout.hero_skill, abilityLayout, false);
            //스킬 아이콘
            ImageView skillImage = skill.findViewById(R.id.skill_image);
            setBitmap(skillImage, ability.getIcon());
            //스킬 제목
            TextView skillTitle = skill.findViewById(R.id.skill_title);
            skillTitle.setText(ability.getName());
            //스킬 설명
            TextView skillDescription = skill.findViewById(R.id.skill_description);
            skillDescription.setText(ability.getDescription());

            //부모 뷰에 붙임
            abilityLayout.addView(skill);
        }
        // 궁극기
        List<HeroicAbility> heronicAbilities = heroData.getHeroicAbilities();
        for (HeroicAbility ability : heronicAbilities) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View skill = layoutInflater.inflate(R.layout.hero_skill, heroicAbilityLayout, false);
            //스킬 아이콘
            ImageView skillImage = skill.findViewById(R.id.skill_image);
            setBitmap(skillImage, ability.getIcon());
            //스킬 제목
            TextView skillTitle = skill.findViewById(R.id.skill_title);
            skillTitle.setText(ability.getName());
            //스킬 설명
            TextView skillDescription = skill.findViewById(R.id.skill_description);
            skillDescription.setText(ability.getDescription());

            //부모 뷰에 붙임
            heroicAbilityLayout.addView(skill);
        }
        // 기타 스킬
        List<OtherAbility> otherAbilities  = heroData.getOtherAbilities();
        if (otherAbilities != null)
            for (OtherAbility ability : otherAbilities) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View skill = layoutInflater.inflate(R.layout.hero_skill, otherAbilityLayout, false);
                //스킬 아이콘
                ImageView skillImage = skill.findViewById(R.id.skill_image);
                setBitmap(skillImage, ability.getIcon());
                //스킬 제목
                TextView skillTitle = skill.findViewById(R.id.skill_title);
                skillTitle.setText(ability.getName());
                //스킬 설명
                TextView skillDescription = skill.findViewById(R.id.skill_description);
                skillDescription.setText(ability.getDescription());

                //부모 뷰에 붙임
                otherAbilityLayout.addView(skill);
            }
        else {
            TextView other_ab = allSkills.findViewById(R.id.other_ab);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) other_ab.getLayoutParams();
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
            other_ab.setLayoutParams(layoutParams);
            other_ab.setText("");
        }
    }
    //img URL -> BItmap
    private void setBitmap(ImageView imageView, String url) {
        Thread thread = new Thread() {
            URL imgUrl;
            HttpURLConnection connection;
            InputStream is;
            Bitmap retBitmap = null;
            @Override
            public void run() {
                try{
                    imgUrl = new URL(url);
                    connection = (HttpURLConnection) imgUrl.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    is = connection.getInputStream();
                    retBitmap = BitmapFactory.decodeStream(is);
                    imageView.setImageBitmap(retBitmap);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}
