package com.deskit.controller;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.deskit.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

 public class GoogleAuthController {

    private static final String TAG = "GoogleAuthActivity";

    public static final int RC_SIGN_IN = 9001;

    private FirebaseAuth firebaseAuth;

    private GoogleApiClient googleApiClient;

    private FragmentActivity activity;

    public GoogleAuthController(FragmentActivity activity, GoogleApiClient.OnConnectionFailedListener connectionFailedListener) {
        this.activity = activity;

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(activity)
                .enableAutoManage(activity, connectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount account, OnCompleteListener<AuthResult> onCompleteListener) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, onCompleteListener);
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut(ResultCallback<Status> resultCallback) {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(resultCallback);
    }

    void revokeAccess(ResultCallback<Status> resultCallback) {
        // Firebase sign out
        firebaseAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(resultCallback);
    }

     public FirebaseUser getCurrentUser() {
         return firebaseAuth.getCurrentUser();
     }
}
