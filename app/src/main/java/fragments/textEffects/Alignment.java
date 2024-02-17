package fragments.textEffects;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/11/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Alignment extends BaseFragment {
    @Override
    public String myNameIs() {
        return "Alignment";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_alignment = inflater.inflate(R.layout.f_text_align, group, false);
        f_alignment.setClickable(true);
        f_alignment.setEnabled(false);
        AlignmentButtonListener alignmentButtonListener = new AlignmentButtonListener();
        TextIcon btntextleft = (TextIcon) f_alignment.findViewById(R.id.btntextleft);
        TextIcon btntextright = (TextIcon) f_alignment.findViewById(R.id.btntextright);
        TextIcon btntextcenter = (TextIcon) f_alignment.findViewById(R.id.btntextcenter);
        btntextleft.setOnClickListener(alignmentButtonListener);
        btntextright.setOnClickListener(alignmentButtonListener);
        btntextcenter.setOnClickListener(alignmentButtonListener);
        return f_alignment;
    }

    private static class AlignmentButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btntextleft:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MainActivity.mainInstance().SelecetedTextView.setGravity(Gravity.START);
                        MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextGravity(Gravity.START);
                    }
                    break;
                case R.id.btntextright:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MainActivity.mainInstance().SelecetedTextView.setGravity(Gravity.END);
                        MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextGravity(Gravity.END);
                    }
                    break;
                case R.id.btntextcenter:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MainActivity.mainInstance().SelecetedTextView.setGravity(Gravity.CENTER);
                        MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextGravity(Gravity.CENTER);
                    }
                    break;
            }
        }
    }
}
