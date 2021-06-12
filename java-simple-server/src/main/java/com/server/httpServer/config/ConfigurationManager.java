package com.server.httpServer.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.server.httpServer.util.json;

public class ConfigurationManager {
  private static ConfigurationManager myConfigurationManager;
  private static Configuration myCurrenConfiguration;
  private FileReader fileReader;

  private ConfigurationManager() {
  }

  public static ConfigurationManager getInstance() {
    if (myConfigurationManager == null) {
      myConfigurationManager = new ConfigurationManager();
    }
    return myConfigurationManager;
  }

  public void loadMyConfigurationFile(String filePath){
    try {
      fileReader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      throw new HttpConfigurationException(e);
    }
    StringBuffer sBuffer = new StringBuffer();

    int i;
    try {
      while( (i = fileReader.read()) != -1) {
        sBuffer.append((char) i);
      }
    } catch (IOException e) {
      throw new HttpConfigurationException(e);
    };
    JsonNode conf;
    try {
      conf = json.parse(sBuffer.toString());
    } catch (IOException e) {
      throw new HttpConfigurationException("Error parsing the Configuration File");
    }
    try {
      myCurrenConfiguration = json.fromJson(conf, Configuration.class);
    } catch (JsonProcessingException e) {
      throw new HttpConfigurationException("Error parsing the Configuration File Internal");
    }
  }

  public Configuration getCurrentConfiguration() {
    if (myCurrenConfiguration == null) {
      throw new HttpConfigurationException("No Current Configuration set. ");
    }
    return myCurrenConfiguration;
  }
}
