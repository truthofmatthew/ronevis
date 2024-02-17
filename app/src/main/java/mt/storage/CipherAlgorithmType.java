package mt.storage;

public enum CipherAlgorithmType {
    AES("AES"),
    DES("DES"),
    DESede("DESede"),
    RSA("RSA");
    private final String mName;

    CipherAlgorithmType(String name) {
        mName = name;
    }

    public String getAlgorithmName() {
        return mName;
    }
}
