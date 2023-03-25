import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ArrayGraph extends JFrame implements ActionListener {

    private final JTextField textField;
    private final JButton createButton;
    private final JButton sortButton;
    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private final ChartPanel chartPanel;
    private final JFreeChart chart;

    private final String[] options = {"Insertion Sort", "Quick Sort", "Merge Sort", "Tim Sort"};
    private final JComboBox<String> comboBox = new JComboBox<>(options);
    public ArrayGraph() {
        super("Chart with Input Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create input box panel
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel("Enter the length of the array: 2^");
        textField = new JTextField(10);
        textField.addActionListener(this);
        inputPanel.add(label);
        inputPanel.add(textField);

        // Create buttons
        createButton = new JButton("Create Graph");
        sortButton = new JButton("Next Step");

        // Add button to panel and event listener
        createButton.addActionListener(this);
        sortButton.addActionListener(this);
        inputPanel.add(createButton);
        inputPanel.add(sortButton);
        // Add combo box to panel
        inputPanel.add(comboBox);

        // Create chart
        chart = ChartFactory.createLineChart(
                "Array Graph", "Index", "Value", dataset,
                PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(chart);
        add(chartPanel);
        setTitle("Insertion Sort Animation");
        setSize(1300,1000);
        setLocationRelativeTo(null);
        add(inputPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        setVisible(true);
    }
    // Update the graph, it updates the graph with the array that is passed in with set a new set of value for the graph
    // so this won't create a new graph that covers on the old one
    // This is important for 2 reasons
    // 1. It will not eat up more memory
    // 2. When user stretch the window, we won't see the old graphs
    // This is interesting because it's similar to the fragment of android
    public void updateGraph(ArrayList<Integer> arr, int length) {
        dataset.clear();
        for (int i = 0; i < length; i++) {
            dataset.setValue(arr.get(i), "Data", Integer.toString(i + 1));
        }

    }


    // This is the event listener for the buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == textField) {
            try {
                int value = Integer.parseInt(textField.getText());
                dataset.addValue(value, "Series 1", "Category " + (dataset.getRowCount() + 1));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid integer value.");
            }
            textField.setText("");
        }
    }
    public JButton getGraphButton() {
        return createButton;
    }
    public JButton getSortButton() {
        return sortButton;
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
    public JTextField getTextField() {
        return textField;
    }
    public DefaultCategoryDataset getDataset() {
        return dataset;
    }


}
