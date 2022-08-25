package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import ir.mahchegroup.vision.UserItems;

public class GetVisionInfo {
    private ArrayList<String> result;
    public OnGetVisionInfoListener onGetVisionInfoListener;

    public GetVisionInfo() {
        onGetVisionInfoListener = null;
    }

    public void setOnGetVisionInfoListener(OnGetVisionInfoListener onGetVisionInfoListener) {
        this.onGetVisionInfoListener = onGetVisionInfoListener;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void getVisionInfo(String visionTbl, String date) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_VISION_INFO)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .setTag("get_vision_info")
                .build()
                .getAsObject(UserItems.class, new ParsedRequestListener<UserItems>() {
                    @Override
                    public void onResponse(UserItems response) {
                        String moneyVision = response.getMoneyVision();
                        String dayVision = response.getDayVision();
                        String oneDayVision = response.getOneDayVision();
                        String receive = response.getReceive();
                        String payment = response.getPayment();
                        String profit = response.getProfit();
                        String leftover = response.getLeftover();
                        String ss = response.getSs();
                        String mm = response.getMm();
                        String hh = response.getHh();
                        String isTick = response.getIsTick();

                        result = new ArrayList<>();

                        result.add(moneyVision);
                        result.add(dayVision);
                        result.add(oneDayVision);
                        result.add(receive);
                        result.add(payment);
                        result.add(profit);
                        result.add(leftover);
                        result.add(ss);
                        result.add(mm);
                        result.add(hh);
                        result.add(isTick);

                        onGetVisionInfoListener.onGetVisionInfoResult();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get vision info", anError.toString());
                    }
                });
    }


    public interface OnGetVisionInfoListener {
        void onGetVisionInfoResult();
    }

}
