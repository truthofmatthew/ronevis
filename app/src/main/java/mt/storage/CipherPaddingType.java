package mt.storage;

public enum CipherPaddingType {
    NoPadding("NoPadding"),
    PKCS5Padding("PKCS5Padding"),
    PKCS1Padding("PKCS1Padding"),
    OAEPWithSHA_1AndMGF1Padding("OAEPWithSHA-1AndMGF1Padding"),
    OAEPWithSHA_256AndMGF1Padding("OAEPWithSHA-256AndMGF1Padding");
    private final String mName;

    CipherPaddingType(String name) {
        mName = name;
    }

    public String getAlgorithmName() {
        return mName;
    }
}
