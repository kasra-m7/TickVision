package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import ir.mahchegroup.vision.UserItems;

public class GetItemVisions {
    private final ArrayList<String> nameVisionsResult;
    private final ArrayList<String> isTickResult;
    private OnGetItemVisions onGetItemVisions;


    public GetItemVisions() {
        nameVisionsResult = new ArrayList<>();
        isTickResult = new ArrayList<>();
        onGetItemVisions = null;
    }


    public void setOnGetItemVisions(OnGetItemVisions onGetItemVisions) {
        this.onGetItemVisions = onGetItemVisions;
    }


    public ArrayList<String> getNameVisionsResult() {
        return nameVisionsResult;
    }


    public ArrayList<String> getIsTickResult() {
        return isTickResult;
    }

    public void getItemVision(String userTbl) {
        AndroidNetworking.post(UserItems.PATH + UserItems.GET_ITEM_VISIONS)
                .addBodyParameter(UserItems.USER_TABLE_NAME, userTbl)
                .setTag("get_item_vision")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = response.getJSONObject(i);
                                String nameVision = jo.getString("name_vision");
                                nameVisionsResult.add(nameVision);

                                String isTick = jo.getString("is_tick");
                                isTickResult.add(isTick);
                            }
                        } catch (Exception ignore) {
                        }

                        onGetItemVisions.onGetItemVisions();

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
