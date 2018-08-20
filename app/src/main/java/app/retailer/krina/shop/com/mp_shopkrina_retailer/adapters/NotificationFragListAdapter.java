package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.NotificationListBean;

/**
 * Created by Krishna on 03-01-2017.
 */

public class NotificationFragListAdapter extends RecyclerView.Adapter<NotificationFragListAdapter.ViewHolder> {
    private ArrayList<NotificationListBean> notificationListArrayList;
    private Context context;

    public NotificationFragListAdapter(Context context, ArrayList<NotificationListBean> notificationListArrayList) {
        this.notificationListArrayList = notificationListArrayList;
        this.context = context;
    }

    @Override
    public NotificationFragListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_row, viewGroup, false);
        return new NotificationFragListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationFragListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.tvNotiTitle.setText(notificationListArrayList.get(i).getTitle());
        viewHolder.tvNotiMessage.setText(notificationListArrayList.get(i).getMessage());

        /*if (!TextUtils.isNullOrEmpty(notificationListArrayList.get(i).getImageUrl()))
            Picasso.with(context).load(Constant.BASE_URL_Images + "images/itemimages/" + itemListArrayList.get(i).getLogoUrl() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);*/


    }

    @Override
    public int getItemCount() {
        return notificationListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotiTitle;
        private TextView tvNotiMessage;

        public ViewHolder(View view) {
            super(view);
            tvNotiTitle = (TextView) view.findViewById(R.id.notification_title);
            tvNotiMessage = (TextView) view.findViewById(R.id.notification_message);

        }
    }
}
