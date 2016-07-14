package in.yousee.jeevandaan.model;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by mittu on 14-07-2016.
 */
public class Request {
    private String url;
    private int requestCode;
    private List<NameValuePair> parameters;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    /*
    public List<NameValuePair> getParameters() {
        return parameters;
    }
    */

    public void setParameters(List<NameValuePair> parameters) {

        this.parameters = parameters;
    }
    public String getParameters() throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : this.parameters)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
