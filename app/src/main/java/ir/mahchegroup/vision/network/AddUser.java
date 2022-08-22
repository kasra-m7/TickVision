package ir.mahchegroup.vision.network;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;

import ir.mahchegroup.vision.UserItems;

public class AddUser {
    private String result;
    private OnAddUserListener addUserListener;


    // کانستراکتور
    public AddUser() {
        addUserListener = null;
    }


    // ستر
    public void setOnAddUserListener(OnAddUserListener AddUserListener) {
        this.addUserListener = AddUserListener;
    }


    // گرفتن پاسخ درخواست که بصورت متنی است
    public String getResult() {
        return result;
    }


    // ارسال اطلاعات کاربر به سرور و ایجاد حساب کاربری جدید
    public void addUser(String mail, String username, String password) {

        AndroidNetworking.post(UserItems.PATH + UserItems.ADD_USER)
                .addBodyParameter(UserItems.MAIL, mail)
                .addBodyParameter(UserItems.USERNAME, username)
                .addBodyParameter(UserItems.PASSWORD, password)
                .setTag("add_user")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("duplicate")) {
                            result = "duplicate";

                        } else if (response.equals("success")) {
                            result = "success";

                        } else if (response.equals("unSuccess")) {
                            result = "unSuccess";
                        }else {
                            result = response;
                        }

                        addUserListener.onAddUserResult();
                    }


                    @Override
                    public void onError(ANError anError) {
                        Log.e("addUser", anError.toString());
                    }
                });
    }


    // اینترفیس کلاس جهت دسترسی به پاسخ از کلاس های دیگر
    public interface OnAddUserListener {
        void onAddUserResult();
    }
}
