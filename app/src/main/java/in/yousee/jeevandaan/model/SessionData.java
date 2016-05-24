package in.yousee.jeevandaan.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionData implements JSONParsable
{
	private final String TAG_SESSION_ID = "session_id";
	private final String TAG_DONOR_ID = "donor_id";
	private final String TAG_USER_TYPE_ID = "userTypeId";
	private final String TAG_SUCCESS = "status_code";

	private boolean success;
	private String sessionId;
	private int userId;
	private String userType;

	public int donorId;
	public String phone;


	public SessionData(JSONObject JsonObject)
	{
		parseJSON(JsonObject);
	}

	public SessionData(String JSONString)
	{
		try
		{
			Log.i("tag", JSONString);
			parseJSON(new JSONObject(JSONString));
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getUserType()
	{
		return userType;
	}

	public void setUserType(String userType)
	{
		this.userType = userType;
	}

	@Override
	public void parseJSON(JSONObject JSONObject)
	{
		try
		{
			this.sessionId = JSONObject.getString(TAG_SESSION_ID);
			//setSessionId(JSONObject.getString(TAG_SESSION_ID));
			//this.success = JSONObject.getBoolean(TAG_SUCCESS);
			//setUserType(JSONObject.getString(TAG_USER_TYPE_ID));

		} catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

}
