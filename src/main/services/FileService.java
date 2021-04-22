package main.services;

import main.exceptions.DirectoryNotCreated;
import main.utils.enums.ResourcePath;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FileService {
  private static final String FILE_PROTOCOL = "file:" + File.separator + File.separator + File.separator;
  
  private FileService() {}
  
  public static Set<File> getAllFilesInDirectory(String directoryPath) {
    return Arrays.stream(Objects.requireNonNull(new File(directoryPath).listFiles()))
                 .filter(File::isFile)
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
}
