package fragments.graphic;

import java.util.HashMap;
import java.util.Iterator;

class HashBag<K> extends HashMap<K, Integer> {
    public HashBag() {
        super();
    }

    public int getCount(K value) {
        if (get(value) == null) {
            return 0;
        } else {
            return get(value);
        }
    }

    public void add(K value) {
        if (get(value) == null) {
            put(value, 1);
        } else {
            put(value, get(value) + 1);
        }
    }

    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}