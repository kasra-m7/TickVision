package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class CreateVisionTable {
    private String result;
    private OnCreateVisionTableListener onCreateVisionTableListener;


    public CreateVisionTable() {
        this.onCreateVisionTableListener = null;
    }


    public void setOnCreateVisionTableListener(OnCreateVisionTableListener onCreateVisionTableListener) {
        this.onCreateVisionTableListener = onCreateVisionTableListener;
    }

    public String getResult() {
        return result;
    }

    public void createVisionTable(String visionTableName, String userTableName, String dateVision, String nameVision, String moneyVision, String day_vision, String one_day_vision) {
        AndroidNetworking.post(UserItems.PATH + UserItems.CREATE_VISION_TABLE)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTableName)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTableName)
                .addBodyParameter(UserItems.DATE_VISION, dateVision)
                .addBodyParameter(UserItems.NAME_VISION, nameVision)
                .addBodyParameter(UserItems.MONEY_VISION, moneyVision)
                .addBodyParameter(UserItems.DAY_VISION, day_vision)
                .addBodyParameter(UserItems.ONE_DAY_VISION, one_day_vision)
                .setTag("create_vision_table")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        result = response;

                        onCreateVisionTableListener.onCreateVisionResult();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("create table vision", anError.toString());
                    }
                });
    }


    public interface OnCreateVisionTableListener {
        void onCreateVisionResult();
    }
}
