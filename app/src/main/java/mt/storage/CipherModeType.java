package mt.storage;

public enum CipherModeType {
    CBC("CBC"),
    ECB("ECB");
    private final String mName;

    CipherModeType(String name) {
        mName = name;
    }

    public String getAlgorithmName() {
        return mName;
    }
}
