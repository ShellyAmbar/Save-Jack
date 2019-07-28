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
import com.shellyambar.thegame.Models.UsersModel;
import com.shellyambar.thegame.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersAdapter extends  RecyclerView.Adapter<AllUsersAdapter.ViewHolder>{

    private Context context;
    private List<UsersModel> usersModelList;

    public AllUsersAdapter(Context context, List<UsersModel> usersModelList) {
        this.context = context;
        this.usersModelList = usersModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_item,viewGroup,false);
        return new AllUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        UsersModel usersModel = usersModelList.get(i);
        viewHolder.userName.setText(usersModel.getUserName());
        if(!usersModel.getUserPhoto().isEmpty()){
            viewHolder.userPhoto.setImageURI(Uri.fromFile(new File(usersModel.getUserPhoto())));
        }

        YoYo.with(Techniques.Shake)
                .duration(800)
                .repeat(2)
                .playOn(viewHolder.itemView);

    }

    @Override
    public int getItemCount() {
        return usersModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private CircleImageView userPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userPhoto = itemView.findViewById(R.id.image_user);

        }
    }
}
