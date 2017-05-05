package com.deskit.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.deskit.R;
import com.deskit.controller.ItemsController;
import com.deskit.listeners.OnItemClickListener;
import com.deskit.model.ItemType;
import com.deskit.model.ListItem;
import com.deskit.persistance.DatabaseHelper;
import com.deskit.ui.adapter.ItemAdapter;
import com.deskit.ui.adapter.ResourceAdapter;
import com.deskit.utils.Mock;

import java.util.ArrayList;

public class ListFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    public static final String ARGUMENT_ITEM_TYPE = "ItemType";

    private ImageView cancelImageView;

    private EditText searchItemEditText;

    private ListFragmentListener fragmentListener;

    private ItemsController controller;

    private int itemType;

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentListener = (ListFragmentListener) getActivity();
        controller = (ItemsController) getActivity();
        databaseHelper = DatabaseHelper.getInstance(getActivity().getApplicationContext());

        if (getArguments() != null) {
            itemType = getArguments().getInt(ARGUMENT_ITEM_TYPE);
        }

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        cancelImageView = (ImageView) view.findViewById(R.id.cancel_image_view);
        searchItemEditText = (EditText) view.findViewById(R.id.search_item_edit_text);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // set listeners
        cancelImageView.setOnClickListener(this);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        RecyclerView.Adapter mAdapter;
        switch (itemType) {
            case ItemType.UNIVERSITY:
                mAdapter = new ItemAdapter<>(databaseHelper.getUniversities(), this);
                break;
            case ItemType.FACULTY:
                if (controller.getCurrentUniversity() != null) {
                    mAdapter = new ItemAdapter<>(databaseHelper.getFaculties(controller.getCurrentUniversity()), this);
                } else {
                    mAdapter = new ItemAdapter<>(databaseHelper.getFaculties(), this);
                }
                break;
            case ItemType.SUBJECT:
                mAdapter = new ItemAdapter<>(Mock.getListOfSubjects(controller.getCurrentFaculty()), this);
                break;
            case ItemType.RESOURCE:
                view.setBackgroundColor(getResources().getColor(R.color.colorGrayLight));
                mAdapter = new ResourceAdapter(Mock.getListOfResources(controller.getCurrentSubject()), this);
                break;
            default:
                mAdapter = new ItemAdapter<>(new ArrayList<ListItem>(), this);
                break;
        }
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_image_view:
                fragmentListener.onCloseFragment();
        }
    }

    @Override
    public void onItemClick(ListItem listItem) {
        fragmentListener.onItemChosen(listItem);
    }

    public interface ListFragmentListener {

        void onCloseFragment();

        void onItemChosen(ListItem listItem);
    }
}
