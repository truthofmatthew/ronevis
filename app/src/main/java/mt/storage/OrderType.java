package mt.storage;

import java.io.File;
import java.util.Comparator;

public enum OrderType {
    NAME,
    DATE,
    SIZE;

    public Comparator<File> getComparator() {
        switch (ordinal()) {
            case 0:
                return new Comparator<File>() {
                    @Override
                    public int compare(File lhs, File rhs) {
                        return lhs.getName().compareTo(rhs.getName());
                    }
                };
            case 1:
                return new Comparator<File>() {
                    @Override
                    public int compare(File lhs, File rhs) {
                        return (int) (rhs.lastModified() - lhs.lastModified());
                    }
                };
            case 2:
                return new Comparator<File>() {
                    @Override
                    public int compare(File lhs, File rhs) {
                        return (int) (lhs.length() - rhs.length());
                    }
                };
            default:
                break;
        }
        return null;
    }
}
