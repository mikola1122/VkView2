package com.example.mikola11.vkview2.ui.friends;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikola11.vkview2.PutFriendsDataEvent;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.RequestFriendsDataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class FriendsListFragment extends Fragment {

    List<Friend> friendList = new ArrayList<Friend>();
    FriendsListAdapter friendsListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().post(new RequestFriendsDataEvent());
        super.onCreate(savedInstanceState);
    }

    public void onEventAsync(PutFriendsDataEvent event){
        if(event.massage!=null){
            friendList.addAll(event.massage);
            friendsListAdapter.notifyDataSetChanged();
        }else {
            Log.d("NIKI", "Empty massage");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.friends_list_fragment, null);


//        populateFriends(friendList);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.friendsListRecyclerView);
        friendsListAdapter = new FriendsListAdapter(getActivity(), friendList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        return v;

    }




//    private void populateFriends(List<Friend> friendList) {
//        for (int i = 0; i < 55; i++) {
//            Friend friend = new Friend();
//            friend.setFirst_name("Friend");
//            friend.setLast_name(Integer.toString(i));
//            friendList.add(friend);
//        }
//    }

}
