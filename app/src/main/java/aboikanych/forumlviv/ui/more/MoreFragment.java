package aboikanych.forumlviv.ui.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.ui.main.ForumActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class MoreFragment extends BaseFragment {

    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.profile_image)
    ImageView profile;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.loginView)
    View loginView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            loginView.setVisibility(View.VISIBLE);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .fitCenter()
                        .into(profile);

                name.setText(
                        TextUtils.isEmpty(user.getDisplayName()) ? "No display name" : user.getDisplayName());

                login.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
            }
        } else {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
        }
        return view;
    }

    @Optional
    @OnClick(value = {R.id.login, R.id.logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setLogo(R.mipmap.ic_logo)
                                .setTheme(R.style.AuthTheme)
                                .setProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                                .build(), RC_SIGN_IN);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                login.setVisibility(View.VISIBLE);
                logout.setVisibility(View.GONE);
                loginView.setVisibility(View.GONE);
                break;
            default:
                throw new IllegalArgumentException("View id is not found");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(getActivity(), ForumActivity.class));
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showToast(R.string.error_sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showToast(R.string.error_no_internet_message);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showToast(R.string.error_unknown);
                    return;
                }
            }
            showToast(R.string.error_server_unavailable);
        }
    }
}

