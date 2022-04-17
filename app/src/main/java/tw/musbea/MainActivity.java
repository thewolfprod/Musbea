package tw.musbea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.material.textview.MaterialTextView;

/*
 * APP MADED BY THEWOLF
 * 18-04-2022 01:28
 *
 * THE IDEA OF THE APP IS WRITTEN AT:
 * https://github.com/thewolfprod/Musbea/blob/master/README.md
 */

public class MainActivity extends AppCompatActivity {

    /* VIEWS */
    private MaterialTextView title_txt;
    private CardView separator_crdv, alpha_notice;

    /* INTERPOLATOR FOR ANIMATIONS */
    private TimeInterpolator interpolator = new AccelerateDecelerateInterpolator();

    /* VARS */
    private int ANIMATION_DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        setup();
    }

    void initialize() {
        title_txt = findViewById(R.id.title_txt);
        separator_crdv = findViewById(R.id.separator_crdv);
        alpha_notice = findViewById(R.id.alpha_notice);
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
    }
}