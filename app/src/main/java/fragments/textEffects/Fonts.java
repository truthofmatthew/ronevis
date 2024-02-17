package fragments.textEffects;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.Font_Fragment;
import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.dragLayout.DargInnerViews;
import fragments.views.dragLayout.DragLayout;
import mt.karimi.ronevis.R;

public class Fonts extends BaseFragment {
    private static Fonts FontsInstance;
    public Fragment fragment;
    public ArrayList<Font_Fragment> fragments = new ArrayList<>();
    List<String> TITLES = new ArrayList<>();
    HashMap<Integer, Font_Fragment> mPageReferenceMap;
    private ViewPager tab_pager;
    private FilesDBManager filesDBManager;

    public static Fonts getInstance() {
        return FontsInstance;
    }

    @Override
    public String myNameIs() {
        return "Fonts";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsInstance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DragLayout FontLoaderView = DargInnerViews.DragLayoutLinear(getContext(), false, 184);
        FontLoaderView.addView(DargInnerViews.dragger_handle(getContext(), null));
        FontLoaderView.addView(DargInnerViews.container_Pager(getContext(), null));
        filesDBManager = FilesDBManager.getInstance(getContext());
        List<FilesInfo> filesInfo = filesDBManager.getTypeInfos("font");
        mPageReferenceMap = new HashMap<>();
        Font_Fragment f_Font = new Font_Fragment();
        Bundle b_Font = new Bundle();
        b_Font.putBoolean("fontFav", true);
        b_Font.putBoolean("fontInstalled", false);
        f_Font.setArguments(b_Font);
        fragments.add(f_Font);

        Font_Fragment if_Font = new Font_Fragment();
        Bundle ib_Font = new Bundle();
        ib_Font.putBoolean("fontFav", false);
        ib_Font.putBoolean("fontInstalled", true);
        if_Font.setArguments(ib_Font);
        fragments.add(if_Font);

        TITLES.add(Util.Persian(R.string.TabFontFav));
        TITLES.add(Util.Persian(R.string.TabFontInstalled));
        for (int i = 0; i < filesInfo.size(); i++) {
            Font_Fragment ff_Font = new Font_Fragment().newInstance(filesInfo.get(i));
            ff_Font.setTags(i);
            fragments.add(ff_Font);
            TITLES.add(filesInfo.get(i).getName_fa());
        }
        SmartTabLayout tab_bar = (SmartTabLayout) FontLoaderView.findViewById(R.id.tab_bar);
        tab_bar.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                LayoutInflater inflater = LayoutInflater.from(container.getContext());
                Resources res = container.getContext().getResources();
                View tab = inflater.inflate(R.layout.custom_tab, container, false);
                TextView customText = (TextView) tab.findViewById(R.id.custom_text);
                customText.setTypeface(Util.GetSelfTypeFace(getActivity(), 5));
                customText.setText(adapter.getPageTitle(position));
                return tab;
            }
        });
        tab_pager = (ViewPager) FontLoaderView.findViewById(R.id.tab_pager);
        tab_pager.setAdapter(new FontbuildAdapter(getChildFragmentManager(), fragments, TITLES));
        tab_bar.setViewPager(tab_pager);
        tab_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                final Font_Fragment font_fragment = getFragment(tab_pager.getCurrentItem());
                if (font_fragment != null) {
                    font_fragment.updateData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        FontLoaderView.init();
        return FontLoaderView;
    }

    public Font_Fragment getFragment(int key) {
        return mPageReferenceMap.get(key);
    }

    public void updateCurrent() {
        if (tab_pager != null) {
            if (tab_pager.getCurrentItem() <= -1) {
                mPageReferenceMap.get(tab_pager.getCurrentItem()).updateView();
            }
        }
    }

    public class FontbuildAdapter extends FragmentPagerAdapter {
        List<String> TITLES = new ArrayList<>();
        List<Font_Fragment> fragments = new ArrayList<>();
        FragmentManager mFragmentManager;

        public FontbuildAdapter(FragmentManager fm, List<Font_Fragment> fragments, List<String> titles) {
            super(fm);
            mFragmentManager = fm;
            this.fragments = fragments;
            TITLES = titles;
            if (titles.size() <= 0) {
                Pref.put("RemindVitrin", false);
                MainActivity.mainInstance().remindVitrin(R.string.dRemMessage, R.string.Icon_shopping);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES.get(position);
        }

        public int getCount() {
            return fragments.size();
        }

        public Fragment getItem(int position) {
            mPageReferenceMap.put(position, fragments.get(position));
            return fragments.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public int getItemPosition(Object item) {
            Font_Fragment fragment = (Font_Fragment) item;
            int position = fragment.getTags();
            if (position >= 0) {
                return position;
            } else {
                return POSITION_NONE;
            }
        }
    }
}