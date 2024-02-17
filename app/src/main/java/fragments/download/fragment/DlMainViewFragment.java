package fragments.download.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import activities.AppDetailActivity;
import activities.BaseActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fragments.animation.LoadingFace;
import fragments.download.DataSource;
import fragments.download.adapter.DlMainViewAdapter;
import fragments.download.listener.OnItemClickListener;
import fragments.download.model.Dlfile;
import fragments.download.model.Download;
import fragments.lisetener.RespondListener;
import mt.karimi.ronevis.R;
import retrofit2.Response;

public class DlMainViewFragment extends Fragment implements OnItemClickListener<Dlfile> {
    RecyclerView recyclerView;
    LoadingFace mLoadingFace;
    private DlMainViewAdapter mAdapter;

    public DlMainViewFragment() {
    }

    private void checkAdapterIsEmpty() {
        if (mAdapter.getItemCount() == 0) {
            mLoadingFace.setVisibility(View.VISIBLE);
        } else {
            mLoadingFace.setVisibility(View.GONE);
            mLoadingFace.stopAnim();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.RequestStorage();
        mAdapter = new DlMainViewAdapter();
        mAdapter.setOnItemClickListener(this);
        DataSource.getInstance().getAllFiles(new RespondListener() {
            @Override
            public void onSuccess(Response<Download> response) {
                Download user = response.body();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(mAdapter);
                mAdapter.setData(user);
                checkAdapterIsEmpty();
            }

            @Override
            public void onFail(Throwable t) {
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLoadingFace = (LoadingFace) view.findViewById(R.id.lv_circularSmile);
        mLoadingFace.setVisibility(View.VISIBLE);
        mLoadingFace.startAnim();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(View v, int position, Dlfile appInfo) {
        try {
            Dlfile dd = new Dlfile(appInfo.getTitle(),
                    appInfo.getType(),
                    appInfo.getCover(),
                    appInfo.getPacks());
            Intent intent = new Intent(v.getContext(), AppDetailActivity.class);
            intent.putExtra("EXTRA_PACKINFO", dd);
            v.getContext().startActivity(intent);
            getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View v, int position, Dlfile dlfile, String fragmenttag) {
    }
}
