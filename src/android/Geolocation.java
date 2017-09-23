package me.jacklu.cordova.plugin;

import org.apache.cordova.*;

import org.json.*;

import java.util.HashMap;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;

import android.location.*;

public class Geolocation extends CordovaPlugin {

public static final String LOGTAG = "Geolocation";

@Override
public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
	if (action.equals("watchPosition")) {
		int id = args.getInt(0);
		JSONObject arg = args.getJSONObject(1);
		Log.d(LOGTAG, "watchPosition: " + arg.toString());
		String provider = arg.getString("provider");
		long minTime = arg.getLong("minTime");
		float minDistance = (float)arg.getDouble("minDistance");
		requestLocationUpdates(id, provider, minTime, minDistance, callbackContext);
		return true;
	}
	return false;
}

HashMap<Integer, LocationListener> listener = new HashMap<Integer, LocationListener>();
void requestLocationUpdates(int id, String provider, long minTime, float minDistance, CallbackContext callbackContext) {
	LocationManager locationManager =
		(LocationManager)cordova.getActivity()
			.getSystemService(Context.LOCATION_SERVICE);
	LocationListener listener = new LocationListener(callbackContext);
	this.listener.put(id, listener);
	locationManager.requestLocationUpdates(provider, minTime, minDistance, listener);
}

class LocationListener implements android.location.LocationListener {
	CallbackContext callbackContext;
	public LocationListener(CallbackContext callbackContext) {
		this.callbackContext = callbackContext;
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	@Override
	public void onProviderEnabled(String provider) {}
	@Override
	public void onProviderDisabled(String provider) {}
	@Override
	public void onLocationChanged(Location location) {
		JSONObject json = toJSON(location);
		Log.d(LOGTAG, "onLocationChanged: " + json.toString());
		PluginResult result = new PluginResult(PluginResult.Status.OK, json);
		result.setKeepCallback(true);
		callbackContext.sendPluginResult(result);
	}
}

JSONObject toJSON(Location location) {
	JSONObject json = new JSONObject();
	try {
	json.put("latitude", location.getLatitude());
	json.put("longitude", location.getLongitude());
	json.put("accuracy", location.getAccuracy());
	json.put("timestamp", location.getTime());
	} catch (JSONException e) {}
	return json;
}

}
