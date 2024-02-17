package fragments.ButtonBar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import activities.MainActivity;
import androidx.recyclerview.widget.RecyclerView;
import fragments.BaseFragment;
import mt.karimi.ronevis.R;

public class BarButton_Fragment extends BaseFragment implements BarButton_Listener<BarButton_Config> {
    static BarButton_Fragment CurrentInstance;
    SnapRecyclerAdapter adapter;
    RecyclerView recyclerView;
    private List<BarButton_Config> items;

    public static BarButton_Fragment getCurrentInstance() {
        return CurrentInstance;
    }

    public static BarButton_Fragment newInstance(ArrayList<BarButton_Config> BarButton_Tools, String Tag) {
        BarButton_Fragment fragment = new BarButton_Fragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("BARBUTTON_TOOLS", BarButton_Tools);
        bundle.putString("myNameIs", Tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String myNameIs() {
        return getArguments().getString("myNameIs");
    }

    public SnapRecyclerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        CurrentInstance = this;
        View bar_frag_bg = inflater.inflate(R.layout.bottom_bar, group, false);
        recyclerView = (RecyclerView) bar_frag_bg.findViewById(R.id.bottom_bar_recycler);
        items = (List<BarButton_Config>) getArguments().getSerializable("BARBUTTON_TOOLS");
//        FixedGridLayoutManager manager = new FixedGridLayoutManager();
//
//        manager.setTotalColumnCount(5);
//        SnapHelper  snapHelper = new GravitySnapHelper(Gravity.BOTTOM);
//        snapHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setOnFlingListener(snapHelper);
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//        recyclerView.setOnFlingListener(snapHelper);
        recyclerView.setLayoutManager(BarButton_Manager.layoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new GridDividerDecoration(getContext()));
        adapter = new SnapRecyclerAdapter(getContext(), items);
        adapter.setOnItemClickListener(this);
        adapter.setRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        MainActivity.mainInstance().BackGroundID = MainActivity.mainInstance().MainImageBG.getId();
        MainActivity.mainInstance().backGroundProperties.setBackGroundScaleType();
        MainActivity.mainInstance().backGroundProperties.setBackGroundGravity();
        MainActivity.mainInstance().BackGroundMap.put(MainActivity.mainInstance().MainImageBG.getId(), MainActivity.mainInstance().backGroundProperties);
        return bar_frag_bg;
    }

    @Override
    public void onItemClick(View v, final int position) {
        BarButton_ButtonClicks.onItemClick(v, position, getActivity(), adapter);
    }
}
