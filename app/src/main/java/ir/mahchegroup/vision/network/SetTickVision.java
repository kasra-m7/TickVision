package ir.mahchegroup.vision.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class SetTickVision {

    public void setTickVision(String visionTbl, String date, String userTbl, String nameVision, String tick) {
        AndroidNetworking.post(UserItems.PATH + UserItems.SET_TICK_VISION)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTbl)
                .addBodyParameter(UserItems.NAME_VISION, nameVision)
                .addBodyParameter(UserItems.TICK_VISION, tick)
                .setTag("set_tick_vision")
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
