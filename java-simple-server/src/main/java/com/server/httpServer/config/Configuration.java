package com.server.httpServer.config;

public class Configuration {
  private int port;
  private String webroot;

  public String getWebroot() {
    return webroot;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setWebroot(String webroot) {
    this.webroot = webroot;
  }
}
