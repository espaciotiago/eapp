package com.ufo.mobile.eapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufo.mobile.eapp.R;

import java.util.ArrayList;
import java.util.List;

import ModelManager.User;
import Utils.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends
        RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private List<User> users = new ArrayList<>();
    private UserSelectedCallback userSelectedCallback;

    public UserAdapter(List<User> users, UserSelectedCallback userSelectedCallback) {
        this.users = users;
        this.userSelectedCallback = userSelectedCallback;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.user_row, viewGroup, false);

        // Return a new holder instance
        UserAdapter.UserViewHolder viewHolder = new UserAdapter.UserViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        final User user = users.get(i);
        Bitmap bp = Constants.loadImageFromStorage(userViewHolder.mContext,user.getImage());

        userViewHolder.txtName.setText(user.getName());
        if(bp != null) {
            userViewHolder.imgUser.setImageBitmap(bp);
        }

        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSelectedCallback.userSelectedCallback(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView txtName;
        private CircleImageView imgUser;

        public UserViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            imgUser = (CircleImageView) itemView.findViewById(R.id.img_user);
        }
    }
}
