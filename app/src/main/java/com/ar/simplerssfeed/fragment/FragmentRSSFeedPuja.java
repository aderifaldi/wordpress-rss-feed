package com.ar.simplerssfeed.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.ar.simplerssfeed.R;
import com.ar.simplerssfeed.activity.DetailFeedActivity;
import com.ar.simplerssfeed.app.adapter.FeedAdapter;
import com.ar.simplerssfeed.app.api.APIGetWordPressPuja;
import com.ar.simplerssfeed.app.model.Post;
import com.radyalabs.irfan.util.AppUtility;

/**
 * Created by aderifaldi on 23/06/2016.
 */
public class FragmentRSSFeedPuja extends Fragment {

    private RecyclerView list_feed;
    private FeedAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_feed, container, false);

        loading = AppUtility.showLoading(loading, getActivity());

        list_feed = (RecyclerView) view.findViewById(R.id.list_feed);

        adapter = new FeedAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        getFeed();

        list_feed.setLayoutManager(linearLayoutManager);
        list_feed.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = adapter.getListFeed().get(position);
                Intent intent = new Intent(getActivity(), DetailFeedActivity.class);
                intent.putExtra("data", post);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getFeed(){
        APIGetWordPressPuja apiGetWordPressFeed = new APIGetWordPressPuja(getActivity()) {
            @Override
            public void onFinishRequest(boolean success, String returnItem) {

                loading.dismiss();

                if (success){
                    if (data != null){
                        for (Post post : data.getPosts()){
                            adapter.getListFeed().add(post);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        };
        apiGetWordPressFeed.executeAjax();
    }

}
