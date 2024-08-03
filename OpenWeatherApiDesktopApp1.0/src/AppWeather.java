import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
// display the data to the user
public class AppWeather {

    // fetch weather data for given location

    public static JSONObject getWeatherData(String locationName){
        JSONObject locationData=getLocationData(locationName);


        // build API request URL with location coordinates


        try{
            JSONArray weatherArray= (JSONArray) locationData.get("weather");
            JSONObject weatherObj =(JSONObject) weatherArray.get(0);
            String weatherIcon= (String) weatherObj.get("icon"); // id for pictures
            String weatherDesc=(String) weatherObj.get("description"); // description of the weather

            // get humidity

            JSONObject mainObj= (JSONObject) locationData.get("main");
            long humidityData=(long) mainObj.get("humidity");

            // get temperature


            double temperatureDataa=(double) mainObj.get("temp");
            temperatureDataa= (double)(temperatureDataa-272.15);


            // get windspeed

            JSONObject windObj= (JSONObject) locationData.get("wind");
            double windspeedData=(double)windObj.get("speed");
            windspeedData=(double)(windspeedData);

            // build the weather json data object that we are going to access in our frontend
            JSONObject weatherData= new JSONObject();
            weatherData.put("temperature",temperatureDataa);
            weatherData.put("humidity",humidityData);
            weatherData.put("weatherIc",weatherIcon);
            weatherData.put("weatherCondition",weatherDesc);
            weatherData.put("windspeed",windspeedData);

            return weatherData;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getLocationData(String locationName){
        // replace anywhitespace in locationname as +
        locationName=locationName.replaceAll(" ","+");

        // build api url with location parameter
        String urlString="https://api.openweathermap.org/data/2.5/weather?q=" +
                locationName+"&appid=d45282d1f7ea737e83b59bb14831616e";

        try{
        // call api and get a response
            HttpURLConnection conn=fetchApiResponse(urlString);
        // chech response status
            if(conn.getResponseCode()!=200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else{
                // store the api results
                StringBuilder resultJson= new StringBuilder();
                Scanner scanner=new Scanner(conn.getInputStream());

                //read and store the data into our stringbuilder
                while(scanner.hasNext()){
                    resultJson.append(scanner.nextLine());

                }

                // close scanner and url connection
                scanner.close();
                conn.disconnect();

                // parse the JSON string into the JSON obj

                JSONParser parser= new JSONParser();
                JSONObject resultsJsonObj=(JSONObject) parser.parse(String.valueOf(resultJson));

                // get the lists of location data the API generated from the location name
                // JSONObject locationData= (JSONObject) resultsJsonObj.get("coord");


                return resultsJsonObj;

            }

        }catch(Exception e){
            e.printStackTrace();
        }
        // couldn't location data
        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try{
            // attempt for connection
            URL url=new URL(urlString);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();

            // set request method to get
            conn.setRequestMethod("GET");

            // connect to our api
            conn.connect();
            return conn;

        }catch(IOException e){
            e.printStackTrace();

        }
        // could not make a connection
        return null;
    }
}
