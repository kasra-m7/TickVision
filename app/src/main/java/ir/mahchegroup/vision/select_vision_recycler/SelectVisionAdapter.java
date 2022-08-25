package ir.mahchegroup.vision.select_vision_recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.mahchegroup.vision.R;

public class SelectVisionAdapter extends RecyclerView.Adapter<SelectVisionAdapter.myViewHolder> {
    Context ctx;
    ArrayList<RvItemsSelectVision> rvItems;
    LayoutInflater inflater;

    public SelectVisionAdapter(Context ctx, ArrayList<RvItemsSelectVision> rvItems) {
        this.ctx = ctx;
        this.rvItems = rvItems;
        inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    @SuppressLint("InflateParams")
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.select_vision_rv_item, null, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        RvItemsSelectVision items = rvItems.get(position);

        holder.tvVisionName.setText(items.getVisionName());

        holder.imgIsTickVision.setImageResource(items.getIsTickVision().equals("0") ? R.drawable.cancel_rv : R.drawable.ok_rv);
    }

    @Override
    public int getItemCount() {
        return rvItems.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvVisionName;
        ImageView imgIsTickVision;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVisionName = itemView.findViewById(R.id.tv_vision_name_show_visions);
            imgIsTickVision = itemView.findViewById(R.id.img_is_tick_vision_show_visions);

        }
    }

}
