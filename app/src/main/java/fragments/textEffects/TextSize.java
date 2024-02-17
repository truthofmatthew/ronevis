package fragments.textEffects;

import fragments.BaseFragment;

/**
 * Created by mt.karimi on 5/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class TextSize extends BaseFragment {
//    private static TextView textSizeSeekXText;
//    private static TextView textSizeSeekYText;
//    private static TextView textSizeSeekAllText;
//
//    ShadowButtonListener shadowButtonListener;
//    SeekBarChangeListener seekBarChangeListener;
//
//    private TextIcon textSizeX;
//    private SeekBar textSizeSeekX;
//    private TextIcon textSizeY;
//    private SeekBar textSizeSeekY;
//    private TextIcon textSizeAll;
//    private SeekBar textSizeSeekAll;
//
//
//    public static Shadow newInstance() {
//        Shadow ftext = new Shadow();
//        Bundle bundle = new Bundle();
//        ftext.setArguments(bundle);
//        return ftext;
//    }
//
//    @Override
//    public String myNameIs() {
//        return "TextSize";
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        shadowButtonListener = null;
//        seekBarChangeListener = null;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.f_textsize, null);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.setClickable(true);
//        view.setEnabled(false);
//
//        textSizeX = (TextIcon) view.findViewById(R.id.textSizeX);
//        textSizeSeekX = (SeekBar) view.findViewById(R.id.textSizeSeekX);
//        textSizeSeekXText = (TextView) view.findViewById(R.id.textSizeSeekXText);
//        textSizeY = (TextIcon) view.findViewById(R.id.textSizeY);
//        textSizeSeekY = (SeekBar) view.findViewById(R.id.textSizeSeekY);
//        textSizeSeekYText = (TextView) view.findViewById(R.id.textSizeSeekYText);
//        textSizeAll = (TextIcon) view.findViewById(R.id.textSizeAll);
//        textSizeSeekAll = (SeekBar) view.findViewById(R.id.textSizeSeekAll);
//        textSizeSeekAllText = (TextView) view.findViewById(R.id.textSizeSeekAllText);
//
//        shadowButtonListener = new ShadowButtonListener();
//        seekBarChangeListener = new SeekBarChangeListener();
//         textSizeX.setOnClickListener(shadowButtonListener);
//        textSizeY.setOnClickListener(shadowButtonListener);
//        textSizeAll.setOnClickListener(shadowButtonListener);
//         textSizeSeekX.setOnSeekBarChangeListener(seekBarChangeListener);
//        textSizeSeekY.setOnSeekBarChangeListener(seekBarChangeListener);
//        textSizeSeekAll.setOnSeekBarChangeListener(seekBarChangeListener);
//
//        if (MainActivity.mainInstance().SelecetedTextView != null) {
//             textSizeSeekX.setProgress((int) TextProperties.getCurrent().getTextSizeX());
//            textSizeSeekY.setProgress((int) TextProperties.getCurrent().getTextSizeY());
//            textSizeSeekAll.setProgress((int) TextProperties.getCurrent().getTextSize());
//         }
//    }
//
//    private static class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            switch (seekBar.getId()) {
//                case R.id.textSizeSeekX:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//
//                            if (progress >= 1) {
//                                MainActivity.mainInstance().SelecetedTextView.setTextSizeX(progress);
//                                TextProperties.getCurrent().setTextSizeX(progress);
//                                textSizeSeekXText.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//
//                            } else {
//                                MainActivity.mainInstance().SelecetedTextView.setTextSizeX(1);
//                            }
//
//                    }
//                    break;
//                case R.id.textSizeSeekY:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//
//                            if (progress >= 1) {
//                                MainActivity.mainInstance().SelecetedTextView.setTextSizeY(progress);
//                                TextProperties.getCurrent().setTextSizeY(progress);
//                                textSizeSeekYText.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//
//                            } else {
//                                MainActivity.mainInstance().SelecetedTextView.setTextSizeY(1);
//                            }
//
//                    }
//                    break;
//
//                case R.id.textSizeSeekAll:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//
//                            if (progress >= 1) {
//                                MainActivity.mainInstance().SelecetedTextView.setTextUserSize(progress);
//                                TextProperties.getCurrent().setTextSize(progress);
//                                textSizeSeekAllText.setText(FormatHelper.toPersianNumber(String.valueOf(progress)));
//                            } else {
//                                MainActivity.mainInstance().SelecetedTextView.setTextUserSize(1);
//                            }
//
//                    }
//                    break;
//            }
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//        }
//    }
//
//    private class ShadowButtonListener implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.textSizeY:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        Util.NumberDialog(1, 10, (int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextSizeY(), textSizeSeekY.getId(), getActivity());
//                    }
//                    break;
//                case R.id.textSizeX:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        Util.NumberDialog(1, 10, (int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextSizeX(), textSizeSeekX.getId(), getActivity());
//                    }
//                    break;
//                case R.id.textSizeAll:
//                    if (MainActivity.mainInstance().SelecetedTextView != null) {
//                        Util.NumberDialog(1, Util.getInt(R.integer.textShadowDenMax),(int) MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).getTextSize(), textSizeSeekAll.getId(), getActivity());
//                    }
//                    break;
//
//            }
//        }
//    }
}