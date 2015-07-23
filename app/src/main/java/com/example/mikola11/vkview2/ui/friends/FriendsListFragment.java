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

import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestFriendsDataEvent;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class FriendsListFragment extends Fragment {

    List<Friend> friendList = new ArrayList<Friend>();
    FriendsListAdapter friendsListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new RequestFriendsDataEvent());
        super.onCreate(savedInstanceState);
    }

    public void onEventAsync(PutFriendsDataEvent event) {
        if (event.massage != null) {
            friendList.addAll(event.massage);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    friendsListAdapter.notifyDataSetChanged();
                }
            });

        } else {
            Log.d("NIKI", "Empty friends massage1");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends_list, null);


        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.friendsListRecyclerView);
        friendsListAdapter = new FriendsListAdapter(getActivity(), friendList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        EventBus.getDefault().post(new GoToAlbumsFragmentEvent());
                        EventBus.getDefault().post(new RequestAlbumsDataEvent(friendList.get(position)
                                .getId()));
                    }
                }));

        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        return v;

    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
