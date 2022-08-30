package ir.mahchegroup.vision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ir.mahchegroup.vision.message_box.LoadingDialog;
import ir.mahchegroup.vision.message_box.SnackBar;
import ir.mahchegroup.vision.network.CreateUserTable;
import ir.mahchegroup.vision.network.GetUser;

public class ActLogin extends AppCompatActivity {
    private MaterialTextField mtfUserMail, mtfPassword;
    private EditText edtUserMail, edtPassword;
    private Button btnLogin;
    private TextView btnLoginToSignup;
    private Typeface tf;
    private String userMail, password;
    private SnackBar snackBar;
    private CoordinatorLayout snackLayout;
    private GetUser getUser;
    private CreateUserTable createUserTable;
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);


        // مقدار دهی اولیه
        init();


        setTypefaceMtf(mtfUserMail);
        setTypefaceMtf(mtfPassword);


        checkConnection();


        // رویداد کلیک دکمه ایجاد حساب کاربری جدید
        btnLoginToSignup.setOnClickListener(view -> {
            closeMtf();

            new Handler().postDelayed(() -> {
                startActivity(new Intent(ActLogin.this, ActSignup.class));
                finish();
            }, 400);
        });


        // تعیین وضعیت چک باکس مرا به خاطر بسپار به محض ورود به اکتیویتی
        boolean isCheckedRemember = ActSplash.shared.getBoolean(UserItems.IS_CHECKED_REMEMBER, false);
        if (isCheckedRemember) {
            rememberCheckBox.setChecked(true);
            rememberCheckBox.setTextColor(getResources().getColor(R.color.primaryColor));

            edtUserMail.setText(ActSplash.shared.getString(UserItems.USER_MAIL, ""));
            edtPassword.setText(ActSplash.shared.getString(UserItems.PASSWORD, ""));

        } else {
            rememberCheckBox.setChecked(false);
            rememberCheckBox.setTextColor(getResources().getColor(R.color.disable));
        }


        // رویداد تغییر وضعیت چک باکس مرا به خاطر بسپار
        rememberCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                rememberCheckBox.setTextColor(getResources().getColor(R.color.primaryColor));
            } else {
                rememberCheckBox.setTextColor(getResources().getColor(R.color.disable));
            }
            ActSplash.editor.putBoolean(UserItems.IS_CHECKED_REMEMBER, b).apply();
        });


        // رویداد دکمه ورود به برنامه
        btnLogin.setOnClickListener(view -> {
            closeMtf();

            LoadingDialog loading = new LoadingDialog(ActLogin.this);
            loading.ShowDialog();

            new Handler().postDelayed(() -> {
                getEdtText();

                if (TextUtils.isEmpty(userMail) || TextUtils.isEmpty(userMail)) {
                    loading.dismissDialog();
                    snackBar.create(getResources().getString(R.string.completeFields), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                } else {
                    getUser.getUser(userMail, password);

                    getUser.setOnAddUserListener(() -> {
                        ArrayList<String> list = getUser.getResult();

                        if (!list.get(0).equals("") && !list.get(1).equals("") && !list.get(2).equals("")) {
                            ActSplash.editor.putString(UserItems.USER_MAIL, userMail);
                            ActSplash.editor.putString(UserItems.MAIL, list.get(0));
                            ActSplash.editor.putString(UserItems.USERNAME, list.get(1));
                            ActSplash.editor.putString(UserItems.PASSWORD, list.get(2));
                            ActSplash.editor.putBoolean(UserItems.IS_WRITE_USER_INFO, true);
                            ActSplash.editor.putBoolean(UserItems.IS_FIRST_TIME, false);
                            ActSplash.editor.putString(UserItems.USER_TABLE_NAME, list.get(1) + "_tbl");
                            ActSplash.editor.apply();

                            String userTable = list.get(1) + "_tbl";
                            createUserTable.createUserTable(userTable);

                            loading.dismissDialog();

                            startActivity(new Intent(ActLogin.this, MainActivity.class));
                            finish();

                        } else {
                            loading.dismissDialog();
                            snackBar.create(getResources().getString(R.string.wrongUser), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));
                        }
                    });
                }

            }, 1900);
        });
    }


    private void checkConnection() {
        new Handler().postDelayed(() -> {
            if (!isConnect()) {
                Intent intent = new Intent(ActLogin.this, ActDisconnect.class);
                intent.putExtra("activity", "ActLogin");
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
            checkConnection();
        }, 1000);
    }


    private boolean isConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isConnectedOrConnecting()) {
            return true;
        }

        NetworkInfo active = cm.getActiveNetworkInfo();
        if (active != null && active.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    private void getEdtText() {
        userMail = edtUserMail.getText().toString();
        password = edtPassword.getText().toString();
    }


    private void closeMtf() {
        mtfUserMail.setHasFocus(false);
        mtfPassword.setHasFocus(false);
    }


    private void setTypefaceMtf(MaterialTextField mtf) {
        mtf.getLabel().setTypeface(tf);
        mtf.getLabel().setTextSize(getResources().getDimension(R.dimen.edtHintSize));
        mtf.getLabel().setTextColor(getResources().getColor(R.color.disable));
    }


    private void init() {

        tf = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");

        snackLayout = findViewById(R.id.snack_layout);

        mtfUserMail = findViewById(R.id.login_mtf_userMail);
        mtfPassword = findViewById(R.id.login_mtf_password);

        edtUserMail = findViewById(R.id.login_edt_userMail);
        edtPassword = findViewById(R.id.login_edt_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLoginToSignup = findViewById(R.id.btn_login_to_signup);

        rememberCheckBox = findViewById(R.id.remember_check_box);

        snackBar = new SnackBar(this, snackLayout);

        getUser = new GetUser();

        createUserTable = new CreateUserTable();
    }
}