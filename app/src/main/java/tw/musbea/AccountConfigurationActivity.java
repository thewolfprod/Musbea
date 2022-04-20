package tw.musbea;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

import de.dlyt.yanndroid.oneui.dialog.ProgressDialog;
import de.dlyt.yanndroid.oneui.view.ViewPager2;
import tw.musbea.adapters.UniversalViewPager;
import tw.musbea.fragments.configurator.AvatarFragment;
import tw.musbea.fragments.configurator.DetailsFragment;

public class AccountConfigurationActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    MaterialButton back_bttn, next_bttn;

    FirebaseAuth auth;
    FirebaseUser user;

    UniversalViewPager adapter;

    ProgressDialog loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration_account);

        initialize();
        setup();
    }

    void initialize() {
        adapter = new UniversalViewPager(getSupportFragmentManager(), getLifecycle());
        adapter.addFragment(new AvatarFragment(), "Avatar");
        adapter.addFragment(new DetailsFragment(), "Details");

        viewPager = findViewById(R.id.viewPager);
        back_bttn = findViewById(R.id.back_bttn);
        next_bttn = findViewById(R.id.next_bttn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        loading = new ProgressDialog(AccountConfigurationActivity.this);
        loading.setProgressStyle(ProgressDialog.STYLE_CIRCLE);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);

        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }

    void setup() {
        next_bttn.setOnClickListener(v -> {
            nextPage();
        });

        back_bttn.setOnClickListener(v -> {
            prevPage();
        });
    }

    void nextPage() {
        if (viewPager.getCurrentItem() != 1) {
            toggleNextButton(false);
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            validate();
        } else {
            showLoading();
            String description = ((DetailsFragment) adapter.getCurrentFragment(1)).description_edt.getText().toString();
            String youtube_url = ((DetailsFragment) adapter.getCurrentFragment(1)).youtube_edt.getText().toString();
            String spotify_url = ((DetailsFragment) adapter.getCurrentFragment(1)).spotify_edt.getText().toString();
            String soundcloud_url = ((DetailsFragment) adapter.getCurrentFragment(1)).soundcloud_edt.getText().toString();
            String twitter_url = ((DetailsFragment) adapter.getCurrentFragment(1)).twitter_edt.getText().toString();

            if (description.equals("")) description = "empty";
            if (youtube_url.equals("")) youtube_url = "empty";
            if (spotify_url.equals("")) spotify_url = "empty";
            if (soundcloud_url.equals("")) soundcloud_url = "empty";
            if (twitter_url.equals("")) twitter_url = "empty";

            HashMap<String, Object> map = new HashMap<>();
            map.put("description", description);
            map.put("youtubeUrl", youtube_url);
            map.put("spotifyUrl", spotify_url);
            map.put("soundcloudUrl", soundcloud_url);
            map.put("twitterUrl", twitter_url);

            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getString("uid").equals(user.getUid())) {
                            database.collection("users").document(document.getId()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    hideLoading();
                                    Intent intent = new Intent(AccountConfigurationActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    finish();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    void prevPage() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            validate();
        } else if (viewPager.getCurrentItem() == 0) {
            Intent intent = new Intent(AccountConfigurationActivity.this, HomeActivity.class);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    void validate() {
        if (viewPager.getCurrentItem() == 0) {
            back_bttn.setText("Skip");
        } else {
            back_bttn.setText("Back");
        }
    }

    void showLoading() {
        if (!loading.isShowing()) {
            loading.show();
        }
    }

    void hideLoading() {
        if (loading.isShowing()) {
            loading.dismiss();
        }
    }

    public void toggleBackButton(boolean enabled) {
        back_bttn.setEnabled(enabled);
    }

    public void toggleNextButton(boolean enabled) {
        next_bttn.setEnabled(enabled);
    }
}
