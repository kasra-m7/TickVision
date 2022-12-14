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
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.Timer;
import java.util.TimerTask;

import ir.mahchegroup.vision.message_box.LoadingDialog;
import ir.mahchegroup.vision.message_box.SnackBar;
import ir.mahchegroup.vision.message_box.ToastBox;
import ir.mahchegroup.vision.network.AddUser;

public class ActSignup extends AppCompatActivity {
    private MaterialTextField mtfMail, mtfUsername, mtfPassword, mtfRepeatPassword;
    private EditText edtMail, edtUsername, edtPassword, edtRepeatPassword;
    private Button btnSignup;
    private TextView btnSignupToLogin;
    private String mail, username, password, repeatPassword;
    private AddUser addUser;
    private CoordinatorLayout snackLayout;
    private SnackBar snackBar;
    private Typeface tfb, tf;
    private ToastBox toast;
    public static Timer timerSingup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_signup);


        // مقدار دهی اولیه
        init();


        setTypefaceMtf(mtfMail);
        setTypefaceMtf(mtfUsername);
        setTypefaceMtf(mtfPassword);
        setTypefaceMtf(mtfRepeatPassword);


        // رویداد کلیک دکمه حساب کاربری دارم
        btnSignupToLogin.setOnClickListener(view -> {
            closeMtf();
            startActivity(new Intent(ActSignup.this, ActLogin.class));
            finish();
        });


        btnSignup.setOnClickListener(view -> {
            closeMtf();

            getEdtText();

            if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword)) {
                snackBar.create(getResources().getString(R.string.completeFields), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));
            }else {

                LoadingDialog loading = new LoadingDialog(ActSignup.this);
                loading.ShowDialog();

                new Handler().postDelayed(() -> {

                    if (!password.equals(repeatPassword)) {
                        loading.dismissDialog();
                        snackBar.create(getResources().getString(R.string.unMatchRepeatPass), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                    } else {

                        checkConnection();

                        addUser.addUser(mail, username, password);

                        addUser.setOnAddUserListener(() -> {

                            loading.dismissDialog();

                            String result = addUser.getResult();

                            if (result.equals("duplicate")) {
                                snackBar.create(getResources().getString(R.string.duplicate), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                            } else if (result.equals("unSuccess")) {
                                snackBar.create(getResources().getString(R.string.fault), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                            } else if (result.equals("success")) {
                                ActSplash.editor.putBoolean(UserItems.IS_FIRST_TIME, false).apply();

//                                Toast.makeText(this, "حساب کاربری شما با موفقیت ایجاد شد", Toast.LENGTH_SHORT).show();
                                toast.showToast("حساب کاربری شما با موفقیت ایجاد شد", true);

                                startActivity(new Intent(ActSignup.this, ActLogin.class));
                                finish();

                            } else {
                                Log.e("add user", result);

                            }
                        });
                    }
                }, 3400);
            }
        });
    }

    private void setTypefaceMtf(MaterialTextField mtf) {
        mtf.getLabel().setTypeface(tf);
        mtf.getLabel().setTextSize(getResources().getDimension(R.dimen.edtHintSize));
        mtf.getLabel().setTextColor(getResources().getColor(R.color.disable));
    }


    private void checkConnection() {
        if (!isConnect()) {
            Intent intent = new Intent(ActSignup.this, ActDisconnect.class);
            intent.putExtra("activity", "ActSignup");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
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
        mail = edtMail.getText().toString();
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        repeatPassword = edtRepeatPassword.getText().toString();
    }


    private void closeMtf() {
        mtfMail.setHasFocus(false);
        mtfUsername.setHasFocus(false);
        mtfPassword.setHasFocus(false);
        mtfRepeatPassword.setHasFocus(false);
    }


    private void init() {
        tfb = Typeface.createFromAsset(getAssets(), "font/iran_sans_bold.ttf");
        tf = Typeface.createFromAsset(getAssets(), "font/iran_sans.ttf");

        mtfMail = findViewById(R.id.signup_mtf_mail);
        mtfUsername = findViewById(R.id.signup_mtf_username);
        mtfPassword = findViewById(R.id.signup_mtf_password);
        mtfRepeatPassword = findViewById(R.id.signup_mtf_repeat_password);

        edtMail = findViewById(R.id.signup_edt_mail);
        edtUsername = findViewById(R.id.signup_edt_username);
        edtPassword = findViewById(R.id.signup_edt_password);
        edtRepeatPassword = findViewById(R.id.signup_edt_repeat_password);

        btnSignup = findViewById(R.id.btn_signup);

        btnSignupToLogin = findViewById(R.id.btn_signup_to_login);

        snackLayout = findViewById(R.id.snack_layout);

        snackBar = new SnackBar(this, snackLayout);

        addUser = new AddUser();

        toast = new ToastBox(this);
    }
}