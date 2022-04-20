package tw.musbea.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import de.dlyt.yanndroid.oneui.dialog.AlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import tw.musbea.AccountConfigurationActivity;
import tw.musbea.R;
import tw.musbea.account.Authentication;
import tw.musbea.account.User;

public class ProfileFragment extends Fragment {
    private View mRootView;

    private CircleImageView avatar_img;
    private MaterialTextView username_txt, profile_description_txt;
    private CardView edit_profile_bttn;
    private ImageView logout_bttn;

    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatar_img = mRootView.findViewById(R.id.avatar_img);
        username_txt = mRootView.findViewById(R.id.username_txt);
        profile_description_txt = mRootView.findViewById(R.id.profile_description_txt);
        logout_bttn = mRootView.findViewById(R.id.logout_bttn);
        edit_profile_bttn = mRootView.findViewById(R.id.edit_profile_bttn);

        Authentication auth = new Authentication(getActivity(), new Authentication.AuthListener() {
            @Override
            public void OnUserLogin(User userData) {

            }

            @Override
            public void OnUserAlreadyLoggedIn(User userData) {
                user = userData;

                username_txt.setText(user.getUsername());
                profile_description_txt.setText(user.getDescription());

                if (!user.getAvatarUrl().equals("default")) {
                    Glide.with(getActivity()).load(user.getAvatarUrl()).into(avatar_img);
                }
            }

            @Override
            public void OnUserLoginError(String reason) {

            }

            @Override
            public void OnUserRegister(User userData) {

            }

            @Override
            public void OnUserRegisterError(String error) {

            }

            @Override
            public void OnUserRequestData(User userData) {

            }

            @Override
            public void OnUserRequestDataError(String error) {

            }

            @Override
            public void OnLoaded() {

            }
        });

        logout_bttn.setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Logout")
                    .setMessage("Are You sure?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            auth.logout();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        });

        edit_profile_bttn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountConfigurationActivity.class);
            intent.putExtra("skipButton", true);
            startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}
