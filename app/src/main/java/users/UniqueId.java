package users;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by mt.karimi on 6/23/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class UniqueId {
    private static final String SHARED_PREF_KEY = "ronevisUser";
    private static final String ID_KEY = "id";
    private static String sID = null;

    public synchronized static String id(Context context) {
        if (sID == null) {
            SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_KEY, 0);
            sID = pref.getString(ID_KEY, "");
            if (sID == "") {
                sID = generateAndStoreUserId(pref);
            }
        }
        return sID;
    }

    public static String getHash(String stringToHash) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] result = null;
        try {
            result = digest != null ? digest.digest(stringToHash.getBytes("UTF-8")) : new byte[0];
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : result != null ? result : new byte[0]) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    private synchronized static String generateAndStoreUserId(SharedPreferences pref) {
        String id = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ID_KEY, id);
        editor.commit();
        return id;
    }
}