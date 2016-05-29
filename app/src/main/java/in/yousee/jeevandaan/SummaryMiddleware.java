package in.yousee.jeevandaan;

import android.content.Context;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;

import in.yousee.jeevandaan.constants.RequestCodes;
import in.yousee.jeevandaan.constants.ServerFiles;
import in.yousee.jeevandaan.model.Donation;
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
        addPhoneNumberToPost();
        //nameValuePairs.add(new BasicNameValuePair(TAG_PHONE_NUMBER, "9505878984" + SessionHandler.getPhoneNumber(getContext())));

        encodePostRequest(nameValuePairs);
    }

    @Override
    public void serveResponse(String result, int requestCode) {
        if(requestCode == RequestCodes.NETWORK_REQUEST_GET_SUMMARY)
        {

            SummaryModel summaryModel = new SummaryModel(result);
            responseListner.onResponseRecieved(summaryModel,requestCode);
        }
    }

    private void createDummyData(String result)
    {
        SummaryModel summary = new SummaryModel(result);
        summary.bloodGroup = "O+";
        summary.donorName = "Captain America";
        summary.location = "Brroklyn";
        Donation dontation1 = new Donation(result);
        dontation1.bloodBankName = "SHEILD BloodBank";
        dontation1.date = new Date().toString();
        //dontation1.unitsOfBlood = 4;
        dontation1.location = "Brooklyn";
        summary.addDonation(dontation1);


        Donation dontation2 = new Donation(result);
        dontation2.bloodBankName = "SHEILD BloodBank";
        dontation2.date = new Date().toString();
        //dontation2.unitsOfBlood = 4;
        dontation2.location = "Brooklyn";
        summary.addDonation(dontation2);

    }
    @Override
    public Context getContext() {
        return responseListner.getContext();
    }
}
