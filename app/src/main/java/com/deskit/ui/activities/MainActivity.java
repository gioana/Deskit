package com.deskit.ui.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.deskit.R;
import com.deskit.controller.GoogleAuthController;
import com.deskit.controller.ItemsController;
import com.deskit.model.Faculty;
import com.deskit.model.ItemType;
import com.deskit.model.ListItem;
import com.deskit.model.Resource;
import com.deskit.model.Subject;
import com.deskit.model.University;
import com.deskit.persistance.DatabaseHelper;
import com.deskit.ui.fragments.AuthFragment;
import com.deskit.ui.fragments.ListFragment;
import com.deskit.ui.fragments.MainFragment;
import com.deskit.utils.FragmentTag;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FileDownloadTask;

import java.io.IOException;

public class MainActivity extends FragmentActivity
        implements GoogleApiClient.OnConnectionFailedListener, ListFragment.ListFragmentListener,
        AuthFragment.AuthFragmentListener, MainFragment.MainFragmentListener,
        ItemsController {

    private FragmentManager fragmentManager;

    private GoogleAuthController googleAuthController;

    private University currentUniversity;

    private Faculty currentFaculty;

    private Subject currentSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        googleAuthController = new GoogleAuthController(this, this);
        fragmentManager = getFragmentManager();

        if (googleAuthController.getCurrentUser() == null) {
            openGoogleAuthFragment();
        } else {
            openMainFragment();
        }
    }

    private void openMainFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, new MainFragment(), FragmentTag.MAIN_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private void openGoogleAuthFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, new AuthFragment(), FragmentTag.GOOGLE_AUTH_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GoogleAuthController.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                googleAuthController.firebaseAuthWithGoogle(result.getSignInAccount(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        fragmentManager.popBackStack();
                        openMainFragment();
                    }
                });
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onUserSignInButtonClick() {
        googleAuthController.signIn();
    }

    @Override
    public void onUserSignOutButtonClick() {
        googleAuthController.signOut(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    fragmentManager.popBackStack();
                    openGoogleAuthFragment();
                }
            }
        });
    }

    @Override
    public void onUniversitySelected(University university) {
        currentUniversity = university;
    }

    @Override
    public void onFacultySelected(Faculty faculty) {
        currentFaculty = faculty;
    }

    @Override
    public void onSubjectSelected(Subject subject) {
        currentSubject = subject;
    }

    @Override
    public void onChooseItemClick(@ItemType int itemType) {
        Bundle bundle = new Bundle();
        bundle.putInt(ListFragment.ARGUMENT_ITEM_TYPE, itemType);
        ListFragment listFragment = new ListFragment();
        listFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, listFragment, FragmentTag.FRAGMENT_LIST)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCloseFragment() {
        fragmentManager.popBackStack();
    }

    @Override
    public void onItemChosen(ListItem listItem) {
        if (listItem instanceof Resource) {
            Toast.makeText(this, "should open new fragment", Toast.LENGTH_SHORT).show();
        } else {
            fragmentManager.popBackStack();

            MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentByTag(FragmentTag.MAIN_FRAGMENT);
            if (mainFragment != null && mainFragment.isVisible()) {
                mainFragment.setListItemToTextView(listItem);
            }
        }
    }

    @Override
    public University getCurrentUniversity() {
        return currentUniversity;
    }

    @Override
    public Faculty getCurrentFaculty() {
        return currentFaculty;
    }

    @Override
    public Subject getCurrentSubject() {
        return currentSubject;
    }
}
