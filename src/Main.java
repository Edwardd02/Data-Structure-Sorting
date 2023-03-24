import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*README: This program is a visual representation of the sorting algorithms Insertion Sort, Quick Sort, Merge Sort, and Tim Sort.
The user can input the length of the array and the program will generate a random array of that length.
The user can then choose which sorting algorithm to use and the program will show the array being sorted.
The user can choose to see the next step of the sorting algorithm.
It has normal functionality for Insertion Sort, Quick Sort, Merge Sort, and Tim Sort.
But for Drawing, in order to make the program more visually appealing, it goes
Through the sorting algorithm multiple times, and each time it draws the array in a different color.
Which make the algorithm unlike normal sorting algorithms(so it's actually slower), but it makes the recursive calls more visible, it calls the recursive actions one by one.
*/
public class Main {
    public static void main(String[] args) {

        for (int n = 5; n < 10; n++) {
            int length = (int) Math.pow(2, n);
            int[] arr = new int[length];
            for (int i = 0; i < Math.pow(2, n); i++) {
                arr[i] = ((int) (Math.random() * ((int) Math.pow(2, 10)))) * ((int) (Math.random() * 2) == 0 ? 1 : -1);
            }
            int[] insertionSort = insertionSort(arr, 0, length);
            System.out.println("Insertion Sort with ---" + length + " elements: " + Arrays.toString(insertionSort));
            int[] quickSort = quickSort(arr, 0, length - 1);
            System.out.println("Quick Sort with -------" + length + " elements: " + Arrays.toString(quickSort));
            int[] mergeSort = mergeSort(arr);
            System.out.println("Merge Sort with -------" + length + " elements: " + Arrays.toString(mergeSort));
            int[] timSort = timSort(arr);
            System.out.println("Tim Sort with ---------" + length + " elements: " + Arrays.toString(timSort));

        }

        // Create a new chart and set the dataset
        ChartWithInputBox chart = new ChartWithInputBox();
        int[] size = new int[1];
        size[0] = 5;
        ArrayList<Integer> randomArr = new ArrayList<>();
        JTextField textField = chart.getTextField();
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTextField(textField, size);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTextField(textField, size);

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTextField(textField, size);

            }


        });

        //var for insertion sort
        AtomicInteger start = new AtomicInteger(1);
        //var for quick sort
        ArrayList<Integer> quickSortIndexes = new ArrayList<>();
        //var for merge sort
        AtomicInteger step = new AtomicInteger(2);
        //var for tim sort
        AtomicBoolean isInserted = new AtomicBoolean(false);
        AtomicInteger timSortRun = new AtomicInteger(32);
        chart.getButton().addActionListener(e -> {
            // Get input value from text field
            try {
                start.set(1);
                quickSortIndexes.clear();
                step.set(2);
                isInserted.set(false);
                timSortRun.set(32);
                int lengthOfArray = (int) Math.pow(2, size[0]);
                quickSortIndexes.add(0);
                quickSortIndexes.add(lengthOfArray - 1);
                randomArr.clear();
                for (int i = 0; i < lengthOfArray; i++) {
                    randomArr.add(((int) (Math.random() * ((int) Math.pow(2, 10)))) * ((int) (Math.random() * 2) == 0 ? 1 : -1));
                }
                chart.updateGraph(randomArr, lengthOfArray);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(chart, "Invalid input: Please enter an integer.");
            }
        });
        AtomicInteger sortType = new AtomicInteger(1);
        chart.getComboBox().addActionListener(e1 -> {
            String selected = (String) chart.getComboBox().getSelectedItem();
            System.out.println(selected);
            switch (Objects.requireNonNull(selected)) {
                case "Insertion Sort" -> sortType.set(1);
                case "Quick Sort" -> sortType.set(2);
                case "Merge Sort" -> sortType.set(3);
                case "Tim Sort" -> sortType.set(4);
            }
        });

        chart.getSortButton().addActionListener(e -> {
            // Get input value from text field
            try {
                int lengthOfArray = (int) Math.pow(2, size[0]);
                if (sortType.get() == 1) {
                    insertionSortDrawing(randomArr, start, (int) Math.pow(2, size[0]), chart);
                    start.getAndIncrement();
                } else if (sortType.get() == 2) {
                    //arr = quickSort(arr, first, pivIndex - 1);
                    //arr = quickSort(arr, pivIndex + 1, last);
                    quickSortIndexes.sort(Comparator.naturalOrder());
                    ArrayList<Integer> temp = new ArrayList<>();
                    for (int i = 0; i < quickSortIndexes.size(); i += 2) {
                        if (quickSortIndexes.get(i) < quickSortIndexes.get(i + 1) && quickSortIndexes.get(i) != -1 && quickSortIndexes.get(i + 1) != lengthOfArray) {
                            int pivIndex = quickSortDrawing(randomArr, quickSortIndexes.get(i), quickSortIndexes.get(i + 1), lengthOfArray, chart);
                            if (quickSortIndexes.get(i + 1) - quickSortIndexes.get(i) > 1) {
                                temp.add(pivIndex - 1);
                                temp.add(pivIndex + 1);
                            }
                        }

                    }
                    quickSortIndexes.addAll(temp);


                } else if (sortType.get() == 3) {

                    for (int i = 0; i < lengthOfArray; i += step.get()) {
                        mergeSortDrawing(randomArr, i, Math.min(i + step.get(), lengthOfArray), chart);
                    }
                    if (step.get() < lengthOfArray) {
                        step.set(step.get() * 2);
                    }
                } else if (sortType.get() == 4) {
                    timSortRun.set(timSortDrawing(randomArr, lengthOfArray, isInserted, timSortRun, chart));
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(chart, "Invalid input: Please enter an integer.");
            }
        });
    }


    private static void updateTextField(JTextField textField, int[] length) {
        String text = textField.getText();
        System.out.println(text);
        if (text.length() > 0) {
            length[0] = Integer.parseInt(text);
        }

        // Do something with the updated text
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
            //set the first element as the pivot
            int pivot = arr[first];
            int up = first;
            int down = last;
            //partition the array
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
            //recursively sort the two sub-arrays
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

    public static void insertionSortDrawing(ArrayList<Integer> arr, AtomicInteger start, int end, ChartWithInputBox chart) {
        int nextPos = start.get();
        if (nextPos < end) {

            int nextVal = arr.get(start.get());
            while (nextPos > 0 && arr.get(nextPos - 1) > nextVal) {

                arr.set(nextPos, arr.get(nextPos - 1));
                nextPos--;
            }
            arr.set(nextPos, nextVal);
        }
        chart.updateGraph(arr, end);

    }

    public static int quickSortDrawing(ArrayList<Integer> arr, int first, int last, int length, ChartWithInputBox chart) {
        //set the first element as the pivot
        int pivot = arr.get(first);
        int up = first;
        int down = last;
        //partition the array
        do {
            while (arr.get(up) <= pivot && up != last) {
                up++;
            }
            while (arr.get(down) >= pivot && down != first) {
                down--;
            }
            if (up < down) {
                int temp = arr.get(up);
                arr.set(up, arr.get(down));
                arr.set(down, temp);
            }
        } while (up < down);
        int temp = arr.get(first);
        arr.set(first, arr.get(down));
        arr.set(down, temp);
        int pivIndex = down;
        //recursively sort the two sub-arrays
        chart.updateGraph(arr, length);
        return pivIndex;

    }

    private static void mergeSortDrawing(ArrayList<Integer> arr, int first, int last, ChartWithInputBox chart) {
        int tableSize = last - first;
        if (tableSize > 1) {
            int halfSize = tableSize / 2;
            int[] leftTable = new int[halfSize];
            int[] rightTable = new int[tableSize - halfSize];
            for (int i = 0; i < halfSize; i++) {
                leftTable[i] = arr.get(first + i);
            }
            for (int i = 0; i < tableSize - halfSize; i++) {
                rightTable[i] = arr.get(first + halfSize + i);
            }
            int i = 0, j = 0, n = first;
            for (; i < leftTable.length && j < rightTable.length; n++) {
                if (leftTable[i] < rightTable[j]) {
                    arr.set(n, leftTable[i]);
                    i++;
                } else {
                    arr.set(n, rightTable[j]);
                    j++;
                }
            }
            if (i > j) {
                for (; j < rightTable.length; j++, n++) {
                    arr.set(n, rightTable[j]);
                }
            } else {
                for (; i < leftTable.length; i++, n++) {
                    arr.set(n, leftTable[i]);
                }
            }

        }

        chart.updateGraph(arr, arr.size());
    }

    private static int timSortDrawing(ArrayList<Integer> randomArr, int lengthOfArray, AtomicBoolean isInserted, AtomicInteger timSortRun, ChartWithInputBox chart) {
        if (!isInserted.get()) {
            for(int i = 0; i < lengthOfArray; i += timSortRun.get()){
                for (int nextPos = i + 1; nextPos < lengthOfArray && nextPos < i + timSortRun.get(); nextPos++) {

                    int nextVal = randomArr.get(nextPos);
                    while (nextPos > i && randomArr.get(nextPos - 1) > nextVal) {

                        randomArr.set(nextPos, randomArr.get(nextPos - 1));
                        nextPos--;
                    }
                    randomArr.set(nextPos, nextVal);
                }
            }


            isInserted.set(true);
            chart.updateGraph(randomArr, lengthOfArray);
        } else {
            if (timSortRun.get() < lengthOfArray) {
                timSortRun.set(timSortRun.get() * 2);
            }
            for (int i = 0; i < lengthOfArray; i += timSortRun.get()) {
                mergeSortDrawing(randomArr, i, Math.min(i + timSortRun.get(), lengthOfArray), chart);
            }

            return timSortRun.get();
        }
        return timSortRun.get();
    }


}