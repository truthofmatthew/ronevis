package fragments.download;

import android.os.StrictMode;

import com.google.firebase.crash.FirebaseCrash;

import org.acra.sender.AcraLSender;

import fragments.download.model.Download;
import fragments.download.model.DownloadAPI;
import fragments.lisetener.RespondListener;
import fragments.tool.preferences.Pref;
import mt.karimi.ronevis.ApplicationLoader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mt.karimi on 2015/7/8.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class DataSource {
    private static final DataSource sDataSource = new DataSource();

    public static DataSource getInstance() {
        return sDataSource;
    }

    public void getAllFiles(final RespondListener respondListener) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.ronevis.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            DownloadAPI downloadAPI = retrofit.create(DownloadAPI.class);
            Call<Download> repos = downloadAPI.dlfile();
            repos.enqueue(new Callback<Download>() {
                @Override
                public void onResponse(Call<Download> call, Response<Download> response) {
                    respondListener.onSuccess(response);
                }

                @Override
                public void onFailure(Call<Download> call, Throwable t) {
                    respondListener.onFail(t);
                }
            });
        } catch (Exception ignored) {
            FirebaseCrash.report(ignored);

        }
    }
}
