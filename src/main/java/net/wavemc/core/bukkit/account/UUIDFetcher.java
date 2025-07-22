package net.wavemc.core.bukkit.account;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UUIDFetcher {

    private ExecutorService executor;

    public UUIDFetcher(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public UUIDFetcher(ExecutorService executor) {
        this.executor = executor;
    }

    public UUID fetchUUID(String playerName) throws Exception {
        // Get response from Mojang API
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
if (playerName == null) {
    System.err.println("The player with name \"" + playerName + "\" is null. Assigning random UUID");
    return UUID.randomUUID();
}
        if(connection.getResponseCode() == 400) {
            System.err.println("There is no player with the name \"" + playerName + "\"! in mojang. Assigning random UUID");
            return UUID.randomUUID();
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Parse JSON response and get UUID
        JsonElement element = new JsonParser().parse(bufferedReader);
        JsonObject object = element.getAsJsonObject();
        String uuidAsString = object.get("id").getAsString();

        // Return UUID
        return parseUUIDFromString(uuidAsString);
    }

    public String fetchName(UUID uuid) throws Exception {
        // Get response from Mojang API
        URL url = new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        if(connection.getResponseCode() == 400) {
            System.err.println("There is no player with the UUID \"" + uuid.toString() + "\"!");
            return null;
        }

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // Parse JSON response and return name
        JsonElement element = new JsonParser().parse(bufferedReader);
        JsonArray array = element.getAsJsonArray();
        JsonObject object = array.get(0).getAsJsonObject();
        return object.get("name").getAsString();

    }

    public void fetchUUIDAsync(String playerName, Consumer<UUID> consumer) {
        executor.execute(() -> {

            try {
                // Get response from Mojang API
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if(connection.getResponseCode() == 400) {
                    System.err.println("There is no player with the name \"" + playerName + "\"!");
                    return;
                }

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // Parse JSON response and get UUID
                JsonElement element = new JsonParser().parse(bufferedReader);
                JsonObject object = element.getAsJsonObject();
                String uuidAsString = object.get("id").getAsString();

                inputStream.close();
                bufferedReader.close();

                // Return UUID
                consumer.accept(parseUUIDFromString(uuidAsString));
            } catch (IOException e) {
                System.err.println("Couldn't connect to URL.");
                e.printStackTrace();
            }
        });
    }

    public void fetchNameAsync(UUID uuid, Consumer<String> consumer) {
        executor.execute(() -> {
            try {
                // Get response from Mojang API
                URL url = new URL(String.format("https://api.mojang.com/user/profiles/%s/names", uuid.toString().replace("-", "")));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if(connection.getResponseCode() == 400) {
                    System.err.println("There is no player with the UUID \"" + uuid.toString() + "\"!");
                    return;
                }

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                // Parse JSON response and return name
                JsonElement element = new JsonParser().parse(bufferedReader);
                JsonArray array = element.getAsJsonArray();
                JsonObject object = array.get(0).getAsJsonObject();

                bufferedReader.close();
                inputStream.close();

                consumer.accept(object.get("name").getAsString());
            } catch(IOException e) {
                System.err.println("Couldn't connect to URL.");
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        // Stop executor (This step is important!)
        executor.shutdown();
    }

    private UUID parseUUIDFromString(String uuidAsString) {
        String[] parts = {
                "0x" + uuidAsString.substring(0, 8),
                "0x" + uuidAsString.substring(8, 12),
                "0x" + uuidAsString.substring(12, 16),
                "0x" + uuidAsString.substring(16, 20),
                "0x" + uuidAsString.substring(20, 32)
        };

        long mostSigBits = Long.decode(parts[0]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[1]).longValue();
        mostSigBits <<= 16;
        mostSigBits |= Long.decode(parts[2]).longValue();

        long leastSigBits = Long.decode(parts[3]).longValue();
        leastSigBits <<= 48;
        leastSigBits |= Long.decode(parts[4]).longValue();

        return new UUID(mostSigBits, leastSigBits);
    }

}