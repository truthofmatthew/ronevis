package fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activities.MainActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fragments.adapter.RecyclerFontAdapter;
import fragments.animation.SmallBang;
import fragments.animation.SmallBangListener;
import fragments.db.Font_DBAdapter;
import fragments.db.dl.FilesInfo;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.RegexFileFilter;
import fragments.tool.Typefaces;
import fragments.tool.Util;
import fragments.tool.preferences.Pref;
import fragments.views.TextIcon;
import fragments.views.mtAlertDialog;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

import static mt.karimi.ronevis.R.color.text_secondary_light;

public class Font_Fragment extends BaseFragment implements OnItemClickListener<File> {
    private static Font_Fragment tools_font_user;
    RecyclerView recyclerView;
    String fontFolder;
    boolean fontFav;
    boolean fontInstalled;
    Font_DBAdapter fontDBAdapter;
    FilesInfo mfilesInfo;
    File PackageDir;
    int position;
    private List<File> BacksFiles;
    private RecyclerFontAdapter mAdapter;
    private SmallBang mSmallBang;

    public Font_Fragment() {
    }

    public static Font_Fragment newInstance(FilesInfo filesInfo) {
        Font_Fragment fragment = new Font_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EXTRA_SUB_CAT_INFO", filesInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Font_Fragment getInstance() {
        return tools_font_user;
    }

    public void notifyFonts() {
        if (mAdapter != null) {
            mAdapter.asddsd();
        }
    }

    public void updateView() {
        notifyFonts();
    }

    public void updateData() {
        getDatas();
    }

    public int getTags() {
        return position;
    }

    public void setTags(int position) {
        this.position = position;
    }

    private boolean listAssetFiles(String path) {
        String[] list;
        try {
            list = getActivity().getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                }
            } else {
//                Logger.d(" " + list);
                // This is a file
                // TODO: add file name to an array list
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
//    private File createFileFromInputStream(InputStream inputStream) {
//
//        try{
//            File f = new File(my_file_name);
//            OutputStream outputStream = new FileOutputStream(f);
//            byte buffer[] = new byte[1024];
//            int length = 0;
//
//            while((length=inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer,0,length);
//            }
//
//            outputStream.close();
//            inputStream.close();
//
//            return f;
//        }catch (IOException e) {
//            //Logging exception
//        }
//
//        return null;
//    }

    public void getDatas() {
        fontDBAdapter = new Font_DBAdapter(getContext());
        fontFav = getArguments().getBoolean("fontFav");
        fontInstalled = getArguments().getBoolean("fontInstalled");
        mAdapter = new RecyclerFontAdapter(recyclerView);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getActivity()));
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
        if (fontFav) {
            BacksFiles = new ArrayList<>();
            Cursor c = null;
            try {
                fontDBAdapter.open();
                c = fontDBAdapter.readData();
                c.moveToFirst();
                for (int i = 1; i <= c.getCount(); i++) {
                    File file = new File(c.getString(c.getColumnIndex("font_path")));
                    if (file.exists()) {
                        BacksFiles.add(new File(c.getString(c.getColumnIndex("font_path"))));
                    }
                    c.moveToNext();
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
                FirebaseCrash.report(ignored);
            } finally {
                fontDBAdapter.close();
                if (c != null) {
                    c.close();
                }
            }
        } else {
            BacksFiles = new ArrayList<>();
            try {
                Bundle bundle = getArguments();
                mfilesInfo = (FilesInfo) bundle.getSerializable("EXTRA_SUB_CAT_INFO");
                if (mfilesInfo != null) {
                    if (mfilesInfo.getMaincat().equals("en")) {
                        PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsEn));
                    } else if (mfilesInfo.getMaincat().equals("fa")) {
                        PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa));
                    }
                }
//                else  if (mfilesInfo.getMaincat().equals("sans")){
//                    PackageDir = null;
////                    BacksFiles.addAll(   );
////                    listAssetFiles("sans");
//
//                    AssetManager am= getActivity().getAssets();
//
//                    String[] list;
//                    try {
//                        list = am.list("sans");
//                        if (list.length > 0) {
//                            for (String file : list) {
//
//                                BacksFiles.add(new File( "/sans/" +file));
//
//
//                            }
//
//                        } else {
//
//                        }
//                    } catch (IOException e) {
//
//                    }
//
//
//                }
                if (PackageDir != null && PackageDir.exists()) {
                    BacksFiles.addAll(Arrays.asList(listFilesMatching(PackageDir, mfilesInfo.getSubcat() + "_*\\d*")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (fontInstalled) {
            BacksFiles = new ArrayList<>();
//            PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsUser));
//            if (PackageDir.exists()) {
////                BacksFiles.addAll(Arrays.asList(listFilesMatching(PackageDir, mfilesInfo.getSubcat() + "_*\\d*")));
//                BacksFiles.addAll(Arrays.asList(listFilesMatching(PackageDir, mfilesInfo.getSubcat() + "_*\\d*")));
//            }

            fontFolder = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa)).getAbsolutePath();

//            String ttfExtension = "([^\\s]+(\\.(?i)(otf|ttf))$)";
//            BacksFiles = ApplicationLoader.appInstance().storage.getFiles(fontFolder, ttfExtension);
            BacksFiles.add(ApplicationLoader.appInstance().storage.getFile(fontFolder, "/fa_calligraphyfamily_6.ttf"));

//            for (int i = 1; i<BacksFiles.size(); i++){
//                Logger.wtf(BacksFiles.get(i).getAbsolutePath());
//            }

//            String ttfExtension = "([^\\s]+(\\.(?i)(otf|ttf))$)";
//            BacksFiles.addAll(ApplicationLoader.appInstance().storage.getFiles(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathFontsFa)).getAbsolutePath(), ttfExtension));
//
//
//            for (int i = 0; i<BacksFiles.size(); i++){
//                Logger.wtf(BacksFiles.get(i).getAbsolutePath());
//
//            }

//            BacksFiles.add(new File("/sdcard/ronevis/.Fonts/.fa/fa_calligraphyfamily_6.ttf"));
        }
        if (recyclerView != null) {
            recyclerView.getRecycledViewPool().clear();
            mAdapter.setData(BacksFiles);
            recyclerView.setAdapter(mAdapter);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        getDatas();
        return view;
    }

    public File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        return root.listFiles(new RegexFileFilter(regex));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                            .icon(Util.getDrawableIcon(R.string.Icon_delete, text_secondary_light))
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
                                            FirebaseCrash.report(ignored);
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
                    FirebaseCrash.report(ignored);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View v, int position, File file, String fragmenttag) {
    }

    private static class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }
}
