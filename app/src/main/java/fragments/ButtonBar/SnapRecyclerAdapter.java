package fragments.ButtonBar;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import fragments.MTViews.LayoutHelper;
import fragments.objects.TextProperties;
import fragments.views.GridAutofitLayoutManager;
import mt.karimi.ronevis.R;

public class SnapRecyclerAdapter extends RecyclerView.Adapter<SnapRecyclerAdapter.ButotnViewHolder> {
    RecyclerView recyclerView;
    private Context context;
    private List<BarButton_Config> button_configs;
    private BarButton_Listener<BarButton_Config> mListener;

    public SnapRecyclerAdapter(Context context, List<BarButton_Config> button_configs) {
        this.context = context;
        this.button_configs = new ArrayList<>();
        this.button_configs.clear();
        this.button_configs.addAll(button_configs);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SnapRecyclerAdapter setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public void notifyRemoveEach() {
        for (int i = 0; i < button_configs.size(); i++) {
            notifyItemRemoved(i);
        }
    }

    public void notifyAddEach() {
        for (int i = 0; i < button_configs.size(); i++) {
            notifyItemInserted(i);
        }
    }

    public void setOnItemClickListener(BarButton_Listener<BarButton_Config> listener) {
        this.mListener = listener;
    }

    private int dpToPx(int dps) {
        return Math.round(context.getResources().getDisplayMetrics().density * dps);
    }

    @Override
    public ButotnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ButotnViewHolder(new BarButton_Layout(context));
    }

    @Override
    public void onBindViewHolder(final ButotnViewHolder holder, int position) {
        bindData(holder, position);
    }

    public void refreshVisibleLayout(int position) {
        notifyItemChanged(position);
    }

    public void refreshWholeLayout() {
        notifyItemRangeChanged(0, button_configs.size());
    }

    private void bindData(final ButotnViewHolder holder, final int position) {
        final BarButton_Config button_config = button_configs.get(position);
//        holder.itemView.setId(button_config.getButton_id());
        RecyclerView.LayoutManager mgr = recyclerView.getLayoutManager();
        if (mgr instanceof GridAutofitLayoutManager || mgr instanceof GridLayoutManager) {
            holder.itemView.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            holder.itemView.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        } else if (mgr instanceof LinearLayoutManager) {
            holder.itemView.setLayoutParams(LayoutHelper.createLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        }
        holder.buttonLayout.setId(button_config.getButton_id());
        holder.buttonLayout.setButton_id(button_config.getButton_id());
        holder.buttonLayout.setIcon_text(button_config.getIcon_text());
        holder.buttonLayout.setIcon_text_on(button_config.getIcon_text_on());
        holder.buttonLayout.setTitle_text(button_config.getTitle_text());
        holder.buttonLayout.setTitle_text_on(button_config.getTitle_text_on());
        holder.buttonLayout.setBtn_toggle(button_config.isBtn_toggle());
        holder.buttonLayout.setChecked(button_config.isBtn_checked());
//        holder.buttonLayout.setBtn_toggle(false);
//        holder.buttonLayout.setChecked(false);
//        holder.buttonLayout.setChecked(button_config.isBtn_checked());
//        holder.buttonIcon.setText(button_config.getIcon_text());
//        holder.buttonText.setText(button_config.getTitle_text());
        if (activities.MainActivity.mainInstance().SelecetedTextView != null) {
//        if (TextProperties.getCurrent() != null) {
            if (button_config.getButton_id() == R.id.btn_Text_Add) {
                holder.buttonLayout.setChecked(true);
            } else if (button_config.getButton_id() == R.id.btn_Text_Bold) {
                holder.buttonLayout.setChecked(TextProperties.getCurrent().getTextBold());
            } else if (button_config.getButton_id() == R.id.btn_Text_Italic) {
                holder.buttonLayout.setChecked(TextProperties.getCurrent().getTextItalic());
            } else if (button_config.getButton_id() == R.id.btn_Text_Lock) {
                holder.buttonLayout.setChecked(TextProperties.getCurrent().getTextLock());
            } else if (button_config.getButton_id() == R.id.btn_Text_Strikethrough) {
                holder.buttonLayout.setChecked(TextProperties.getCurrent().getTextStrike());
            } else if (button_config.getButton_id() == R.id.btn_Text_Underline) {
                holder.buttonLayout.setChecked(TextProperties.getCurrent().getTextUnderline());
            } else {
                holder.buttonLayout.setChecked(false);
            }
        } else {
            holder.buttonLayout.setChecked(false);
        }
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return button_configs.size();
    }

    public static final class ButotnViewHolder extends RecyclerView.ViewHolder {
        public BarButton_Layout buttonLayout;

        public ButotnViewHolder(final BarButton_Layout itemView) {
            super(itemView);
            buttonLayout = itemView;
        }
    }
}
