package com.comscoreplugin;

import com.comscore.Analytics;
import com.comscore.PublisherConfiguration;
import static com.comscore.UsagePropertiesAutoUpdateMode.FOREGROUND_ONLY;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONArray;

public class ComScorePlugin extends CordovaPlugin {

    public static final String TAG = "ComScorePlugin";
    public static final String SETCUSTOMERDATA = "setCustomerData";
    public static final String ONENTERFOREGROUND = "onEnterForeground";
    public static final String ONEXITFOREGROUND = "onExitForeground";
    public static final String START = "start";


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        boolean result = false;

        if (SETCUSTOMERDATA.equals(action)) {
            this.setCustomerData(args.getString(0), args.getString(1), callbackContext);
            result = true;
        }
        else if (ONENTERFOREGROUND.equals(action)) {
            this.onEnterForeground(callbackContext);
            result = true;
        }
        else if (ONEXITFOREGROUND.equals(action)) {
            this.onExitForeground(callbackContext);
            result = true;
        }

        return result;
    }

    @Override
    protected void pluginInitialize() {
        Log.v(TAG, "pluginInitialize");
    }

    private void setCustomerData(String customerID, String customerKey, CallbackContext callbackContext) {
        PublisherConfiguration myPublisherConfig = new PublisherConfiguration.Builder()
                .publisherId(customerID)
                .publisherSecret(customerKey)
                .usagePropertiesAutoUpdateMode(FOREGROUND_ONLY)
                .build();

        Log.v(TAG, "publisherId" + customerID);
        Log.v(TAG, "publisherSecret " + customerKey);
        Analytics.getConfiguration().addClient(myPublisherConfig);
        Analytics.start(this.webView.getContext());
        callbackContext.success("ok");
    }
    
    private void onEnterForeground(CallbackContext callbackContext) {
        Analytics.notifyEnterForeground();
        Log.v(TAG, "onEnterForeground");
        callbackContext.success("ok");
    }

    private void onExitForeground(CallbackContext callbackContext) {
        Analytics.notifyExitForeground();
        Log.v(TAG, "onExitForeground");
        callbackContext.success("ok");
    }
}
