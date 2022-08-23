package ir.mahchegroup.vision;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FloatingActionMenu menu;
    DrawerLayout drawer;
    NavigationView navigation;
    Toolbar toolbar;

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = android.R.id.home;

        if (item.getItemId() == id) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            }else {
                drawer.openDrawer(Gravity.RIGHT);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        menu.getChildAt(0).setOnClickListener(view -> {
            Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
            menu.close(true);
        });

        menu.getChildAt(1).setOnClickListener(view -> {
            Toast.makeText(this, "select", Toast.LENGTH_SHORT).show();
            menu.close(true);
        });

        menu.getChildAt(2).setOnClickListener(view -> {
            Toast.makeText(this, "list", Toast.LENGTH_SHORT).show();
            menu.close(true);
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void init() {
        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer);
        navigation = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);
    }
}