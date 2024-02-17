package fragments.objectHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

class FileLoader {
    private static final char EXTENSION_SEPARATOR = '.';
    private static final char DIRECTORY_SEPARATOR = '/';

    public static Reader openReader(String filename, boolean isResource) throws UnsupportedEncodingException, FileNotFoundException, MalformedURLException {
        return openReader(filename, isResource, "UTF-8");
    }

    private static Reader openReader(String filename, boolean isResource, String charset) throws UnsupportedEncodingException, FileNotFoundException, MalformedURLException {
        return new InputStreamReader(openInputStream(filename, isResource), charset);
    }

    public static InputStream openInputStream(String filename, boolean isResource) throws FileNotFoundException, MalformedURLException {
        if (isResource) {
            return FileLoader.class.getClassLoader().getResourceAsStream(filename);
        }
        return new FileInputStream(load(filename, false));
    }

    public static String read(String path, boolean isResource) throws IOException {
        return read(path, isResource, "UTF-8");
    }

    private static String read(String path, boolean isResource, String charset) throws IOException {
        return read(pathToUrl(path, isResource), charset);
    }

    @SuppressWarnings("resource")
    private static String read(URL url, String charset) throws IOException {
        return new Scanner(url.openStream(), charset).useDelimiter("\\A").next();
    }

    private static File load(String path, boolean isResource) throws MalformedURLException {
        return load(pathToUrl(path, isResource));
    }

    private static File load(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            return new File(url.getPath());
        }
    }

    private static URL pathToUrl(String path, boolean isResource) throws MalformedURLException {
        if (isResource) {
            return FileLoader.class.getClassLoader().getResource(path);
        }
        return new URL("file:/" + path);
    }

    public static String removeExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return filename;
        } else {
            return filename.substring(0, index);
        }
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return filename;
        } else {
            return filename.substring(index);
        }
    }

    private static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(EXTENSION_SEPARATOR);
        int lastDirSeparator = filename.lastIndexOf(DIRECTORY_SEPARATOR);
        if (lastDirSeparator > extensionPos) {
            return -1;
        }
        return extensionPos;
    }

    public String extension(String fullPath, char extensionSeparator) {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        return fullPath.substring(dot + 1);
    }

    public String filename(String fullPath, char extensionSeparator, char pathSeparator) {
        int dot = fullPath.lastIndexOf(extensionSeparator);
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(sep + 1, dot);
    }

    public String path(String fullPath, char pathSeparator) {
        int sep = fullPath.lastIndexOf(pathSeparator);
        return fullPath.substring(0, sep);
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
}