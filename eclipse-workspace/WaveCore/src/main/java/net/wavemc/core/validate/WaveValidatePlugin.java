package net.wavemc.core.validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WaveValidatePlugin {

    private final static String BASE_URL = "https://api.ipify.org";




    public static String getAddress() {
        String address = null;
        try {
            URL url = new URL(BASE_URL);
            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            address = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }
}
