/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.ralph.model.HighLowPoint;
import com.ralph.model.TimeHeight;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("165.225.80.40", 80));
        InputStream is = new URL(url).openConnection(proxy).getInputStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    public List<HighLowPoint> fetchTideHighLows() {
        List<HighLowPoint> heights = new ArrayList<>();
        try {
            JSONObject json = readJsonFromUrl("http://tides.ralphius.duckdns.org/highlow.json");
            JSONArray JSONheights = json.getJSONArray("extremes");
            for (Object obj : JSONheights) {
                JSONObject currentJSONObj = (JSONObject) obj;
                HighLowPoint currentHL = new HighLowPoint();
                currentHL.setHeight(currentJSONObj.getBigDecimal("height"));
                currentHL.setIsoTime(currentJSONObj.getString("date"));
                currentHL.setUnixTime(currentJSONObj.getLong("dt"));
                currentHL.setType(currentJSONObj.getString("type"));
                heights.add(currentHL);
            }
            return heights;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<TimeHeight> fetchTideTimeHeights() {
        List<TimeHeight> heights = new ArrayList<>();
        try {
            JSONObject json = readJsonFromUrl("http://tides.ralphius.duckdns.org/heights.json");
            //System.out.println(json.toString());
            JSONArray JSONheights = json.getJSONArray("heights");
            for (Object obj : JSONheights) {
                JSONObject currentJSONObj = (JSONObject) obj;
                TimeHeight currentTH = new TimeHeight();
                currentTH.setHeight(currentJSONObj.getBigDecimal("height"));
                currentTH.setIsoTime(currentJSONObj.getString("date"));
                currentTH.setUnixTime(currentJSONObj.getLong("dt"));
                heights.add(currentTH);
            }
            return heights;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException, JSONException {
        List<TimeHeight> heights = new JsonReader().fetchTideTimeHeights();
        for (TimeHeight currentTH: heights) {
            System.out.println(currentTH.getUnixTime() + "\t" + currentTH.getIsoTime() + "\t" + currentTH.getHeight());
        }
    }
}
