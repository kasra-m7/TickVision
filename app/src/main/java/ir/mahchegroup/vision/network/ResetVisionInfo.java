package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class ResetVisionInfo {
    private String result;
    private OnResetVisionInfoListener onResetVisionInfoListener;

    public ResetVisionInfo() {
        onResetVisionInfoListener = null;
    }

    public void setOnResetVisionInfoListener(OnResetVisionInfoListener onResetVisionInfoListener) {
        this.onResetVisionInfoListener = onResetVisionInfoListener;
    }

    public String getResult() {
        return result;
    }

    public void resetVisionInfo(String visionTbl, String date, String oneDayVision) {
        AndroidNetworking.post(UserItems.PATH + UserItems.RESET_VISION_INFO)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .addBodyParameter(UserItems.ONE_DAY_VISION, oneDayVision)
                .setTag("reset_vision_info")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        result = response;

                        onResetVisionInfoListener.onResetVisionInfoResult();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("reset vision info", anError.toString());
                    }
                });
    }


    public interface OnResetVisionInfoListener {
        void onResetVisionInfoResult();
    }
}
