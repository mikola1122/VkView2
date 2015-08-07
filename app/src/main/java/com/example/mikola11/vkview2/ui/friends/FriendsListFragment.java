package com.example.mikola11.vkview2.ui.friends;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mikola11.vkview2.R;
import com.example.mikola11.vkview2.api.entity.Friend;
import com.example.mikola11.vkview2.event.GoToAlbumsFragmentEvent;
import com.example.mikola11.vkview2.event.PutFriendsDataEvent;
import com.example.mikola11.vkview2.event.RequestAlbumsDataEvent;
import com.example.mikola11.vkview2.event.RequestFriendsDataEvent;
import com.example.mikola11.vkview2.event.SendSearchFriendEvent;
import com.example.mikola11.vkview2.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


public class FriendsListFragment extends Fragment implements SearchInterf {

    String status;
    String titleToolbar;
    int idClickedFriend;

    private View v;
    private RecyclerView recyclerView;
    private List<Friend> friendList = new ArrayList<Friend>();
    private List<Friend> searchResultFriendList = new ArrayList<Friend>();
    private FriendsListAdapter friendsListAdapter;
    private FriendsListAdapter searchResultFriendsListAdapter;
    private String searchResultMassage;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().post(new RequestFriendsDataEvent());
        super.onCreate(savedInstanceState);
    }

    public void onEventMainThread(PutFriendsDataEvent event) {
        if (event.massage != null) {
            friendList.addAll(event.massage);
            friendsListAdapter.notifyDataSetChanged();
        } else {
            Log.e("NIKI", "Empty friends searchResultMassage");
        }
    }

    public void onEvent(SendSearchFriendEvent event) {
        searchResultMassage = event.massage;
        if (searchResultMassage != null) {
            searchResultFriendList.clear();

            for (int i = 0; i < friendList.size(); i++) {
                if (friendList.get(i).getFullName().contains(searchResultMassage)) {
                    searchResultFriendList.add(friendList.get(i));
                }
            }
        }

        searchResultFriendsListAdapter = new FriendsListAdapter(getActivity(), searchResultFriendList);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(searchResultFriendsListAdapter);
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        EventBus.getDefault().register(this);
        v = inflater.inflate(R.layout.fragment_friends_list, null);

        ((MainActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).setSearchVisibility(true);
        Log.d("NIKI", "Main toolbar show and visible search");

        recyclerView = (RecyclerView) v.findViewById(R.id.friendsListRecyclerView);
        friendsListAdapter = new FriendsListAdapter(getActivity(), friendList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO post album data into GoToAlbumsFragmentEvent
                        // and after into intent extras

                        if (searchResultMassage != null){
                            status = searchResultFriendList.get(position).getDeactivated();
                            titleToolbar = searchResultFriendList.get(position).getFullName();
                            idClickedFriend = searchResultFriendList.get(position).getId();
                        } else {
                            status = friendList.get(position).getDeactivated();
                            titleToolbar = friendList.get(position).getFullName();
                            idClickedFriend = friendList.get(position).getId();
                        }

                        if (status ==null){
                            EventBus.getDefault().post(new GoToAlbumsFragmentEvent(titleToolbar));
                            EventBus.getDefault().post(new RequestAlbumsDataEvent(idClickedFriend));
                        } else {
                            Toast toast = Toast.makeText(v.getContext(),
                                    "This profile has been deleted.",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.getView().setBackgroundColor(getResources().getColor(R.color
                                    .colorStartBackgroundTranslucentToolbar));
                            toast.show();
                        }


                        searchResultMassage = null;
                    }
                }));

        recyclerView.setAdapter(friendsListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
