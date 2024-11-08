package com.shyam.crmproperty.Activity.EditActivities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.shyam.crmproperty.Activity.BaseActivity;
import com.shyam.crmproperty.DataModel.Amenities.AmenitiesItem;
import com.shyam.crmproperty.R;

public class EditAmenitiesActivity extends BaseActivity {

    AmenitiesItem amenitiesItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_amenities);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        amenitiesItem = getIntent().getParcelableExtra("UserItemData");

        //Place Edit Logic Here

    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    protected boolean isValidate() {
        return false;
    }
}