package ir.mahchegroup.vision;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import ir.mahchegroup.vision.R;

public class DReceiveView extends LinearLayout {
    View rootView;

    public DReceiveView(Context context) {
        super(context);
        init(context);
    }

    public DReceiveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        rootView = inflate(context, R.layout.btn_d_receive, this);
    }
}
