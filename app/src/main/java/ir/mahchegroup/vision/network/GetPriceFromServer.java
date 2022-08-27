package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import ir.mahchegroup.vision.UserItems;

public class GetPriceFromServer {
    private ArrayList<String> result;
    private OnGetPriceFromServerListener onGetPriceFromServerListener;

    public GetPriceFromServer() {
        onGetPriceFromServerListener = null;
    }

    public void setOnGetPriceFromServerListener(OnGetPriceFromServerListener onGetPriceFromServerListener) {
        this.onGetPriceFromServerListener = onGetPriceFromServerListener;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void getPriceFromServer(String visionTblName, String date) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_PRICE_FROM_SERVER)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTblName)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .setTag("get_price_from_server")
                .build()
                .getAsObject(UserItems.class, new ParsedRequestListener<UserItems>() {
                    @Override
                    public void onResponse(UserItems response) {
                        String receive = response.getReceive();
                        String payment = response.getPayment();
                        String profit = response.getProfit();
                        String leftover = response.getLeftover();
                        String isTick = response.getIsTick();

                        result = new ArrayList<>();

                        result.add(receive);
                        result.add(payment);
                        result.add(profit);
                        result.add(leftover);
                        result.add(isTick);

                        onGetPriceFromServerListener.onGetPriceFromServerResult();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get price from server", anError.toString());
                    }
                });
    }


    public interface OnGetPriceFromServerListener {
        void onGetPriceFromServerResult();
    }

}
