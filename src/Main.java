import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        for(int n = 5; n < 8; n++)
        {
            int length = (int) Math.pow(2, n);
            int[] arr = new int[length];
            for(int i = 0; i < Math.pow(2, n); i++)
            {
                arr[i] = (int)(Math.random() * ((int) Math.pow(2, 11)));
            }
//            System.out.println("Insertion Sort with " + length +" elements: " + Arrays.toString(insertionSort(arr, 0, length)));
//            System.out.println("Quick Sort with     " + length +" elements: " + Arrays.toString(quickSort(arr, 0, arr.length - 1)));
          System.out.println("Merge Sort with     " + length +" elements: " + Arrays.toString(mergeSort(arr)));
  //          System.out.println("Tim Sort with       " + length +" elements: " + Arrays.toString(timSort(arr)));
        }
    }
    public static int[] insertionSort(int[] arr, int start, int end){
        for(int nextPos = start + 1; nextPos < end; nextPos++){
            int nextVal = arr[nextPos];
            while(nextPos > start && arr[nextPos - 1] > nextVal)
            {
                arr[nextPos] = arr[nextPos - 1];
                nextPos--;
            }
            arr[nextPos] = nextVal;
        }
        return arr;
    }
    static public int[] quickSort(int[] arr, int first, int last){
        if(first < last)
        {
            int pivot = arr[first];
            int up = first;
            int down = last;
            do{
                while(arr[up] <= pivot && up != last)
                {
                    up++;
                }
                while(arr[down] >= pivot && down != first)
                {
                    down--;
                }
                if(up < down)
                {
                    int temp = arr[up];
                    arr[up] = arr[down];
                    arr[down] = temp;
                }
            }while(up < down);
            int temp = arr[first];
            arr[first] = arr[down];
            arr[down] = temp;
            int pivIndex = down;
            arr = quickSort(arr, first, pivIndex - 1);
            arr = quickSort(arr, pivIndex + 1, last);
        }
        return arr;
    }
    public static int[] mergeSort(int[] arr)
    {
        int tableSize = arr.length;
        if(tableSize > 1)
        {
            int halfSize = tableSize / 2;
            int[] leftTable = new int[halfSize];
            int[] rightTable = new int[tableSize -halfSize];
            leftTable = Arrays.copyOfRange(arr, 0, halfSize - 1);
            rightTable = Arrays.copyOfRange(arr, 0, halfSize - 1);
            mergeSort(leftTable);
            mergeSort(rightTable);
            int i = 0, j = 0, n = 0;
            for(; i < leftTable.length && j < rightTable.length; n++)
            {
                if(leftTable[i] < rightTable[j])
                {
                    arr[n] = leftTable[i];
                    i++;
                }
                else
                {
                    arr[n] = rightTable[j];
                    j++;
                }
            }
            if(i > j)
            {
                for(; j < rightTable.length; j++, n++)
                {
                    arr[n] = rightTable[j];
                }
            }
            else
            {
                for(; i < leftTable.length; i++, n++)
                {
                    arr[n] = leftTable[i];
                }
            }
            return arr;
        }
        else {
            return arr;
        }
    }
    public static int[] timSort(int[] arr)
    {
        int length = arr.length;
        int run = 32;
        for(int i = 0; i < length; i += run)
        {
            insertionSort(arr, i, Math.min((i + run), length));
        }
        return arr;
    }
}