package ir.mahchegroup.vision;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TableView extends RelativeLayout {
    View root;
    TextView tvTitleReceive, tvTitlePayment, tvTitleProfit, tvTitleLeftover, tvTextReceive, tvTextPayment, tvTextProfit, tvTextLeftover;

    public TableView(Context context) {
        super(context);

        init(context);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        root = inflate(context, R.layout.table_view, this);
        tvTitleReceive = root.findViewById(R.id.tv_title_receive);
        tvTitlePayment = root.findViewById(R.id.tv_title_payment);
        tvTitleProfit = root.findViewById(R.id.tv_title_profit);
        tvTitleLeftover = root.findViewById(R.id.tv_title_leftover);

        tvTextReceive = root.findViewById(R.id.tv_text_receive);
        tvTextPayment = root.findViewById(R.id.tv_text_payment);
        tvTextProfit = root.findViewById(R.id.tv_text_profit);
        tvTextLeftover = root.findViewById(R.id.tv_text_leftover);
    }
}
