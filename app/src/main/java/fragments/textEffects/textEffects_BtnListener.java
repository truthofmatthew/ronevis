package fragments.textEffects;

import android.view.View;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class textEffects_BtnListener {
    public static void onClick(View v) {
    }
//    PositionButtonListener positionButtonListener;
//    private EditText PNedit;
//    PNedit = (EditText) f_positioning.findViewById(R.id.PNedit);
//    PNedit.setFilters(new InputFilter[]{new InputFilterMinMax(String.valueOf(1), String.valueOf(100))});
//    private class PositionButtonListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            if (!PNedit.getText().toString().isEmpty() && PNedit != null) {
//                MainActivity.mainInstance().changePosition = Integer.parseInt(PNedit.getText().toString());
//            } else {
//                MainActivity.mainInstance().changePosition = 1;
//            }
//            switch (view.getId()) {
//                case R.id.btnToCenterH:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                (SizeChangeListenerUtil.getxNew() / 2) - (SizeChangeListenerUtil.getTextxNew() / 2),
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                (SizeChangeListenerUtil.getxNew() / 2) - (SizeChangeListenerUtil.getImagexNew() / 2),
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnToCenterV:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                (SizeChangeListenerUtil.getyNew() / 2) - (SizeChangeListenerUtil.getTextyNew() / 2),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                (SizeChangeListenerUtil.getyNew() / 2) - (SizeChangeListenerUtil.getImageyNew() / 2),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnToRight:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                SizeChangeListenerUtil.getxNew() - SizeChangeListenerUtil.getTextxNew(),
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                SizeChangeListenerUtil.getxNew() - SizeChangeListenerUtil.getImagexNew(),
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnToLeft:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                0,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                0,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnToBottom:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                SizeChangeListenerUtil.getyNew() - SizeChangeListenerUtil.getTextyNew(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                SizeChangeListenerUtil.getyNew() - SizeChangeListenerUtil.getImageyNew(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnToTop:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                0,
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                0,
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnarrright:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(MainActivity.mainInstance().lastView.getLeft() + MainActivity.mainInstance().changePosition,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(MainActivity.mainInstance().lastView.getLeft() + MainActivity.mainInstance().changePosition,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnarrleft:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft() - MainActivity.mainInstance().changePosition,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams = new MTFrameLayout.LayoutParams(
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft() - MainActivity.mainInstance().changePosition,
//                                MainActivity.mainInstance().lastView.getTop(),
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnarrup:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams =
//                                new MTFrameLayout.LayoutParams(
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                MainActivity.mainInstance().lastView.getTop() - MainActivity.mainInstance().changePosition,
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams =
//                                new MTFrameLayout.LayoutParams(
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(
//                                MainActivity.mainInstance().lastView.getLeft(),
//                                MainActivity.mainInstance().lastView.getTop() - MainActivity.mainInstance().changePosition,
//                                0,
//                                0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//                case R.id.btnarrdown:
//                    if (MainActivity.mainInstance().lastView instanceof MTextView) {
//                        MTFrameLayout.LayoutParams layoutParams =
//                                new MTFrameLayout.LayoutParams(
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(MainActivity.mainInstance().lastView.getLeft(), MainActivity.mainInstance().lastView.getTop()
//                                + MainActivity.mainInstance().changePosition, 0, 0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    } else if (MainActivity.mainInstance().lastView instanceof CacheableImageView) {
//                        MTFrameLayout.LayoutParams layoutParams =
//                                new MTFrameLayout.LayoutParams(
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT,
//                                        MTFrameLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams.setMargins(MainActivity.mainInstance().lastView.getLeft(), MainActivity.mainInstance().lastView.getTop()
//                                + MainActivity.mainInstance().changePosition, 0, 0);
//                        MainActivity.mainInstance().lastView.setLayoutParams(layoutParams);
//                    }
//                    break;
//            }
//        }
//    }
//
}
