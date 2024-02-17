package users;

public class PushUtil {
    private static String savedRegid = null;
    GCMHandler gcmHandler;
    BackendHandler backendHandler;
    PushUtil pushUtil = null;

    public PushUtil getInstance() {
        if (pushUtil == null)
            pushUtil = new PushUtil();
        return pushUtil;
    }
//
//    public void registerGCM(final String cityName) {
//        gcmHandler = new GCMHandler().getInstance();
//        backendHandler = new BackendHandler();
//        try {
//            gcmHandler.registerGCM(new TaskListener() {
//                @Override
//                public void onFail(String msg) {
//                }
//
//                @Override
//                public void onSuccess(String returnedRegId) {
//                    savedRegid = Pref.get("SavedRegID", "");
//                    if (savedRegid == "" || !savedRegid.equals(returnedRegId)) {
//                        Pref.put("SavedRegID", returnedRegId);
//                        if (returnedRegId != null && !returnedRegId.isEmpty()) {
//                            backendHandler.getInstance().registerBackend(returnedRegId, new TaskListener() {
//                                @Override
//                                public void onSuccess(String msg) {
//                                    backendHandler.getInstance().sendUserCat(BuildConfig.marketCatID);
//                                    Logger.e(msg);
//                                }
//
//                                @Override
//                                public void onFail(String msg) {
//                                }
//                            }, cityName);
//                        }
//                    } else {
//                        backendHandler.getInstance().sendUserUpdate(cityName);
//                    }
//                }
//            });
//        } catch (Exception ignored) {
//            AcraLSender acraLSender = new AcraLSender();
//            acraLSender.TrySend(ApplicationLoader.appInstance(), ignored, "PushUtil_56");
//        }
//    }
}
