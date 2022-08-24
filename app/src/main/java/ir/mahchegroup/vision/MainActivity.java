package ir.mahchegroup.vision;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import ir.mahchegroup.vision.data_time.ChangeDate;
import ir.mahchegroup.vision.data_time.ShamsiCalendar;

public class MainActivity extends AppCompatActivity {
        FloatingActionMenu menu;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private Toolbar toolbar;
    private LinearLayout dateLayout, tableLayout;
    private LayoutInflater inflater;
    private TextView tvDay, tvDate, tvTime, tvTitleReceive, tvTitlePayment, tvTitleProfit, tvTitleLeftover;
    public static TextView tvTimer;
    private RelativeLayout viewsLayout;
    private DateView dateView;
    private TableView tableView;
    public static int S, M, H;
    public static boolean isRunTimer = true;
    private ImageView btnStartTimerService;
    private boolean isRunTimerService;
    private Intent startTimer;

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = android.R.id.home;

        if (item.getItemId() == id) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint({"NonConstantResourceId", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTvTimerText();

        isRunTimerService = ActSplash.shared.getBoolean(UserItems.IS_RUN_TIMER_SERVICE, false);
        if (isRunTimerService) {
            btnStartTimerService.setImageResource(R.drawable.timer_on);
        }else {
            btnStartTimerService.setImageResource(R.drawable.timer_off);
        }
//
//        menu.getChildAt(0).setOnClickListener(view -> {
//            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
//            menu.close(true);
//        });
//
//        menu.getChildAt(1).setOnClickListener(view -> {
//            Toast.makeText(this, "select", Toast.LENGTH_SHORT).show();
//            menu.close(true);
//        });
//
//        menu.getChildAt(2).setOnClickListener(view -> {
//            Toast.makeText(this, "list", Toast.LENGTH_SHORT).show();
//            menu.close(true);
//        });

        btnStartTimerService.setOnClickListener(view -> {
            if (!isRunTimerService) {
                btnStartTimerService.setImageResource(R.drawable.timer_on);
                startService(startTimer);
                isRunTimerService = true;
            }else {
                btnStartTimerService.setImageResource(R.drawable.timer_off);
                stopService(startTimer);
                isRunTimerService = false;
            }
            ActSplash.editor.putBoolean(UserItems.IS_RUN_TIMER_SERVICE, isRunTimerService).apply();
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setDataTime();
    }


    // متد ست کردن متن تکست ویوی تایمر
    private void setTvTimerText() {
        if (S < 10 && M >= 10 && H >= 10) {
            tvTimer.setText(FaNum.convert("0" + S + " : " + M + " : " + H));
        } else if (S >= 10 && M < 10 && H >= 10) {
            tvTimer.setText(FaNum.convert(S + " : 0" + M + " : " + H));
        } else if (S >= 10 && M >= 10 && H < 10) {
            tvTimer.setText(FaNum.convert(S + " : " + M + " : 0" + H));
        } else if (S < 10 && M < 10 && H >= 10) {
            tvTimer.setText(FaNum.convert("0" + S + " : 0" + M + " : " + H));
        } else if (S < 10 && M >= 10 && H < 10) {
            tvTimer.setText(FaNum.convert("0" + S + " : " + M + " : 0" + H));
        } else if (S >= 10 && M < 10 && H < 10) {
            tvTimer.setText(FaNum.convert(S + " : 0" + M + " : 0" + H));
        } else if (S < 10 && M < 10 && H < 10) {
            tvTimer.setText(FaNum.convert("0" + S + " : 0" + M + " : 0" + H));
        }
    }


    private void setDataTime() {
        new Handler().postDelayed(() -> {
            tvDate.setText(FaNum.convert(ChangeDate.getCurrentDay() + " / " + ChangeDate.getCurrentMonth() + " / " + ChangeDate.getCurrentYear()));
            tvTime.setText(FaNum.convert(ChangeDate.getCurrentTime()));
            tvDay.setText(showDay());
            setDataTime();
        }, 998);
    }


    private String showDay() {
        String result = "";
        int day = ShamsiCalendar.dayOfWeek(ShamsiCalendar.shSysDate());
        switch (day) {
            case 2:
                result = "دوشنبه";
                break;

            case 3:
                result = "سه شنبه";
                break;

            case 4:
                result = "چهارشنبه";
                break;

            case 5:
                result = "پنج شنبه";
                break;

            case 6:
                result = "جمعه";
                break;

            case 7:
                result = "شنبه";
                break;

            case 1:
                result = "یکشنبه";
        }
        return result;
    }


    private void init() {
//        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dateView = findViewById(R.id.dateViewId);
        tableView = findViewById(R.id.tableViewId);

        tvDay = dateView.findViewById(R.id.tv_day);
        tvDate = dateView.findViewById(R.id.tv_date);
        tvTime = dateView.findViewById(R.id.tv_time);

        tvTimer = findViewById(R.id.tv_timer);

        btnStartTimerService = findViewById(R.id.timer_on_off);

        startTimer = new Intent(MainActivity.this, TimerService.class);
    }


    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        }else {
            super.onBackPressed();
        }
    }
}