import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class AppGui extends JFrame {

    private JSONObject weatherData;
    public AppGui(){
        // set up gui and title
        super("Weather App");

         setDefaultCloseOperation(EXIT_ON_CLOSE);


        // set size of gui (pixels)
        setSize(450,650);

        // set the gui center of the screen
        setLocationRelativeTo(null);

        // set layout
        setLayout(null);

        //resizable gui
        setResizable(false);

        // set background color
        this.getContentPane().setBackground(new java.awt.Color(60,144,240));
        addGuiComponents();
    }
    private void addGuiComponents(){
        // search field
        JTextField searchTextField=new JTextField();

        // set the location and size of our component
        searchTextField.setBounds(15,15,351,45);

        // set the font style and size
        searchTextField.setFont(new Font("Dialog",Font.PLAIN,24));

        add(searchTextField);



        // weather image
        JLabel weatherConditionImage=new JLabel(loadImage("src/assets/brokenclouds.png"));
        weatherConditionImage.setBounds(0,125,450,217);
        add(weatherConditionImage);

        // temperature text
        JLabel temperatureText=new JLabel("10 C");
        temperatureText.setBounds(0,350,450,54);
        temperatureText.setFont(new Font("Dialog",Font.BOLD,48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // weather condition desc
        JLabel weatherConditionDesc=new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0,405,450,36);
        weatherConditionDesc.setFont(new Font("Dialog",Font.PLAIN,32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // humidity image
        JLabel humidityImage=new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,74,66);
        add(humidityImage);

        // humidity text
        JLabel humidityText=new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        // windspeed image
        JLabel windSpeedImage=new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220,500,74,66);
        add(windSpeedImage);

        // windspeed text
        JLabel windSpeedText=new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windSpeedText.setBounds(310,500,85,55);
        windSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(windSpeedText);

        // search button
        Icon icon = new ImageIcon("C:\\Users\\AC\\Desktop\\githubprojeler\\OpenWeatherApiDesktopApp\\OpenWeatherApiDesktopApp1.0\\src\\assets\\search.png");
        JButton searchButton=new JButton(icon);

        //search button on the cursor
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput=searchTextField.getText();

                // validate input fixing
                if(userInput.replaceAll("\\s", "").length()<=0){
                    return;
                }
                // retrieve weather data
                weatherData=AppWeather.getWeatherData(userInput);

                // update gui
                // update weather image

                String weatherIcon;
                weatherIcon = (String) weatherData.get("weatherIc");

                switch (weatherIcon){
                    case "01d":
                        weatherConditionImage.setIcon(loadImage("src/assets/day_clearsky.png"));
                        break;
                    case "01n":
                        weatherConditionImage.setIcon(loadImage("src/assets/night_clearsky.png"));
                        

                        break;
                    case "02d":
                        weatherConditionImage.setIcon(loadImage("src/assets/day_fewclouds.png"));
                        break;
                    case "02n":
                        weatherConditionImage.setIcon(loadImage("src/assets/night_fewclouds.png"));
                        break;
                    case "03d":
                        weatherConditionImage.setIcon(loadImage("src/assets/scatteredclouds.png"));
                        break;
                    case "03n":
                        weatherConditionImage.setIcon(loadImage("src/assets/scatteredclouds.png"));
                        break;
                    case "04d":
                        weatherConditionImage.setIcon(loadImage("src/assets/brokenclouds.png"));
                        break;
                    case "04n":
                        weatherConditionImage.setIcon(loadImage("src/assets/brokenclouds.png"));
                        break;
                    case "09d":
                        weatherConditionImage.setIcon(loadImage("src/assets/showerrain.png"));
                        break;
                    case "09n":
                        weatherConditionImage.setIcon(loadImage("src/assets/showerrain.png"));
                        break;
                    case "10d":
                        weatherConditionImage.setIcon(loadImage("src/assets/day_rain.png"));
                        break;
                    case "10n":
                        weatherConditionImage.setIcon(loadImage("src/assets/night_rain.png"));
                        break;
                    case "11d":
                        weatherConditionImage.setIcon(loadImage("src/assets/thunderstorm.png"));
                        break;
                    case "11n":
                        weatherConditionImage.setIcon(loadImage("src/assets/thunderstorm.png"));
                        break;
                    case "13d":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                    case "13n":
                        weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
                        break;
                    case "50d":
                        weatherConditionImage.setIcon(loadImage("src/assets/mist.png"));
                        break;
                    case "50n":
                        weatherConditionImage.setIcon(loadImage("src/assets/mist.png"));
                        break;

                }

                // update temperature
                DecimalFormat df = new DecimalFormat("##.##");
                df.setMaximumFractionDigits(2);
                Double temperature=(Double)weatherData.get("temperature");
                temperatureText.setText(df.format(temperature) + "°C");


                // update weather condition text
                String weatherCondition=(String)weatherData.get("weatherCondition");
                String tempWc=weatherCondition.substring(0, 1).toUpperCase() + weatherCondition.substring(1);
                weatherConditionDesc.setText(tempWc);

                // update humidity text
                long humidity=(long)weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>" );

                // update windspeed text
                double windspeed=(double)weatherData.get("windspeed");
                windspeed=windspeed*3.6;
                windSpeedText.setText("<html><b>Windspeed</b> " + df.format(windspeed) + "km/h</html>" );




            }
        });
        add(searchButton);
    }
    private ImageIcon loadImage(String resourcePath){
        try {
            BufferedImage image= ImageIO.read(new File(resourcePath));

            return new ImageIcon(image);
        }catch (IOException e){
            e.printStackTrace();

        }
        System.out.println("Couldn't file resource");
        return null;
    }
}
