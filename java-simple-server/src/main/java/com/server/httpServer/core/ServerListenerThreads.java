package com.server.httpServer.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerListenerThreads extends Thread {

  private int port;
  private String webroot;
  private ServerSocket serverSocket;
  private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThreads.class);

  public ServerListenerThreads(int port, String webroot) throws IOException {
    this.port = port;
    this.webroot = webroot;
    this.serverSocket = new ServerSocket(this.port);
  }

  @Override
  public void run() {
    try {
      while (serverSocket.isBound() && !serverSocket.isClosed()) {
        Socket socket = serverSocket.accept();
        LOGGER.info("Connection Success" + socket.getInetAddress());
        HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
        workerThread.start();
      }
      
    } catch (IOException e) {
      LOGGER.info("Problem with setting socket", e);
      e.printStackTrace();
    } finally{
      if (serverSocket!=null) {
        try {
          serverSocket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };
    super.run();
  }
}
