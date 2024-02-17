package mt.storage;

import android.os.Build;

import org.acra.sender.AcraLSender;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import fragments.FireHelper;
import mt.karimi.ronevis.ApplicationLoader;

public class SimpleStorageConfiguration {
    private final int mChunkSize;
    private final boolean mIsEncrypted;
    private final byte[] mIvParameter;
    private final byte[] mSecretKey;

    private SimpleStorageConfiguration(Builder builder) {
        mChunkSize = builder._chunkSize;
        mIsEncrypted = builder._isEncrypted;
        mIvParameter = builder._ivParameter;
        mSecretKey = builder._secretKey;
    }

    public int getChuckSize() {
        return mChunkSize;
    }

    public boolean isEncrypted() {
        return mIsEncrypted;
    }

    public byte[] getSecretKey() {
        return mSecretKey;
    }

    public byte[] getIvParameter() {
        return mIvParameter;
    }

    public static class Builder {
        private static final String UTF_8 = "UTF-8";
        private int _chunkSize = 8 * 1024;
        private boolean _isEncrypted = false;
        private byte[] _ivParameter = null;
        private byte[] _secretKey = null;

        public Builder() {
        }

        public SimpleStorageConfiguration build() {
            return new SimpleStorageConfiguration(this);
        }

        public Builder setChuckSize(int chunkSize) {
            _chunkSize = chunkSize;
            return this;
        }

        public Builder setEncryptContent(String ivx, String secretKey) {
            _isEncrypted = true;
            try {
                _ivParameter = ivx.getBytes(UTF_8);
            } catch (UnsupportedEncodingException ignored) {
                FireHelper fireHelper = new FireHelper();
                fireHelper.SendReport(ignored);
            }
            try {
                int iterationCount = 1000;
                int keyLength = 128;
                SecureRandom random = new SecureRandom();
                byte[] salt = new byte[16];
                random.nextBytes(salt);
                KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount, keyLength);
                SecretKeyFactory keyFactory;
                if (Build.VERSION.SDK_INT >= 19) {
                    keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1And8bit");
                } else {
                    keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                }
                _secretKey = keyFactory.generateSecret(keySpec).getEncoded();
            } catch (InvalidKeySpecException | NoSuchAlgorithmException ignored) {
                FireHelper fireHelper = new FireHelper();
                fireHelper.SendReport(ignored);
            }
            return this;
        }
    }
}
