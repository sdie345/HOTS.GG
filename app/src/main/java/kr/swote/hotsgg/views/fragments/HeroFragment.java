package kr.swote.hotsgg.views.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.API.API;
import kr.swote.hotsgg.functions.API.Client;
import kr.swote.hotsgg.functions.adapter.HeroRecyclerAdapter;
import kr.swote.hotsgg.functions.datas.HeroData;
import kr.swote.hotsgg.views.activitys.HeroSearchActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeroFragment extends Fragment {
    TabLayout tabLayout;
    RecyclerView recyclerView;
    HeroRecyclerAdapter adapter;
    ArrayList<HeroData> datas = new ArrayList<>();
    ArrayList<HeroData> recyclerData = new ArrayList<>();
    ArrayList<HeroData> tank = new ArrayList<>();
    ArrayList<HeroData> bruiser = new ArrayList<>();
    ArrayList<HeroData> supporter = new ArrayList<>();
    ArrayList<HeroData> healer = new ArrayList<>();
    ArrayList<HeroData> meelAssassin = new ArrayList<>();
    ArrayList<HeroData> rangeAssassin = new ArrayList<>();
    FloatingSearchView floatingSearchView;
    SharedPreferences preferences;
    API api;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hero, container, false);
        recyclerView = rootView.findViewById(R.id.hero_view_recycler);
        floatingSearchView = rootView.findViewById(R.id.floating_search_view);
        tabLayout = rootView.findViewById(R.id.hero_tabs);
        //버튼 클릭 이벤트 처리
        floatingSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HeroSearchActivity.class);
                startActivity(intent);
            }
        });
        //TabLayout tab 추가
        //전사
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_tank)));
        //투사
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_bruiser)));
        //지원가
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_support)));
        //치유사
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_healer)));
        //원거리 암살자
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_ranged_assassin)));
        //근접 암살자
        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(R.drawable.ic_melee_assassin)));

        //레트로핏
        api = Client.getClient().create(API.class);
        Call<ArrayList<HeroData>> getHero = api.getHeroData();
        getHero.enqueue(new Callback<ArrayList<HeroData>>() {
            @Override
            public void onResponse(Call<ArrayList<HeroData>> call, Response<ArrayList<HeroData>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    datas.addAll(response.body());
                    //데이터들 분류
                    datas.forEach(it -> {
                        if (it.getExpandedRole().getName().equals("전사"))
                            tank.add(it);
                        else if (it.getExpandedRole().getName().equals("투사"))
                            bruiser.add(it);
                        else if (it.getExpandedRole().getName().equals("지원가"))
                            supporter.add(it);
                        else if (it.getExpandedRole().getName().equals("치유사"))
                            healer.add(it);
                        else if (it.getExpandedRole().getName().equals("근거리 암살자"))
                            meelAssassin.add(it);
                        else
                            rangeAssassin.add(it);
                    });
                    //리사이클러뷰 데이터 설정
                    recyclerData.addAll(tank);
                    adapter = new HeroRecyclerAdapter(recyclerData);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    Toast.makeText(getActivity(), "영웅 정보를 불러오는데 실패했습니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HeroData>> call, Throwable t) {
                Toast.makeText(getActivity(), "서버에러가 발생했습니다!", Toast.LENGTH_SHORT).show();
                Log.e("getHeroData error : ", t.toString());
            }
        });
        tabLayout.addOnTabSelectedListener(mTabSelectedListener);
        return rootView;
    }
    private View createTabView(int id) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        ImageView txt_name = tabView.findViewById(R.id.tab_img);
        txt_name.setImageDrawable(getResources().getDrawable(id));
        return tabView;
    }

    TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    recyclerData.clear();
                    recyclerData.addAll(tank);
                    adapter.notifyDataSetChanged();
                    break;
                case 1:
                    recyclerData.clear();
                    recyclerData.addAll(bruiser);
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    recyclerData.clear();
                    recyclerData.addAll(supporter);
                    adapter.notifyDataSetChanged();
                    break;
                case 3:
                    recyclerData.clear();
                    recyclerData.addAll(healer);
                    adapter.notifyDataSetChanged();
                    break;
                case 4:
                    recyclerData.clear();
                    recyclerData.addAll(meelAssassin);
                    adapter.notifyDataSetChanged();
                    break;
                case 5:
                    recyclerData.clear();
                    recyclerData.addAll(rangeAssassin);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}
        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    }
}
