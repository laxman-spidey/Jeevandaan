package in.yousee.jeevandaan.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mittu on 23-05-2016.
 */
public class Donation implements JSONParsable {
    public String location;
    public String bloodBankName;
    //public int unitsOfBlood;
    public String date;

    public static final String TAG_LOCATION = "camp_name";
    public static final String TAG_BLOODBANK = "blood_bank";
    //public static final String TAG_UNITS_OF_BLOOD = "location";
    public static final String TAG_DATE = "donation_date";



    public Donation(String json)
    {
        try
        {
            JSONObject object = new JSONObject(json);
            parseJSON(object);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Donation(JSONObject json)
    {
        parseJSON(json);
    }

    public Donation()
    {

    }
    @Override
    public void parseJSON(JSONObject JSONObject) {
        try
        {
            this.date = JSONObject.getString(TAG_DATE);
            this.bloodBankName = JSONObject.getString(TAG_BLOODBANK);
            this.location = JSONObject.getString(TAG_LOCATION);
            //this.unitsOfBlood = JSONObject.getInt(TAG_UNITS_OF_BLOOD);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
