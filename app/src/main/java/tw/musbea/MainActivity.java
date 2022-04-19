package tw.musbea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import de.dlyt.yanndroid.oneui.view.Snackbar;
import tw.musbea.account.Authentication;
import tw.musbea.account.User;

/*
 * APP MADED BY THEWOLF
 * 18-04-2022 01:28
 *
 * THE IDEA OF THE APP IS WRITTEN AT:
 * https://github.com/thewolfprod/Musbea/blob/master/README.md
 */

public class MainActivity extends AppCompatActivity {

    /* VIEWS */
    private LinearLayout login_register_linear, main_linear;
    private MaterialTextView title_txt, login_register_txt;
    private CardView separator_crdv, alpha_notice;
    private MaterialButton register_bttn, login_bttn, crash_bttn;
    private TextInputLayout username_til;
    private TextInputEditText email_edt, username_edt, password_edt;

    /* INTERPOLATOR FOR ANIMATIONS */
    private TimeInterpolator interpolator = new AccelerateDecelerateInterpolator();

    /* VARS */
    private int ANIMATION_DURATION = 1000;
    private boolean isLoginRegisterWindowVisible = false;
    private boolean isRegister = true;

    /* AUTH */
    Authentication auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setup();
    }

    void initialize() {
        auth = new Authentication(MainActivity.this, new Authentication.AuthListener() {
            @Override
            public void OnUserLogin(User userData) {
                showError("User logged in as " + userData.getUsername());
            }

            @Override
            public void OnUserAlreadyLoggedIn(User userData) {
                showError("User already logged in! Username " + userData.getUsername());
            }

            @Override
            public void OnUserLoginError(String reason) {
                showError(reason);
            }

            @Override
            public void OnUserRegister(User userData) {
                showError("UserRegistered\nUserName " + userData.getUsername());
            }

            @Override
            public void OnUserRegisterError(String error) {
                showError(error);
            }

            @Override
            public void OnUserRequestData(User userData) {

            }

            @Override
            public void OnUserRequestDataError(String error) {
                showError(error);
            }
        });

        title_txt = findViewById(R.id.title_txt);
        separator_crdv = findViewById(R.id.separator_crdv);
        alpha_notice = findViewById(R.id.alpha_notice);
        register_bttn = findViewById(R.id.register_bttn);
        login_bttn = findViewById(R.id.login_bttn);
        login_register_txt = findViewById(R.id.login_register_txt);
        username_til = findViewById(R.id.username_til);
        email_edt = findViewById(R.id.email_edt);
        username_edt = findViewById(R.id.username_edt);
        password_edt = findViewById(R.id.password_edt);
        login_register_linear = findViewById(R.id.login_register_linear);
        main_linear = findViewById(R.id.main_linear);
        crash_bttn = findViewById(R.id.crash_bttn);
    }

    void setup() {
        title_txt.setAlpha(0f);
        title_txt.setTranslationX(-180);
        title_txt.animate().alpha(1).translationX(0f).setInterpolator(interpolator).setDuration(ANIMATION_DURATION).start();

        separator_crdv.setAlpha(0f);
        separator_crdv.animate().alpha(1f).setInterpolator(interpolator).setStartDelay(250).setDuration(ANIMATION_DURATION).start();

        alpha_notice.setAlpha(0f);
        alpha_notice.setTranslationX(360);
        alpha_notice.animate().alpha(1).translationX(0f).setInterpolator(interpolator).setDuration(ANIMATION_DURATION).start();

        crash_bttn.setOnClickListener(v -> {
            String a = "d";
            int b = Integer.parseInt(a);
            int c = b + 1 / 0;
        });

        login_bttn.setOnClickListener(v -> {
            if (login_register_linear.getVisibility() == View.GONE) {
                login_register_linear.setVisibility(View.VISIBLE);
            }
            setupLoginLinear();
        });

        register_bttn.setOnClickListener(v -> {
            if (login_register_linear.getVisibility() == View.GONE) {
                login_register_linear.setVisibility(View.VISIBLE);
            }
            setupRegisterLinear();
        });


    }

    void setupLoginLinear() {
        login_register_txt.setText("Login");
        username_til.setVisibility(View.GONE);

        if (isRegister) {
            isRegister = false;
        } else {
            if (validateInputs()) {
                auth.login(email_edt.getText().toString(), password_edt.getText().toString());
            }
        }
    }

    void setupRegisterLinear() {
        login_register_txt.setText("Register");
        username_til.setVisibility(View.VISIBLE);

        if (!isRegister) {
            isRegister = true;
        } else {
            if (validateInputs()) {
                auth.register(email_edt.getText().toString(), password_edt.getText().toString(), username_edt.getText().toString());
            }
        }
    }

    void log(String message) {
        Log.e("MainActivity", message);
    }

    void showError(String error) {
        Snackbar.make(main_linear, error, Snackbar.LENGTH_LONG).show();
    }

    boolean validateInputs() {
        if (email_edt.getText().toString().equals("")) {
            showError("Email can't be empty!");
            return false;
        }

        if (!email_edt.getText().toString().equals("")) {
            return (Patterns.EMAIL_ADDRESS.matcher(email_edt.getText()).matches());
        }

        if (username_edt.getText().toString().equals("") && isRegister) {
            return false;
        }

        if (password_edt.getText().toString().equals("") && password_edt.getText().toString().length() < 6) {
            return false;
        }

        return true;
    }
}