package multithreaddownload;

/**
 * Created by mt.karimi on 2015/7/15.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DownloadException extends Exception {
    private String errorMessage;
    private int errorCode;

    public DownloadException() {
    }

    public DownloadException(String detailMessage) {
        super(detailMessage);
        this.errorMessage = detailMessage;
    }

    public DownloadException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.errorMessage = detailMessage;
    }

    public DownloadException(int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public DownloadException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.errorMessage = detailMessage;
    }

    public DownloadException(int errorCode, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        this.errorCode = errorCode;
        this.errorMessage = detailMessage;
    }

    public DownloadException(Throwable throwable) {
        super(throwable);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
