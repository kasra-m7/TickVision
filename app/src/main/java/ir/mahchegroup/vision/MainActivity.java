package ir.mahchegroup.vision;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import ir.mahchegroup.vision.data_time.ChangeDate;
import ir.mahchegroup.vision.data_time.ShamsiCalendar;
import ir.mahchegroup.vision.message_box.LoadingDialog;
import ir.mahchegroup.vision.message_box.SnackBar;
import ir.mahchegroup.vision.message_box.ToastBox;
import ir.mahchegroup.vision.network.CreateVisionTable;
import ir.mahchegroup.vision.network.GetItemVisions;
import ir.mahchegroup.vision.network.GetNumberNewVision;
import ir.mahchegroup.vision.network.GetPriceFromServer;
import ir.mahchegroup.vision.network.GetTimerNumFromServer;
import ir.mahchegroup.vision.network.GetVisionInfo;
import ir.mahchegroup.vision.network.GetVisionTableName;
import ir.mahchegroup.vision.network.HasVision;
import ir.mahchegroup.vision.network.SetPriceInServer;
import ir.mahchegroup.vision.network.SetTickVision;
import ir.mahchegroup.vision.network.SetTimerNumInServer;
import ir.mahchegroup.vision.select_vision_recycler.RecyclerItemClick;
import ir.mahchegroup.vision.select_vision_recycler.RvItemsSelectVision;
import ir.mahchegroup.vision.select_vision_recycler.SelectVisionAdapter;

public class MainActivity extends AppCompatActivity {
    private FloatingActionMenu menu;
    private DrawerLayout drawer;
    private NavigationView navigation;
    private Toolbar toolbar;
    private LayoutInflater inflater;
    private TextView tvDay, tvDate, tvTime, tvTitleReceive, tvTitlePayment, tvTitleProfit,
            tvTitleLeftover, tvToolbar, tvTextReceive, tvTextPayment, tvTextProfit, tvTextLeftover;
    public static TextView tvTimer;
    private DateView dateView;
    private TableView tableView;
    public static int S, M, H;
    public static boolean isRunTimer = true;
    private ImageView btnStartTimerService;
    private boolean isRunTimerService;
    private Intent startTimer;
    private int counterAddVisionDialog = 0;
    private String nameVision, moneyVision, dayVision, dateVision, numberNewVisionResult,
            recyclerClickItem, selectedVision, userTbl, netMoneyVision, netDayVision, netOneDayVision,
            netReceive, netPayment, netProfit, netLeftover, netSs, netMm, netHh, netIsTick, strReceive, strPayment,
            strProfit, strLeftover, visionTblName, strMoney, strS, strM, strH;
    private HasVision hasVision;
    private LoadingDialog loading;
    private SnackBar snack;
    private CoordinatorLayout snackLayout;
    private GetNumberNewVision getNumberNewVision;
    private CreateVisionTable createVisionTable;
    private ToastBox toast;
    private ArrayList<String> nameVisionResult, isTickResult, getVisionInfoResult, getPriceFromServerResult,
            getTimerNumResult;
    private ArrayList<RvItemsSelectVision> rvItems;
    private SelectVisionAdapter adapter;
    private GetItemVisions getItemVisions;
    private GetVisionTableName getVisionTableName;
    private GetVisionInfo getVisionInfo;
    private int intReceive, intPayment, intProfit, intLeftover, newReceive, newPayment, newProfit, newLeftover, negativeLeftover, negativeProfit;
    private View btnReceive, btnPayment;
    private GetPriceFromServer getPriceFromServer;
    private SetPriceInServer setPriceInServer;
    private SetTimerNumInServer setTimerNumInServer;
    private GetTimerNumFromServer getTimerNumFromServer;
    private ImageView imgTick;
    private Animation tickIn, tickOut;
    private SetTickVision setTickVision;
    private LinearLayout disableLayout, enableLayout;

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = android.R.id.home;

        if (item.getItemId() == id) {

            if (menu.isOpened()) {
                menu.close(false);
            }

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

        tvDate.setText(FaNum.convert(ChangeDate.getCurrentDay() + " / " + ChangeDate.getCurrentMonth() + " / " + ChangeDate.getCurrentYear()));
        tvTime.setText(FaNum.convert(ChangeDate.getCurrentTime()));
        tvDay.setText(showDay());

        isRunTimerService = ActSplash.shared.getBoolean(UserItems.IS_RUN_TIMER_SERVICE, false);
        userTbl = ActSplash.shared.getString(UserItems.USER_TABLE_NAME, "");

        if (isRunTimerService) {
            btnStartTimerService.setImageResource(R.drawable.timer_on);
        } else {
            btnStartTimerService.setImageResource(R.drawable.timer_off);
        }


        setDataTime();


        selectedVision = ActSplash.shared.getString(UserItems.SELECTED_VISION, "");
        if (!TextUtils.isEmpty(selectedVision)) {
            tvToolbar.setText(ActSplash.shared.getString(UserItems.SELECTED_VISION, ""));
            visionTblName = ActSplash.shared.getString(UserItems.VISION_TABLE_NAME, "");
            dateVision = ChangeDate.getCurrentDay() + " / " + ChangeDate.getCurrentMonth() + " / " + ChangeDate.getCurrentYear();

            getVisionTableInfo();
        }

        menu.getChildAt(0).setOnClickListener(view -> {
            menu.close(true);
            new Handler().postDelayed(this::createAddVisionDialog, 400);
        });

        menu.getChildAt(1).setOnClickListener(view -> {
            menu.close(true);
            new Handler().postDelayed(this::createSelectVisionDialog, 400);
        });

        menu.getChildAt(2).setOnClickListener(view -> {
            Toast.makeText(this, "list", Toast.LENGTH_SHORT).show();
            menu.close(true);
        });

        btnStartTimerService.setOnClickListener(view -> {
            if (menu.isOpened()) {
                menu.close(true);
            }

            if (!isRunTimerService) {
                btnStartTimerService.setImageResource(R.drawable.timer_on);
                startService(startTimer);
                isRunTimerService = true;

            } else {

                setTimerNumInServer.setTimerNum(visionTblName, dateVision, String.valueOf(S), String.valueOf(M), String.valueOf(H));

                btnStartTimerService.setImageResource(R.drawable.timer_off);
                stopService(startTimer);
                isRunTimerService = false;
            }
            ActSplash.editor.putBoolean(UserItems.IS_RUN_TIMER_SERVICE, isRunTimerService).apply();
        });


        btnReceive.setOnClickListener(view -> createReceiveDialog());


        btnPayment.setOnClickListener(view -> createPaymentDialog());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @SuppressLint("InflateParams")
    private void createReceiveDialog() {
        Dialog receiveDialog = new Dialog(this);
        receiveDialog.setContentView(R.layout.receive_dialog_layout);
        EditText edtReceive = receiveDialog.findViewById(R.id.edt_receive);
        ImageView voice = receiveDialog.findViewById(R.id.btn_voice_receive);
        Button btnSave = receiveDialog.findViewById(R.id.btn_ok_receive);
        Button btnCancel = receiveDialog.findViewById(R.id.btn_cancel_receive);
        receiveDialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;

        getPriceFromServer.getPriceFromServer(visionTblName, dateVision);

        getPriceFromServer.setOnGetPriceFromServerListener(() -> {

            getPriceFromServerResult = getPriceFromServer.getResult();

            netReceive = getPriceFromServerResult.get(0);
            netPayment = getPriceFromServerResult.get(1);
            netProfit = getPriceFromServerResult.get(2);
            netLeftover = getPriceFromServerResult.get(3);
            netIsTick = getPriceFromServerResult.get(4);

        });

        receiveDialog.create();
        receiveDialog.show();

        voice.setOnClickListener(view -> {
            toast.showToast("امکان ثبت صوتی مبلغ به زودی به برنامه اضافه خواهد شد", false);
        });

        btnCancel.setOnClickListener(view -> receiveDialog.dismiss());

        btnSave.setOnClickListener(view -> {
            Log.e("leftover receive", netLeftover);
            strMoney = edtReceive.getText().toString().trim();

            if (TextUtils.isEmpty(strMoney)) {
                toast.showToast(getResources().getString(R.string.moneyError), false);

            } else {
                int intMoney = Integer.parseInt(strMoney);
                stringToInteger();

                newReceive = intReceive + intMoney;
                newPayment = intPayment;
                newProfit = newReceive - intPayment;
                newLeftover = intLeftover - intMoney;

                if (intLeftover > 0 && newLeftover <= 0) {

                    enableLayout.setVisibility(View.GONE);
                    disableLayout.setVisibility(View.VISIBLE);

                    imgTick.setVisibility(View.VISIBLE);
                    imgTick.startAnimation(tickIn);

                    setTickVision.setTickVision(visionTblName, dateVision, userTbl, selectedVision, "1");
                }

                setPriceInServer.setPriceInServer(visionTblName, dateVision, String.valueOf(newReceive), String.valueOf(newPayment), String.valueOf(newProfit), String.valueOf(newLeftover));

                setPriceInServer.setOnSetPriceInServerListener(() -> {
                    String result = setPriceInServer.getResult();

                    if (result.equals("success")) {
                        receiveDialog.dismiss();

                        getTimerNumFromServer.getTimerNum(visionTblName, dateVision);

                        getTimerNumFromServer.setOnGetTimerNumListener(() -> {
                            getTimerNumResult = getTimerNumFromServer.getResult();

                            strS = getTimerNumResult.get(0);
                            strM = getTimerNumResult.get(1);
                            strH = getTimerNumResult.get(2);

                            S = Integer.parseInt(strS);
                            M = Integer.parseInt(strM);
                            H = Integer.parseInt(strH);

                            getVisionInfo();
                            getVisionTableInfo();
                        });

                        strMoney = "";

                        toast.showToast("اطلاعات شما با موفقیت ذخیره شد", true);
                    } else {
                        toast.showToast("ذخیره اطلاعات انجام نشد", false);
                    }
                });
            }
        });
    }


    private void createPaymentDialog() {
        Dialog paymentDialog = new Dialog(this);
        paymentDialog.setContentView(R.layout.payment_dialog_layout);
        EditText edtPayment = paymentDialog.findViewById(R.id.edt_payment);
        ImageView voice = paymentDialog.findViewById(R.id.btn_voice_payment);
        Button btnSave = paymentDialog.findViewById(R.id.btn_ok_payment);
        Button btnCancel = paymentDialog.findViewById(R.id.btn_cancel_payment);
        paymentDialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;

        getPriceFromServer.getPriceFromServer(visionTblName, dateVision);

        getPriceFromServer.setOnGetPriceFromServerListener(() -> {

            getPriceFromServerResult = getPriceFromServer.getResult();

            netReceive = getPriceFromServerResult.get(0);
            netPayment = getPriceFromServerResult.get(1);
            netProfit = getPriceFromServerResult.get(2);
            netLeftover = getPriceFromServerResult.get(3);
            netIsTick = getPriceFromServerResult.get(4);

        });

        paymentDialog.create();
        paymentDialog.show();

        voice.setOnClickListener(view -> {
            toast.showToast("امکان ثبت صوتی مبلغ به زودی به برنامه اضافه خواهد شد", false);
        });

        btnCancel.setOnClickListener(view -> paymentDialog.dismiss());

        btnSave.setOnClickListener(view -> {
            Log.e("leftover payment", netLeftover);
            strMoney = edtPayment.getText().toString().trim();

            if (TextUtils.isEmpty(strMoney)) {
                toast.showToast(getResources().getString(R.string.moneyError), false);

            } else {
                int intMoney = Integer.parseInt(strMoney);
                stringToInteger();

                newReceive = intReceive;
                newPayment = intPayment + intMoney;
                newProfit = newReceive - newPayment;
                newLeftover = intLeftover + intMoney;

                if (intLeftover <= 0 && newLeftover > 0) {
                    imgTick.setVisibility(View.INVISIBLE);
                    imgTick.startAnimation(tickOut);

                    setTickVision.setTickVision(visionTblName, dateVision, userTbl, selectedVision, "0");
                }

                setPriceInServer.setPriceInServer(visionTblName, dateVision, String.valueOf(newReceive), String.valueOf(newPayment), String.valueOf(newProfit), String.valueOf(newLeftover));

                setPriceInServer.setOnSetPriceInServerListener(() -> {
                    String result = setPriceInServer.getResult();

                    if (result.equals("success")) {
                        paymentDialog.dismiss();

                        getVisionInfo();
                        getVisionTableInfo();

                        strMoney = "";

                        toast.showToast("اطلاعات شما با موفقیت ذخیره شد", true);
                    } else {
                        toast.showToast("ذخیره اطلاعات انجام نشد", false);
                    }
                });
            }
        });
    }


    private void createAddVisionDialog() {
// ساخت دیالوگ افزودن هدف جدید
        Dialog addVisionDialog = new Dialog(this);
        addVisionDialog.setContentView(R.layout.add_vision_dialog_layout);
        EditText edtTitleVision = addVisionDialog.findViewById(R.id.edt_title_vision);
        EditText edtMoneyVision = addVisionDialog.findViewById(R.id.edt_money_vision);
        EditText edtDayVision = addVisionDialog.findViewById(R.id.edt_day_vision);
        Button btnSave = addVisionDialog.findViewById(R.id.btn_save_add_vision_dialog);
        Button btnCancel = addVisionDialog.findViewById(R.id.btn_cancel_add_vision_dialog);
        addVisionDialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;
        addVisionDialog.create();
        addVisionDialog.show();


        // نمایش کیبورد روی ادیت تکست با تاخیر 200 میلی ثانیه ای
        new Handler().postDelayed(() -> {
            edtTitleVision.requestFocus();
            UIUtil.showKeyboard(MainActivity.this, edtTitleVision);
        }, 200);

        // رویداد کلیک دکمه بازگشت دیالوگ افزودن هدف جدید
        btnCancel.setOnClickListener(view -> {
            addVisionDialog.dismiss();
            counterAddVisionDialog = 0;
        });

        // صفر کردن شمارنده هر بار که دیالوگ باز میشود
        counterAddVisionDialog = 0;


        // ست کردن متن دکمه ثبت هدف
        btnSave.setText(getResources().getString(R.string.next));


        // رویداد کلیک دکمه ثبت هدف دیالوگ افزودن هدف جدید
        btnSave.setOnClickListener(view -> {
            // چک کردن شمارنده دیالوگ جهت نمایش یا عدم نمایش ادیت تکست ها
            if (counterAddVisionDialog == 0) {
                btnSave.setText(getResources().getString(R.string.next));
                nameVision = edtTitleVision.getText().toString().trim();


                // چک کردن خالی بودن یا نبودن ادیت تکست عنوان هدف
                if (TextUtils.isEmpty(nameVision)) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.titleVisionError), Toast.LENGTH_SHORT).show();

                } else {
                    counterAddVisionDialog++;
                    edtMoneyVision.setVisibility(View.VISIBLE);
                    edtMoneyVision.requestFocus();

                }


                // چک کردن شمارنده دیالوگ جهت نمایش یا عدم نمایش ادیت تکست ها
            } else if (counterAddVisionDialog == 1) {
                moneyVision = edtMoneyVision.getText().toString().trim();


                // چک کردن خالی بودن یا نبودن ادیت تکست مبلغ هدف
                if (TextUtils.isEmpty(moneyVision)) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.moneyVisionError), Toast.LENGTH_SHORT).show();

                } else {
                    btnSave.setText(getResources().getString(R.string.save_vision));
                    counterAddVisionDialog++;
                    edtDayVision.setVisibility(View.VISIBLE);
                    edtDayVision.requestFocus();

                }


                // چک کردن شمارنده دیالوگ جهت نمایش یا عدم نمایش ادیت تکست ها
            } else if (counterAddVisionDialog == 2) {
                dayVision = edtDayVision.getText().toString();


                // چک کردن خالی بودن یا نبودن ادیت تکست تعداد روز
                if (TextUtils.isEmpty(dayVision)) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.dayVisionError), Toast.LENGTH_SHORT).show();

                } else {

                    nameVision = edtTitleVision.getText().toString().trim();
                    moneyVision = edtMoneyVision.getText().toString().trim();
                    dayVision = edtDayVision.getText().toString().trim();

                    UIUtil.hideKeyboard(this);
                    addVisionDialog.dismiss();

                    new Handler().postDelayed(() -> {
                        loading.ShowDialog();

                        String userTable = ActSplash.shared.getString(UserItems.USER_TABLE_NAME, "");

                        // چک کردن وجود نام هدف در سرور
                        hasVision.hasVision(userTable, nameVision);


                        hasVision.setOnHasVisionListener(() -> {


                            // اگر هدف جدید در سرور وجود داشت ترو و در غیر این صورت فالس برمیگردد
                            String hasVisionResult = hasVision.getResult();

                            new Handler().postDelayed(() -> {

                                loading.dismissDialog();

                                if (hasVisionResult.equals("true")) {
                                    snack.create(getResources().getString(R.string.duplicateVision), getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.primaryUltraLightColor), getResources().getColor(R.color.accentLightColor));

                                } else if (hasVisionResult.equals("false")) {
                                    String userTableName = ActSplash.shared.getString(UserItems.USER_TABLE_NAME, "");

                                    getNumberNewVision.getNumberNewVision(userTableName);

                                    getNumberNewVision.setOnGetNumberNewVisionListener(() -> {

                                        numberNewVisionResult = getNumberNewVision.getResult();

                                        String visionTableName = ActSplash.shared.getString(UserItems.USERNAME, "") + "_" + numberNewVisionResult;

                                        int money = Integer.parseInt(moneyVision);
                                        int day = Integer.parseInt(dayVision);
                                        int oneDay = money / day;

                                        createVisionTable.createVisionTable(visionTableName, userTableName, dateVision, nameVision, moneyVision, dayVision, String.valueOf(oneDay));

                                        createVisionTable.setOnCreateVisionTableListener(() -> {
                                            String result = createVisionTable.getResult();

                                            if (result.equals("success")) {
                                                toast.showToast("اطلاعات با موفقیت ذخیره شد", true);
                                            } else if (result.equals("failed")) {
                                                toast.showToast("ذخیره اطلاعات انجام نشد", false);
                                            }
                                        });

                                    });
                                }
                            }, 1600);
                        });
                    }, 300);
                }
            }
        });
    }


    @SuppressLint("NotifyDataSetChanged")
    private void createSelectVisionDialog() {
        nameVisionResult.clear();
        isTickResult.clear();


        // ساخت دیالوگ انتخاب هدف
        Dialog selectVisionDialog = new Dialog(this);
        selectVisionDialog.setContentView(R.layout.select_vision_dialog_layout);
        TextView tvSelectedItem = selectVisionDialog.findViewById(R.id.tv_selected_vision_vision_dialog);
        RecyclerView rv = selectVisionDialog.findViewById(R.id.rv_select_vision_dialog);
        Button btnOk = selectVisionDialog.findViewById(R.id.btn_ok_select_vision_dialog);
        Button btnCancel = selectVisionDialog.findViewById(R.id.btn_cancel_select_vision_dialog);
        selectVisionDialog.getWindow().getAttributes().windowAnimations = R.style.animDialog;
        selectVisionDialog.create();
        selectVisionDialog.show();


        // مقدار دهی اولیه ریسایکلرویو
        rvItems = new ArrayList<>();
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(selectVisionDialog.getContext()));
        adapter = new SelectVisionAdapter(selectVisionDialog.getContext(), rvItems);
        rv.setAdapter(adapter);


        // ارسال اطالاعات به سرور جهت گرفتن و نمایش تمام هدف های کاربر
        userTbl = ActSplash.shared.getString(UserItems.USER_TABLE_NAME, "");
        getItemVisions.getItemVision(userTbl);

        getItemVisions.setOnGetItemVisions(() -> {

            nameVisionResult = getItemVisions.getNameVisionsResult();
            isTickResult = getItemVisions.getIsTickResult();

            for (int i = 1; i < nameVisionResult.size(); i++) {
                rvItems.add(new RvItemsSelectVision(nameVisionResult.get(i), isTickResult.get(i)));
            }
            adapter.notifyDataSetChanged();
        });


        // رویداد کلیک دکمه بازگشت
        btnCancel.setOnClickListener(view -> selectVisionDialog.dismiss());


        // رویداد کلیک آیتم های ریسایکلرویو
        rv.addOnItemTouchListener(new RecyclerItemClick(getBaseContext(), (view, position) -> {
            tvSelectedItem.setText(nameVisionResult.get(position + 1));
            recyclerClickItem = tvSelectedItem.getText().toString();
        }));


        // رویداد کلیک دکمه انتخاب
        btnOk.setOnClickListener(view -> {
            selectedVision = recyclerClickItem;
            recyclerClickItem = "";

            if (TextUtils.isEmpty(selectedVision)) {
                toast.showToast(getResources().getString(R.string.selectVisionText), false);

            } else {
                selectVisionDialog.dismiss();
                ActSplash.editor.putString(UserItems.SELECTED_VISION, selectedVision).apply();
                tvToolbar.setText(selectedVision);

                getVisionInfo();
            }
        });
    }


    private void getVisionInfo() {
        getVisionTableName.getVisionTableName(userTbl, selectedVision);

        getVisionTableName.setOnGetVisionTableNameListener(() -> {
            String result = getVisionTableName.getResult();
            int intResult = Integer.parseInt(result);
            intResult--;
            visionTblName = ActSplash.shared.getString(UserItems.USERNAME, "") + "_" + intResult;

            ActSplash.editor.putString(UserItems.VISION_TABLE_NAME, visionTblName).apply();

            getVisionTableInfo();
        });
    }


    private void getVisionTableInfo() {

        loading.ShowDialog();

        getVisionInfo.getVisionInfo(visionTblName, dateVision);

        getVisionInfo.setOnGetVisionInfoListener(() -> {
            getVisionInfoResult = getVisionInfo.getResult();

            netMoneyVision = getVisionInfoResult.get(0);
            netDayVision = getVisionInfoResult.get(1);
            netOneDayVision = getVisionInfoResult.get(2);
            netReceive = getVisionInfoResult.get(3);
            netPayment = getVisionInfoResult.get(4);
            netProfit = getVisionInfoResult.get(5);
            netLeftover = getVisionInfoResult.get(6);
            netSs = getVisionInfoResult.get(7);
            netMm = getVisionInfoResult.get(8);
            netHh = getVisionInfoResult.get(9);
            netIsTick = getVisionInfoResult.get(10);

            new Handler().postDelayed(() -> {

                if (netIsTick.equals("0")) {
                    enableLayout.setVisibility(View.VISIBLE);
                    disableLayout.setVisibility(View.GONE);
                    imgTick.setVisibility(View.INVISIBLE);
                } else {
                    enableLayout.setVisibility(View.GONE);
                    disableLayout.setVisibility(View.VISIBLE);
                    imgTick.setVisibility(View.VISIBLE);
                }

                stringToInteger();

                setColorTableInfo();

                integerToString();

                setNegativeTextTable();

                setTextTableInfo();

                S = Integer.parseInt(netSs);
                M = Integer.parseInt(netMm);
                H = Integer.parseInt(netHh);

                setTvTimerText();
                loading.dismissDialog();
            }, 1000);

        });
    }


    private void stringToInteger() {

        intReceive = Integer.parseInt(netReceive);
        intPayment = Integer.parseInt(netPayment);
        intProfit = Integer.parseInt(netProfit);
        intLeftover = Integer.parseInt(netLeftover);
    }


    // متد جداسازی سه رقم سه رقم عدد
    private static String splitDigits(int number) {
        return new DecimalFormat("###,###,###,###,###,###,###,###,###").format(number);
    }


    // متد تبدیل عدد به متن
    private void integerToString() {
        strReceive = FaNum.convert(splitDigits(intReceive));
        strPayment = FaNum.convert(splitDigits(intPayment));
        strProfit = FaNum.convert(splitDigits(intProfit));
        strLeftover = FaNum.convert(splitDigits(intLeftover));
    }


    private void setColorTableInfo() {

        tvTitleReceive.setTextColor(intReceive == 0 ? getResources().getColor(R.color.primaryColor) : getResources().getColor(R.color.accentColor));
        tvTextReceive.setTextColor(intReceive == 0 ? getResources().getColor(R.color.primaryColor) : getResources().getColor(R.color.accentColor));

        tvTitlePayment.setTextColor(intPayment == 0 ? getResources().getColor(R.color.accentColor) : getResources().getColor(R.color.primaryColor));
        tvTextPayment.setTextColor(intPayment == 0 ? getResources().getColor(R.color.accentColor) : getResources().getColor(R.color.primaryColor));

        tvTitleProfit.setTextColor(intProfit <= 0 ? getResources().getColor(R.color.primaryColor) : getResources().getColor(R.color.accentColor));
        tvTextProfit.setTextColor(intProfit <= 0 ? getResources().getColor(R.color.primaryColor) : getResources().getColor(R.color.accentColor));

        tvTitleLeftover.setTextColor(intLeftover <= 0 ? getResources().getColor(R.color.accentColor) : getResources().getColor(R.color.primaryColor));
        tvTextLeftover.setTextColor(intLeftover <= 0 ? getResources().getColor(R.color.accentColor) : getResources().getColor(R.color.primaryColor));
    }


    @SuppressLint("SetTextI18n")
    private void setNegativeTextTable() {
        if (intLeftover < 0) {
            negativeLeftover = Math.abs(intLeftover);
            tvTextLeftover.setText(FaNum.convert(splitDigits(negativeLeftover) + "   ریال"));
            tvTitleLeftover.setText("مازاد");
        } else {
            tvTextLeftover.setText(strLeftover + "   ریال");
            tvTitleLeftover.setText("باقیمانده");
        }

        if (intProfit < 0) {
            negativeProfit = Math.abs(intProfit);
            tvTextProfit.setText(FaNum.convert(splitDigits(negativeProfit) + "   ریال"));
            tvTitleProfit.setText("زیان");
        } else {
            tvTextProfit.setText(strProfit + "   ریال");
            tvTitleProfit.setText("سود");
        }
    }


    @SuppressLint("SetTextI18n")
    private void setTextTableInfo() {
        tvTextReceive.setText(strReceive + "   ریال");
        tvTextPayment.setText(strPayment + "   ریال");
    }


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
            dateVision = ChangeDate.getCurrentDay() + " / " + ChangeDate.getCurrentMonth() + " / " + ChangeDate.getCurrentYear();
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
        menu = findViewById(R.id.menu);
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

        tickIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_tick);
        tickOut = AnimationUtils.loadAnimation(this, R.anim.fade_out_tick);

        tvTitleReceive = findViewById(R.id.tv_title_receive);
        tvTitlePayment = findViewById(R.id.tv_title_payment);
        tvTitleProfit = findViewById(R.id.tv_title_profit);
        tvTitleLeftover = findViewById(R.id.tv_title_leftover);

        tvTextReceive = findViewById(R.id.tv_text_receive);
        tvTextPayment = findViewById(R.id.tv_text_payment);
        tvTextProfit = findViewById(R.id.tv_text_profit);
        tvTextLeftover = findViewById(R.id.tv_text_leftover);

        btnStartTimerService = findViewById(R.id.timer_on_off);

        nameVisionResult = new ArrayList<>();
        isTickResult = new ArrayList<>();

        startTimer = new Intent(MainActivity.this, TimerService.class);

        FloatingActionMenu menu = findViewById(R.id.menu);
        menu.setIconAnimated(true);

        hasVision = new HasVision();

        loading = new LoadingDialog(this);

        snackLayout = findViewById(R.id.snack_layout);

        snack = new SnackBar(this, snackLayout);

        createVisionTable = new CreateVisionTable();

        toast = new ToastBox(this);

        getNumberNewVision = new GetNumberNewVision();

        getItemVisions = new GetItemVisions();

        tvToolbar = findViewById(R.id.tv_toolbar);

        imgTick = tableView.findViewById(R.id.img_tick);

        getVisionTableName = new GetVisionTableName();

        getVisionInfo = new GetVisionInfo();

        getVisionInfoResult = new ArrayList<>();

        btnReceive = findViewById(R.id.btn_receive_id);

        btnPayment = findViewById(R.id.btn_payment_id);

        getPriceFromServer = new GetPriceFromServer();

        setPriceInServer = new SetPriceInServer();

        setTimerNumInServer = new SetTimerNumInServer();

        getTimerNumFromServer = new GetTimerNumFromServer();

        setTickVision = new SetTickVision();

        disableLayout = findViewById(R.id.disableLayout);

        enableLayout = findViewById(R.id.enableLayout);
    }


    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else if (menu.isOpened()) {
            menu.close(true);
        } else {
            super.onBackPressed();
        }
    }
}