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
    public static final String UPDATECONSENT = "updateConsent";
    private String customerID;

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
        else if (START.equals(action)) {
            this.start(callbackContext);
            result = true;
        }
        else if (UPDATECONSENT.equals(action)) {
            this.updateConsent(args.getString(0), callbackContext);
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
        this.customerID = customerID;
        
        Log.v(TAG, "publisherId" + customerID);
        Log.v(TAG, "publisherSecret " + customerKey);
        Analytics.getConfiguration().addClient(myPublisherConfig);
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
    
    private void start(CallbackContext callbackContext) {
        Analytics.start(this.webView.getContext());
        Log.v(TAG, "start");
        callbackContext.success("ok");
    }
    
    private void updateConsent(String consentValue, CallbackContext callbackContext) {
        Analytics.getConfiguration().getPublisherConfiguration(this.customerID).setPersistentLabel("cs_ucfr", consentValue); 
        Analytics.notifyHiddenEvent();
        Log.v(TAG, "updateConsent");
        callbackContext.success("ok");
    }
}
