package pdesigns.com.lastorders.ClientSide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * The type Bar pager view.
 */
public class BarPagerView extends FragmentStatePagerAdapter {
    /**
     * The M num of tabs.
     */
    int mNumOfTabs;

    /**
     * Instantiates a new Bar pager view.
     *
     * @param fm        the fm
     * @param NumOfTabs the num of tabs
     */
    public BarPagerView(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BarProfile tab1 = new BarProfile();
                return tab1;
            case 1:
                MapFragment tab2 = new MapFragment();
                tab2.getAllowReturnTransitionOverlap();
                return tab2;

            case 2:
                BarComments tab3 = new BarComments();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
