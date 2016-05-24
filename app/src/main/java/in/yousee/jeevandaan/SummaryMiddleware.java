package in.yousee.jeevandaan;

import android.content.Context;

import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;

import in.yousee.jeevandaan.constants.RequestCodes;
import in.yousee.jeevandaan.constants.ServerFiles;
import in.yousee.jeevandaan.model.SummaryModel;

/**
 * Created by mittu on 23-05-2016.
 */
public class SummaryMiddleware extends Middleware {

    private OnResponseRecievedListener responseListner;

    public SummaryMiddleware(OnResponseRecievedListener responseListner)
    {
        this.responseListner = responseListner;
        assembleRequest();
    }
    @Override
    public void assembleRequest() {
        postRequest = new HttpPost(NetworkConnectionHandler.DOMAIN + ServerFiles.GET_SUMMARY);
        nameValuePairs = new ArrayList<>();
        setRequestCode(RequestCodes.NETWORK_REQUEST_GET_SUMMARY);
        addSessionIdToPost();
        encodePostRequest(nameValuePairs);
    }

    @Override
    public void serveResponse(String result, int requestCode) {
        if(requestCode == RequestCodes.NETWORK_REQUEST_GET_SUMMARY)
        {
            SummaryModel summary = new SummaryModel(result);
            responseListner.onResponseRecieved(summary,requestCode);
        }
    }

    @Override
    public Context getContext() {
        return responseListner.getContext();
    }
}
