package com.example.mikola11.vkview2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FriendsListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friends_list_fragment, null);

        List<Friend> friendList = new ArrayList<Friend>();
        populateFriends(friendList);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.friendsListRecyclerView);
        FriendsListAdapter friendsListAdapter = new FriendsListAdapter(friendList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        return v;

    }

    private void populateFriends(List<Friend> friendList) {
        for (int i = 0; i < 55; i++){
            Friend friend = new Friend();
            friend.setFirstName("Friend");
            friend.setLastName(Integer.toString(i));
            friendList.add(friend);
        }
    }
}
