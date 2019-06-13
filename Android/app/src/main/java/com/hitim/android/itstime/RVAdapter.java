package com.hitim.android.itstime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.SphereViewHolder> {

    List<Sphere> spheres;
    Context context;
    ItemClickListener clickListener;


    public RVAdapter(List<Sphere> spheres, Context context) {
        this.spheres = spheres;
        this.context = context;
    }


    @Override
    public int getItemCount() {
        return spheres.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @NonNull
    @Override
    public SphereViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sphere_items_card, viewGroup, false);
        return new SphereViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull SphereViewHolder sphereViewHolder, int i) {
        sphereViewHolder.cl.setBackground(context.getDrawable(spheres.get(i).getDrawableId()));
        sphereViewHolder.sphereIcon.setImageResource(spheres.get(i).getIconId());
        switch (spheres.get(i).getName()) {
            case "All task's":
                sphereViewHolder.sphereName.setText(context.getString(R.string.all_tasks));
                break;
            case "Work":
                sphereViewHolder.sphereName.setText(context.getString(R.string.work));
                break;
            case "Routine":
                sphereViewHolder.sphereName.setText(context.getString(R.string.routine));
                break;
            case "Yourself":
                sphereViewHolder.sphereName.setText(context.getString(R.string.yourself));
                break;
            case "Health":
                sphereViewHolder.sphereName.setText(context.getString(R.string.health));
                break;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class SphereViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        ConstraintLayout cl;
        TextView sphereName;
        ImageView sphereIcon;

        SphereViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.sphere_fragment_card_view);
            cl = itemView.findViewById(R.id.sphere_fragment_constraint_layout);
            sphereName = itemView.findViewById(R.id.sphere_fragment_text_view);
            sphereIcon = itemView.findViewById(R.id.sphere_fragment_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
}


