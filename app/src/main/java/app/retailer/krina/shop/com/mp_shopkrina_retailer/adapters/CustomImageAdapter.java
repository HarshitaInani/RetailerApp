package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.ActivitiesActivity;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;
import app.retailer.krina.shop.com.mp_shopkrina_retailer.bean.Activitiespojo;

/**
 * Created by Zeta Apponomics 3 on 25-06-2015.
 */
public class CustomImageAdapter extends BaseAdapter {

    List<Activitiespojo> _data;
    private List<Activitiespojo> knowledgePojoList = null;
    private ArrayList<Activitiespojo> arraylist;

    Context _c;
    ViewHolder v;


    public CustomImageAdapter(Context context, List<Activitiespojo> personNamesList) {
        _c = context;
        this.knowledgePojoList = personNamesList;
        this.arraylist = new ArrayList<Activitiespojo>();
        this.arraylist.addAll(personNamesList);
    }
   @Override
    public int getCount() {
        return knowledgePojoList.size();
    }
    @Override
    public Object getItem(int position) {
        return knowledgePojoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.parcel_images, null);
        } else {
            view = convertView;
        }

        v = new ViewHolder();
        v.name = (TextView) view.findViewById(R.id.document);
        v.imageView = (ImageView) view.findViewById(R.id.imgPrv);
        v.browse = (Button) view.findViewById(R.id.browse);

        // Set data in listView
        final Activitiespojo dataSet = (Activitiespojo) knowledgePojoList.get(position);
        dataSet.setListItemPosition(position);
        System.out.println("imageshow:::"+dataSet.getImage());
        v.imageView.setImageBitmap(dataSet.getImage());
        if (!dataSet.isHaveImage()) {
          // Bitmap icon = BitmapFactory.decodeResource(_c.getResources(), R.mipmap.ic_launcher);
         // v.imageView.setImageBitmap(icon);
        } else {

          }
        v.name.setText(dataSet.getDocumentName());
        v.browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call parent method of activity to click image
                ((ActivitiesActivity) _c).captureImage(dataSet.getListItemPosition(), dataSet.getDocumentName() );
            }
        });

        return view;
    }

    /**
     * @param position Get position of of object
     * @param imageSrc set image in imageView
     */
    public void setImageInItem(int position, Bitmap imageSrc, String imagePath) {
        System.out.println("imageSrc:::"+imageSrc);
        Activitiespojo dataSet = (Activitiespojo) knowledgePojoList.get(position);
        dataSet.setImage(imageSrc);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imageView;
        TextView name;
     Button browse;
    }

}
