package fragments.tool;

public class ArrayUtils {
    private static int[] removeDuplicate(int[] arr, boolean removeAllDuplicates) {
        int size = arr.length;
        for (int i = 0; i < size; ) {
            boolean flag = false;
            for (int j = i + 1; j < size; ) {
                if (arr[i] == arr[j]) {
                    flag = true;
                    shrinkArray(arr, j, size);
                    size--;
                } else
                    j++;
            }
            if (flag && removeAllDuplicates) {
                shrinkArray(arr, i, size);
                size--;
            } else
                i++;
        }
        int unique[] = new int[size];
        System.arraycopy(arr, 0, unique, 0, size);
        return unique;
    }

    public static int[] removeDuplicate(int[] arr) {
        return removeDuplicate(arr, false);
    }

    private static void shrinkArray(int[] arr, int pos, int size) {
        System.arraycopy(arr, pos + 1, arr, pos, size - 1 - pos);
    }

    public static void displayArray(int arr[]) {
        System.out.println("\n\nThe Array Is:-\n");
        for (int anArr : arr) {
            System.out.print(anArr + "\t");
        }
    }

    private static void initializeArray(int[] arr, int withValue) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = withValue;
        }
    }

    public static boolean contains(int arr[], int element) {
        for (int anArr : arr) {
            if (anArr == element)
                return true;
        }
        return false;
    }

    public static int removeElement(int[] arr, int element) {
        int size = arr.length;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == element) {
                shrinkArray(arr, i, arr.length);
                size--;
            }
        }
        return size;
    }

    public static int uniqueElementCount(int arr[]) {
        int count = 0;
        int uniqueCount = 0;
        int[] consideredElements = new int[arr.length];
        initializeArray(consideredElements, 0);
        for (int i = 0; i < arr.length; i++) {
            int element = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (element != arr[j] && !contains(consideredElements, element)) {
                    consideredElements[count++] = element;
                }
            }
        }
        for (int consideredElement : consideredElements)
            if (consideredElement != 0)
                uniqueCount++;
        return uniqueCount;
    }

    public static int[] add(int[] array, int value) {
        if (array == null)
            return new int[]{value};
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }
}