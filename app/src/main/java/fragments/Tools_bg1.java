package fragments;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.crash.FirebaseCrash;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.List;

import activities.MainActivity;
import fragments.adapter.RecyclerImageAdapter;
import fragments.download.listener.OnItemClickListener;
import fragments.objectHelper.BackGroundHelper;
import fragments.objectHelper.Manimator;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class Tools_bg1 extends BottomSheetDialogFragment implements OnItemClickListener<File> {
    RecyclerView recyclerView;
    private List<File> BacksFiles;
    private RecyclerImageAdapter mAdapter;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    public Tools_bg1() {
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_recycler_view, null);
        dialog.setContentView(contentView);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
//        if( behavior != null && behavior instanceof BottomSheetBehavior ) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//            ((BottomSheetBehavior) behavior).setPeekHeight(300);
//        }
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from(((View) contentView.getParent()));
        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
            mBottomSheetBehavior.setPeekHeight(MainActivity.mainInstance().appWH[1] / 2);
        }
        contentView.getLayoutParams().height = MainActivity.mainInstance().appWH[1] / 2;
//        contentView.requestLayout();
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
        BacksFiles = null;
        recyclerView = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            mAdapter = new RecyclerImageAdapter(true);
            mAdapter.setOnItemClickListener(this);
            if (ApplicationLoader.appInstance().storage.isDirectoryExists(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg))) {
                String ttfExtension = "([^\\s]+(\\.(?i)(jpg|jpeg|png))$)";
                BacksFiles = ApplicationLoader.appInstance().storage.getFiles(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg), ttfExtension);
            }
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            recyclerView.setAdapter(mAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    rv.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                }
            });
            mAdapter.setData(BacksFiles);
        } catch (Exception e) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private RecyclerImageAdapter.ImageViewHolder getViewHolder(int position) {
        return (RecyclerImageAdapter.ImageViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    @Override
    public void onItemClick(View v, int position, File appInfo) {
        try {
            Manimator.Alhpa(MainActivity.mainInstance().MainImageBG, 0, 100);
//            Manimator.Alhpa(MainActivity.mainInstance().checkerBoard,0,0);
//            BackGroundHelper.SetBackGroundRefresh();
            BackGroundHelper.setBackGroundRemove();
            final File currentBG = BacksFiles.get(position);
            Bitmap bitmap = BitmapFactory.decodeFile(currentBG.getAbsolutePath());
            Ion.with(MainActivity.mainInstance().MainImageBG)
                    .placeholder(null).crossfade(false).smartSize(true).fadeIn(false).animateIn(R.anim.zoom_in_img)
                    .load(currentBG.getAbsolutePath());
            MainActivity.mainInstance().backGroundProperties.setBackGroundHaveImage(true);
//            Tools_bg_setting.seekbgblur.setProgress(0);
//            MainActivity.mainInstance().MainImageBG.setImageDrawable(new BitmapDrawable(ApplicationLoader.appInstance().getResources(), bitmap));
//            MainActivity.mainInstance().MainImageBG.setImageBitmap(bitmap);
            MainActivity.mainInstance().backGroundProperties.setBackGroundBitmap(bitmap);
            MainActivity.mainInstance().backGroundProperties.setBackGroundSrc(currentBG.getAbsolutePath());
//            Tools_bg_setting.SeekBarBGAlpha.setProgress(100);
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);
        }
    }

    @Override
    public void onItemClick(View v, int position, File file, String fragmenttag) {
    }
}