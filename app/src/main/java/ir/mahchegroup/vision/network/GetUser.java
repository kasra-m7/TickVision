package ir.mahchegroup.vision.network;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import java.util.ArrayList;
import ir.mahchegroup.vision.UserItems;

public class GetUser {
    private ArrayList<String> result;
    private OnAddUserListener onAddUserListener;


    public GetUser() {
        onAddUserListener = null;
    }


    public void setOnAddUserListener(OnAddUserListener onAddUserListener) {
        this.onAddUserListener = onAddUserListener;
    }

    public ArrayList<String> getResult() {
        return result;
    }


    public void getUser(String userMail, String password) {

        AndroidNetworking.post(UserItems.PATH + UserItems.GET_USER)
                .addBodyParameter(UserItems.USER_MAIL, userMail)
                .addBodyParameter(UserItems.PASSWORD, password)
                .setTag("get_user")
                .build()
                .getAsObject(UserItems.class, new ParsedRequestListener<UserItems>() {
                    @Override
                    public void onResponse(UserItems response) {

                        String itemMail = response.getMail();
                        String itemUsername = response.getUsername();
                        String itemPassword = response.getPassword();

                        result = new ArrayList<>();

                        result.add(itemMail);
                        result.add(itemUsername);
                        result.add(itemPassword);

                        onAddUserListener.onAddUserResult();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("get user", anError.toString());
                    }
                });
    }


    public interface OnAddUserListener {
        void onAddUserResult();
    }

}