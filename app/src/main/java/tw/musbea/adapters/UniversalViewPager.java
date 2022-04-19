package tw.musbea.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import java.util.ArrayList;
import java.util.List;

import de.dlyt.yanndroid.oneui.sesl.viewpager2.adapter.FragmentStateAdapter;

public class UniversalViewPager extends FragmentStateAdapter {
    private final List mFragmentList = new ArrayList<>();
    private final List mFragmentTitleList = new ArrayList<>();

    public UniversalViewPager(@NonNull FragmentManager manager, @NonNull Lifecycle lifecycle) {
        super(manager, lifecycle);
    }

    public Fragment getCurrentFragment(int position) {
        return (Fragment) mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int i) {
        return (Fragment) mFragmentList.get(i);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
