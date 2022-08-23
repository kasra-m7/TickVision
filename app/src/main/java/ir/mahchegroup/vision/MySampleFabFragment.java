package ir.mahchegroup.vision;

import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

public class MySampleFabFragment extends AAH_FabulousFragment {

    public static MySampleFabFragment newInstance() {
        MySampleFabFragment f = new MySampleFabFragment();
        return f;
    }

    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.menu_layout, null);
        RelativeLayout rl_content = contentView.findViewById(R.id.rl_content);
        LinearLayout ll_buttons = contentView.findViewById(R.id.ll_buttons);
        contentView.findViewById(R.id.btn).setOnClickListener(v -> closeFilter("closed"));

        //params to set
        setAnimationDuration(600); //optional; default 500ms
        setPeekHeight(300); // optional; default 400dp
        setCallbacks((Callbacks) getActivity()); //optional; to get back result
        setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(ll_buttons); // optional; layout to stick at bottom on slide
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog, style); //call super at last
    }

}