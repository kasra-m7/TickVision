package ir.mahchegroup.vision.message_box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.mahchegroup.vision.R;

public class ToastBox {
    Context ctx;

    public ToastBox(Context ctx) {
        this.ctx = ctx;
    }

    @SuppressLint("InflateParams")
    public void showToast(String text, boolean isOk) {
        View view;
        TextView tv;
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (isOk) {
            view = inflater.inflate(R.layout.ok_toast, null);
            tv = view.findViewById(R.id.ok_toast);
        }else {
            view = inflater.inflate(R.layout.nok_toast, null);
            tv = view.findViewById(R.id.nok_toast);
        }
        tv.setText(text);

        Toast toast = new Toast(ctx);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 50);
        toast.setView(view);
        toast.show();
    }
}
