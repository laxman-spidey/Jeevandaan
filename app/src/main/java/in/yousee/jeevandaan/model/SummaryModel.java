package in.yousee.jeevandaan.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mittu on 23-05-2016.
 */
public class SummaryModel implements JSONParsable{
    public String donorName;
    public String bloodGroup;
    public String location;
    public ArrayList<Donation> donations = new ArrayList<>();

    public static final String TAG_DONOR_NAME = "donor_name";
    public static final String TAG_BLOOD_GROUP = "blood_group";
    public static final String TAG_LOCATION = "location";
    public static final String TAG_DONATIONS = "donations";

    public SummaryModel(String json)
    {

    }

    @Override
    public void parseJSON(JSONObject JSONObject) {
        JSONArray array;
        try
        {
            this.location = JSONObject.getString(TAG_LOCATION);
            this.bloodGroup= JSONObject.getString(TAG_BLOOD_GROUP);
            this.donorName = JSONObject.getString(TAG_DONOR_NAME);
            array = JSONObject.getJSONArray(TAG_DONATIONS);
            for(int i=0; i< array.length(); i++)
            {
                JSONObject item = (JSONObject) array.get(i);
                this.donations.add(new Donation(item));

            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
