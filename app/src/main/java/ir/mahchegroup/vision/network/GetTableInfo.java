package ir.mahchegroup.vision.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import ir.mahchegroup.vision.UserItems;

public class GetTableInfo {

    public void getTableInfo(String visionTblName, String date) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_TABLE_INFO)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTblName)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .setTag("get_table_info")
                .build()
                .getAsObject(UserItems.class, new ParsedRequestListener<UserItems>() {
                    @Override
                    public void onResponse(UserItems response) {

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

}
