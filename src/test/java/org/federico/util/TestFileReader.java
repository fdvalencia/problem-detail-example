package org.federico.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFileReader {
  public static String read(String filename) {
    ClassLoader classLoader = TestFileReader.class.getClassLoader();
    URL url = classLoader.getResource(filename);
    try {
      if (url == null) throw new FileNotFoundException("resource " + filename + " not found");
      return Files.readString(Paths.get(url.toURI()));
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
