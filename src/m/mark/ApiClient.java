package m.mark;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ApiClient {
    String requestURL;

    public ApiClient(String postcodeSearch) {
        this.requestURL = "http://sandbox.kriswelsh.com/hygieneapi/hygiene.php?op=search_postcode&postcode=" + postcodeSearch;
    }

    public JSONArray getResponse() throws FuckedUpException {
        JSONArray response = null;
        try {
            // make the request, put the response in a string
            URLConnection connection = new URL(requestURL).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder data = new StringBuilder();
            String line = "";

            while ((line = reader.readLine()) != null) {
                data.append(line);
            }

            response = new JSONArray(data.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            return response;
        } else {
            throw new FuckedUpException();
        }
    }
}
