package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class SetPriceInServer {
    private String result;
    private OnSetPriceInServerListener onSetPriceInServerListener;

    public SetPriceInServer() {
        onSetPriceInServerListener = null;
    }


    public void setOnSetPriceInServerListener(OnSetPriceInServerListener onSetPriceInServerListener) {
        this.onSetPriceInServerListener = onSetPriceInServerListener;
    }


    public String getResult() {
        return result;
    }

    public void setPriceInServer(String visionTbl, String date, String receive, String payment, String profit, String leftover) {
        AndroidNetworking.post(UserItems.PATH + UserItems.SET_PRICE_IN_SERVER)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .addBodyParameter(UserItems.RECEIVE, receive)
                .addBodyParameter(UserItems.PAYMENT, payment)
                .addBodyParameter(UserItems.PROFIT, profit)
                .addBodyParameter(UserItems.LEFTOVER, leftover)
                .setTag("set_price_in_server")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        result = response;

                        onSetPriceInServerListener.onSetPriceInServerResult();

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("set price in server", anError.toString());
                    }
                });
    }


    public interface OnSetPriceInServerListener {
        void onSetPriceInServerResult();
    }
}
