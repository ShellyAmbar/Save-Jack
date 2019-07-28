package com.shellyambar.thegame.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shellyambar.thegame.Models.ScoresModel;
import com.shellyambar.thegame.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    private Context context;
    private List<ScoresModel> scoresModelList;

    public ScoresAdapter(Context context, List<ScoresModel> scoresModelList) {
        this.context = context;
        this.scoresModelList = scoresModelList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.score_item,viewGroup,false);
        return new ScoresAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        ScoresModel scoresModel = scoresModelList.get(i);
        viewHolder.userName.setText(scoresModel.getUserName().toString());
        if(!scoresModel.getUserPhoto().isEmpty()){
            viewHolder.userPhoto.setImageURI(Uri.fromFile(new File(scoresModel.getUserPhoto())));
        }

        viewHolder.userScore.setText(scoresModel.getUserScore()+"  Points");

        YoYo.with(Techniques.Wave)
                .duration(800)
                .repeat(1)
                .playOn(viewHolder.itemView);

    }

    public void SetNewArrayOfScores(List<ScoresModel> newList){
        scoresModelList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return scoresModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private CircleImageView userPhoto;
        private TextView userScore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            userPhoto = itemView.findViewById(R.id.image_user);
            userScore = itemView.findViewById(R.id.score_text);
        }
    }
}
