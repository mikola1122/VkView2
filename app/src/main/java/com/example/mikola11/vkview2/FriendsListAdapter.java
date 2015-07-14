package com.example.mikola11.vkview2;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder> {

    private List<Friend> friendList;

    public FriendsListAdapter(List<Friend> friendList) {
        this.friendList = friendList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.friendName.setText(friendList.get(position).getFullName());
        if ((position % 2) == 0) {
            holder.friendIcon.setImageResource(R.drawable.one);
        } else {
            holder.friendIcon.setImageResource(R.drawable.two);
        }
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
