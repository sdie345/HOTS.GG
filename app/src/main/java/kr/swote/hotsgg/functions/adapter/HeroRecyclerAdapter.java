package kr.swote.hotsgg.functions.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.datas.HeroData;

public class HeroRecyclerAdapter extends RecyclerView.Adapter<HeroRecyclerAdapter.ViewHolder> {
    ArrayList<HeroData> data = new ArrayList<>();
    Bitmap bitmap;

    public View.OnClickListener onClickListener = null;

    public HeroRecyclerAdapter(ArrayList<HeroData> items) {
        this.data = items;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_list_item, parent, false);

        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //이미지 url로 가져오기
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    //url 데이터 가져와서 연걸하기
                    URL url = new URL(data.get(position).getCircleIcon());
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.setDoInput(true);
                    //이미지 비트맵 받음
                    InputStream is = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("heroRecyclerViewAdapter", e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("heroRecyclerViewAdapter", e.toString());
                }

            }
        };
        //스레드 실행
        mThread.start();
        //이미지 설정해주기
        try {
            mThread.join();
            holder.img.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("heroRecyclerViewAdapter", e.toString());
        }
        //이름 설정
        holder.name.setText(data.get(position).getName());
        //승률
        //holder.winRate.setText("승률 : " + data.get(position).winRate + "%");
        //holder.progressBar.setProgress(data.get(position).winRate);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView winRate;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.hero_img);
            name = itemView.findViewById(R.id.hero_name);
            winRate = itemView.findViewById(R.id.hero_win_rate);
            progressBar = itemView.findViewById(R.id.hero_progress);
        }
    }
}
