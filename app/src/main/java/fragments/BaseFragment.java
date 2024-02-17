package fragments;

import androidx.fragment.app.Fragment;

/**
 * Created by mt.karimi on 6/7/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String myNameIs() {
        return "";
    }
//     SuicidalFragmentListener suicideListener;
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            suicideListener = (SuicidalFragmentListener) activity;
//        } catch (ClassCastException e) {
//            throw new RuntimeException(getActivity().getClass().getSimpleName() + " must implement the suicide listener to use this fragment", e);
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        // Attach the close listener to whatever action on the fragment you want
////        addSuicideTouchListener();
//    }
//
//    public void addSuicideTouchListener() {
////        getView().setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
//                suicideListener.onFragmentSuicide(getTag());
////            }
////        });
//    }
}
