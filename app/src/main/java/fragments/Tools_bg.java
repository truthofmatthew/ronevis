package fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import activities.MainActivity;
import fragments.adapter.RecyclerImageAdapter;
import fragments.db.dl.FilesInfo;
import fragments.download.listener.OnItemClickListener;
import fragments.imageHelper.ImageLoader;
import fragments.tool.RegexFileFilter;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import mt.karimi.ronevis.ApplicationLoader;
import mt.karimi.ronevis.R;

public class Tools_bg extends BaseFragment implements OnItemClickListener<File> {
    RecyclerView recyclerView;
    FilesInfo mfilesInfo;
    File PackageDir;
    private List<File> BacksFiles;
    private RecyclerImageAdapter mAdapter;

    public Tools_bg() {
        recyclerView = null;
        BacksFiles = null;
        mAdapter = null;
    }

    public static Tools_bg newInstance(FilesInfo filesInfo) {
        Tools_bg fragment = new Tools_bg();
        Bundle bundle = new Bundle();
        bundle.putSerializable("EXTRA_SUB_CAT_INFO", filesInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new RecyclerImageAdapter(true);
        mAdapter.setOnItemClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadImage loadImage = new LoadImage();
                loadImage.execute();
            }
        }, 230);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
        BacksFiles = null;
        recyclerView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public File[] listFilesMatching(File root, String regex) {
        if (!root.isDirectory()) {
            throw new IllegalArgumentException(root + " is no directory.");
        }
        return root.listFiles(new RegexFileFilter(regex));
    }

    @Override
    public void onItemClick(View v, int position, File appInfo) {
        ImageLoader.getPictureFromDevice(Uri.fromFile(BacksFiles.get(position)), MainActivity.mainInstance().MainImageBG);
    }

    @Override
    public void onItemClick(View v, int position, File file, String fragmenttag) {
    }

    public class LoadImage extends AsyncTask<Void, Void, List<File>> {
        public LoadImage() {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected List<File> doInBackground(Void... params) {
            BacksFiles = new ArrayList<>();
            Bundle bundle = getArguments();
            mfilesInfo = (FilesInfo) bundle.getSerializable("EXTRA_SUB_CAT_INFO");
            PackageDir = ApplicationLoader.appInstance().storage.getFile(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg));
            if (ApplicationLoader.appInstance().storage.isDirectoryExists(ApplicationLoader.appInstance().getString(R.string.ronevisPathBackGroundsImg))) {
                BacksFiles.addAll(Arrays.asList(listFilesMatching(PackageDir, mfilesInfo.getSubcat() + "_*\\d*")));
            }
            return BacksFiles;
        }

        @Override
        protected void onPostExecute(List<File> aVoid) {
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
            alphaAdapter.setDuration(130);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            recyclerView.setAdapter(alphaAdapter);
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
            mAdapter.setData(aVoid);
        }
    }
}