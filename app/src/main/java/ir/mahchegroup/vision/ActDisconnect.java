package ir.mahchegroup.vision;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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


    private void setIntent(String str) {
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