import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] timSort = new int[0];
        for (int n = 5; n < 10; n++) {
            int length = (int) Math.pow(2, n);
            int[] arr = new int[length];
            for (int i = 0; i < Math.pow(2, n); i++) {
                arr[i] = ((int) (Math.random() * ((int) Math.pow(2, 11)))) * ((int) (Math.random() * 2) == 0 ? 1 : -1);
            }
            int[] insertionSort = insertionSort(arr, 0, length);
            System.out.println("Insertion Sort with ---" + length + " elements: " + Arrays.toString(insertionSort));
            int[] quickSort = quickSort(arr, 0, length - 1);
            System.out.println("Quick Sort with -------" + length + " elements: " + Arrays.toString(quickSort));
            int[] mergeSort = mergeSort(arr);
            System.out.println("Merge Sort with -------" + length + " elements: " + Arrays.toString(mergeSort));
            timSort = timSort(arr);
            System.out.println("Tim Sort with ---------" + length + " elements: " + Arrays.toString(timSort));

        }

        // Create a new chart and set the dataset
        ChartWithInputBox chart = new ChartWithInputBox();
        chart.setSize(1800, 1800);
        chart.getButton().addActionListener(e -> {
            // Get input value from text field
            String inputText = chart.getTextField().getText();
            try {
                int lengthOfArray = Integer.parseInt(inputText);
                int length = (int) Math.pow(2, lengthOfArray);
                int[] randomArr = new int[length];
                for (int i = 0; i < Math.pow(2, lengthOfArray); i++) {
                    randomArr[i] = ((int) (Math.random() * ((int) Math.pow(2, 11)))) * ((int) (Math.random() * 2) == 0 ? 1 : -1);
                }
                chart.createGraph(randomArr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(chart, "Invalid input: Please enter an integer.");
            }
        });

        chart.getSortButton().addActionListener(e -> {
            // Get input value from text field
            String inputText = chart.getTextField().getText();
            try {
                int lengthOfArray = Integer.parseInt(inputText);
                int length = (int) Math.pow(2, lengthOfArray);
                int[] randomArr = new int[length];
                for (int i = 0; i < Math.pow(2, lengthOfArray); i++) {
                    randomArr[i] = ((int) (Math.random() * ((int) Math.pow(2, 11)))) * ((int) (Math.random() * 2) == 0 ? 1 : -1);
                }
                insertionSortDrawing(randomArr, 0, length, chart);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(chart, "Invalid input: Please enter an integer.");
            }
        });
    }

    public static void insertionSortDrawing(int[] arr, int start, int end, ChartWithInputBox chart) {
            for (int nextPos = start + 1; nextPos < end; nextPos++) {

                int nextVal = arr[nextPos];
                while (nextPos > start && arr[nextPos - 1] > nextVal) {

                    arr[nextPos] = arr[nextPos - 1];
                    nextPos--;
                }
                arr[nextPos] = nextVal;
            }
            chart.createGraph(arr);

    }

    public static int[] insertionSort(int[] arr, int start, int end) {
        for (int nextPos = start + 1; nextPos < end; nextPos++) {

            int nextVal = arr[nextPos];
            while (nextPos > start && arr[nextPos - 1] > nextVal) {

                arr[nextPos] = arr[nextPos - 1];
                nextPos--;
            }
            arr[nextPos] = nextVal;
        }
        return arr;
    }

    static public int[] quickSort(int[] arr, int first, int last) {
        if (first < last) {
            int pivot = arr[first];
            int up = first;
            int down = last;
            do {
                while (arr[up] <= pivot && up != last) {
                    up++;
                }
                while (arr[down] >= pivot && down != first) {
                    down--;
                }
                if (up < down) {
                    int temp = arr[up];
                    arr[up] = arr[down];
                    arr[down] = temp;
                }
            } while (up < down);
            int temp = arr[first];
            arr[first] = arr[down];
            arr[down] = temp;
            int pivIndex = down;
            arr = quickSort(arr, first, pivIndex - 1);
            arr = quickSort(arr, pivIndex + 1, last);
        }
        return arr;
    }

    public static int[] mergeSort(int[] arr) {
        int tableSize = arr.length;
        if (tableSize > 1) {
            int halfSize = tableSize / 2;
            int[] leftTable;
            int[] rightTable;
            leftTable = Arrays.copyOfRange(arr, 0, halfSize);
            rightTable = Arrays.copyOfRange(arr, halfSize, tableSize);
            mergeSort(leftTable);
            mergeSort(rightTable);
            int i = 0, j = 0, n = 0;
            for (; i < leftTable.length && j < rightTable.length; n++) {
                if (leftTable[i] < rightTable[j]) {
                    arr[n] = leftTable[i];
                    i++;
                } else {
                    arr[n] = rightTable[j];
                    j++;
                }
            }
            if (i > j) {
                for (; j < rightTable.length; j++, n++) {
                    arr[n] = rightTable[j];
                }
            } else {
                for (; i < leftTable.length; i++, n++) {
                    arr[n] = leftTable[i];
                }
            }
        }
        return arr;
    }

    public static int[] timSort(int[] arr) {
        int length = arr.length;
        int run = 16;
        for (int i = 0; i < length; i += run) {
            insertionSort(arr, i, Math.min((i + run), length));
        }
        if (run > length) {
            return arr;
        } else {
            for (int i = run * 2; i <= length; i *= 2) {
                for (int j = 0; j < length; j += i) {
                    int[] temp = Arrays.copyOfRange(arr, j, Math.min((j + i), length));
                    mergeSort(temp);
                    System.arraycopy(temp, 0, arr, j, temp.length);
                }
            }
        }
        return arr;

    }
}