package in.yousee.jeevandaan;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by mittu on 29-05-2016.
 */
public class App extends MultiDexApplication {
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
