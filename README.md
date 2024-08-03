# Java Desktop Weather App
##### This is a simply weather app created by using *OpenWeather API*. I created *HTTP* connections and used *GET* method to access the informations which provided by OpenWeather.
##### I will develop this app better. This is the first version.
![pic1](https://github.com/user-attachments/assets/c5d9e8f6-7057-4669-b7df-12ceed507322) </br>
###### This is the code of my API.
` String urlString="https://api.openweathermap.org/data/2.5/weather?q=" +
                locationName+"&appid=THISISTHEAREAOFTHEAPIID";` </br>
###### This is the HTTP Connection and GET method.
` try{ URL url=new URL(urlString);
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            return conn;
            }catch(IOException e){
            e.printStackTrace();
            }`
            
![pic2](https://github.com/user-attachments/assets/3dcc334a-e6d6-466b-a177-dd34fb0ed509)
###### That's it for now.
