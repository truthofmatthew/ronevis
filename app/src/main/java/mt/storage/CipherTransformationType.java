package mt.storage;

class CipherTransformationType {
    private static final String _ID = "/";
    public static final String AES_CBC_NoPadding = CipherAlgorithmType.AES + _ID + CipherModeType.CBC + _ID + CipherPaddingType.NoPadding;
    public static final String AES_CBC_PKCS5Padding = CipherAlgorithmType.AES + _ID + CipherModeType.CBC + _ID + CipherPaddingType.PKCS5Padding;
    public static final String AES_ECB_NoPadding = CipherAlgorithmType.AES + _ID + CipherModeType.ECB + _ID + CipherPaddingType.NoPadding;
    public static final String AES_ECB_PKCS5Padding = CipherAlgorithmType.AES + _ID + CipherModeType.ECB + _ID + CipherPaddingType.PKCS5Padding;
    public static final String DES_CBC_NoPadding = CipherAlgorithmType.DES + _ID + CipherModeType.CBC + _ID + CipherPaddingType.NoPadding;
    public static final String DES_CBC_PKCS5Padding = CipherAlgorithmType.DES + _ID + CipherModeType.CBC + _ID + CipherPaddingType.PKCS5Padding;
    public static final String DES_ECB_NoPadding = CipherAlgorithmType.DES + _ID + CipherModeType.ECB + _ID + CipherPaddingType.NoPadding;
    public static final String DES_ECB_PKCS5Padding = CipherAlgorithmType.DES + _ID + CipherModeType.ECB + _ID + CipherPaddingType.PKCS5Padding;
    public static final String DESede_CBC_NoPadding = CipherAlgorithmType.DESede + _ID + CipherModeType.CBC + _ID + CipherPaddingType.NoPadding;
    public static final String DESede_CBC_PKCS5Padding = CipherAlgorithmType.DESede + _ID + CipherModeType.CBC + _ID + CipherPaddingType.PKCS5Padding;
    public static final String DESede_ECB_NoPadding = CipherAlgorithmType.DESede + _ID + CipherModeType.ECB + _ID + CipherPaddingType.NoPadding;
    public static final String DESede_ECB_PKCS5Padding = CipherAlgorithmType.DESede + _ID + CipherModeType.ECB + _ID + CipherPaddingType.PKCS5Padding;
    public static final String RSA_ECB_PKCS1Padding = CipherAlgorithmType.RSA + _ID + CipherModeType.ECB + _ID + CipherPaddingType.PKCS1Padding;
    public static final String RSA_ECB_OAEPWithSHA_1AndMGF1Padding = CipherAlgorithmType.RSA + _ID + CipherModeType.ECB + _ID + CipherPaddingType.OAEPWithSHA_1AndMGF1Padding;
    public static final String RSA_ECB_OAEPWithSHA_256AndMGF1Padding = CipherAlgorithmType.RSA + _ID + CipherModeType.ECB + _ID + CipherPaddingType.OAEPWithSHA_256AndMGF1Padding;
}
