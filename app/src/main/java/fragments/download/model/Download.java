package fragments.download.model;

import java.util.ArrayList;

public class Download {
    private ArrayList<Dlfile> dlfile = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     */
    public Download() {
    }

    /**
     * @param dlfile
     */
    public Download(ArrayList<Dlfile> dlfile) {
        this.dlfile = dlfile;
    }

    /**
     * @return The dlfile
     */
    public ArrayList<Dlfile> getDlfile() {
        return dlfile;
    }

    /**
     * @param dlfile The dlfile
     */
    public void setDlfile(ArrayList<Dlfile> dlfile) {
        this.dlfile = dlfile;
    }
}
