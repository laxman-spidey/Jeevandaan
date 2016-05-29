package in.yousee.jeevandaan;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mittu on 30-05-2016.
 */
public class FactsPagerAdapter extends PagerAdapter {
    private int[] image_resources = {R.drawable.pic,R.drawable.pic1,R.drawable.pic2,R.drawable.pic3};
    public String[] head = {"3 LIVES"," SAFE "," ENERGY ","ANYONE"};
    public String[] strings = {"450ml of blood can save as many as three lives.","Giving blood is 100% safe.","Blood donation never leads to weakness it moreover brings in new energy.",
            "A person can donate blood every 90 days."};
    private Context ctx;
    private android.view.LayoutInflater LayoutInflater;
    public FactsPagerAdapter(Context ctx)
    {
        this.ctx =  ctx;
    }
    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object ob) {
        return (view==(FrameLayout)ob);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = LayoutInflater.inflate(R.layout.layout_fact_slide,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        TextView headview = (TextView)item_view.findViewById(R.id.heading);
        TextView textview = (TextView)item_view.findViewById(R.id.textView2);
        imageView.setImageResource(image_resources[position]);

        headview.setText(" "+head[position]);
        textview.setText(" "+strings[position]);
        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }



}
