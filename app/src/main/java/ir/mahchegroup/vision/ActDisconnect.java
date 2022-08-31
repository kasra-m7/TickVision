package ir.mahchegroup.vision;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.TimerTask;

public class ActDisconnect extends AppCompatActivity {
    private String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_disconnect);

        Button btnTry = findViewById(R.id.btn_try_again);

        Bundle bundle = getIntent().getExtras();
        activity = bundle.getString("activity", "ActSplash");

        btnTry.setOnClickListener(view -> setIntent(activity));
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


    private void setIntent(String str) {

        if (isConnect()) {

            String stringClass = "ir.mahchegroup.vision." + str;
            Class<?> c = null;
            try {
                c = Class.forName(stringClass);
            } catch (ClassNotFoundException e) {
                Log.e("dis", e.getMessage());
            }
            Intent intent = new Intent(ActDisconnect.this, c);
            startActivity(intent);
            finish();
        }
    }

}