package tw.musbea;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textview.MaterialTextView;

import de.dlyt.yanndroid.oneui.dialog.ProgressDialog;
import de.dlyt.yanndroid.oneui.layout.DrawerLayout;
import de.dlyt.yanndroid.oneui.sesl.tabs.SamsungTabLayout;
import de.dlyt.yanndroid.oneui.view.ViewPager2;
import de.dlyt.yanndroid.oneui.widget.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import tw.musbea.account.Authentication;
import tw.musbea.account.User;
import tw.musbea.adapters.UniversalViewPager;
import tw.musbea.fragments.ChatFragment;
import tw.musbea.fragments.HomeFragment;
import tw.musbea.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private ProgressDialog loading;
    private CircleImageView drawer_avatar_img;
    private LinearLayout drawer_settings_bttn, drawer_create_post_bttn, drawer_add_friend_bttn;
    private MaterialTextView drawer_username_txt, drawer_showprofile_txt;

    private UniversalViewPager adapter;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private User user;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
        setup();
    }

    void initialize() {
        loading = new ProgressDialog(HomeActivity.this);
        loading.setProgressStyle(ProgressDialog.STYLE_CIRCLE);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

        adapter = new UniversalViewPager(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(new HomeFragment(), "Home");
        adapter.addFragment(new ChatFragment(), "Chat");
        adapter.addFragment(new ProfileFragment(), "Profile");

        drawer_avatar_img = findViewById(R.id.drawer_avatar_img);
        drawer_username_txt = findViewById(R.id.drawer_username_txt);
        drawer_showprofile_txt = findViewById(R.id.drawer_showprofile_txt);
        drawer_add_friend_bttn = findViewById(R.id.drawer_add_friend_bttn);
        drawer_create_post_bttn = findViewById(R.id.drawer_create_post_bttn);
        drawerLayout = findViewById(R.id.drawer_view);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.main_tabs);

        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.home)), true);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.chat)), false);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.profile)), false);

        Authentication auth = new Authentication(HomeActivity.this, new Authentication.AuthListener() {
            @Override
            public void OnUserLogin(User userData) {

            }

            @Override
            public void OnUserAlreadyLoggedIn(User userData) {
                user = userData;

                drawer_username_txt.setText(user.getUsername());

                if (!user.getAvatarUrl().equals("default")) {
                    Glide.with(HomeActivity.this).load(user.getAvatarUrl()).into(drawer_avatar_img);
                }

                loading.dismiss();
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
    }

    void setup() {
        tabLayout.addOnTabSelectedListener(new SamsungTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(SamsungTabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case "Home":
                        viewPager.setCurrentItem(0);
                        drawerLayout.setSubtitle("Home");
                        break;
                    case "Chat":
                        viewPager.setCurrentItem(1);
                        drawerLayout.setSubtitle("Chats");
                        break;
                    case "Profile":
                        viewPager.setCurrentItem(2);
                        drawerLayout.setSubtitle("Profile");
                        break;
                }
            }

            @Override
            public void onTabUnselected(SamsungTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(SamsungTabLayout.Tab tab) {

            }
        });

        drawer_showprofile_txt.setOnClickListener(v -> {
            drawerLayout.setDrawerOpen(false, true);
            tabLayout.getTabAt(2).select();
            viewPager.setCurrentItem(2);
            drawerLayout.setSubtitle("Profile");
        });

        drawer_add_friend_bttn.setOnClickListener(v -> {
            drawerLayout.setDrawerOpen(false, false);
            Intent intent = new Intent(HomeActivity.this, AddFriendActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }
}
