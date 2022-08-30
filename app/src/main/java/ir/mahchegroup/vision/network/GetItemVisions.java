package ir.mahchegroup.vision.network;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import org.json.JSONArray;
import java.util.ArrayList;
import ir.mahchegroup.vision.UserItems;

public class GetItemVisions {
    private ArrayList<String> nameVisionResult;
    private ArrayList<String> isTickVisionResult;
    private OnGetItemVisions onGetItemVisions;


    public GetItemVisions() {
        onGetItemVisions = null;
    }


    public void setOnGetItemVisions(OnGetItemVisions onGetItemVisions) {
        this.onGetItemVisions = onGetItemVisions;
    }


    public ArrayList<String> getNameVisionResult() {
        return nameVisionResult;
    }

    public ArrayList<String> getIsTickVisionResult() {
        return isTickVisionResult;
    }

    public void getItemVision(String userTbl) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_ITEM_VISIONS)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTbl)
                .setTag("get_item_vision")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        nameVisionResult = new ArrayList<>();
                        isTickVisionResult = new ArrayList<>();

                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONArray jo = response.getJSONArray(i);

                                String name = jo.getString(0);
                                nameVisionResult.add(name);

                                String isTick = jo.getString(1);
                                isTickVisionResult.add(isTick);
                            }

                            onGetItemVisions.onGetItemVisions();

                        }catch (Exception e) {
                            Log.e("catch get item visions", e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get item visions", anError.toString());
                    }
                });

    }


    public interface OnGetItemVisions {
        void onGetItemVisions();
    }

}
