package ir.mahchegroup.vision.message_box;

import android.app.Dialog;
import android.content.Context;

import ir.mahchegroup.vision.R;

public class LoadingDialog {
    private Context ctx;
    private Dialog dialog;


    public LoadingDialog(Context ctx) {
        this.ctx = ctx;
    }


    public void ShowDialog() {
        dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.setCancelable(false);
        dialog.create();
        dialog.show();
    }


    public void dismissDialog() {
        dialog.dismiss();
    }
}
