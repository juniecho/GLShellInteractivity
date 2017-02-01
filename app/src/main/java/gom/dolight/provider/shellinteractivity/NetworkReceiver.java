package gom.dolight.provider.shellinteractivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.BroadcastReceiver;
import android.content.Intent;

import java.io.File;
import java.io.IOException;

/**
 * Created by Gomdolius on 1/31/2017.
 */

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ContextWrapper wrapper  = new ContextWrapper(context);
        String BASE_DIR = wrapper.getFilesDir().getParent();
        File WIFI_CONN_FILE = new File(BASE_DIR, "/connected_wifi");
        File DATA_CONN_FILE = new File(BASE_DIR, "/connected_data");
        ConnectivityManager connMgr;
        connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo now = connMgr.getActiveNetworkInfo();
        if (now != null) {
            if (now.getType() == ConnectivityManager.TYPE_WIFI) {
                try {
                    WIFI_CONN_FILE.createNewFile();
                    if (DATA_CONN_FILE.exists()) {
                        DATA_CONN_FILE.delete();
                    }
                } catch (IOException e) {
                    return;
                }
            } else if (now.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    DATA_CONN_FILE.createNewFile();
                    if (WIFI_CONN_FILE.exists()) {
                        WIFI_CONN_FILE.delete();
                    }
                } catch (IOException e) {
                    return;
                }
            } else {
                if (WIFI_CONN_FILE.exists()) {
                    WIFI_CONN_FILE.delete();
                }
                if (DATA_CONN_FILE.exists()) {
                    DATA_CONN_FILE.delete();
                }
            }
        } else {
            if (WIFI_CONN_FILE.exists()) {
                WIFI_CONN_FILE.delete();
            }
            if (DATA_CONN_FILE.exists()) {
                DATA_CONN_FILE.delete();
            }
        }
        connMgr = null;
    }
}
