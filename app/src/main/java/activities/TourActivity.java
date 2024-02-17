package activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;

import fragments.objectHelper.Manimator;
import fragments.tool.Util;
import fragments.views.TextIcon;
import fragments.views.UnderlinePageIndicator;
import mt.karimi.ronevis.R;
import ronevistour.TourFragment;
//import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TourActivity extends AppCompatActivity {
    public ArrayList<Fragment> fragments = new ArrayList<>();
    ViewPager pager;
    PagerAdapter pagerAdapter;
    private String[] text_images = new String[]{
            "http://files.ronevis.com/images/tutorial/intro/t_10.png",
            "http://files.ronevis.com/images/tutorial/intro/t_9.png",
            "http://files.ronevis.com/images/tutorial/intro/t_8.png",
            "http://files.ronevis.com/images/tutorial/intro/t_7.png",
            "http://files.ronevis.com/images/tutorial/intro/t_6.png",
            "http://files.ronevis.com/images/tutorial/intro/t_5.png",
            "http://files.ronevis.com/images/tutorial/intro/t_4.png",
            "http://files.ronevis.com/images/tutorial/intro/t_3.png",
            "http://files.ronevis.com/images/tutorial/intro/t_2.png",
            "http://files.ronevis.com/images/tutorial/intro/t_1.png"
    };
    private int[] text_head = new int[]{
            R.string.t_h_10,
            R.string.t_h_9,
            R.string.t_h_8,
            R.string.t_h_7,
            R.string.t_h_6,
            R.string.t_h_5,
            R.string.t_h_4,
            R.string.t_h_3,
            R.string.t_h_2,
            R.string.t_h_1,
    };
    private int[] text_content = new int[]{
            R.string.t_m_10,
            R.string.t_m_9,
            R.string.t_m_8,
            R.string.t_m_7,
            R.string.t_m_6,
            R.string.t_m_5,
            R.string.t_m_4,
            R.string.t_m_3,
            R.string.t_m_2,
            R.string.t_m_1
    };
    private String[] bg_images = new String[]{
            "http://files.ronevis.com/images/tutorial/intro/b_5.png",
            "http://files.ronevis.com/images/tutorial/intro/b_4.png",
            "http://files.ronevis.com/images/tutorial/intro/b_3.png",
            "http://files.ronevis.com/images/tutorial/intro/b_2.png",
            "http://files.ronevis.com/images/tutorial/intro/b_1.png"
    };
    private int[] bg_head = new int[]{
            R.string.b_h_5,
            R.string.b_h_4,
            R.string.b_h_3,
            R.string.b_h_2,
            R.string.b_h_1,
    };
    private int[] bg_content = new int[]{
            R.string.b_m_5,
            R.string.b_m_4,
            R.string.b_m_3,
            R.string.b_m_2,
            R.string.b_m_1
    };
    private String[] menu_images = new String[]{
            "http://files.ronevis.com/images/tutorial/intro/m_2.png",
            "http://files.ronevis.com/images/tutorial/intro/m_1.png"
    };
    private int[] menu_head = new int[]{
            R.string.m_h_2,
            R.string.m_h_1,
    };
    private int[] menu_content = new int[]{
            R.string.m_m_2,
            R.string.m_m_1
    };
    private int NUM_PAGES = menu_head.length + bg_head.length + text_head.length + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_tutorial);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
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
                    endTutorial();
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
        fragments.add(TourFragment.newInstance(R.layout.tour_end));
        for (int i = 0; i < menu_head.length; i++) {
            fragments.add(TourFragment.newInstance(R.layout.tour_master, menu_head[i], menu_content[i], menu_images[i]));
        }
        for (int i = 0; i < bg_head.length; i++) {
            fragments.add(TourFragment.newInstance(R.layout.tour_master, bg_head[i], bg_content[i], bg_images[i]));
        }
        for (int i = 0; i < text_head.length; i++) {
            fragments.add(TourFragment.newInstance(R.layout.tour_master, text_head[i], text_content[i], text_images[i]));
        }
        fragments.add(TourFragment.newInstance(R.layout.tour_begin));
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
                endTutorial();
            }
        });
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
        startActivity(new Intent(TourActivity.this, MainActivity.class));
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

