package ir.mahchegroup.vision;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import ir.mahchegroup.vision.data_time.ChangeDate;
import ir.mahchegroup.vision.data_time.ShamsiCalendar;

public class MainActivity extends AppCompatActivity {
    //    FloatingActionMenu menu;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private Toolbar toolbar;
    private LinearLayout dateLayout, tableLayout;
    private LayoutInflater inflater;
    private View dateView, tableView;
    private TextView tvDay, tvDate, tvTime, tvTitleReceive, tvTitlePayment, tvTitleProfit, tvTitleLeftover;
    private RelativeLayout viewsLayout;

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

        setDateView();

        setTableView();
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


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @SuppressLint("InflateParams")
    private void setTableView() {
        tableView = new View(this);
        tvTitleReceive = tableView.findViewById(R.id.tv_title_receive);
        tvTitlePayment = tableView.findViewById(R.id.tv_title_payment);
        tvTitleProfit = tableView.findViewById(R.id.tv_title_profit);
        tvTitleLeftover = tableView.findViewById(R.id.tv_title_leftover);

        tableView = inflater.inflate(R.layout.table_view, null);

        tableLayout.addView(tableView);
    }


    private void setDataTime() {
        new Handler().postDelayed(() -> {
            tvDate.setText(FaNum.convert(ChangeDate.getCurrentDay() + " / " + ChangeDate.getCurrentMonth() + " / " + ChangeDate.getCurrentYear()));
            tvTime.setText(FaNum.convert(ChangeDate.getCurrentTime()));
            tvDay.setText(showDay());
        }, 1000);
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
                result = "چهار شنبه";
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

    @SuppressLint("InflateParams")
    private void setDateView() {
        dateView = new View(this);
        tvDay = dateView.findViewById(R.id.tv_day);
        tvDate = dateView.findViewById(R.id.tv_date);
        tvTime = dateView.findViewById(R.id.tv_time);

        dateView = inflater.inflate(R.layout.date_view, null);

        dateLayout.addView(dateView);

        setDataTime();
    }


    private void init() {
//        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        viewsLayout = drawer.findViewById(R.id.views_layout);

        dateLayout = viewsLayout.findViewById(R.id.date_layout);
        tableLayout = viewsLayout.findViewById(R.id.table_layout);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}