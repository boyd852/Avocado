package app.doscervezas.avocado.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.doscervezas.avocado.ui.fragments.AddEntryFragment;
import app.doscervezas.avocado.ui.fragments.DetailFragment;
import app.doscervezas.avocado.ui.fragments.SummaryFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;
    Fragment one, two,three;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs){
        super(fm);
        this.tabCount=numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {
            case 0:
                if (one == null)
                    one = new AddEntryFragment();
                return one;

            case 1:
                if (two == null)
                    two = new DetailFragment();
                return two;

            case 2:
                if(three == null)
                    three = new SummaryFragment();
                return three;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
