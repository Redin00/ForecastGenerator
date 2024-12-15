package org.redinn;

// Classe per le eccezioni nel caso si verifica un problema con la connessione all'API OpenWeatherMap causato da uno specifico codice (401)

public class ApiKeyException extends Exception{

    public ApiKeyException(String errorMsg){
        super(errorMsg);
    }
}