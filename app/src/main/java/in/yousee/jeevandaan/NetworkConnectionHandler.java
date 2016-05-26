package in.yousee.jeevandaan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import in.yousee.jeevandaan.model.CustomException;
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
public class NetworkConnectionHandler extends AsyncTask<HttpPost, Void, ResponseBody>
{
	// used to get System services to check network status and required
	// information
	Context context;

	// web service URL
	//public static final String DOMAIN = "https://jeevandaan-mittu-spidey.c9users.io/index.php";
	//public static final String DOMAIN = "http://192.168.0.4/jeevandaan/index.php";
	public static final String DOMAIN = "http://health4all.online/jeevandaan";
	//public static final String DOMAIN = "http://yousee.in/YouseeMobile/";
	// DownloadWebpageTask downloadwebContent;
	HttpPost postRequest;
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
	public ResponseBody sendRequest(HttpPost postRequest)
	{
		this.listener = listener;
		this.postRequest = postRequest;
		// downloadwebContent = new DownloadWebpageTask();

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

	/**
	 * This method does the same job as sendRequest() but executed in a new
	 * Thread
	 *
	 */
	public void sendRequestInMultiThreadedMode(HttpPost postRequest, Middleware listener) throws CustomException
	{
		this.listener = listener;
		this.postRequest = postRequest;
		// Thread networkThread = new Thread(this);
		try
		{
			LogUtil.print("fksdjklfjskdhfkjshd");
			LogUtil.print(readIt(postRequest.getEntity().getContent()));
		}
		catch (IllegalStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (NetworkConnectionHandler.isNetworkConnected(context))
		{
			LogUtil.print("before Started");
			// downloadwebContent = new DownloadWebpageTask();
			// networkThread.start();
		}

	}

	public void run()
	{

		LogUtil.print("networkThread Started");

		// downloadwebContent.execute(postRequest);
	}


	@Override
	protected ResponseBody doInBackground(HttpPost... postRequests)
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
			//Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
		}
		LogUtil.print("onPostExecute()");
		if (responseBody != null) {
			LogUtil.print("response body !=  null");
			listener.serveResponse(responseBody.getResponseString(), responseBody.getRequestCode());
		}
	}

	/**
	 * This method is called whenever the response is received from the
	 * server.
	 */

	public ResponseBody onResponseReceived(HttpResponse response)
	{
		LogUtil.print("onResponseReceived()");
		int requestCode = 0;
		int resultCode = 0;
		/*
		String contetString1;
		try {
			contetString1 = readIt(response.getEntity().getContent());
			LogUtil.print(contetString1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/

		if (response != null)
		{
			if (response.containsHeader(Middleware.TAG_NETWORK_REQUEST_CODE))
			{

				LogUtil.print("response has headers");
				ResponseBody responseBody = new ResponseBody();
				Header[] headers = response.getAllHeaders();

				for (int i = 0; i < headers.length; i++)
				{
					LogUtil.print("header " + headers[i].getName() + " : " + headers[i].getValue());
				}

				String requestCodeString = response.getFirstHeader(Middleware.TAG_NETWORK_REQUEST_CODE).getValue();
				LogUtil.print("requestCode : " + requestCodeString);
				requestCode = Integer.valueOf(requestCodeString);
				responseBody.setRequestCode(requestCode);

				InputStream is = null;
				String contentAsString = null;
				try
				{
					is = response.getEntity().getContent();
					contentAsString = readIt(is);
					responseBody.setResponseString(contentAsString);

				}
				catch (IllegalStateException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if (is != null)
					{
						try
						{
							is.close();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}

				LogUtil.print("content string : " + contentAsString);
				try {
					response.getEntity().consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return responseBody;

			}
			else {
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

	/**
	 * This method connects to Server and downloads Response is returned
	 */
	private ResponseBody downloadUrl(HttpPost postRequest) throws IOException
	{
		InputStream is = null;

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
		HttpResponse response = httpclient.execute(postRequest);
		//String responseStr = EntityUtils.toString(response.getEntity());
		//LogUtil.print("response string " +responseStr);
		return onResponseReceived(response);

		// Makes sure that the InputStream is closed after the
		// app is
		// finished using it.

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
