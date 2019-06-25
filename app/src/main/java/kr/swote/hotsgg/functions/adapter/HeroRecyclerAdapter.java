package kr.swote.hotsgg.functions.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import kr.swote.hotsgg.R;
import kr.swote.hotsgg.functions.datas.HeroData;
import kr.swote.hotsgg.views.activitys.HeroResultActivity;

public class HeroRecyclerAdapter extends RecyclerView.Adapter<HeroRecyclerAdapter.ViewHolder> {
    ArrayList<HeroData> data;
    Map<String, Bitmap> icons;
    Context context;

    public HeroRecyclerAdapter(ArrayList<HeroData> items, Map<String, Bitmap> icons, Context context) {
        this.data = items;
        this.icons = icons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //이미지 url로 가져오기
        HeroData nowData = data.get(position);
        holder.img.setImageBitmap(icons.get(nowData.getName()));
        //이름 설정
        holder.name.setText(nowData.getName());

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

        public ViewHolder(View itemView)  {
            super(itemView);

            img = itemView.findViewById(R.id.hero_img);
            name = itemView.findViewById(R.id.hero_name);
            winRate = itemView.findViewById(R.id.hero_win_rate);
            progressBar = itemView.findViewById(R.id.hero_progress);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                Log.e("Adapter", position + "data clicked");
                HeroData item = data.get(position);
                Gson gson = new Gson();
                Intent intent = new Intent(context, HeroResultActivity.class);
                intent.putExtra("heroData", gson.toJson(item));
                context.startActivity(intent);
            });
        }
    }
}
