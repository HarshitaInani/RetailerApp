package app.retailer.krina.shop.com.mp_shopkrina_retailer;

/**
 * Created by Krishna on 12/24/2016.
 */

public class Constant {
        public static String Tag = " ";

    //public static String BASE_URL = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/";
   //public static String BASE_URL_Images = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/";
    //Live 2 latest
//
//    public static String BASE_URL = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/";
//    public static String BASE_URL_Images = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/";




    //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com:808/
    //http://shopkiranamarketplace.moreyeahs.net


//    public static String BASE_URL = "http://shopkiranamarketplace.moreyeahs.net/api/";
//    public static String BASE_URL_Images = "http://shopkiranamarketplace.moreyeahs.net/";

    //public static String BASE_URL = "http://ec2-54-200-35-172.us-west-2.compute.amazonaws.com/api/";   27-dec-2017 ka h
  public static String BASE_URL = "http://137.59.52.130/api/";//live URL
          public static String BASE_URL_Slider = "http://137.59.52.130/";
          public static String BASE_URL_Slider_API= "http://137.59.52.130/api/Slider";
//public static String BASE_URL = "http://137.59.52.130:8080/api/";//test url
          public static String BASE_URL_Images = "http://137.59.52.130/";
          public static String BASE_URL_Images1 = "https://s3.ap-south-1.amazonaws.com/fsaapi/imageupload/";
          public static final String BASE_SLIDER_IMAGE = "http://137.59.52.130/api/Sliderimages/";
          public static final String UPLOAD_URL = BASE_URL+"imageupload";
   // http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com:808/
         public static String BASE_URL_SUMMARY= BASE_URL + "/target/Report?";
    public static String BASE_URL_DOCUMENT= "http://137.59.52.130/api/Documents/GetDocument";
//    Testing
//    public static String BASE_URL_Images = "http://SKDeliveryApp.moreyeahs.net/";
//    public static String BASE_URL = "http://SKDeliveryApp.moreyeahs.net/api/";
//    Testing
//
//    public static String BASE_URL_Images = "http://shopkiranatesting.moreyeahs.net/";
//    public static String BASE_URL = "http://shopkiranatesting.moreyeahs.net/api/";


    public static String BASE_URL_REWARD_ITEMS= BASE_URL + "RewardItem";
    public static String BASE_URL_APP_VERSION = BASE_URL + "appVersion";
    public static String BASE_URL_LOGIN = BASE_URL + "signup";
    public static String BASE_URL_REQUEST_BRAND = BASE_URL + "request";
    public static String BASE_URL_FEEDBACK = BASE_URL + "feedback";
    public static String BASE_URL_SIGNUP = BASE_URL + "signup?type=ids";
    public static String BASE_URL_SIGNUP_UPDATE= BASE_URL + "signup";


   // public static String BASE_URL_ITEM_MASTER = BASE_URL + "itemMaster";
    public static String BASE_URL_ITEM_SEARCH = BASE_URL + "itemMaster";;
    public static String BASE_URL_ITEM_MASTER = BASE_URL + "itemMaster/category";
    public static String BASE_URL_SHOPBYCMPNY = BASE_URL + "itemMaster/GetItemBySubCatId";
    public static String BASE_URL_BRAND_PROMOTION = BASE_URL + "BrandPramotions";
    public static String BASE_URL_EXECLUSIVE_BRAND = BASE_URL + "BrandPramotions/Exclusivebrand";
    public static String BASE_URL_BRAND_WISE_PROMOTION = BASE_URL + "Brandwisepramotion";
    public static String BASE_URL_ALL_TOP_ADDED_ITEM = BASE_URL + "AppPromotion/AllTopAddedItem";

    public static String BASE_URL_APP_PROMOTION = BASE_URL + "AppPromotion";
    public static String BASE_URL_TODAY_DHAMAKA = BASE_URL + "AppPromotion/GetTodayDhamaka";
    public static String BASE_URL_NEWLY_ADDED_BRANDS = BASE_URL + "AppPromotion/NewlyAddedBrands";
    public static String BASE_URL_MOST_SELLED_ITEM = BASE_URL + "AppPromotion/MostSelledItem";
    public static String BASE_URL_FIND_ITEM_HIGH_DP = BASE_URL + "itemMaster/FindItemHighDP";
    public static String BASE_URL_EMPTY_STOCK = BASE_URL + "CurrentStock/GetEmptyStockItem";
    public static String BASE_URL_BULK_ITEM = BASE_URL + "AppPromotion/GetBulkItem";
    public static String BASE_URL_ITEM_LIST = BASE_URL + "ssitem";
    public static String BASE_URL_NEWLY_ADDED_BRAND_ITEM = BASE_URL + "ssitem";
    public static String BASE_URL_PLACE_ORDER = BASE_URL + "OrderMastersAPI";
    public static String BASE_URL_MY_ORDERS = BASE_URL + "Orders";
    public static String BASE_URL_MY_WALLET = BASE_URL + "wallet";
   // public static String BASE_URL_OFFER_ITEM = BASE_URL + "offer/GetAllOfferItem";
    public static String BASE_URL_OFFER_ITEM = BASE_URL + "offer/GetAllOffers";

    public static String BASE_URL_NOTIFICATION = BASE_URL + "Notification/getall";
    public static String BASE_URL_FORGETPASS = BASE_URL + "Customers/Forgrt";
    public static String BASE_URL_NEWS_FEED= BASE_URL + "NewsApi";
    public static String BASE_URL_REQUEST_SERVICE= BASE_URL + "ReqService";
    public static String BASE_URL_MY_FAV= BASE_URL + "Customers/favourite";
    public static String BASE_URL_MY_GULLAK = BASE_URL + "Gullak/gethistoryLastTen";
    public static String BASE_URL_GULLAK = BASE_URL + "Gullak/gethistoryByDate";


    public static String ShopBY_PREF_OBJ="shopbycompanyobj";
    public static String ShopBY_PREF="shopbycompany";
    public static String HOMECARE_ITEM_PREFOBJ = "homecareitemprefobj";
    public static String HOMECARE_ITEM_PREF = "homecareitempref";
    public static String PERSONAL_ITEM_PREFOBJ = "personalitemprefobj";
    public static String PERSONAL_ITEM_PREF = "personalitem";
 public static String GROCERY_ITEM_PREFOBJ = "groceryitemprefobj";
 public static String GROCERY_ITEM_PREF = "groceryitempref";
    public static String RETAILER_BEAN_PREF = "retailerBeanPref";
    public static String RETAILER_BEAN_PREF_OBJ = "retailerBeanPrefObj";

    public static String BASECAT_CAT_SUBCAT_PREF = "BaseCatSubCatPref";
    public static String BASECAT_CAT_SUBCAT_PREFOBJ = "BaseCatSubCatPrefObj";

    public static String POPULAR_BRANDS_PREF = "PopularBrandsPref";
    public static String POPULAR_BRANDS_PREFOBJ = "PopularBrandsPrefObj";
    public static String POPULAR_BRANDS_PREFOBJ1 = "PopularBrandsPrefObj1";
    public static String POPULAR_BRANDS_PREFOBJ2 = "PopularBrandsPrefObj2";
    public static String APP_PROMOTION_PREFOBJ = "AppPromotionPrefObj";
    public static String TODAY_DHAMAKA_PREFOBJ = "TodayDhamakaPrefObj";
    public static String NEWLY_ADDED_BRANDS_PREFOBJ = "NewlyAddedBrandsPrefObj";
    public static String ALL_TOP_ADDED_PREFOBJ = "AllTOPAddedPrefObj";
    public static String BULK_ITEM_PREFOBJ = "BulkItemPrefObj";
    public static String MOST_SELLED_ITEM_PREFOBJ = "MostSelledItemPrefObj";
    public static String EMPTY_STOCK_PREFOBJ = "EmptyStockPrefObj";
    public static String FIND_HIGH_DP_PREFOBJ = "FindHighDpPrefObj";
    public static String EXECLUSIVE_BRANDS_PREFOBJ = "ExecutiveBrandsPrefObj";
    public static String POPULAR_BRANDS_PREF1 = "PopularBrandsPref1";
    public static String POPULAR_BRANDS_PREF2 = "PopularBrandsPref2";
    public static String ALL_BRANDS_PREF =        "AllBrandPref";
    public static String APP_PROMOTION_PREF = "AppPromotionPref";
    public static String TODAY_DHAMAKA_PREF = "TodayDhamakaPref";
    public static String NEWLY_ADDED_BRANDS_PREF = "NewlyAddedBrandsPref";
    public static String EXECLUSIVE_BRANDS_PREF = "ExeclusiveBrandsPref";
    public static String ALL_TOP_ADDED_PREF = "AllTOPAddedPref";
    public static String BULK_ITEM_PREF = "BulkItemPref";
    public static String MOST_SELLED_ITEM_PREF = "MostSelledItemPref";
    public static String EMPTY_STOCK_PREF = "EmptyStockPref";
    public static String FIND_HIGH_DP_PREF = "FindHighDpPref";
    public static String SUB_SUB_CAT_ITEM_PREF = "SubSubCatItemPref";
    //public static String SUB_SUB_CAT_ITEM_PREFOBJ = "SubSubCatItemPrefObj";
    public static String CART_ITEM_ARRAYLIST_PREF = "CartItemArraylistPref";
    public static String CART_ITEM_ARRAYLIST_PREF_OBJ = "CartItemArraylistPrefObj";
    public static String OFFER_SELLED_ITEM_PREF = "OfferSelledItemPref";
    public static String OFFER_SELLED_ITEM_PREFOBJ = "OfferSelledItemPrefObj";
    public static String OFFER_ALL_ITEM_PREF = "OfferAllItemPref";
    public static String OFFER_ALL_ITEM_PREFOBJ = "OfferAllItemPrefObj";
    public static String MY_ORDER_PREF = "myOrderPref";
    public static String MY_ORDER_PREF_OBJ = "myOrderPrefObj";
    public static String APP_VERSION_PREF = "appVersionPref";
    public static String APP_VERSION_PREF_OBJ = "APP_VERSIONPrefObj";
    public static String FCM_KEY_PREF = "FCMKeyPref";
    public static String FCM_KEY_PREF_OBJ = "FCMKeyPrefObj";
    public static String BASE_URL_BUT_REWARD_ITEMS= BASE_URL + "OrderMastersAPI/dreamitem";
    public static String BASE_URL_CHEQUE_BOUNCE = BASE_URL + "SalesSettlement/SearchSkcode";
    public static String BASE_URL_SLIDER = BASE_URL_Slider + "Sliderimages/";
 public static String BASE_URL_SUB_SLIDER = BASE_URL + "CategoryImage/";

}
