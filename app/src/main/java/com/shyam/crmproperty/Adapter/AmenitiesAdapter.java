package com.shyam.crmproperty.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.Amenities.AmenitiesItem;
import com.shyam.crmproperty.Fragment.DetailData.DetailAmenityFragment;
import com.shyam.crmproperty.R;

import java.util.ArrayList;
import java.util.List;

public class AmenitiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private FragmentActivity activity;
    List<AmenitiesItem> list = new ArrayList<>();
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private boolean isLoadingFooterAdded = false;

    public AmenitiesAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new DataViewholder(LayoutInflater.from(context).inflate(R.layout.view_hold_amenity_item, parent, false));
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
        list.add(new AmenitiesItem());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingFooterAdded) {
            isLoadingFooterAdded = false;

            int position = list.size() - 1;
            AmenitiesItem item = getItem(position);

            if (item != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private AmenitiesItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<AmenitiesItem> amenitiesItems) {
        list = amenitiesItems;
        notifyDataSetChanged();
    }

    public class DataViewholder extends RecyclerView.ViewHolder {

        TextView v_h_amenity_name, v_h_amenity_category, v_h_amenity_remarks;

        public DataViewholder(@NonNull View itemView) {
            super(itemView);
            v_h_amenity_name = itemView.findViewById(R.id.v_h_amenity_name);
            v_h_amenity_category = itemView.findViewById(R.id.v_h_amenity_category);
            v_h_amenity_remarks = itemView.findViewById(R.id.v_h_amenity_remarks);
        }

        public void setData(AmenitiesItem amenitiesItem) {
            loadData(amenitiesItem);
            clickListener(amenitiesItem);
        }

        private void loadData(AmenitiesItem amenitiesItem) {
            v_h_amenity_name.setText(amenitiesItem.getName());
            v_h_amenity_category.setText(amenitiesItem.getCat());
            v_h_amenity_remarks.setText(amenitiesItem.getRemarks());
        }


        private void clickListener(AmenitiesItem amenitiesItem) {

            itemView.setOnClickListener(v -> {
                MainActivity.removeTabItemSelection();
                MainActivity.loadFragment(activity, new DetailAmenityFragment(amenitiesItem.getId()), true);
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

