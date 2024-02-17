package fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import activities.BaseActivity;
import activities.MainActivity;
import fragments.adapter.RecyclerFontAdapter;
import fragments.adapter.SimpleDividerItemDecoration;
import fragments.animation.SmallBang;
import fragments.animation.SmallBangListener;
import fragments.db.Font_DBAdapter;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.TextIcon;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class Tools_Font_User extends BaseFragment implements OnItemClickListener<File> /* , FragmentLifecycle*/ {
    private static Tools_Font_User tools_font_user;
    RecyclerView recyclerView;
    String fontFolder;
    boolean fontFav;
    Font_DBAdapter fontDBAdapter;
    private List<File> BacksFiles;
    private RecyclerFontAdapter mAdapter;
    //    @Override
//    public void onDestroyView() {
//    fontDBAdapter = null;
//    objectList = null;
//    listfonts = null;
//    bookadapter = null;
//        super.onDestroyView();
//        recyclerView = null;
//        BacksFiles = null;
//        mAdapter = null;
//     }
//    @Override
//    public void onPauseFragment() {
//        Log.i("Override", "onPauseFragment " + getArguments().getString("fontFragment"));
//    }
//
//    @Override
//    public void onResumeFragment() {
//        Log.i("Override", "onResumeFragment " + getArguments().getString("fontFragment"));
//    }
    private SmallBang mSmallBang;

    public Tools_Font_User() {
    }

    public static Tools_Font_User getInstance() {
        return tools_font_user;
    }

    public void notifyFonts() {
        if (mAdapter != null) {
            mAdapter.asddsd();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {
                BaseActivity.RequestStorage();
                getDatas();
                notifyFonts();
            }
        } catch (Exception e) {
        }
    }

    public void getDatas() {
        try {
            mAdapter = new RecyclerFontAdapter(recyclerView);
            mAdapter.setOnItemClickListener(this);
            fontFolder = getArguments().getString("fontFolder");
            fontFav = getArguments().getBoolean("fontFav");
            BacksFiles = new ArrayList<>();
            if (fontFolder != null && !fontFav) {
                String ttfExtension = "([^\\s]+(\\.(?i)(otf|ttf))$)";
                BacksFiles = ApplicationLoader.appInstance().storage.getFiles(fontFolder, ttfExtension);
            }
            fontDBAdapter = new Font_DBAdapter(getContext());
        } catch (Exception ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        if (fontFav) {
            BacksFiles = new ArrayList<>();
            Cursor c = null;
            try {
                fontDBAdapter.open();
                c = fontDBAdapter.readData();    //TODO:
                c.moveToFirst();
                for (int i = 1; i <= c.getCount(); i++) {
                    File file = new File(c.getString(c.getColumnIndex("font_path")));
                    if (file.exists()) {
                        BacksFiles.add(new File(c.getString(c.getColumnIndex("font_path"))));
                    }
                    c.moveToNext();
                }
            } catch (Exception ignored) {
                FireHelper fireHelper = new FireHelper();
                fireHelper.SendReport(ignored);
            } finally {
                fontDBAdapter.close();
                if (c != null) {
                    c.close();
                }
            }
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(mAdapter);
            mAdapter.setData(BacksFiles);
//            notifyFonts();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools_font_user = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        notifyFonts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        getDatas();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
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
        if (BacksFiles != null) {
            mAdapter.setData(BacksFiles);
        }
        mSmallBang = SmallBang.attach2Window(getActivity());
    }

    @Override
    public void onItemClick(View v, final int position, final File appInfo) {
        switch (v.getId()) {
            case R.id.singleRowFontText:
                if (appInfo.exists()) {
                    if (MainActivity.mainInstance().SelecetedTextView != null) {
                        MainActivity.mainInstance().tf_selected = Typefaces.getTypeface(ApplicationLoader.appInstance(), appInfo.getAbsolutePath());
                        Pref.put(ApplicationLoader.appInstance().getString(R.string.SharedPrefronevisLastFont), appInfo.getAbsolutePath());
                        MainActivity.mainInstance().SelecetedTextView.setTypeface(MainActivity.mainInstance().tf_selected);
                        MainActivity.mainInstance().TextViewMap.get(MainActivity.mainInstance().textArrayID).setTextTypeFacePath(appInfo.getAbsolutePath());
                        MainActivity.mainInstance().SelecetedTextView.redraw();
                    }
                } else {
                    Toast.makeText(getContext(), Util.Persian(R.string.dfFileDeleted), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.singleRowFontTextID:
                break;
            case R.id.singleRowFontDelete:
                if (appInfo.exists()) {
                    mtAlertDialog.on(getActivity())
                            .with()
                            .cancelable(true)
                            .gravity(Gravity.BOTTOM)
                            .title(R.string.ddTitle)
                            .message(R.string.ddMessage)
                            .icon(Util.getDrawableIcon(R.string.Icon_delete, R.color.colorRed))
                            .cancelable(true)
                            .when(R.string.ddPositiveButton, new mtAlertDialog.Positive() {
                                @Override
                                public void clicked(DialogInterface dialog) {
                                    boolean deleted = appInfo.delete();
                                    if (deleted) {
                                        try {
                                            fontDBAdapter.open();
                                            fontDBAdapter.deleteBook(appInfo.getAbsolutePath());
                                            fontDBAdapter.close();
                                            mAdapter.deleteItem(position);
                                        } catch (Exception ignored) {
                                            FireHelper fireHelper = new FireHelper();
                                            fireHelper.SendReport(ignored);
                                        }
                                    }
                                    dialog.dismiss();
                                }
                            })
                            .when(R.string.ddNegativeButton, new mtAlertDialog.Negative() {
                                @Override
                                public void clicked(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(getContext(), Util.Persian(R.string.dfFileDeleted), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.singleRowFontStar:
                try {
                    if (appInfo.exists()) {
                        fontDBAdapter.open();
                        if (fontDBAdapter.Exists(appInfo.getAbsolutePath())) {
                            fontDBAdapter.deleteBook(appInfo.getAbsolutePath());
                            if (fontFav) {
                                mAdapter.deleteItem(position);
                                BacksFiles.remove(position);
                            } else {
                                ((TextIcon) v).setChecked(false);
                            }
                        } else {
                            ((TextIcon) v).setChecked(true);
                            fontDBAdapter.insertData(appInfo.getAbsolutePath(), "internal");
                            mSmallBang.bang(v, 50, new SmallBangListener() {
                                @Override
                                public void onAnimationStart() {
                                }

                                @Override
                                public void onAnimationEnd() {
                                }
                            });
                        }
                        fontDBAdapter.close();
                    } else {
                        Toast.makeText(getContext(), Util.Persian(R.string.dfFileDeleted), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ignored) {
                    FireHelper fireHelper = new FireHelper();
                    fireHelper.SendReport(ignored);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View v, int position, File file, String fragmenttag) {
    }

    private class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }
}
