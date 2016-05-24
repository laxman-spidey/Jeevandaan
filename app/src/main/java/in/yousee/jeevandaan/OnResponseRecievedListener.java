package in.yousee.jeevandaan;

import android.content.Context;

public interface OnResponseRecievedListener
{
	public void onResponseRecieved(Object response, int requestCode);
	public Context getContext();
}
