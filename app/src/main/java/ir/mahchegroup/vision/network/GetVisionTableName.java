package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class GetVisionTableName {
    private String result;
    private OnGetVisionTableNameListener onGetVisionTableNameListener;


    public GetVisionTableName() {
        onGetVisionTableNameListener = null;
    }


    public void setOnGetVisionTableNameListener(OnGetVisionTableNameListener onGetVisionTableNameListener) {
        this.onGetVisionTableNameListener = onGetVisionTableNameListener;
    }


    public String getResult() {
        return result;
    }


    public void getVisionTableName(String userTbl, String nameVision) {

        AndroidNetworking.post(UserItems.PATH + UserItems.GET_VISION_TABLE_NAME)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTbl)
                .addBodyParameter(UserItems.NAME_VISION, nameVision)
                .setTag("get_vision_table_name")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        result = response;

                        onGetVisionTableNameListener.onGetVisionTableNameResult();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get vision table name", anError.toString());
                    }
                });
    }


    public interface OnGetVisionTableNameListener {
        void onGetVisionTableNameResult();
    }
}