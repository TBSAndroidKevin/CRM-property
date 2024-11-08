package com.shyam.crmproperty.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;
import com.shyam.crmproperty.DataModel.Followup.FollowupItem;
import com.shyam.crmproperty.Fragment.DetailData.DetailUserFragment;
import com.shyam.crmproperty.R;

import java.util.ArrayList;
import java.util.List;

public class FollowupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    FragmentActivity activity;
    List<FollowupItem> list = new ArrayList<>();
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private boolean isLoadingFooterAdded = false;

    public FollowupAdapter(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.activity = fragmentActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new DataViewholder(LayoutInflater.from(context).inflate(R.layout.view_hold_followup_item, parent, false));
        } else {
            return new LoadingViewholder(LayoutInflater.from(context).inflate(R.layout.view_hold_loading, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            ((DataViewholder) holder).setData(list.get(position));
        } else {
            ((LoadingViewholder) holder).setData();
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingFooterAdded) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void addLoadingFooter() {
        isLoadingFooterAdded = true;
        list.add(new FollowupItem());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingFooterAdded) {
            isLoadingFooterAdded = false;

            int position = list.size() - 1;
            FollowupItem item = getItem(position);

            if (item != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private FollowupItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<FollowupItem> followupItems) {
        list = followupItems;
        notifyDataSetChanged();
    }

    public class DataViewholder extends RecyclerView.ViewHolder {

        TextView v_h_f_up_status, v_h_f_up_name, v_h_f_up_project, v_h_f_up_email, v_h_f_up_group;
        ImageView v_h_f_up_status_img;


        public DataViewholder(@NonNull View itemView) {
            super(itemView);
            v_h_f_up_status = itemView.findViewById(R.id.v_h_f_up_status);
            v_h_f_up_status_img = itemView.findViewById(R.id.v_h_f_up_status_img);
            v_h_f_up_name = itemView.findViewById(R.id.v_h_f_up_name);
            v_h_f_up_project = itemView.findViewById(R.id.v_h_f_up_project);
            v_h_f_up_email = itemView.findViewById(R.id.v_h_f_up_email);
            v_h_f_up_group = itemView.findViewById(R.id.v_h_f_up_group);

        }

        public void setData(FollowupItem followupItem) {
            loadData(followupItem);
            clickListener(followupItem);
        }

        private void loadData(FollowupItem followupItem) {

            v_h_f_up_name.setText(followupItem.getFtime());
            v_h_f_up_email.setText(followupItem.getTime());

            loadStatus(followupItem);

        }

        private void loadStatus(FollowupItem followupItem) {

            v_h_f_up_status.setText(followupItem.getActive());

            if (followupItem.getActive().equals(DataConstant.ACTIVE)) {
                v_h_f_up_status_img.setImageResource(R.drawable.ic_circle_check);
                v_h_f_up_status_img.setColorFilter(Color.parseColor("#52a2e3"));
            } else {
                v_h_f_up_status_img.setImageResource(R.drawable.ic_circle_x);
                v_h_f_up_status_img.setColorFilter(Color.parseColor("#d73925"));
            }

        }

        private void clickListener(FollowupItem followupItem) {

            itemView.setOnClickListener(v -> {
                MainActivity.removeTabItemSelection();
                MainActivity.loadFragment(activity, new DetailUserFragment(followupItem.getId()), true);
            });

        }

    }


    public static class LoadingViewholder extends RecyclerView.ViewHolder {
        public LoadingViewholder(@NonNull View itemView) {
            super(itemView);
        }

        public void setData() {
        }
    }
}
