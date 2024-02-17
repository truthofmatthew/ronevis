package fragments.download.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by mt.karimi on 6/17/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface DownloadAPI {
    @Headers("Cache-Control: no-cache")
    @GET("/file.txt")
    Call<Download> dlfile();

    @GET("/file.txt")
    Call<FileObject> dlfiles();
}
