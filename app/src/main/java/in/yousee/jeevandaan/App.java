package in.yousee.jeevandaan;

import android.app.Application;
import android.content.Context;

/**
 * Created by mittu on 29-05-2016.
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {

        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext()
    {

        return context;
    }
}
