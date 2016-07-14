package in.yousee.jeevandaan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import in.yousee.jeevandaan.model.CustomException;
import in.yousee.jeevandaan.model.Request;
import in.yousee.jeevandaan.model.ResponseBody;
import in.yousee.jeevandaan.util.LogUtil;

/**
 *
 * NetworkConnectionHandler.java Purpose: Connects to server, receives response
 * and passes it to class which implements class Chef
 *
 * @author Laxman
 * @version 1.0 15/08/2013
 */
public class NetworkConnectionHandler extends AsyncTask<Request, Void, ResponseBody>
{
	// used to get System services to check network status and required
	// information
	Context context;

	// web service URL
	//public static final String DOMAIN = "https://jeevandaan-mittu-spidey.c9users.io/index.php";
	//public static final String DOMAIN = "http://192.168.0.4/jeevandaan/index.php";
	public static final String DOMAIN = "http://health4all.online/jeevandaan";
	// DownloadWebpageTask downloadwebContent;
	Request postRequest;
	Middleware listener;
	public static String sessionId;
	public static final DefaultHttpClient httpclient = new DefaultHttpClient();

	public static boolean isExecuting = false;
	public String toastString = null;

	public NetworkConnectionHandler(Context context)
	{
		LogUtil.print("networkConnection created");
		this.context = context;
	}

	public NetworkConnectionHandler(Context context, Middleware listener)
	{
		LogUtil.print("networkConnection created");
		this.listener = listener;
		this.context = context;

	}

	/**
	 * Tells whether network is connected or not
	 */
	public static boolean isNetworkConnected(Context context) throws CustomException
	{
		LogUtil.print("is network connected");
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		LogUtil.print("network information is recieved");
		if (networkInfo != null && networkInfo.isConnected())
		{
			LogUtil.print("network connection is available");
			return true;

		}
		else
		{
			LogUtil.print("throwing exception");
			throw new CustomException(CustomException.ERROR_NETWORK_NOT_FOUND);
		}

	}

	/**
	 * Checks network status and creates a AsyncTask object starts its
	 * execution
	 */
	public ResponseBody sendRequest(Request postRequest)
	{
		this.postRequest = postRequest;

		try
		{
			return downloadUrl(postRequest);
		}
		catch (IOException e)
		{
			LogUtil.print("cannot retrieve");
			e.printStackTrace();
			return null;
		}

		// onResponseRecieved();

	}

	@Override
	protected ResponseBody doInBackground(Request... postRequests)
	{
		isExecuting = true;
		return sendRequest(postRequests[0]);

	}

	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(ResponseBody responseBody)
	{
		isExecuting = false;
		if (toastString != null)
		{
			Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
		}
		LogUtil.print("onPostExecute()");
		if (responseBody != null) {
			LogUtil.print("response body !=  null");
			listener.serveResponse(responseBody.getResponseString(), responseBody.getRequestCode());
		}
	}



	/**
	 * This method connects to Server and downloads Response is returned
	 */
	private ResponseBody downloadUrl(Request postRequest) throws IOException
	{
		InputStream is = null;
		/*
		LogUtil.print("download Started" + readIt(postRequest.getEntity().getContent()));
		Header[] headers = postRequest.getAllHeaders();
		LogUtil.print("lenght " + headers.length);
		// headers[i].getValue());
		for (int i = 0; i < headers.length; i++)
		{
			LogUtil.print("request " + headers[i].getName() + " : " + headers[i].getValue());
		}
		LogUtil.print(readIt(postRequest.getEntity().getContent()));
		// httpclient.getCookieStore().addCookie();
		*/
		URL url = new URL(postRequest.getUrl());
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
		connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
		connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
		connection.setDoOutput(true);
		DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());

		String urlParameters = "q=anything";


		dStream.writeBytes(postRequest.getParameters());

		//dStream.writeBytes(urlParameters); //Writes out the string to the underlying output stream as a sequence of bytes
		dStream.flush(); // Flushes the data output stream.
		dStream.close();


		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String requestCodeString = connection.getHeaderField(Middleware.TAG_NETWORK_REQUEST_CODE);
			if(requestCodeString != null)
			{
				InputStream inputStream = connection.getInputStream();
				String contentAsString = readIt(inputStream);
				ResponseBody responseBody = new ResponseBody();
				int requestCode = new Integer(requestCodeString).intValue();
				responseBody.setRequestCode(requestCode);
				responseBody.setResponseString(contentAsString);
				LogUtil.print(contentAsString);
				return responseBody;

			}
			else
			{
				toastString = "error: 101 - Something went wrong, Please Report the issue to the developer.";
				LogUtil.print(toastString);
			}
		}
		else
		{
			toastString = "error: 102 - Something went wrong, Please report the issue to the developer.";
		}
		return null;

	}

	// Reads an InputStream and converts it to a String.
	private String readIt(InputStream stream) throws IOException
	{

		InputStreamReader is = new InputStreamReader(stream);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(is);
		String read = br.readLine();

		while (read != null)
		{
			// System.out.println(read);
			sb.append(read);
			read = br.readLine();

		}

		return sb.toString();
	}

}
