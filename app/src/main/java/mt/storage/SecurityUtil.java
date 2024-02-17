package mt.storage;

import org.acra.sender.AcraLSender;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import fragments.FireHelper;
import mt.karimi.ronevis.ApplicationLoader;

class SecurityUtil {
    public static byte[] encrypt(byte[] content, int encryptionMode, final byte[] secretKey, final byte[] ivx) {
        if (secretKey.length != 16 || ivx.length != 16) {
            throw new RuntimeException("Set the encryption parameters correctly. The must be 16 length long each");
        }
        try {
            final SecretKey secretkey = new SecretKeySpec(secretKey, CipherAlgorithmType.AES.getAlgorithmName());
            final IvParameterSpec IV = new IvParameterSpec(ivx);
            final String transformation = CipherTransformationType.AES_CBC_PKCS5Padding;
            final Cipher decipher = Cipher.getInstance(transformation);
            decipher.init(encryptionMode, secretkey, IV);
            return decipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to encrypt/descrypt - Unknown Algorithm", e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("Failed to encrypt/descrypt- Unknown Padding", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Failed to encrypt/descrypt - Invalid Key", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("Failed to encrypt/descrypt - Invalid Algorithm Parameter", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Failed to encrypt/descrypt", e);
        }
    }

    public String xor(String msg, String key) {
        try {
            final String UTF_8 = "UTF-8";
            byte[] msgArray;
            msgArray = msg.getBytes(UTF_8);
            byte[] keyArray = key.getBytes(UTF_8);
            byte[] out = new byte[msgArray.length];
            for (int i = 0; i < msgArray.length; i++) {
                out[i] = (byte) (msgArray[i] ^ keyArray[i % keyArray.length]);
            }
            return new String(out, UTF_8);
        } catch (UnsupportedEncodingException ignored) {
            FireHelper fireHelper = new FireHelper();
            fireHelper.SendReport(ignored);
        }
        return null;
    }
}
