package mt.storage;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

import mt.storage.SimpleStorage.StorageType;

public interface Storage {
    StorageType getStorageType();

    boolean createDirectory(String name);

    boolean createDirectory(String name, boolean override);

    boolean deleteDirectory(String name);

    boolean isDirectoryExists(String name);

    boolean createFile(String directoryName, String fileName, String content);

    boolean createFile(String directoryName, String fileName, Storable storable);

    boolean createFile(String directoryName, String fileName, Bitmap bitmap);

    boolean createFile(String directoryName, String fileName, byte[] content);

    boolean deleteFile(String directoryName, String fileName);

    boolean isFileExist(String directoryName, String fileName);

    byte[] readFile(String directoryName, String fileName);

    byte[] readFile(String fileName);

    String readTextFile(String directoryName, String fileName);

    String readTextFile(String fileName);

    void appendFile(String directoryName, String fileName, String content);

    void appendFile(String directoryName, String fileName, byte[] bytes);

    List<File> getNestedFiles(String directoryName);

    List<File> getFiles(String directoryName, String matchRegex);

    List<File> getFiles(String directoryName, OrderType orderType);

    File getFile(String name);

    File getFile(String directoryName, String fileName);

    void rename(File file, String newName);

    double getSize(File file, SizeUnit unit);

    long getFreeSpace(SizeUnit sizeUnit);

    long getUsedSpace(SizeUnit sizeUnit);

    void copy(File file, String directoryName, String fileName);

    void move(File file, String directoryName, String fileName);
}
