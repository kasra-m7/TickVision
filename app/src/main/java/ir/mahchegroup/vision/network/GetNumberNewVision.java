package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class GetNumberNewVision {
    private String result;
    private OnGetNumberNewVisionListener onGetNumberNewVisionListener;

    public GetNumberNewVision() {

        onGetNumberNewVisionListener = null;

    }


    public String getResult() {
        return result;
    }


    public void setOnGetNumberNewVisionListener(OnGetNumberNewVisionListener onGetNumberNewVisionListener) {
        this.onGetNumberNewVisionListener = onGetNumberNewVisionListener;
    }

    public void getNumberNewVision(String userTbl) {

        AndroidNetworking.post(UserItems.PATH + UserItems.GET_NUMBER_NEW_VISION)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTbl)
                .setTag("get_number_new_vision")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        result = response;

                        onGetNumberNewVisionListener.onGetNumberNewVisionResult();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get number new vision", anError.toString());
                    }
                });

    }


    public interface OnGetNumberNewVisionListener {
        void onGetNumberNewVisionResult();
    }

}
