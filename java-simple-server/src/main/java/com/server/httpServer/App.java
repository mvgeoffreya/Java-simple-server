package com.server.httpServer;

import java.io.IOException;

import com.server.httpServer.config.Configuration;
import com.server.httpServer.config.ConfigurationManager;
import com.server.httpServer.core.ServerListenerThreads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LOGGER.info("Server starting");
        ConfigurationManager.getInstance().loadMyConfigurationFile("src/main/java/resource/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        int port = conf.getPort();
        String webroot = conf.getWebroot();
        LOGGER.info("Using Port = " + port);
        LOGGER.info("Using webroot = " + webroot);

        try {
            ServerListenerThreads serverListenerThreads = new ServerListenerThreads(port, webroot);
            serverListenerThreads.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
