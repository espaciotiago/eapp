package com.ufo.mobile.eapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufo.mobile.eapp.R;
import java.util.List;

import ModelManager.Area;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder>{

    private List<Area> areas;
    private AreaSelectedCallback callback;

    public AreaAdapter(List<Area> areas, AreaSelectedCallback areaSelectedCallback) {
        this.areas = areas;
        this.callback = areaSelectedCallback;
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.area_row, viewGroup, false);

        // Return a new holder instance
        AreaViewHolder viewHolder = new AreaViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder areaViewHolder, int i) {
        final Area area = areas.get(i);

        areaViewHolder.txtName.setText(area.getName());

        areaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.AreaSelectedCallback(area);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class AreaViewHolder extends RecyclerView.ViewHolder {

       private Context mContext;
       private TextView txtName;

       public AreaViewHolder(View itemView) {
           super(itemView);
           mContext = itemView.getContext();
           txtName = (TextView) itemView.findViewById(R.id.txt_name);
       }
    }
}
