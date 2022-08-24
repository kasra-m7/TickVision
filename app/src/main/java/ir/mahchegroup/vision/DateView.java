package ir.mahchegroup.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DateView extends RelativeLayout {
    View rootView;
    TextView tvDay, tvDate, tvTime;
    LayoutInflater inflater;

    public DateView(Context context) {
        super(context);
        init(context);
    }

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint("InflateParams")
    private void init(Context context) {

        rootView = inflate(context, R.layout.date_view, this);
        tvDay = rootView.findViewById(R.id.tv_day);
        tvDate = rootView.findViewById(R.id.tv_date);
        tvTime = rootView.findViewById(R.id.tv_time);
    }
}
