package space.cstm.services;

import space.cstm.exceptions.DirectoryNotCreated;
import space.cstm.utils.enums.ResourcePath;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileService {
  private static final String FILE_PROTOCOL = "file:" + File.separator + File.separator + File.separator;

  private FileService() {}

  public static Set<String> getFilesInJarFromDirectory(String directoryPath) throws IOException {
    final CodeSource src = FileService.class.getProtectionDomain().getCodeSource();
    final Set<String> files = new HashSet<>();

    if( src != null ) {
      final URL jar = src.getLocation();
      final ZipInputStream zip = new ZipInputStream( jar.openStream());

      ZipEntry zipEntry;

      while ( ( zipEntry = zip.getNextEntry() ) != null ) {
        String entryName = zipEntry.getName();

        if ( entryName.contains(directoryPath) && !zipEntry.isDirectory()) {
          files.add("/".concat(entryName));
        }
      }

    }

    return files;
  }

  public static Set<String> getAllFilesInDirectory(String directoryPath) {
    if (JarService.runningFromJar()) {
      try {
        return getFilesInJarFromDirectory(directoryPath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return Arrays.stream(Objects.requireNonNull(new File(directoryPath).listFiles()))
            .filter(File::isFile)
            .map(file -> file.toURI().toString())
            .collect(Collectors.toSet());
  }

  public static String saveFile(File file, String directoryPath, boolean isImage) throws IllegalAccessException, DirectoryNotCreated, IOException {
    final File homePathFile = new File(ResourcePath.HOME_PATH.toString());
    
    if (homePathFile.isDirectory() && !homePathFile.canWrite()) {
      throw new IllegalAccessException("Trying to save file in a protected area");
    }
    
    createDirectoryIfNotExist(directoryPath);
  
    File newFile = new File(directoryPath + File.separator + file.getName());
  
    if (isImage) {
      BufferedImage bufferedImage = ImageIO.read(file);
      ImageIO.write(bufferedImage, FileService.getFileExtension(file), newFile);
    }
    
    return FILE_PROTOCOL + newFile.getCanonicalPath();
  }
  
  public static void createDirectoryIfNotExist(String directoryPath) throws DirectoryNotCreated {
    final File directory = new File(directoryPath);
  
    if (!directory.isDirectory() && !directory.exists()) {
      boolean mkdir = directory.mkdirs();
    
      if (!mkdir) {
        throw new DirectoryNotCreated("Maybe you don't have the permission to do that");
      }
    }
  }
  
  /**
   * https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
   *
   * @param file
   * @return
   */
  private static String getFileExtension(File file) {
    if (file == null) {
      return "";
    }
    
    final String name = file.getName();
    
    int i = name.lastIndexOf('.');
  
    return i > 0 ? name.substring(i + 1) : "";
  }

  public static void copyFile(String pathCopy, String pathPaste) throws IOException {
    if (!Files.exists(Path.of(pathPaste))) {
      if (!JarService.runningFromJar()) {
        Files.copy(Paths.get(pathCopy), Paths.get(pathPaste));
      } else {
        InputStream stream = FileService.class.getResourceAsStream(pathCopy);
        Files.copy(stream, Paths.get(pathPaste));
      }
    }
  }
}
