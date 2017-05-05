package com.deskit.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deskit.R;

public class AuthFragment extends Fragment implements View.OnClickListener {

    private AuthFragmentListener fragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentListener = (AuthFragmentListener) getActivity();

        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        // Button listeners
        view.findViewById(R.id.sign_in_button).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button) {
            fragmentListener.onUserSignInButtonClick();
        }
    }

    public interface AuthFragmentListener {
        void onUserSignInButtonClick();
    }
}
