package in.yousee.jeevandaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.yousee.jeevandaan.model.Donation;
import in.yousee.jeevandaan.util.LogUtil;

/**
 * Created by mittu on 29-05-2016.
 */
public class ListAdapter extends ArrayAdapter<Donation> {

    private Context context;
    ArrayList<Donation> donations;

    public ListAdapter(Context context, int resource, List<Donation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.donations = (ArrayList<Donation> ) objects;
    }
    public int getCount()
    {
        if(donations !=null)
        {
            return donations.size();
        }
        return 0;

    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.donation_row, parent, false);
        TextView location = (TextView) rowView.findViewById(R.id.donation_location_value);
        TextView date = (TextView) rowView.findViewById(R.id.date_value);
        TextView name = (TextView) rowView.findViewById(R.id.bloodbank_name_value);
        //TextView units = (TextView) rowView.findViewById(R.id.units_value);
        Donation donation = donations.get(position);
        if(donation.location != null)
        {
            location.setText(donation.location);
        }
        else
        {
            location.setText("--");
        }

        date.setText(donation.date);
        name.setText(donation.bloodBankName);
        LogUtil.print("rendering View "+ position);
        //units.setText(donation.unitsOfBlood);
        return rowView;
    }
    public Donation getItem(int position)
    {
        return donations.get(position);
    }
}
