package activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import fragments.objectHelper.Manimator;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.TextIcon;
import fragments.views.UnderlinePageIndicator;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;
import permissions.dispatcher.PermissionUtils;
import ronevistour.AgreementFragment;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AgreementActivity extends AppCompatActivity {
    public static final String[] PERMISSIONS_ALL = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    public ArrayList<Fragment> fragments = new ArrayList<>();
    ViewPager pager;
    PagerAdapter pagerAdapter;
    private int[] a_images = new int[]{
            R.string.Icon_heart_broken,
            R.string.Icon_shopping,
            R.string.Icon_check_circle_outline,
            R.string.Icon_lightbulb_outline
    };
    private int[] a_head = new int[]{
            R.string.a_h_4,
            R.string.a_h_3,
            R.string.a_h_2,
            R.string.a_h_1,
    };
    private int NUM_PAGES = a_head.length;
    private int[] a_content = new int[]{
            R.string.a_m_4,
            R.string.a_m_3,
            R.string.a_m_2,
            R.string.a_m_1
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_tutorial);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            NUM_PAGES++;
        final TextIcon btnLeftArrow = (TextIcon) findViewById(R.id.btnLeftArrow);
        final TextIcon btnRightArrow = (TextIcon) findViewById(R.id.btnRightArrow);
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    CheckBox chkagreeout = (CheckBox) getCurrentView(pager).findViewById(R.id.chkagree);
                    if (chkagreeout != null) {
                        if (chkagreeout.isChecked()) {
                            endTutorial();
                        } else {
                            pager.setCurrentItem(1);
                        }
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (position == 2) {
                        if (!PermissionUtils.hasSelfPermissions(AgreementActivity.this, PERMISSIONS_ALL)) {
                            pager.setCurrentItem(4);
                            Toast.makeText(ApplicationLoader.appInstance(), Util.Persian(R.string.permission_deny), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (NUM_PAGES == 0) {
                    if ((btnLeftArrow != null ? btnLeftArrow.getVisibility() : 0) != View.INVISIBLE)
                        Manimator.AlhpaGone(btnLeftArrow, 130);
                } else {
                    if ((btnLeftArrow != null ? btnLeftArrow.getVisibility() : 0) != View.VISIBLE)
                        Manimator.AlhpaVis(btnLeftArrow, 130);
                }
                if (NUM_PAGES - 1 == position) {
                    if ((btnRightArrow != null ? btnRightArrow.getVisibility() : 0) != View.INVISIBLE)
                        Manimator.AlhpaGone(btnRightArrow, 130);
                } else {
                    if ((btnRightArrow != null ? btnRightArrow.getVisibility() : 0) != View.VISIBLE)
                        Manimator.AlhpaVis(btnRightArrow, 130);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        UnderlinePageIndicator titleIndicator = (UnderlinePageIndicator) findViewById(R.id.titles);
        if (titleIndicator != null) {
            titleIndicator.setViewPager(pager);
        }
        fragments.add(AgreementFragment.newInstance(R.layout.tour_end));
        fragments.add(AgreementFragment.newInstance(R.layout.tour_agree, a_head[0], a_content[0], a_images[0]));
        fragments.add(AgreementFragment.newInstance(R.layout.agree_master, a_head[1], a_content[1], a_images[1]));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            fragments.add(AgreementFragment.newInstance(R.layout.tour_perm, a_head[2], a_content[2], a_images[2]));
        fragments.add(AgreementFragment.newInstance(R.layout.agree_master, a_head[3], a_content[3], a_images[3]));
        if (btnRightArrow != null) {
            btnRightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }
            });
        }
        if (btnLeftArrow != null) {
            btnLeftArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(pager.getCurrentItem() - 1);
                }
            });
        }
        pager.setCurrentItem(NUM_PAGES);
        TextView btn_Skip = (TextView) findViewById(R.id.btn_Skip);
        btn_Skip.setTypeface(Util.GetSelfTypeFace(this, 5));
        btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean agreed = Pref.get("RoneviUserAgreed", false);
                if (!PermissionUtils.hasSelfPermissions(AgreementActivity.this, PERMISSIONS_ALL)) {
                    pager.setCurrentItem(4);
                } else if (!agreed) {
                    pager.setCurrentItem(1);
                } else {
                    endTutorial();
                }
            }
        });
    }

    private View getCurrentView(ViewPager pager) {
        for (int i = 0; i < pager.getChildCount(); i++) {
            View child = pager.getChildAt(i);
            if (ViewHelper.getX(child) <= pager.getScrollX() + pager.getWidth() &&
                    ViewHelper.getX(child) + child.getWidth() >= pager.getScrollX() + pager.getWidth()) {
                return child;
            }
        }
        return pager.getChildAt(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pager != null) {
            pager.clearOnPageChangeListeners();
        }
    }

    private void endTutorial() {
        finish();
        startActivity(new Intent(AgreementActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == NUM_PAGES) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermission.onRequestPermissionsResult(this, requestCode, grantResults);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragments.get(pos);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head = page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View images_head = page.findViewById(R.id.images_head);
            View chkagree = page.findViewById(R.id.chkagree);
            View a01 = page.findViewById(R.id.a01);
            View btnPerm = page.findViewById(R.id.btnPerm);
            if (0 <= position && position < 1) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (-1 < position && position < 0) {
                ViewHelper.setTranslationX(page, pageWidth * -position);
            }
            if (position > -1.0f && position < 1.0f) {
                if (position != 0.0f) {
                    if (backgroundView != null) {
                        ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));
                        ViewHelper.setTranslationX(text_head, pageWidth * position);
                        ViewHelper.setTranslationX(text_content, pageWidth * position);
                        ViewHelper.setTranslationX(images_head, pageWidth * position);
                        if (chkagree != null) {
                            ViewHelper.setTranslationX(chkagree, pageWidth * position);
                        }
                    }
                }
                if (a01 != null) {
                    ViewHelper.setTranslationX(a01, (pageWidth * position));
                    ViewHelper.setTranslationX(text_head, pageWidth * position);
                    ViewHelper.setTranslationX(text_content, pageWidth * position);
                }
                if (btnPerm != null) {
                    ViewHelper.setTranslationX(btnPerm, (pageWidth * position));
                }
            }
        }
    }
}

