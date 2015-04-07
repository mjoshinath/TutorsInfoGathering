package helper;

/**
 * Created by ehc on 1/4/15.
 */
public interface WebServiceCallBack {
    public void populateData(String jsonResponse);

    public void hideProgressBarOnFailure(String response);
}