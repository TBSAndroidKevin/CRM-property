package com.shyam.crmproperty.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.DataModel.CustomerItem;
import com.shyam.crmproperty.DataModel.DataConstant.DataConstant;
import com.shyam.crmproperty.Fragment.DetailData.DetailCustomerFragment;
import com.shyam.crmproperty.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    private FragmentActivity activity;
    List<CustomerItem> list = new ArrayList<>();
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    private boolean isLoadingFooterAdded = false;

    public CustomerAdapter(Context context, FragmentActivity activity) {
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new DataViewholder(LayoutInflater.from(context).inflate(R.layout.view_hold_customer_item, parent, false));
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
        list.add(new CustomerItem());
        notifyItemInserted(list.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingFooterAdded) {
            isLoadingFooterAdded = false;

            int position = list.size() - 1;
            CustomerItem item = getItem(position);

            if (item != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private CustomerItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<CustomerItem> customerItemList) {
        list = customerItemList;
        notifyDataSetChanged();
    }

    public class DataViewholder extends RecyclerView.ViewHolder {

        TextView v_h_cus_name, v_h_cus_status, v_h_cus_date, v_h_cus_city, v_h_cus_type;
        ImageView v_h_cus_call, v_h_whatsapp, v_h_cus_positive, v_h_cus_status_img;

        public DataViewholder(@NonNull View itemView) {
            super(itemView);
            v_h_cus_name = itemView.findViewById(R.id.v_h_cus_name);
            v_h_cus_status = itemView.findViewById(R.id.v_h_cus_status);
            v_h_cus_date = itemView.findViewById(R.id.v_h_cus_date);
            v_h_cus_call = itemView.findViewById(R.id.v_h_cus_call);
            v_h_whatsapp = itemView.findViewById(R.id.v_h_whatsapp);
            v_h_cus_positive = itemView.findViewById(R.id.v_h_cus_positive);
            v_h_cus_city = itemView.findViewById(R.id.v_h_cus_city);
            v_h_cus_type = itemView.findViewById(R.id.v_h_cus_type);
            v_h_cus_status_img = itemView.findViewById(R.id.v_h_cus_status_img);
        }

        public void setData(CustomerItem customerItem) {
            loadData(customerItem);
            clickListener(customerItem);
        }

        private void loadData(CustomerItem customerItem) {
            v_h_cus_name.setText(customerItem.getSurname() + " " + customerItem.getFirstName());
            v_h_cus_status.setText(customerItem.getStatus());
            v_h_cus_date.setText(convertDate(customerItem.getDate()));
            v_h_cus_city.setText(customerItem.getCity());
            v_h_cus_type.setText(customerItem.getType());

            loadStatus(customerItem);

            switch (customerItem.getPositiveIndex()) {

                case "a":
                    v_h_cus_positive.setImageResource(R.drawable.ic_circle_a);
                    break;
                case "b":
                    v_h_cus_positive.setImageResource(R.drawable.ic_circle_b);
                    break;
                case "c":
                    v_h_cus_positive.setImageResource(R.drawable.ic_circle_c);
                    break;

            }

        }

        private void loadStatus(CustomerItem customerItem) {

            switch (customerItem.getStatus()) {

                case DataConstant.INQUIRY:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_circle_help);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#FF111111"));
                    v_h_cus_name.setTextColor(Color.parseColor("#FF111111"));
                    break;
                case DataConstant.VISITED_SITE:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_map);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#0073b7"));
                    v_h_cus_name.setTextColor(Color.parseColor("#0073b7"));
                    break;
                case DataConstant.VISITED_INTERESTED:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_thumb_up);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#00c0ef"));
                    v_h_cus_name.setTextColor(Color.parseColor("#00c0ef")); // Blue
                    break;
                case DataConstant.PLANNING:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_edit);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#ff851b"));
                    v_h_cus_name.setTextColor(Color.parseColor("#ff851b"));
                    break;
                case DataConstant.BOOKING_DONE:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_check);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#00a65a")); // Green
                    v_h_cus_name.setTextColor(Color.parseColor("#00a65a")); // Green
                    break;
                case DataConstant.NOT_INTERESTED:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_thumb_down);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#dd4b39"));
                    v_h_cus_name.setTextColor(Color.parseColor("#dd4b39"));  // Red
                    break;
                case DataConstant.BLACK_LISTED:
                    v_h_cus_status_img.setImageResource(R.drawable.ic_circle_help);
                    v_h_cus_status_img.setColorFilter(Color.parseColor("#FF111111"));
                    v_h_cus_name.setTextColor(Color.parseColor("#FF111111"));
                    break;

            }

        }

        public String convertDate(String inputDate) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                Date date = inputFormat.parse(inputDate);

                return outputFormat.format(date);
            } catch (Exception ignored) {
            }
            return inputDate;
        }


        private void clickListener(CustomerItem customerItem) {

            v_h_cus_status_img.setOnClickListener(v -> {

                String status = customerItem.getStatus();

                if (!(status.equals(DataConstant.BOOKING_DONE) || status.equals(DataConstant.NOT_INTERESTED))) {

                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_cus_status);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

                    LinearLayout dia_visit = dialog.findViewById(R.id.dia_visit);
                    LinearLayout dia_interested = dialog.findViewById(R.id.dia_interested);
                    LinearLayout dia_planning = dialog.findViewById(R.id.dia_planning);
                    LinearLayout dia_booking = dialog.findViewById(R.id.dia_booking);
                    LinearLayout dia_not_interested = dialog.findViewById(R.id.dia_not_interested);
                    View view_1 = dialog.findViewById(R.id.view_1);
                    View view_2 = dialog.findViewById(R.id.view_2);
                    View view_3 = dialog.findViewById(R.id.view_3);

                    switch (status) {
                        case DataConstant.VISITED_INTERESTED:
                            dia_visit.setVisibility(View.GONE);
                            dia_interested.setVisibility(View.GONE);
                            view_1.setVisibility(View.GONE);
                            view_2.setVisibility(View.GONE);
                            break;
                        case DataConstant.VISITED_SITE:
                            dia_visit.setVisibility(View.GONE);
                            view_1.setVisibility(View.GONE);
                            break;
                        case DataConstant.PLANNING:
                            dia_visit.setVisibility(View.GONE);
                            dia_interested.setVisibility(View.GONE);
                            dia_planning.setVisibility(View.GONE);
                            view_1.setVisibility(View.GONE);
                            view_2.setVisibility(View.GONE);
                            view_3.setVisibility(View.GONE);
                            break;
                    }

                    dialog.show();
                }
            });


            v_h_cus_call.setOnClickListener(v -> {
                if (customerItem.getMobile().trim().isEmpty() || customerItem.getMobile() == null) {
                    Toast.makeText(context, "Mobile Number Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    if (!customerItem.getMobile().startsWith("+91")) {
                        customerItem.setMobile("+91" + customerItem.getMobile());
                    }
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + customerItem.getMobile()));
                    context.startActivity(Intent.createChooser(intent, "Choose Calling App"));
                }
            });

            v_h_whatsapp.setOnClickListener(v -> {

                if (customerItem.getWhatsapp().trim().isEmpty() || customerItem.getWhatsapp() == null) {
                    Toast.makeText(context, "Whatsapp Number Not Found", Toast.LENGTH_SHORT).show();
                } else {

                    if (!customerItem.getWhatsapp().startsWith("+91")) {
                        customerItem.setWhatsapp("+91" + customerItem.getWhatsapp());
                    }

                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        String url = "https://api.whatsapp.com/send?phone=" + customerItem.getWhatsapp();
                        intent.setData(Uri.parse(url));
                        context.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(context, "WhatsApp not installed on this device", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(v -> {

                MainActivity.removeTabItemSelection();
                MainActivity.loadFragment(activity, new DetailCustomerFragment(customerItem.getId()),false);
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
