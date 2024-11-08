package com.shyam.crmproperty.Fragment.MainFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.shyam.crmproperty.API.RestClient;
import com.shyam.crmproperty.Activity.LoginActivity;
import com.shyam.crmproperty.Activity.MainActivity;
import com.shyam.crmproperty.Other.PrefHandler;
import com.shyam.crmproperty.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    SwipeRefreshLayout hm_refreshLayout;
    LineChart hm_lineChart;
    CardView hm_man_customer,hm_man_project;
    PrefHandler prefHandler;
    TextView hm_total_customer,hm_web_inquiry,hm_call_inquiry,hm_visit_inquiry,hm_total_project,hm_total_sms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        intView(view);

        loadData();

        clickListener();

        hm_refreshLayout.setOnRefreshListener(this::loadData);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 0.6f));
        entries.add(new Entry(1, 0.7f));
        entries.add(new Entry(2, 0.4f));
        entries.add(new Entry(3, 0.5f));
        entries.add(new Entry(4, 0.3f));
        entries.add(new Entry(5, 0.6f));

        LineDataSet dataSet = new LineDataSet(entries, "Last 30 Days Customers");
        dataSet.setColor(Color.GREEN);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(2f);
        dataSet.setCircleColor(Color.WHITE);
        dataSet.setCircleRadius(5f);
        dataSet.setCircleHoleColor(Color.GREEN);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);  // Smooth curve

        // Set up the X and Y axes
        XAxis xAxis = hm_lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = hm_lineChart.getAxisLeft();
        YAxis rightAxis = hm_lineChart.getAxisRight();
        rightAxis.setEnabled(false);  // Disable right Y axis

        leftAxis.setAxisMinimum(0);
        leftAxis.setAxisMaximum(1);

        // Set up the chart
        LineData lineData = new LineData(dataSet);
        hm_lineChart.setData(lineData);
        hm_lineChart.getDescription().setEnabled(false); // Disable description
        hm_lineChart.getLegend().setEnabled(false);  // Disable legend
        hm_lineChart.animateX(1000); // Animation for X-axis
        hm_lineChart.setDragEnabled(true);
        hm_lineChart.setScaleEnabled(true);
        hm_lineChart.setPinchZoom(true);
        hm_lineChart.setHighlightPerTapEnabled(true);


        return view;
    }

    private synchronized void loadData() {

        hm_refreshLayout.setRefreshing(true);

        Call<ResponseBody> call= RestClient.post().getDashboard(prefHandler.getACCESS_TOKEN());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");

                    if (status.equals("success")){

                        hm_refreshLayout.setRefreshing(false);

                        JSONObject dataObject = jsonObject.getJSONObject("data");

                        setData(dataObject);

                    } else if (status.equals("error")) {

                        Toast.makeText(getContext(), "Token Expired", Toast.LENGTH_SHORT).show();

                        prefHandler.setIsLogin(false);

                        startActivity(new Intent(getContext(), LoginActivity.class));
                        requireActivity().finish();

                    }else {
                        Toast.makeText(getContext(), "Invalid Error", Toast.LENGTH_SHORT).show();
                    }

                    hm_refreshLayout.setRefreshing(false);

                } catch (Exception e) {
//                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                hm_refreshLayout.setRefreshing(true);
            }
        });

    }

    private void setData(JSONObject dataObject) throws JSONException {

        hm_total_customer.setText(dataObject.getString("total_cust_unique")+"/"+dataObject.getString("total_cust"));
        hm_web_inquiry.setText(dataObject.getString("web_total_cust_unique")+"/"+dataObject.getString("web_total_cust"));
        hm_call_inquiry.setText(dataObject.getString("call_total_cust_unique")+"/"+dataObject.getString("call_total_cust"));
        hm_visit_inquiry.setText(dataObject.getString("visit_total_cust_unique")+"/"+dataObject.getString("visit_total_cust"));

        hm_total_project.setText(dataObject.getString("total_projects"));
        hm_total_sms.setText(dataObject.getString("total_sms")+"/"+dataObject.getString("unique_sms"));

    }

    private void clickListener() {

        hm_man_customer.setOnClickListener(v->{
            MainActivity.viewPager.setCurrentItem(1,true);
        });

        hm_man_project.setOnClickListener(v->{
            MainActivity.viewPager.setCurrentItem(2,true);
        });

    }

    private void intView(View view) {


        prefHandler = new PrefHandler(getContext());

        hm_refreshLayout = view.findViewById(R.id.hm_refreshLayout);

        hm_lineChart = view.findViewById(R.id.hm_lineChart);

        hm_total_customer = view.findViewById(R.id.hm_total_customer);
        hm_web_inquiry = view.findViewById(R.id.hm_web_inquiry);
        hm_call_inquiry = view.findViewById(R.id.hm_call_inquiry);
        hm_visit_inquiry = view.findViewById(R.id.hm_visit_inquiry);
        hm_total_project = view.findViewById(R.id.hm_total_project);
        hm_total_sms = view.findViewById(R.id.hm_total_sms);
        hm_man_customer = view.findViewById(R.id.hm_man_customer);
        hm_man_project = view.findViewById(R.id.hm_man_project);

    }

}
