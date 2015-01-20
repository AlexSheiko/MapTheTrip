package sheyko.aleksey.mapthetrip.utils.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import sheyko.aleksey.mapthetrip.models.Device;

public class SendLocationTask extends AsyncTask<List<ParseObject>, Void, Void> {
    public static final String TAG = SendLocationTask.class.getSimpleName();

    private Context mContext;

    public SendLocationTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(List<ParseObject>... coordinatesList) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain JSON responses as a string
        String firstServerJsonResponse;
        String secondServerJsonResponse;

        Device mDevice = new Device(mContext);

        // Request to first server
        try {
            for (List<ParseObject> coordinates : coordinatesList) {
                for (ParseObject coordinate : coordinates) {
                    String tripId = coordinate.getString("trip_id");
                    String latitude = coordinate.getString("latitude");
                    String longitude = coordinate.getString("longitude");
                    String altitude = coordinate.getString("altitude");
                    String accuracy = coordinate.getString("accuracy");

                    // Construct the URL for the query
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("wsapp.mapthetrip.com")
                            .appendPath("TrucFuelLog.svc")
                            .appendPath("TFLRecordTripCoordinates")
                            .appendQueryParameter("TripId", tripId)
                            .appendQueryParameter("Latitute", latitude)
                            .appendQueryParameter("Longitude", longitude)
                            .appendQueryParameter("CoordinatesRecordDateTime", mDevice.getCurrentDateTime())
                            .appendQueryParameter("CoordinatesRecordTimezone", mDevice.getTimeZone())
                            .appendQueryParameter("CoordinatesIdStatesRegions", "")
                            .appendQueryParameter("CoordinatesStateRegionCode", "")
                            .appendQueryParameter("CoordinatesCountry", mDevice.getCoordinatesCountry())
                            .appendQueryParameter("UserId", mDevice.getUserId())
                            .appendQueryParameter("Altitude", altitude)
                            .appendQueryParameter("Accuracy", accuracy)
                    ;
                    String mUrlString = builder.build().toString();

                    Log.i(TAG, "Service: TFLRecordTripCoordinates,\n" +
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
                    //            firstServerJsonResponse = buffer.toString();
                    //            Log.i(TAG, "Service: TFLRecordTripCoordinates,\n" +
                    //                    "Result: " + java.net.URLDecoder.decode(firstServerJsonResponse, "UTF-8"));
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } finally {
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

        // Request to second server
        try {
            for (List<ParseObject> coordinates : coordinatesList) {
                for (ParseObject coordinate : coordinates) {
                    String tripId = coordinate.getString("trip_id");
                    String latitude = coordinate.getString("latitude");
                    String longitude = coordinate.getString("longitude");
                    String altitude = coordinate.getString("altitude");
                    String accuracy = coordinate.getString("accuracy");

                    // Construct the URL for the query
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http")
                            .authority("64.251.25.139")
                            .appendPath("trucks_app")
                            .appendPath("ws")
                            .appendPath("record-position.php")
                            .appendQueryParameter("lat", latitude)
                            .appendQueryParameter("lon", longitude)
                            .appendQueryParameter("alt", altitude)
                            .appendQueryParameter("id", tripId)
                            .appendQueryParameter("datetime", mDevice.getCurrentDateTime())
                            .appendQueryParameter("timezone", mDevice.getTimeZone())
                            .appendQueryParameter("accuracy", accuracy);
                    String mUrlString = builder.build().toString();

                    Log.i(TAG, "Service: record-position.php,\n" +
                            "Query: " + java.net.URLDecoder.decode(mUrlString, "UTF-8"));

                    //            URL mUrl = new URL(mUrlString);
                    //            // Create the request and open the connection
                    //            urlConnection = (HttpURLConnection) mUrl.openConnection();
                    //            urlConnection.setRequestMethod("GET");
                    //
                    //            if (isNetworkAvailable()) {
                    //                urlConnection.connect();
                    //            } else {
                    //                return null;
                    //            }
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
                    //            secondServerJsonResponse = buffer.toString();
                    //            Log.i(TAG, "Service: record-position.php,\n" +
                    //                    "Result: " + java.net.URLDecoder.decode(secondServerJsonResponse, "UTF-8"));
                }}
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } finally {
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
