package ir.mahchegroup.vision.network;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;

import ir.mahchegroup.vision.UserItems;

public class GetTimerNumFromServer {
    private ArrayList<String> result;
    private OnGetTimerNumListener onGetTimerNumListener;

    public GetTimerNumFromServer() {
        onGetTimerNumListener = null;
    }

    public void setOnGetTimerNumListener(OnGetTimerNumListener onGetTimerNumListener) {
        this.onGetTimerNumListener = onGetTimerNumListener;
    }

    public ArrayList<String> getResult() {
        return result;
    }

    public void getTimerNum(String visionTbl, String date) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_TIMER_NUM)
                .addBodyParameter(UserItems.VISION_TABLE_NAME, visionTbl)
                .addBodyParameter(UserItems.DATE_VISION, date)
                .setTag("get_timer_num")
                .build()
                .getAsObject(UserItems.class, new ParsedRequestListener<UserItems>() {
                    @Override
                    public void onResponse(UserItems response) {
                        String ss = response.getSs();
                        String mm = response.getMm();
                        String hh = response.getHh();

                        result = new ArrayList<>();

                        result.add(ss);
                        result.add(mm);
                        result.add(hh);

                        onGetTimerNumListener.onGetTimerNumResult();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    public interface OnGetTimerNumListener {
        void onGetTimerNumResult();
    }

}
