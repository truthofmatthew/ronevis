package ronevistour;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import fragments.tool.Util;
import fragments.views.AutoSIzeImageView;
import fragments.views.backImageView;
import mt.karimi.ronevis.R;

public class TourFragment extends Fragment {
    final static String LAYOUT_ID = "layoutid";
    final static String HEAD_ID = "headId";
    final static String CONTENT_ID = "contentId";
    final static String IMG_LINK = "imgLink";
    private final long mDelay = 40;
    private final Handler mHandler = new Handler();
    TextView text_content;
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

    public static TourFragment newInstance(int layoutId, int headId, int contentId, String imgId) {
        TourFragment pane = new TourFragment();
        Bundle args = new Bundle();
        args.putInt(LAYOUT_ID, layoutId);
        args.putInt(HEAD_ID, headId);
        args.putInt(CONTENT_ID, contentId);
        args.putString(IMG_LINK, imgId);
        pane.setArguments(args);
        return pane;
    }

    public static TourFragment newInstance(int layoutId) {
        TourFragment pane = new TourFragment();
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
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.tour_begin) {
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
            backImageView images_head = (backImageView) rootView.findViewById(R.id.images_head);
//            text_head.setText(getArguments().getInt(HEAD_ID, -1));
//            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
//            images_head.setBackgroundResource(getArguments().getString(IMG_ID, -1));
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
            animateText(Util.Persian(R.string.i_1_m));
        }
        if (getArguments().getInt(LAYOUT_ID, -1) == R.layout.tour_master) {
            TextView text_head = (TextView) rootView.findViewById(R.id.heading);
            text_content = (TextView) rootView.findViewById(R.id.content);
            AutoSIzeImageView images_head = (AutoSIzeImageView) rootView.findViewById(R.id.images_head);
            text_head.setText(getArguments().getInt(HEAD_ID, -1));
            text_content.setText(getArguments().getInt(CONTENT_ID, -1));
            Ion.with(getContext())
                    .load(getArguments().getString(IMG_LINK))
                    .withBitmap()
                    .placeholder(R.drawable.empty_dl)
                    .intoImageView(images_head);
            text_head.setTypeface(Util.GetSelfTypeFace(getActivity(), 3));
            text_content.setTypeface(Util.GetSelfTypeFace(getActivity(), 1));
        }
        return rootView;
    }
}