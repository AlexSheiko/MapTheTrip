package sheyko.aleksey.mapthetrip.utils.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;

import sheyko.aleksey.mapthetrip.models.Device;

public class UpdateTripStatusTask extends AsyncTask<String, Void, Void> {

    public static final String TAG = UpdateTripStatusTask.class.getSimpleName();

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain JSON responses as a string
        String JsonResponseStr = null;

        try {
            // Construct the URL for the query
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("wsapp.mapthetrip.com")
                    .appendPath("TrucFuelLog.svc")
                    .appendPath("TFLUpdateTripStatus")
                    .appendQueryParameter("TripId", params[0])
                    .appendQueryParameter("TripStatus", params[1])
                    .appendQueryParameter("TripDateTime", new Device(mContext).getCurrentDateTime())
                    .appendQueryParameter("TripTimezone", new Device(mContext).getTimeZone())
                    .appendQueryParameter("UserId", sheyko.aleksey.mapthetrip.utils.helpers.Constants.Device.USER_ID);
            String mUrlString = builder.build().toString();

            Log.i(TAG, "Service: TFLUpdateTripStatus,\n" +
                    "Query: " + java.net.URLDecoder.decode(mUrlString, "UTF-8"));

//            URL mUrl = new URL(mUrlString);
//
//            // Create the request and open the connection
//            urlConnection = (HttpURLConnection) mUrl.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.connect();
//
//            // Read the input stream into a String
//            InputStream inputStream = urlConnection.getInputStream();
//            StringBuffer buffer = new StringBuffer();
//            if (inputStream == null) {
//                // Nothing to do.
//                return null;
//            }
//            reader = new BufferedReader(new InputStreamReader(inputStream));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
//                // But it does make debugging a *lot* easier if you print out the completed
//                // buffer for debugging.
//                buffer.append(line + "\n");
//            }
//
//            updateTripJsonResponse = buffer.toString();
//            Log.i(TAG, "Service: TFLUpdateTripStatus " + "(" + params[1] + " trip)" + ",\n" +
//                    "Result: " + java.net.URLDecoder.decode(updateTripJsonResponse, "UTF-8"));
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
}