package ir.mahchegroup.vision.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import ir.mahchegroup.vision.UserItems;

public class SetTimerNumInServer {

    public void setTimerNum(String visionTbl, String date, String s, String m, String h) {
        AndroidNetworking.post(UserItems.PATH + UserItems.SET_TIMER_NUM_IN_SERVER)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .addBodyParameter(UserItems.SS, s)
                .addBodyParameter(UserItems.MM, m)
                .addBodyParameter(UserItems.HH, h)
                .setTag("set_timer_num")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
