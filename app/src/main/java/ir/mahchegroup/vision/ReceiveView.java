package ir.mahchegroup.vision;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class ReceiveView extends LinearLayout {
    View rootView;

    public ReceiveView(Context context) {
        super(context);
        init(context);
    }

    public ReceiveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.btn_receive, this);
    }
}
