package fragments.lisetener;

import fragments.download.model.Download;
import retrofit2.Response;

/**
 * Created by mt.karimi on 2/3/2015.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public interface RespondListener {
    void onSuccess(Response<Download> response);

    void onFail(Throwable t);
}
