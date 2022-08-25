package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class CreateUserTable {

    public void createUserTable(String userTableName) {

        AndroidNetworking.post(UserItems.PATH + UserItems.CREATE_USER_TABLE)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTableName)
                .setTag("create_user_table")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("createUserTable", anError.toString());
                    }
                });
    }
}