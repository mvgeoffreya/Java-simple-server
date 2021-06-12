package com.server.httpServer.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread {

  private Socket socket;

  private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

  public HttpConnectionWorkerThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = socket.getInputStream();
      outputStream = socket.getOutputStream();

      String html = "<html><h1> test </h1></html>";

      final String cRLFString = "\n\r";

      String response = "HTTP/1.1 200 OK" + cRLFString + "Content-Length: " + html.getBytes().length + cRLFString
          + cRLFString + html + cRLFString + cRLFString;
      outputStream.write(response.getBytes());
      LOGGER.info("Connection Processing Finish");
    } catch (IOException e) {
      LOGGER.info("Problem with communication", e);
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    }
    super.run();
  }
}
