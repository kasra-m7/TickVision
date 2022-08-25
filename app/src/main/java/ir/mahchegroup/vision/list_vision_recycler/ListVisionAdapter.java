package ir.mahchegroup.vision.list_vision_recycler;

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

public class ListVisionAdapter extends RecyclerView.Adapter<ListVisionAdapter.myViewHolder> {
    Context ctx;
    ArrayList<RvItemListVision> list;
    LayoutInflater inflater;

    public ListVisionAdapter(Context ctx, ArrayList<RvItemListVision> list) {
        this.ctx = ctx;
        this.list = list;

        inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.list_vision_rv_item, null, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        RvItemListVision rvItemListVision = list.get(position);

        holder.tvVisionName.setText(rvItemListVision.getNameVision());
        holder.imgMark.setImageResource(rvItemListVision.getMarkIcon() == 0 ? R.drawable.bookmark : R.drawable.un_bookmark);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tvVisionName;
        ImageView imgEdit, imgChart, imgDel, imgMark;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tvVisionName = itemView.findViewById(R.id.tv_vision_name_list_vision);
            imgEdit = itemView.findViewById(R.id.btn_edit);
            imgChart = itemView.findViewById(R.id.btn_chart);
            imgDel = itemView.findViewById(R.id.btn_delete);
            imgMark = itemView.findViewById(R.id.btn_mark);
        }
    }

}
