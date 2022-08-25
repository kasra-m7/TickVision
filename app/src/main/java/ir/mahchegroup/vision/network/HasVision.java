package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.ActSplash;
import ir.mahchegroup.vision.UserItems;

public class HasVision {
    private String result;
    private OnHasVisionListener onHasVisionListener;

    public HasVision() {
        onHasVisionListener = null;
    }

    public void setOnHasVisionListener(OnHasVisionListener onHasVisionListener) {
        this.onHasVisionListener = onHasVisionListener;
    }

    public String getResult() {
        return result;
    }

    public void hasVision(String userTable, String nameVision) {
        AndroidNetworking.post(UserItems.PATH + UserItems.HAS_VISION)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTable)
                .addBodyParameter(UserItems.NAME_VISION, nameVision)
                .setTag("has_vision")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        result = response;

                        onHasVisionListener.onHasVisionResult();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("has vision", anError.toString());
                    }
                });
    }


    public interface OnHasVisionListener {
        void onHasVisionResult();
    }

}
