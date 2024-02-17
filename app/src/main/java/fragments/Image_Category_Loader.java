package fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.List;

import fragments.db.dl.FilesDBManager;
import fragments.db.dl.FilesInfo;
import fragments.views.dragLayout.DargInnerViews;
import fragments.views.dragLayout.DragLayout;

public class Image_Category_Loader extends DialogFragment {
    private FilesDBManager filesDBManager;

    public static Image_Category_Loader newInstance() {
        Image_Category_Loader ftext = new Image_Category_Loader();
        Bundle bundle = new Bundle();
        ftext.setArguments(bundle);
        return ftext;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AppCompatDialog dialog = new AppCompatDialog(getActivity(), getTheme());
        dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group,
                             Bundle bundle) {
        filesDBManager = FilesDBManager.getInstance(getContext());
        DragLayout dragLayout = null;
        dragLayout = DargInnerViews.DragLayoutLinear(getContext(), false, 184);
        dragLayout.addView(DargInnerViews.dragger_handle(getContext(), null));
//        if (buildAdapter().getCount() > 0) {
        dragLayout.addView(DargInnerViews.container_Pager(getContext(), buildAdapter()));
//        } else {
//            dragLayout.setBackgroundColor(Util.getColorWrapper(R.color.bg_tools));
//            Pref.put("RemindVitrin", false);
//            MainActivity.mainInstance().remindVitrin(R.string.dRemMessage, R.string.Icon_shopping);
//        }
//        Logger.e(" " + buildAdapter().getCount());
        dragLayout.init();
        return dragLayout;
    }

    private PagerAdapter buildAdapter() {
        List<FilesInfo> filesInfo = filesDBManager.getTypeInfos(getArguments().getString("bundleFileType"));
        return (new SampleAdapter(getActivity(), getChildFragmentManager(), filesInfo));
    }

    public class SampleAdapter extends FragmentPagerAdapter {
        Context ctxt = null;
        List<FilesInfo> filesInfo;

        public SampleAdapter(Context ctxt, FragmentManager mgr, List<FilesInfo> filesInfo) {
            super(mgr);
            this.ctxt = ctxt;
            this.filesInfo = filesInfo;
//            if(filesInfo.size() <= 0){
//                Pref.put("RemindVitrin", false);
//                MainActivity.mainInstance().remindVitrin(R.string.dRemMessage,R.string.Icon_shopping);
//            }
        }

        @Override
        public Fragment getItem(int pos) {
            return getArguments().getString("bundleFileType").equals("background") ? new Tools_bg().newInstance(filesInfo.get(pos)) : new Tools_sticker().newInstance(filesInfo.get(pos));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return filesInfo.get(position).getName_fa();
        }

        @Override
        public int getCount() {
            return filesInfo.size();
        }
    }
}