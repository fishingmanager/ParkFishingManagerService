package com.fishing.namtran.fishingmanagerservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.Toast;

import com.fishing.namtran.fishingmanagerservice.dbconnection.FishingManager;
import com.fishing.namtran.fishingmanagerservice.dbconnection.Settings;
import com.fishing.namtran.fishingmanagerservice.dbconnection.SettingsManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nam.tran on 1/17/2018.
 */

public class SendMailService extends Service {

    // constant
    public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds
    // run on another Thread to avoid crash
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        //mTimer.scheduleAtFixedRate(new mainTask(), 0, NOTIFY_INTERVAL);
        Date currentDate = new Date();
        currentDate.setHours(22);
        currentDate.setMinutes(30);
        mTimer.schedule(new mainTask(), currentDate);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            final DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date currentDate = new Date();
            final String fileName = "BaoCao_" + dateFormat.format(currentDate) + ".xlsx";
            final String body = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(currentDate);

            FishingManager fishing = new FishingManager(getApplicationContext());
            Cursor cursor = fishing.getFishingEntries((new SimpleDateFormat("yyyy/MM/dd")).format(currentDate));

            if(Utils.saveExcelFile(getApplicationContext(), fileName, cursor)) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Cursor settings = (new SettingsManager(getApplicationContext())).getSettingEntry("1");
                            String serverEmail = "";
                            String serverPass = "";
                            String receiveEmail = "";

                            if (settings.moveToNext()) {
                                serverEmail = settings.getString(settings.getColumnIndexOrThrow(Settings.Properties.SERVER_EMAIL));
                                serverPass = settings.getString(settings.getColumnIndexOrThrow(Settings.Properties.SERVER_PASSWORD));
                                receiveEmail = settings.getString(settings.getColumnIndexOrThrow(Settings.Properties.RECEIVE_EMAIL));
                            }
                            settings.close();

                            GMailSender sender = new GMailSender(
                                    serverEmail,
                                    serverPass);
                            sender.addAttachment(getApplicationContext().getExternalFilesDir(null) + "/" + fileName);
                            sender.sendMail(getApplicationContext().getString(R.string.report), getApplicationContext().getString(R.string.report) + ": " + body,
                                    serverEmail,
                                    receiveEmail);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.email_unsuccess), Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }
        }
    }
}
