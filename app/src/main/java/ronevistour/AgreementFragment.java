package ronevistour;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import activities.BaseActivityPermission;
import androidx.fragment.app.Fragment;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class AgreementFragment extends Fragment {
    final static String LAYOUT_ID = "layoutid";
    final static String HEAD_ID = "headId";
    final static String CONTENT_ID = "contentId";
    final static String IMG_ID = "imgId";
    private static TextView images_head;
    private final long mDelay = 40; //Default 500ms delay
    private final Handler mHandler = new Handler();
    TextView text_content;
    private CheckBox chkagree;
    private CharSequence mText;
    private int mIndex;
    private final Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            text_content.setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    public static AgreementFragment newInstance(int layoutId, int headId, int contentId, int imgId) {
        AgreementFragment pane = new AgreementFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        args.putInt(HEAD_ID, headId);
        args.putInt(CONTENT_ID, contentId);
        args.putInt(IMG_ID, imgId);
        pane.setArguments(args);
        return pane;
    }

    public static AgreementFragment newInstance(int layoutId) {
        AgreementFragment pane = new AgreementFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        pane.setArguments(args);
        return pane;
    }

    private void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;
        text_content.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(getArguments().getInt(LAYOUT_ID, -1), container, false);
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.agree_master) {
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
            TextView images_head = (TextView) rootView.findViewById(R.id.images_head);
            text_head.setText(getArguments().getInt(HEAD_ID, -1));
            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
            images_head.setText(getArguments().getInt(IMG_ID, -1));
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            images_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 6));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
        }
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.tour_agree) {
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
            images_head = (TextView) rootView.findViewById(R.id.images_head);
            images_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 6));
            text_head.setText(getArguments().getInt(HEAD_ID, -1));
            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
//            images_head.setBackgroundResource(getArguments().getInt(IMG_ID, -1));
            images_head.setText(R.string.Icon_heart);
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
            chkagree = (CheckBox) rootView.findViewById(R.id.chkagree);
            chkagree.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
            boolean agreed = Pref.get("RoneviUserAgreed", false);
            chkagree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Pref.put("RoneviUserAgreed", true);
                        chkagree.setEnabled(false);
                        images_head.setText(R.string.Icon_heart);
                        images_head.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
                        ObjectAnimator animationX = ObjectAnimator.ofFloat(images_head, "scaleX", 1.0f, 1.3f);
                        animationX.setRepeatCount(ObjectAnimator.INFINITE);
                        animationX.setRepeatMode(ObjectAnimator.REVERSE);
                        animationX.setInterpolator(new AccelerateDecelerateInterpolator());
                        ObjectAnimator animationY = ObjectAnimator.ofFloat(images_head, "scaleY", 1.0f, 1.3f);
                        animationY.setRepeatCount(ObjectAnimator.INFINITE);
                        animationY.setRepeatMode(ObjectAnimator.REVERSE);
                        animationY.setInterpolator(new AccelerateDecelerateInterpolator());
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(
                                Glider.glide(Skill.ExpoEaseIn, 1750, animationX),
                                Glider.glide(Skill.ExpoEaseIn, 1750, animationY)
                        );
                        set.setDuration(750).start();
                        animateText(Util.Persian(R.string.a_chk_e_1));
                    }
                }
            });
            chkagree.setChecked(agreed);
            chkagree.setEnabled(!agreed);
        }
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.tour_begin) {
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
//            text_head.setText(getArguments().getInt(HEAD_ID, -1));
//            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
//            images_head.setBackgroundResource(getArguments().getInt(IMG_ID, -1));
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
            animateText(Util.Persian(R.string.i_1_m));
        }
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.tour_perm) {
            Button btnPerm = (Button) rootView.findViewById(R.id.btnPerm);
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
            TextView images_head = (TextView) rootView.findViewById(R.id.images_head);
            text_head.setText(getArguments().getInt(HEAD_ID, -1));
            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
            images_head.setText(getArguments().getInt(IMG_ID, -1));
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            images_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 6));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
            btnPerm.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            btnPerm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplicationLoader.setCurrentActivity(getActivity());
                    BaseActivityPermission.RequestAll(getActivity());
                }
            });
        }
        return rootView;
    }
}