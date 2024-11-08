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
import com.shyam.crmproperty.DataModel.User.UserItem;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;
import com.shyam.crmproperty.Fragment.DetailData.DetailUserFragment;
import com.shyam.crmproperty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    FragmentActivity activity;
    List<UserItem> list = new ArrayList<>();
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private boolean isLoadingFooterAdded = false;

    public UserAdapter(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        this.activity = fragmentActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new DataViewholder(LayoutInflater.from(context).inflate(R.layout.view_hold_user_item, parent, false));
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
        list.add(new UserItem());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingFooterAdded) {
            isLoadingFooterAdded = false;

            int position = list.size() - 1;
            UserItem item = getItem(position);

            if (item != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private UserItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<UserItem> UserItemList) {
        list = UserItemList;
        notifyDataSetChanged();
    }

    public class DataViewholder extends RecyclerView.ViewHolder {

        TextView v_h_user_status, v_h_user_name, v_h_user_project, v_h_user_email, v_h_user_group;
        ImageView v_h_user_status_img;


        public DataViewholder(@NonNull View itemView) {
            super(itemView);
            v_h_user_status = itemView.findViewById(R.id.v_h_user_status);
            v_h_user_status_img = itemView.findViewById(R.id.v_h_user_status_img);
            v_h_user_name = itemView.findViewById(R.id.v_h_user_name);
            v_h_user_project = itemView.findViewById(R.id.v_h_user_project);
            v_h_user_email = itemView.findViewById(R.id.v_h_user_email);
            v_h_user_group = itemView.findViewById(R.id.v_h_user_group);

        }

        public void setData(UserItem UserItem) {
            loadData(UserItem);
            clickListener(UserItem);
        }

        private void loadData(UserItem userItem) {

            v_h_user_name.setText(userItem.getFirstName() + " " + userItem.getLastName());

            v_h_user_email.setText(userItem.getEmail());

            v_h_user_project.setText(userItem.getProjectName());

            v_h_user_group.setText(userItem.getGroupName());

            loadStatus(userItem);

        }

        private void loadStatus(UserItem userItem) {

            v_h_user_status.setText(userItem.getActive());

            if (userItem.getActive().equals(DataConstant.ACTIVE)) {
                v_h_user_status_img.setImageResource(R.drawable.ic_circle_check);
                v_h_user_status_img.setColorFilter(Color.parseColor("#52a2e3"));
            } else {
                v_h_user_status_img.setImageResource(R.drawable.ic_circle_x);
                v_h_user_status_img.setColorFilter(Color.parseColor("#d73925"));
            }

        }

        private void clickListener(UserItem userItem) {

            itemView.setOnClickListener(v -> {
                MainActivity.removeTabItemSelection();
                MainActivity.loadFragment(activity, new DetailUserFragment(userItem.getId()), true);
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
