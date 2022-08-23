package ir.mahchegroup.vision;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.github.florent37.materialtextfield.MaterialTextField;
import java.util.ArrayList;
import ir.mahchegroup.vision.message_box.SnackBar;
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
    private CheckBox rememberCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);


        // مقدار دهی اولیه
        init();


        setTypefaceMtf(mtfUserMail);
        setTypefaceMtf(mtfPassword);


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


        // رویداد دکمه ایجاد حساب کاربری جدید
        btnLogin.setOnClickListener(view -> {
            closeMtf();

            new Handler().postDelayed(() -> {
                getEdtText();

                if (TextUtils.isEmpty(userMail) || TextUtils.isEmpty(userMail)) {
                    snackBar.create(getResources().getString(R.string.completeFields), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                } else {
                    getUser.getUser(userMail, password);

                    getUser.setOnAddUserListener(() -> {
                        ArrayList<String> list = getUser.getResult();

                        boolean isWriteUserInfo = ActSplash.shared.getBoolean(UserItems.IS_WRITE_USER_INFO, false);

                        if (!list.get(0).equals("") && !list.get(1).equals("") && !list.get(2).equals("")) {

                            if (!isWriteUserInfo) {
                                ActSplash.editor.putString(UserItems.USER_MAIL, list.get(0));
                                ActSplash.editor.putString(UserItems.USERNAME, list.get(1));
                                ActSplash.editor.putString(UserItems.PASSWORD, list.get(2));
                                ActSplash.editor.putBoolean(UserItems.IS_WRITE_USER_INFO, true);
                                ActSplash.editor.putBoolean(UserItems.IS_FIRST_TIME, false);
                                ActSplash.editor.apply();
                            }

                            startActivity(new Intent(ActLogin.this, MainActivity.class));
                            finish();

                        } else {
                            snackBar.create(getResources().getString(R.string.wrongUser), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));
                        }
                    });
                }

            }, 400);
        });

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
    }
}