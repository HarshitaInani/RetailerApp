package app.retailer.krina.shop.com.mp_shopkrina_retailer.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.retailer.krina.shop.com.mp_shopkrina_retailer.R;

/**
 * Created by user on 4/7/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private JSONArray jsonArrayQ;

    private Context context;
    int count;

    ArrayList<String> itemNameList = new ArrayList<>();
    ArrayList<String> itemDesList = new ArrayList<>();

    ArrayList<String> itemIdList = new ArrayList<>();

    ArrayList<String> itemPointList = new ArrayList<>();


    ArrayList<String> itemImageUrl = new ArrayList<>();

    ArrayList<String> isActiveList = new ArrayList<>();
    ArrayList<String> isDeletedList = new ArrayList<>();

    ArrayList<Integer> quantityList = new ArrayList<>();

    ArrayList<String> newsImageList = new ArrayList<>();
    ArrayList<String> newsTitleList = new ArrayList<>();
    ArrayList<String>  newsDesList = new ArrayList<>();


    ArrayList<Integer> jsonquantityList = new ArrayList<>();


//    LinkedList<Integer> quantityList = new_added LinkedList<>();

    //JSONObject qJson = new_added JSONObject();







    JSONObject jsonObject = new JSONObject();

    JSONObject jsonObjectQ = new JSONObject();


    int quantityCount = 1;


    public NewsAdapter(Context context, JSONArray jsonArray, JSONArray jsonArrayQ, int count) {

        this.context = context;
        this.jsonArray = jsonArray;
        this.count = count;
        this.jsonArrayQ = jsonArrayQ;


    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_item_row, viewGroup, false);
        return new NewsAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final NewsAdapter.ViewHolder viewHolder, final int i) {

        //  Toast.makeText(context, "Adapter Array"+jsonArray.toString(), Toast.LENGTH_SHORT).show();


      //  viewHolder.setIsRecyclable(false);

        try {
            for (int j = 0; j < jsonArray.length(); j++) {

                jsonObject = jsonArray.getJSONObject(j);



                isActiveList.add(jsonObject.getString("IsAvailable"));
                isDeletedList.add(jsonObject.getString("IsDeleted"));

                newsImageList.add(jsonObject.getString("Image"));
                newsTitleList.add(jsonObject.getString("NewsName"));
                newsDesList.add(jsonObject.getString("Description"));




            }

        } catch (Exception e) {

        }


        if (isActiveList.get(i).equals("false")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);
            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
         //   viewHolder.currentLinearLayout.set

        }

        if (isDeletedList.get(i).equals("true")) {

            viewHolder.currentLinearLayout.setVisibility(View.GONE);

            viewHolder.currentLinearLayout.setMinimumHeight(0);

            viewHolder.cardView.setVisibility(View.GONE);
        }



        /*

        viewHolder.tvItemName.setText(itemNameList.get(i));
        viewHolder.tvDes.setText(itemDesList.get(i));

        viewHolder.tvPointValue.setText(itemPointList.get(i));


*/





        String temp = newsImageList.get(i).trim();
        temp = temp.replaceAll(" ", "%20");


//        Picasso.with(context).load(newsImageList.get(i)).placeholder(R.drawable.top_img_bg).error(R.drawable.top_img_bg).into(viewHolder.newsImage);


        Picasso.with(context).load(temp).placeholder(R.drawable.top_img_bg).error(R.drawable.top_img_bg).into(viewHolder.newsImage);


        viewHolder.tvNewsTitle.setText(newsTitleList.get(i));
        viewHolder.tvNewsDes.setText(newsDesList.get(i));
       // viewHolder.tvNewsTitle.setText(newsTitleList.get(i));


    }

    @Override
    public int getItemCount() {


//        return itemListArrayList.size();

        return count;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvNewsTitle, tvNewsDes;
        ImageView newsImage;
        Button buyBtn;

        ImageView plusBtn, minusBtn;

        LinearLayout currentLinearLayout;

        CardView cardView;
        public ViewHolder(View view) {
            super(view);

            tvNewsTitle= (TextView) view.findViewById(R.id.news_title);
            tvNewsDes= (TextView) view.findViewById(R.id.news_description);

            newsImage = (ImageView) view.findViewById(R.id.news_image);


            currentLinearLayout = (LinearLayout) view.findViewById(R.id.news_item_view);


            cardView= (CardView) view.findViewById(R.id.cardView);


        }



    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
