package com.ufo.mobile.eapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.mobile.eapp.NewOrderActivity;
import com.ufo.mobile.eapp.R;

import java.util.List;

import ModelManager.Item;
import Utils.Constants;

public class ItemAdapter extends
        RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<Item> items;

    public ItemAdapter(List<Item> items) {
        this.items = items;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_row, viewGroup, false);

        // Return a new holder instance
        ItemViewHolder viewHolder = new ItemViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int i) {
        final Item item = items.get(i);
        itemViewHolder.txtReference.setText(item.getReference());
        itemViewHolder.txtName.setText(item.getName());
        itemViewHolder.txtStock.setText(item.getStock()+"");

        if (item.getImagePath() != null && !item.getImagePath().isEmpty()) {
            Bitmap imageOfItem = Constants.loadImageFromStorage(itemViewHolder.mContext, item.getImagePath());
            itemViewHolder.imgItem.setImageBitmap(imageOfItem);
        }


        itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Validar si hay disponibilidad de cantidad cada x días
                //TODO: si no verificar la próxima fecha en la que va a estar disponible
                Intent intent = new Intent(itemViewHolder.mContext,NewOrderActivity.class);
                intent.putExtra("itemId",item.getId());
                itemViewHolder.mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView txtName,txtReference,txtStock;
        private ImageView imgItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            txtName = (TextView) itemView.findViewById(R.id.txt_name);
            txtReference = (TextView) itemView.findViewById(R.id.txt_ref);
            txtStock = (TextView) itemView.findViewById(R.id.txt_stock);
            imgItem = (ImageView) itemView.findViewById(R.id.img_item);
        }
    }
}
