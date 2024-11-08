package com.shyam.crmproperty.Other;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.shyam.crmproperty.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExtraFIle extends AppCompatActivity {


    public static String getIpAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress() && !addr.isLinkLocalAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (isIPv4) {
                            return sAddr; // Return the IPv4 address
                        } else {
                            // For IPv6 addresses, filter out the ones with '%'
                            int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                            return delim < 0 ? sAddr : sAddr.substring(0, delim);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; // If no IP found, return null
    }


    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public ArrayAdapter<String> getSpinnerAdapter(ArrayList<String> spinnerArray) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.layout_spinner,
                        spinnerArray); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        return spinnerArrayAdapter;

    }
}
