package kr.swote.hotsgg.views.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.API.API;
import kr.swote.hotsgg.functions.API.Client;
import kr.swote.hotsgg.functions.adapter.HeroRecyclerAdapter;
import kr.swote.hotsgg.functions.datas.HeroData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaticFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    HeroRecyclerAdapter adapter;
    Map<String, Bitmap> icons = new HashMap<>();
    ArrayList<HeroData> datas = new ArrayList<>();
    ArrayList<HeroData> recyclerData = new ArrayList<>();
    TabLayout tabs;
    RecyclerView recyclerView;
    API api;
    int pickFlag = 0;
    int winFlag = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_static, container, false);
        tabs = rootView.findViewById(R.id.tabs);
        recyclerView = rootView.findViewById(R.id.recycler);

        tabs.addTab(tabs.newTab().setText("승률"));
        tabs.addTab(tabs.newTab().setText("픽률"));
        tabs.addOnTabSelectedListener(mTabSelectedListener);
        //레트로핏
        Log.e("asdf", datas.isEmpty() + "");
        if (datas.isEmpty()) {
            api = Client.getClient().create(API.class);
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
                            Thread mThread = new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        //url 데이터 가져와서 연걸하기
                                        Log.e("circle_icon", it.getCircleIcon());
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
                        });
                        //리사이클러뷰 데이터 설정
                        recyclerData.addAll(datas);
                        Collections.sort(recyclerData, new UpWinRate());
                        adapter.notifyDataSetChanged();
                    } else {
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

        adapter = new HeroRecyclerAdapter(recyclerData, icons, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        return rootView;
    }

    TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    Collections.sort(recyclerData, new UpWinRate());
                    adapter.notifyDataSetChanged();
                    winFlag = 0;
                    break;
                case 1:
                    Collections.sort(recyclerData, new UpPickRate());
                    adapter.notifyDataSetChanged();
                    pickFlag = 1;
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    if (winFlag == 0) {
                        Collections.sort(recyclerData, new DownWinRate());
                        winFlag  = 1;
                    }
                    else {
                        Collections.sort(recyclerData, new UpWinRate());
                        winFlag = 0;
                    }
                        adapter.notifyDataSetChanged();
                    break;
                case 1:
                    if (pickFlag == 0) {
                        Collections.sort(recyclerData, new DownPickRate());
                        pickFlag = 1;
                    }
                    else {
                        Collections.sort(recyclerData, new UpPickRate());
                        pickFlag = 0;
                    }
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}

class UpWinRate implements Comparator<HeroData> {
    @Override
    public int compare(HeroData a, HeroData b) {
        if (b.getWinRate() > a.getWinRate())
            return 1;
        else
            return -1;
    }
}
class DownWinRate implements Comparator<HeroData> {
    @Override
    public int compare(HeroData a, HeroData b) {
        if (b.getWinRate() > a.getWinRate())
            return -1;
        else
            return 1;
    }
}
class UpPickRate implements Comparator<HeroData> {
    @Override public int compare(HeroData a, HeroData b) {
        if (b.getPickRate() > a.getPickRate())
            return 1;
        else
            return -1;
    }
}
class DownPickRate implements Comparator<HeroData> {
    @Override public int compare(HeroData a, HeroData b) {
        if (b.getPickRate() > a.getPickRate())
            return -1;
        else
            return 1;
    }
}
