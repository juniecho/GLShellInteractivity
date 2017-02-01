package gom.dolight.provider.shellinteractivity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * GLNotification
 * Class: BootReceiver
 * Created by WindSekirun on 14. 11. 21..
 */
public class BootReceiver extends BroadcastReceiver {
    Intent i;
    Context c;
    NotificationManager mNotifyMgr;
    ArrayList<Integer> idlist = new ArrayList<>();
    String LOGTAG = "GomdoLightNotification";
    NaraePreference np;

    @Override
    public void onReceive(Context c, Intent i) {
        this.i = i;
        this.c = c;
        np = new NaraePreference(c);
        mNotifyMgr = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            if (i.getAction().equals("gom.dolight.provider.shellinteractivity.Toast")) showToast();
            else if (i.getAction().equals("gom.dolight.provider.shellinteractivity.Notification")) showNotification();
            else if (i.getAction().equals("gom.dolight.provider.shellinteractivity.DebugToast")) showToastwithDebug();
            else if (i.getAction().equals("gom.dolight.provider.shellinteractivity.DebugNotification")) showNotificationwithDebug();
            else if (i.getAction().equals("gom.dolight.provider.shellinteractivity.Cancel")) cancelNotificaiton();
            else if (i.getAction().equals("gom.dolight.provider.shellinteractivity.CancelAll")) cancelAllNotification();
        } catch (Exception e) {
            Toast.makeText(c, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelNotificaiton() {
        String message = i.getStringExtra("message");
        int cancelid = Integer.getInteger(message);
        mNotifyMgr.cancel(cancelid);
        idlist.remove(cancelid);
        Log.d(LOGTAG, "Canceled Notification with ID Index: " + message);
        np.put("GLNP", idlist.size());
    }

    public void cancelAllNotification() {
        mNotifyMgr.cancelAll();
        for (int i = 0; i < idlist.size(); i++) idlist.remove(i);
        Log.d(LOGTAG, "Canceled All Notifications");
        np.put("GLNP", idlist.size());
    }

    public void showToast() {
        String message = i.getStringExtra("message");
        String duration = message.substring(0, 1);
        String toastMessage = message.substring(1);
        if (duration.equals("0")) Toast.makeText(c, toastMessage, Toast.LENGTH_SHORT).show();
        else Toast.makeText(c, toastMessage, Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification() {
        int id = getRandomNotify();
        idlist.add(id);
        np.put("GLNP", idlist.size());
        String message = i.getStringExtra("message");
        int guideLength = message.indexOf("|");
        String notiTitle = message.substring(0, guideLength);
        String notiBody = message.substring(guideLength + 1);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setTicker(notiTitle);
        mBuilder.setContentTitle(notiTitle);
        mBuilder.setContentText(notiBody);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_notification));
        mBuilder.setAutoCancel(true);
        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle(mBuilder);
        notiStyle.setBigContentTitle(notiTitle);
        notiStyle.bigText(notiBody);
        mBuilder.setStyle(notiStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mNotifyMgr.notify(id, mBuilder.build());
    }

    public void showToastwithDebug() {
        String message = i.getStringExtra("message");
        Log.d(LOGTAG, "received message: " + message);
        String duration = message.substring(0, 1);
        String toastMessage = message.substring(1);
        if (duration.equals("0")) Toast.makeText(c, toastMessage, Toast.LENGTH_SHORT).show();
        else Toast.makeText(c, toastMessage, Toast.LENGTH_LONG).show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotificationwithDebug() {
        int id = getRandomNotify();
        idlist.add(id);
        Log.d(LOGTAG, "generated notification id: " + id + "Index: " + idlist.size());
        np.put("GLNP", idlist.size());
        String message = i.getStringExtra("message");
        Log.d(LOGTAG, "received message: " + message);
        int guideLength = message.indexOf("|");
        String notiTitle = message.substring(0, guideLength);
        String notiBody = message.substring(guideLength + 1);
        Log.d(LOGTAG, "generated title from message: " + notiTitle);
        Log.d(LOGTAG, "generated body from message: " + notiBody);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setTicker(notiTitle);
        mBuilder.setContentTitle(notiTitle);
        mBuilder.setContentText(notiBody);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(c.getResources(), R.drawable.ic_notification));
        mBuilder.setAutoCancel(true);
        NotificationCompat.BigTextStyle notiStyle = new NotificationCompat.BigTextStyle(mBuilder);
        notiStyle.setBigContentTitle(notiTitle);
        notiStyle.bigText(notiBody);
        mBuilder.setStyle(notiStyle);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mNotifyMgr.notify(id, mBuilder.build());
    }

    public int getRandomNotify() {
        Random r = new Random();
        return r.nextInt(Integer.MAX_VALUE);
    }
}
