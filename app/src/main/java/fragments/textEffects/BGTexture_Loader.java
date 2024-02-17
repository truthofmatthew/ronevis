package fragments.textEffects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activities.MainActivity;
import fragments.BaseFragment;
import fragments.adapter.RecyclerImageAdapter;
import fragments.db.dl.FilesInfo;
import fragments.download.listener.OnItemClickListener;
import fragments.tool.RegexFileFilter;
import fragments.tool.Util;
import fragments.views.SwitchButton;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class BGTexture_Loader extends BaseFragment implements OnItemClickListener<File> {
    public static SwitchButton gradient_Switch;
    RecyclerView recyclerView;
    File PackageDir;
    private List<File> Texture;
    private RecyclerImageAdapter mAdapter;

    public BGTexture_Loader() {
        recyclerView = null;
        Texture = null;
        mAdapter = null;
    }

    public static BGTexture_Loader newInstance(FilesInfo filesInfo) {
        BGTexture_Loader fragment = new BGTexture_Loader();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EXTRA_SUB_CAT_INFO", filesInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String myNameIs() {
        return "Texture_Loader";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
        Texture = null;
        recyclerView = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View f_texture = inflater.inflate(R.layout.f_texture, container, false);
        f_texture.setClickable(true);
        f_texture.setEnabled(false);
        gradient_Switch = (SwitchButton) f_texture.findViewById(R.id.gradient_Switch);
        gradient_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (MainActivity.mainInstance().SelecetedTextView != null) {
                    MainActivity.mainInstance().backGroundProperties.setBackGroundTexture(isChecked);
                    if (MainActivity.mainInstance().backGroundProperties.getBackGroundTexture()) {
                    }
                }
            }
        });
        if (MainActivity.mainInstance().SelecetedTextView != null) {
            gradient_Switch.setChecked(MainActivity.mainInstance().backGroundProperties.getBackGroundTexture());
//            gradient_Wheel.setDegreesAngle((int) MainActivity.mainInstance().backGroundProperties.getBackgroundTextureRotate());
        }
        recyclerView = (RecyclerView) f_texture.findViewById(R.id.recyclerView);
        return f_texture;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(View v, int position, File appInfo) {
        switch (v.getId()) {
            case R.id.imgbgsingle:
                File currentBG = Texture.get(position);
                MainActivity.mainInstance().backGroundProperties.setBackGroundTexturePath(currentBG.getAbsolutePath());
                final Bitmap bitmap = BitmapFactory.decodeFile(currentBG.getAbsolutePath());
                ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
                    @Override
                    public Shader resize(int width, int height) {
                        return new BitmapShader(bitmap, MainActivity.mainInstance().backGroundProperties.getBackGroundTextureTileMode(), MainActivity.mainInstance().backGroundProperties.getBackGroundTextureTileMode());
                    }
                };
                PaintDrawable p = new PaintDrawable();
                p.setShape(new RectShape());
                p.setShaderFactory(sf);
                MainActivity.mainInstance()._exportroot.setBackgroundDrawable((Drawable) p);
                break;
            case R.id.singleRowFontDelete:
                if (appInfo.exists()) {
                    boolean deleted = appInfo.delete();
                    if (deleted) {
                        try {
//                            File fileToDeleteImg = new File(ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg)) + "/" + filesInfo.getSubcat() + "_" + i + ".jpg");
//                            if (fileToDeleteImg.exists()) {
//                                fileToDeleteImg.delete();
//                            }
                        } catch (Exception ignored) {
                            FirebaseCrash.report(ignored);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), Util.Persian(R.string.dfFileDeleted), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        return root.listFiles(new RegexFileFilter(regex));
    }

    @Override
    public void onItemClick(View v, int position, File file, String fragmenttag) {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Texture = new ArrayList<>();
        try {
            Bundle bundle = getArguments();
            PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathTexture));
            mAdapter = new RecyclerImageAdapter(false);
            mAdapter.setOnItemClickListener(this);
            if (ApplicationLoader.appInstance().storage.isDirectoryExists(ApplicationLoader.appInstance().getString(R.string.ronevisPathTexture))) {
                Texture.addAll(Arrays.asList(listFilesMatching(PackageDir, "_*\\d*")));
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
            mAdapter.setData(Texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}