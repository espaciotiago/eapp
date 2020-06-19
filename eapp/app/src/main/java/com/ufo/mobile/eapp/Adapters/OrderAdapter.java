package com.ufo.mobile.eapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufo.mobile.eapp.MainActivity;
import com.ufo.mobile.eapp.OrderDetailActivity;
import com.ufo.mobile.eapp.OrdersActivity;
import com.ufo.mobile.eapp.R;

import java.util.Date;
import java.util.List;

import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.OrderDao;
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

    /**
     *
     * @param item
     * @return
     */
    private String calculateNextDate(Item item, Order order){
        String message = "";
        double qty = order.getQty();
        if(item.getNextAvailable()!=null){
            if(item.getNextAvailable().compareTo(new Date()) == 1){
                message = "No disponible hasta: " + Constants.formatDate(item.getNextAvailable());
            }else{
                double ordersUntilNow = calculateOrdersOfItem(item.getId(),item.getNextAvailable());
                double requested = ordersUntilNow + qty;
                if(requested <= item.getQtyInSpecificTime()) {
                    if((item.getQtyInSpecificTime() - requested) == 0) {
                        message = "No disponible hasta: " + Constants.formatDate(Constants.calculateDatePlusDays(item.getNextAvailable(), item.getEachInDays()));
                    }
                }else{
                    message = "La cantidad solicitada sobrepasó la cantidad permitida hasta: " + Constants.formatDate(item.getNextAvailable());
                }
            }
        }else{
            double ordersUntilNow = calculateOrdersOfItem(item.getId(),item.getNextAvailable());
            double requested = ordersUntilNow + qty;
            if(requested <= item.getQtyInSpecificTime()) {
                // Is the first time that a item is requested and get the qty permited
                if((item.getQtyInSpecificTime() - requested) == 0) {
                    item.setNextAvailable(Constants.calculateDatePlusDays(new Date(), item.getEachInDays()));
                    message = "No disponible hasta: " + Constants.formatDate(Constants.calculateDatePlusDays(new Date(), item.getEachInDays()));
                }
            }else{
                message = "La cantidad solicitada sobrepasó la cantidad permitida";
            }
        }
        return message;
    }

    /**
     *
     * @param itemId
     * @return
     */
    public double calculateOrdersOfItem(Long itemId, Date lastAvailable){
        double ordersCount = 0;
        List<Order> orders = daoSession.getOrderDao().queryBuilder().where(OrderDao.Properties.Item.eq(itemId)).list();
        for(int i = 0; i < orders.size(); i++){
            Order or = orders.get(i);
            if(lastAvailable != null) {
                if (or.getCreationDate().compareTo(lastAvailable) == 1) {
                    ordersCount += or.getQty();
                }
            }else{
                ordersCount += or.getQty();
            }
        }
        return ordersCount;
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
            if(owner != null) {
                Bitmap bp = Constants.loadImageFromStorage(orderViewHolder.mContext, owner.getImage());
                if (bp != null) {
                    orderViewHolder.imgOwner.setImageBitmap(bp);
                }
            }
        }

        if(item != null) {
            orderViewHolder.txtName.setText(item.getName() + "-" + item.getReference());
            Bitmap bpItem = Constants.loadImageFromStorage(orderViewHolder.mContext, item.getImagePath());
            if (bpItem != null) {
                orderViewHolder.imgItem.setImageBitmap(bpItem);
            }
        }else{
            orderViewHolder.txtName.setText("No se ha encontrado el item, pudo haber sido eliminado");
        }
        orderViewHolder.txtNumber.setText(String.valueOf(order.getId()));
        orderViewHolder.txtDate.setText(Constants.formatDate(order.getCreationDate()));
        orderViewHolder.txtDateAvailable.setText(calculateNextDate(item,order));


        orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(orderViewHolder.mContext, OrderDetailActivity.class);
                intent.putExtra("orderId", order.getId());
                orderViewHolder.mContext.startActivity(intent);
            }
        });

        orderViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(orderViewHolder.mContext);
                builder.setTitle("Cancelar orden")
                        .setMessage("¿Estas seguro que deseas cancelar esta orden? \nEsta acción eliminara la order del sistema");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((OrdersActivity)orderViewHolder.mContext).onDeleteOrder(order);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private TextView txtName,txtNumber,txtDate,txtDateAvailable;
        private CircleImageView imgOwner, imgItem;
        private ImageButton btnDelete;
        private View statusView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            txtName = (TextView) itemView.findViewById(R.id.txt_name_reference);
            txtNumber = (TextView) itemView.findViewById(R.id.txt_order_num);
            txtDateAvailable = (TextView) itemView.findViewById(R.id.txt_date_available);
            txtDate = (TextView) itemView.findViewById(R.id.txt_date);
            imgOwner = (CircleImageView) itemView.findViewById(R.id.img_owner);
            imgItem = (CircleImageView) itemView.findViewById(R.id.img_item);
            statusView = (View) itemView.findViewById(R.id.status_view);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);
        }
    }
}
