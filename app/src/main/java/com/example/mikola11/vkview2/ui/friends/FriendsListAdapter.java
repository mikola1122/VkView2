package com.example.mikola11.vkview2.ui.friends;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.api.entity.FriendsResponseWrapper;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    private List<FriendsResponseWrapper.Friend> friendList;
    private Context mContext;

    public FriendsListAdapter(Context context, List<FriendsResponseWrapper.Friend> friendList) {
        this.friendList = friendList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_friends_list_recyclerview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.friendName.setText(friendList.get(position).getFullName());
        Glide.with(mContext).load(friendList.get(position).getPhoto_100())
                .transform(new CircleTransform(mContext))
                .into(holder.friendIcon);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView friendName;
        public ImageView friendIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            friendName = (TextView) itemView.findViewById(R.id.vkFriendsItemName);
            friendIcon = (ImageView) itemView.findViewById(R.id.vkFriendsItemIcon);

        }
    }

}
