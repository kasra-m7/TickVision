package ir.mahchegroup.vision.message_box;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;

import ir.mahchegroup.vision.R;

public class SnackBar {
    private CoordinatorLayout snackLayout;
    private Context ctx;

    public SnackBar(Context ctx, CoordinatorLayout snackLayout) {
        this.snackLayout = snackLayout;
        this.ctx = ctx;
    }

    public void create(String text, int colorBackground, int colorText, int colorAction) {
        Snackbar snackbar = Snackbar.make(snackLayout, text, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        snackbar.setAction("بسیار خب", view -> snackbar.dismiss());

        Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "font/iran_sans.ttf");
        Typeface tfb = Typeface.createFromAsset(ctx.getAssets(), "font/iran_sans_bold.ttf");

        View view = snackbar.getView();
        view.setBackgroundColor(colorBackground);

        TextView txt = view.findViewById(com.google.android.material.R.id.snackbar_text);
        txt.setTextSize(ctx.getResources().getDimension(R.dimen.snackTextSize));
        txt.setTypeface(tfb);
        txt.setTextColor(colorText);

        TextView action = view.findViewById(com.google.android.material.R.id.snackbar_action);
        action.setTextSize(ctx.getResources().getDimension(R.dimen.snackTextSize));
        action.setTypeface(tf);
        action.setTextColor(colorAction);
    }
}
