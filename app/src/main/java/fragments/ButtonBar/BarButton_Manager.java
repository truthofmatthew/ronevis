package fragments.ButtonBar;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fragments.tool.preferences.Pref;
import fragments.views.RtlGridLayoutManager;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BarButton_Manager {
    static RecyclerView.LayoutManager layoutManager;

    public static RecyclerView.LayoutManager layoutManager(Context context) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setReverseLayout(true);
        RtlGridLayoutManager gridLayoutManager;
        if (Pref.get("btn_title_visibile", true)) {
            gridLayoutManager = new RtlGridLayoutManager(context, 3);
            gridLayoutManager.setOrientation(RtlGridLayoutManager.VERTICAL);
        } else {
            gridLayoutManager = new RtlGridLayoutManager(context, 6);
            gridLayoutManager.setOrientation(RtlGridLayoutManager.VERTICAL);
        }
        boolean isChecked = Pref.get("btn_layout_type", true);
        if (isChecked) {
            layoutManager = gridLayoutManager;
        } else {
            layoutManager = llm;
        }
        return layoutManager;
    }
}
