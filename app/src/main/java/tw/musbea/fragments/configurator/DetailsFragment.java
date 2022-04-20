package tw.musbea.fragments.configurator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import de.dlyt.yanndroid.oneui.widget.Spinner;
import tw.musbea.R;

public class DetailsFragment extends Fragment {
    private View mRootView;

    public TextInputEditText description_edt, youtube_edt, spotify_edt, soundcloud_edt, twitter_edt;
    private Spinner accountTypesSpinner;

    String[] accountTypes = {"Music Producer", "BeatMaker", "Singer", "Sound Designer", "Sound Engineer"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_details, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
    }

    void initialize() {
        description_edt = mRootView.findViewById(R.id.description_edt);
        youtube_edt = mRootView.findViewById(R.id.youtube_edt);
        spotify_edt = mRootView.findViewById(R.id.spotify_edt);
        soundcloud_edt = mRootView.findViewById(R.id.soundcloud_edt);
        twitter_edt = mRootView.findViewById(R.id.twitter_edt);
        accountTypesSpinner = mRootView.findViewById(R.id.accountTypeSpinner);

        ArrayAdapter ad
                = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                accountTypes);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        accountTypesSpinner.setAdapter(ad);
    }
}
