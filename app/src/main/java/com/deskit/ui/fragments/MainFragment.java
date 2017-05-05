package com.deskit.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deskit.R;
import com.deskit.model.Faculty;
import com.deskit.model.ItemType;
import com.deskit.model.ListItem;
import com.deskit.model.Subject;
import com.deskit.model.University;

public class MainFragment extends Fragment implements View.OnClickListener {

    private MainFragmentListener fragmentListener;

    private TextView chooseUniversityTextView;

    private TextView chooseFacultyTextView;

    private TextView chooseSubjectTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentListener = (MainFragmentListener) getActivity();

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        chooseUniversityTextView = (TextView) view.findViewById(R.id.choose_university_text_view);
        chooseFacultyTextView = (TextView) view.findViewById(R.id.choose_faculty_text_view);
        chooseSubjectTextView = (TextView) view.findViewById(R.id.choose_subject_text_view);

        chooseUniversityTextView.setOnClickListener(this);
        chooseFacultyTextView.setOnClickListener(this);
        chooseSubjectTextView.setOnClickListener(this);
        view.findViewById(R.id.search_resources).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_university_text_view:
                fragmentListener.onChooseItemClick(ItemType.UNIVERSITY);
                break;
            case R.id.choose_faculty_text_view:
                fragmentListener.onChooseItemClick(ItemType.FACULTY);
                break;
            case R.id.choose_subject_text_view:
                fragmentListener.onChooseItemClick(ItemType.SUBJECT);
                break;
            case R.id.search_resources:
                fragmentListener.onChooseItemClick(ItemType.RESOURCE);
        }
    }

    public void setListItemToTextView(ListItem listItem) {
        if (listItem instanceof University) {
            chooseUniversityTextView.setText(listItem.getTitle());
            chooseFacultyTextView.setText("");
            chooseSubjectTextView.setText("");
            fragmentListener.onUniversitySelected((University) listItem);
        } else if (listItem instanceof Faculty) {
            chooseFacultyTextView.setText(listItem.getTitle());
            chooseSubjectTextView.setText("");
            fragmentListener.onFacultySelected((Faculty) listItem);
        } else if (listItem instanceof Subject) {
            chooseSubjectTextView.setText(listItem.getTitle());
            fragmentListener.onSubjectSelected((Subject) listItem);
        }
    }

    public interface MainFragmentListener {
        void onChooseItemClick(@ItemType int itemType);

        void onUserSignOutButtonClick();

        void onUniversitySelected(University university);

        void onFacultySelected(Faculty faculty);

        void onSubjectSelected(Subject subject);
    }
}
