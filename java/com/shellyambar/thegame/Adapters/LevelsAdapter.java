package com.shellyambar.thegame.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shellyambar.thegame.Game.GameActivity;
import com.shellyambar.thegame.Models.LevelsModel;
import com.shellyambar.thegame.R;

import java.util.List;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {

    private Context context;
    private List<LevelsModel> levelsModelsList;

    public LevelsAdapter(Context context, List<LevelsModel> levelsModelsList) {
        this.context = context;
        this.levelsModelsList = levelsModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.level_item,viewGroup,false);
        return new LevelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final LevelsModel levelsModel = levelsModelsList.get(i);
        viewHolder.levelNumber.setText(context.getResources().getString(R.string.level)+" "+levelsModel.getLevelNum());

        if(levelsModel.getIsOpen().equals("true"))
        {
            viewHolder.lockImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.lock_open));
        }else if(levelsModel.getIsOpen().equals("false"))
        {
            viewHolder.lockImage.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.lock_closed));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelsModel.getIsOpen().equals("true")){
                    Intent intent = new Intent(context, GameActivity.class);
                    intent.putExtra("level_num",levelsModel.getLevelNum()+"");
                    context.startActivity(intent);
                }
                else if(levelsModel.getIsOpen().equals("false")){
                    Toast.makeText(context, "The level is CLOSED!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        YoYo.with(Techniques.Flash)
                .duration(800)
                .repeat(1)
               .playOn(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return levelsModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView levelNumber;
        private ImageView lockImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            levelNumber = itemView.findViewById(R.id.level_number);
            lockImage = itemView.findViewById(R.id.image_lock);

        }
    }
}
