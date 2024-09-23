package com.uniquindio.microservicios.taller_auto_prueba.cucumber.utils;

import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static DataManager instance = null;
    private Map<String, String> userIds = new HashMap<>();
    private Map<String, String> authTokens = new HashMap<>();

    // Constructor privado para evitar la creación de instancias externas
    private DataManager() {}

    // Método para obtener la instancia única del DataManager
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public String getUserId(String userKey) {
        return userIds.get(userKey);
    }

    public void setUserId(String userKey, String userId) {
        this.userIds.put(userKey, userId);
    }

    public String getAuthToken(String userKey) {
        return authTokens.get(userKey);
    }

    public void setAuthToken(String userKey, String authToken) {
        this.authTokens.put(userKey, authToken);
    }
}
