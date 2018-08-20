package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.ActivitiesActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.ShowUploadImageActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Activitiespojo;


public class ActivitiesAdapter extends BaseAdapter {


    Context mContext;

    LayoutInflater inflater;
    private List<Activitiespojo> knowledgePojoList = null;
    private ArrayList<Activitiespojo> arraylist;


    public ActivitiesAdapter(Context context, List<Activitiespojo> personNamesList) {
        mContext = context;
        this.knowledgePojoList = personNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Activitiespojo>();
        this.arraylist.addAll(personNamesList);
    }

    public class ViewHolder {
        TextView name;
        Button browse;
        ImageView upload_image;

    }

    @Override
    public int getCount() {
        return knowledgePojoList.size();
    }

    @Override
    public Activitiespojo getItem(int position) {
        return knowledgePojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.activities, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.document);
            holder.browse = (Button) view.findViewById(R.id.browse);
            holder.upload_image = (ImageView) view.findViewById(R.id.imgPrv);
            // Set data in listView
            final Activitiespojo dataSet = (Activitiespojo) knowledgePojoList.get(position);
            dataSet.setListItemPosition(position);
            if (!dataSet.isHaveImage()) {
                Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
                holder.upload_image.setImageBitmap(icon);
            } else {
                holder.upload_image.setImageBitmap(dataSet.getImage());
            }
            holder.browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ((ActivitiesActivity) mContext).captureImage(dataSet.getListItemPosition(), dataSet.getDocumentName() );


                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(knowledgePojoList.get(position).getDocumentName());
        return view;
    }
  public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        Activitiespojo dataSet = (Activitiespojo) knowledgePojoList.get(position);
        dataSet.setImage(imageSrc);
        dataSet.setHaveImage(true);
        notifyDataSetChanged();
    }

}