package mt.storage;

public enum SizeUnit {
    B(1),
    KB(SizeUnit.BYTES),
    MB(SizeUnit.BYTES * SizeUnit.BYTES),
    GB(SizeUnit.BYTES * SizeUnit.BYTES * SizeUnit.BYTES);
    private static final int BYTES = 1024;
    private final long inBytes;

    SizeUnit(long bytes) {
        this.inBytes = bytes;
    }

    public long inBytes() {
        return inBytes;
    }
}
