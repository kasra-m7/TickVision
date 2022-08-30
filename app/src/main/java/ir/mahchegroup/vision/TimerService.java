package ir.mahchegroup.vision;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class TimerService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MainActivity.isRunTimer = true;

        String CHANNEL_ID = "channel_id";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String offerChannelName = "service channel";
            String offerChannelDescription = "timer channel";
            int offerChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notifChannel = new NotificationChannel(CHANNEL_ID, offerChannelName, offerChannelImportance);

            notifChannel.setDescription(offerChannelDescription);

            NotificationManager notifManager = getSystemService(NotificationManager.class);
            notifManager.createNotificationChannel(notifChannel);
        }

        NotificationCompat.Builder sNotifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.key)
                .setContentTitle(getResources().getString(R.string.tick_vision))
                .setContentText(getResources().getString(R.string.notifText));

        Notification servNotif = sNotifBuilder.build();

        startForeground(1, servNotif);

        setTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        MainActivity.isRunTimer = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void setTimer() {
        if (MainActivity.isRunTimer) {
            new Handler().postDelayed(() -> {
                if (MainActivity.S < 59) {
                    MainActivity.S++;
                } else {
                    MainActivity.S = 0;
                    if (MainActivity.M < 59) {
                        MainActivity.M++;
                    } else {
                        MainActivity.M = 0;
                        if (MainActivity.H < 23) {
                            MainActivity.H++;
                        } else {
                            MainActivity.H = 0;
                        }
                    }
                }
                setTvTimerText();
                setTimer();
            }, 1000);
        }
    }


    private void setTvTimerText() {
        if (MainActivity.S < 10 && MainActivity.M >= 10 && MainActivity.H >= 10) {
            MainActivity.tvTimer.setText(FaNum.convert("0" + MainActivity.S + " : " + MainActivity.M + " : " + MainActivity.H));
        } else if (MainActivity.S >= 10 && MainActivity.M < 10 && MainActivity.H >= 10) {
            MainActivity.tvTimer.setText(FaNum.convert(MainActivity.S + " : 0" + MainActivity.M + " : " + MainActivity.H));
        } else if (MainActivity.S >= 10 && MainActivity.M >= 10 && MainActivity.H < 10) {
            MainActivity.tvTimer.setText(FaNum.convert(MainActivity.S + " : " + MainActivity.M + " : 0" + MainActivity.H));
        } else if (MainActivity.S < 10 && MainActivity.M < 10 && MainActivity.H >= 10) {
            MainActivity.tvTimer.setText(FaNum.convert("0" + MainActivity.S + " : 0" + MainActivity.M + " : " + MainActivity.H));
        } else if (MainActivity.S < 10 && MainActivity.M >= 10 && MainActivity.H < 10) {
            MainActivity.tvTimer.setText(FaNum.convert("0" + MainActivity.S + " : " + MainActivity.M + " : 0" + MainActivity.H));
        } else if (MainActivity.S >= 10 && MainActivity.M < 10 && MainActivity.H < 10) {
            MainActivity.tvTimer.setText(FaNum.convert(MainActivity.S + " : 0" + MainActivity.M + " : 0" + MainActivity.H));
        } else if (MainActivity.S < 10 && MainActivity.M < 10 && MainActivity.H < 10) {
            MainActivity.tvTimer.setText(FaNum.convert("0" + MainActivity.S + " : 0" + MainActivity.M + " : 0" + MainActivity.H));
        }
    }
}