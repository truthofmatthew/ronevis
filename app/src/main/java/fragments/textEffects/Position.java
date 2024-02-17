package fragments.textEffects;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.lisetener.SizeChangeListenerUtil;
import fragments.tool.InputFilterMinMax;
import fragments.views.MTFrameLayout;
import fragments.views.TextIcon;
import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class Position extends BaseFragment {
    PositionButtonListener positionButtonListener;
    private EditText PNedit;

    @Override
    public String myNameIs() {
        return "Position";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PNedit = null;
        positionButtonListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View f_positioning = inflater.inflate(R.layout.f_positioning, group, false);
        f_positioning.setClickable(true);
        f_positioning.setEnabled(false);
        positionButtonListener = new PositionButtonListener();
        TextIcon btnarrright = (TextIcon) f_positioning.findViewById(R.id.btnarrright);
        TextIcon btnarrleft = (TextIcon) f_positioning.findViewById(R.id.btnarrleft);
        TextIcon btnarrup = (TextIcon) f_positioning.findViewById(R.id.btnarrup);
        TextIcon btnarrdown = (TextIcon) f_positioning.findViewById(R.id.btnarrdown);
        TextIcon btnToBottom = (TextIcon) f_positioning.findViewById(R.id.btnToBottom);
        TextIcon btnToTop = (TextIcon) f_positioning.findViewById(R.id.btnToTop);
        TextIcon btnToLeft = (TextIcon) f_positioning.findViewById(R.id.btnToLeft);
        TextIcon btnToRight = (TextIcon) f_positioning.findViewById(R.id.btnToRight);
        TextIcon btnToCenterV = (TextIcon) f_positioning.findViewById(R.id.btnToCenterV);
        TextIcon btnToCenterH = (TextIcon) f_positioning.findViewById(R.id.btnToCenterH);
        btnarrright.setOnClickListener(positionButtonListener);
        btnarrleft.setOnClickListener(positionButtonListener);
        btnarrup.setOnClickListener(positionButtonListener);
        btnarrdown.setOnClickListener(positionButtonListener);
        btnToBottom.setOnClickListener(positionButtonListener);
        btnToTop.setOnClickListener(positionButtonListener);
        btnToLeft.setOnClickListener(positionButtonListener);
        btnToRight.setOnClickListener(positionButtonListener);
        btnToCenterV.setOnClickListener(positionButtonListener);
        btnToCenterH.setOnClickListener(positionButtonListener);
        PNedit = (EditText) f_positioning.findViewById(R.id.PNedit);
        PNedit.setFilters(new InputFilter[]{new InputFilterMinMax(String.valueOf(1), String.valueOf(100))});
        return f_positioning;
    }

    private class PositionButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!PNedit.getText().toString().isEmpty() && PNedit != null) {
                MainActivity.mainInstance().changePosition = Integer.parseInt(PNedit.getText().toString());
            } else {
                MainActivity.mainInstance().changePosition = 1;
            }
            switch (view.getId()) {
                case R.id.btnToCenterH:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                (SizeChangeListenerUtil.getxNew() / 2) - (MainActivity.mainInstance().SelecetedTextView.getTextxNew() / 2),
                                MainActivity.mainInstance().SelecetedTextView.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                (SizeChangeListenerUtil.getxNew() / 2) - (MainActivity.mainInstance().SelectedImage.getImagexNew() / 2),
                                MainActivity.mainInstance().SelectedImage.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnToCenterV:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelecetedTextView.getLeft(),
                                (SizeChangeListenerUtil.getyNew() / 2) - (MainActivity.mainInstance().SelecetedTextView.getTextyNew() / 2),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelectedImage.getLeft(),
                                (SizeChangeListenerUtil.getyNew() / 2) - (MainActivity.mainInstance().SelectedImage.getImageyNew() / 2),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnToRight:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                SizeChangeListenerUtil.getxNew() - MainActivity.mainInstance().SelecetedTextView.getTextxNew(),
                                MainActivity.mainInstance().SelecetedTextView.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                SizeChangeListenerUtil.getxNew() - MainActivity.mainInstance().SelectedImage.getImagexNew(),
                                MainActivity.mainInstance().SelectedImage.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnToLeft:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                0,
                                MainActivity.mainInstance().SelecetedTextView.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                0,
                                MainActivity.mainInstance().SelectedImage.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnToBottom:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelecetedTextView.getLeft(),
                                SizeChangeListenerUtil.getyNew() - MainActivity.mainInstance().SelecetedTextView.getTextyNew(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelectedImage.getLeft(),
                                SizeChangeListenerUtil.getyNew() - MainActivity.mainInstance().SelectedImage.getImageyNew(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnToTop:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelecetedTextView.getLeft(),
                                0,
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelectedImage.getLeft(),
                                0,
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnarrright:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(MainActivity.mainInstance().SelecetedTextView.getLeft() + MainActivity.mainInstance().changePosition,
                                MainActivity.mainInstance().SelecetedTextView.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(MainActivity.mainInstance().SelectedImage.getLeft() + MainActivity.mainInstance().changePosition,
                                MainActivity.mainInstance().SelectedImage.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnarrleft:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelecetedTextView.getLeft() - MainActivity.mainInstance().changePosition,
                                MainActivity.mainInstance().SelecetedTextView.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelectedImage.getLeft() - MainActivity.mainInstance().changePosition,
                                MainActivity.mainInstance().SelectedImage.getTop(),
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnarrup:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams =
                                new MTFrameLayout.LayoutParams(
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelecetedTextView.getLeft(),
                                MainActivity.mainInstance().SelecetedTextView.getTop() - MainActivity.mainInstance().changePosition,
                                0,
                                0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams =
                                new MTFrameLayout.LayoutParams(
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(
                                MainActivity.mainInstance().SelectedImage.getLeft(),
                                MainActivity.mainInstance().SelectedImage.getTop() - MainActivity.mainInstance().changePosition,
                                0,
                                0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
                case R.id.btnarrdown:
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MTFrameLayout.LayoutParams layoutParams =
                                new MTFrameLayout.LayoutParams(
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(MainActivity.mainInstance().SelecetedTextView.getLeft(), MainActivity.mainInstance().SelecetedTextView.getTop()
                                + MainActivity.mainInstance().changePosition, 0, 0);
                        MainActivity.mainInstance().SelecetedTextView.setLayoutParams(layoutParams);
                    } else if (MainActivity.mainInstance().SelectedImage != null) {
                        MTFrameLayout.LayoutParams layoutParams =
                                new MTFrameLayout.LayoutParams(
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(MainActivity.mainInstance().SelectedImage.getLeft(), MainActivity.mainInstance().SelectedImage.getTop()
                                + MainActivity.mainInstance().changePosition, 0, 0);
                        MainActivity.mainInstance().SelectedImage.setLayoutParams(layoutParams);
                    }
                    break;
            }
        }
    }
}