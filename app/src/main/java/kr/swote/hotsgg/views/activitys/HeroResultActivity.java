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
import kr.swote.hotsgg.functions.datas.Characteristic;
import kr.swote.hotsgg.functions.datas.HeroData;
import kr.swote.hotsgg.functions.datas.HeroicAbility;
import kr.swote.hotsgg.functions.datas.OtherAbility;
import kr.swote.hotsgg.functions.datas.Trait;

public class HeroResultActivity extends AppCompatActivity {
    View profile;
    View allSkills;
    View tiers;
    LinearLayout traitLayout;
    LinearLayout abilityLayout;
    LinearLayout heroicAbilityLayout;
    LinearLayout otherAbilityLayout;
    LinearLayout tier1;
    LinearLayout tier2;
    LinearLayout tier3;
    LinearLayout tier4;
    LinearLayout tier5;
    LinearLayout tier6;
    LinearLayout tier7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_result);
        profile = findViewById(R.id.hero_profile);
        allSkills = findViewById(R.id.hero_total_skills);
        tiers = findViewById(R.id.hero_total_characteristic);
        //하위 스킬 inflate 시킬 레이아웃 찾아오기
        traitLayout = allSkills.findViewById(R.id.traits);
        abilityLayout = allSkills.findViewById(R.id.ability);
        heroicAbilityLayout = allSkills.findViewById(R.id.heroic_ability);
        otherAbilityLayout = allSkills.findViewById(R.id.other_ability);
        //하위 티어 inflate 시킬 레이아웃 찾아오기.
        tier1 = tiers.findViewById(R.id.tier1);
        tier2 = tiers.findViewById(R.id.tier2);
        tier3 = tiers.findViewById(R.id.tier3);
        tier4 = tiers.findViewById(R.id.tier4);
        tier5 = tiers.findViewById(R.id.tier5);
        tier6 = tiers.findViewById(R.id.tier6);
        tier7 = tiers.findViewById(R.id.tier7);
        Intent intent = getIntent(); // 넘어온 intent 확인
        HeroData heroData = new Gson().fromJson(intent.getStringExtra("heroData"), HeroData.class); // 넘어온 string intent 변환


        //hero Appbar 수정
        //circle icon 설정
        ImageView circle_icon = findViewById(R.id.circle_icon);
        setBitmap(circle_icon, heroData.getCircleIcon());
        //name, sub title 설정
        TextView name = findViewById(R.id.name);
        name.setText(heroData.getName());
        TextView subTitle = findViewById(R.id.sub_title);
        subTitle.setText(heroData.getTitle());
        //hero profile 작성란
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


        //Hero Skill 작성란
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


        // Hero Characteristic 작성란
        List<Characteristic> characteristics = heroData.getCharacteristic();
        for (Characteristic characteristic : characteristics) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (characteristic.getTier()) {
                case "1" :
                    View character1 = layoutInflater.inflate(R.layout.hero_characteristic, tier1, false);
                    //특성 이름
                    TextView characterName1 = character1.findViewById(R.id.character_name);
                    characterName1.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText1 = character1.findViewById(R.id.character_text);
                    characterText1.setText(characteristic.getText());
                    tier1.addView(character1);
                    break;
                case "2" :
                    View character2 = layoutInflater.inflate(R.layout.hero_characteristic, tier2, false);
                    //특성 이름
                    TextView characterName2 = character2.findViewById(R.id.character_name);
                    characterName2.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText2 = character2.findViewById(R.id.character_text);
                    characterText2.setText(characteristic.getText());
                    tier2.addView(character2);
                    break;
                case "3" :
                    View character3 = layoutInflater.inflate(R.layout.hero_characteristic, tier3, false);
                    //특성 이름
                    TextView characterName3 = character3.findViewById(R.id.character_name);
                    characterName3.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText3 = character3.findViewById(R.id.character_text);
                    characterText3.setText(characteristic.getText());
                    tier3.addView(character3);
                    break;
                case "4" :
                    View character4 = layoutInflater.inflate(R.layout.hero_characteristic, tier4, false);
                    //특성 이름
                    TextView characterName4 = character4.findViewById(R.id.character_name);
                    characterName4.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText4 = character4.findViewById(R.id.character_text);
                    characterText4.setText(characteristic.getText());
                    tier4.addView(character4);
                    break;
                case "5" :
                    View character5 = layoutInflater.inflate(R.layout.hero_characteristic, tier5, false);
                    //특성 이름
                    TextView characterName5 = character5.findViewById(R.id.character_name);
                    characterName5.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText5 = character5.findViewById(R.id.character_text);
                    characterText5.setText(characteristic.getText());
                    tier5.addView(character5);
                    break;
                case "6" :
                    View character6 = layoutInflater.inflate(R.layout.hero_characteristic, tier6, false);
                    //특성 이름
                    TextView characterName6 = character6.findViewById(R.id.character_name);
                    characterName6.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText6 = character6.findViewById(R.id.character_text);
                    characterText6.setText(characteristic.getText());
                    tier6.addView(character6);
                    break;
                case "7" :
                    View character7 = layoutInflater.inflate(R.layout.hero_characteristic, tier7, false);
                    //특성 이름
                    TextView characterName7 = character7.findViewById(R.id.character_name);
                    characterName7.setText(characteristic.getName());
                    //특성 설명
                    TextView characterText7 = character7.findViewById(R.id.character_text);
                    characterText7.setText(characteristic.getText());
                    tier7.addView(character7);
                    break;
            }
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
