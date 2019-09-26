package com.ufo.mobile.eapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.mobile.eapp.OrderDetailActivity;
import com.ufo.mobile.eapp.R;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.User;
import ModelManager.UserDao;
import Utils.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends
        RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<Order> orders;
    private DaoSession daoSession;

    public OrderAdapter(List<Order> orders, DaoSession daoSession) {
        this.orders = orders;
        this.daoSession = daoSession;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.order_row, viewGroup, false);

        // Return a new holder instance
        OrderViewHolder viewHolder = new OrderViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, int i) {
        final Order order = orders.get(i);
        Item item = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id.eq(order.getItem())).unique();
        if(order.getOwner() != null && order.getOwner() >= 0) {
            User owner = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(order.getOwner())).unique();
            Bitmap bp = Constants.loadImageFromStorage(orderViewHolder.mContext, owner.getImage());
            if (bp != null) {
                orderViewHolder.imgOwner.setImageBitmap(bp);
            }
        }

        orderViewHolder.txtName.setText(item.getName() + "-" + item.getReference());
        orderViewHolder.txtNumber.setText(String.valueOf(order.getId()));
        orderViewHolder.txtDate.setText(Constants.formatDate(order.getCreationDate()));

        Bitmap bpItem = Constants.loadImageFromStorage(orderViewHolder.mContext, item.getImagePath());
        if (bpItem != null) {
            orderViewHolder.imgItem.setImageBitmap(bpItem);
        }

        orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderViewHolder.mContext, OrderDetailActivity.class);
                intent.putExtra("orderId", order.getId());
                orderViewHolder.mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView txtName,txtNumber,txtDate;
        private CircleImageView imgOwner, imgItem;
        private View statusView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            txtName = (TextView) itemView.findViewById(R.id.txt_name_reference);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_order_num);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date);
            imgOwner = (CircleImageView) itemView.findViewById(R.id.img_owner);
            imgItem = (CircleImageView) itemView.findViewById(R.id.img_item);
            statusView = (View) itemView.findViewById(R.id.status_view);
        }
    }
}
