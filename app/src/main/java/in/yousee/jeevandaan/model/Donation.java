package in.yousee.jeevandaan.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mittu on 23-05-2016.
 */
public class Donation implements JSONParsable {
    public String location;
    public String bloodBankName;
    public int unitsOfBlood;
    public String date;

    public static final String TAG_LOCATION = "location";
    public static final String TAG_BLOODBANK_NAME = "location";
    public static final String TAG_UNITS_OF_BLOOD = "location";
    public static final String TAG_DATE = "location";



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

    @Override
    public void parseJSON(JSONObject JSONObject) {
        try
        {
            this.location = JSONObject.getString(TAG_LOCATION);
            this.bloodBankName = JSONObject.getString(TAG_BLOODBANK_NAME);
            this.unitsOfBlood = JSONObject.getInt(TAG_UNITS_OF_BLOOD);
            this.date = JSONObject.getString(TAG_DATE);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
