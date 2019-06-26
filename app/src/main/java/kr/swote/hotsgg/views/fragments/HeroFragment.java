package kr.swote.hotsgg.views.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.API.API;
import kr.swote.hotsgg.functions.API.Client;
import kr.swote.hotsgg.functions.adapter.HeroRecyclerAdapter;
import kr.swote.hotsgg.functions.datas.DataHelper;
import kr.swote.hotsgg.functions.datas.HeroData;
import kr.swote.hotsgg.functions.datas.HeroSuggestion;
import kr.swote.hotsgg.views.activitys.HeroResultActivity;
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
    Map<String, Bitmap> icons = new HashMap<>();
    Map<String, HeroData> search = new HashMap<>();
    ArrayList<HeroSuggestion> names = new ArrayList<>();
    FloatingSearchView mSearchView;
    SharedPreferences preferences;

    private String mLastQuery = "";
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
        mSearchView = rootView.findViewById(R.id.floating_search_view);
        tabLayout = rootView.findViewById(R.id.hero_tabs);

        //TabLayout tab 추가
        //전사
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_tank)));
        //투사
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_bruiser)));
        //지원가
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_support)));
        //치유사
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_healer)));
        //원거리 암살자
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_ranged_assassin)));
        //근접 암살자
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_melee_assassin)));

        //레트로핏
        if (datas.isEmpty()) {api = Client.getClient().create(API.class);
            Call<ArrayList<HeroData>> getHero = api.getHeroData();
            getHero.enqueue(new Callback<ArrayList<HeroData>>() {
                @Override
                public void onResponse(Call<ArrayList<HeroData>> call, Response<ArrayList<HeroData>> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        datas.addAll(response.body());
                        Log.e("getHeroData datas ", datas.toString());
                        //데이터들 분류
                        Integer k = 0;
                        datas.forEach(it -> {
                            switch (it.getExpandedRole().getName()) {
                                case "전사":
                                    tank.add(it);
                                    break;
                                case "투사":
                                    bruiser.add(it);
                                    break;
                                case "지원가":
                                    supporter.add(it);
                                    break;
                                case "치유사":
                                    healer.add(it);
                                    break;
                                case "근접 암살자":
                                    meelAssassin.add(it);
                                    break;
                                default:
                                    rangeAssassin.add(it);
                                    break;
                            }
                            Thread mThread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        //url 데이터 가져와서 연걸하기
                                        Log.e("circle_icon",it.getCircleIcon());
                                        URL url = new URL(it.getCircleIcon());
                                        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                                        con.setDoInput(true);
                                        //이미지 비트맵 받음
                                        InputStream is = con.getInputStream();
                                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                                        icons.put(it.getName(), bitmap);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                        Log.e("heroRecyclerViewAdapter", e.toString());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.e("heroRecyclerViewAdapter", e.toString());
                                    }
                                }
                            };
                            mThread.start();
                            search.put(it.getName(), it);
                            names.add(new HeroSuggestion(it.getName()));
                        });
                        //리사이클러뷰 데이터 설정
                        recyclerData.addAll(tank);
                        adapter.notifyDataSetChanged();
                        DataHelper.setHeroSuggestions(names);
                        setupSearchBar();
                    }
                    else {
                        // code 200이 아니면
                        Toast.makeText(getActivity(), "영웅 정보를 불러오는데 실패했습니다!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<HeroData>> call, Throwable t) {
                    Toast.makeText(getActivity(), "서버에러가 발생했습니다!", Toast.LENGTH_SHORT).show();
                    Log.e("getHeroData error ", t.toString());
                }
            });
        }
        tabLayout.addOnTabSelectedListener(mTabSelectedListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new HeroRecyclerAdapter(recyclerData, icons, getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }
    // 탭 레이아웃 클릭 리스너
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
                    recyclerData.addAll(rangeAssassin);
                    adapter.notifyDataSetChanged();
                    break;
                case 5:
                    recyclerData.clear();
                    recyclerData.addAll(meelAssassin);
                    adapter.notifyDataSetChanged();
                    break;
            }
            int tabIconColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            int tabIconColor = ContextCompat.getColor(getContext(), R.color.lightGray);
            tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    private void setupSearchBar() {
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {
            if (!oldQuery.equals("") && newQuery.equals("")) {
                mSearchView.clearSuggestions();
            } else {
                mSearchView.showProgress();
                DataHelper.findSuggestions(getActivity(), newQuery, 5,
                        250, results -> {
                            mSearchView.swapSuggestions(results);

                            mSearchView.hideProgress();
                        });
            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                HeroSuggestion heroSuggestion = (HeroSuggestion) searchSuggestion;
                HeroData item = search.get(heroSuggestion.getBody());
                Intent intent = new Intent(getContext(), HeroResultActivity.class);
                intent.putExtra("heroData", new Gson().toJson(item));
                mLastQuery = searchSuggestion.getBody();
                //searchView focus 해제
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

                startActivity(intent);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
            }
        });
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {

                mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));
            }

            @Override
            public void onFocusCleared() {

                mSearchView.setSearchBarTitle(mLastQuery);
            }
        });
    }
}
