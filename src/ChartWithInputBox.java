import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartWithInputBox extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton createButton;
    private JButton sortButton;
    private DefaultCategoryDataset dataset;

    private JFreeChart chart;
    public ChartWithInputBox() {
        super("Chart with Input Box");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create input box panel
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel("Enter the length of the array: 2^");
        textField = new JTextField(10);
        textField.addActionListener(this);
        inputPanel.add(label);
        inputPanel.add(textField);

        // Create button
        createButton = new JButton("Create Graph");
        sortButton = new JButton("Start Sorting");
        createButton.addActionListener(this);
        sortButton.addActionListener(this);
        inputPanel.add(createButton);
        inputPanel.add(sortButton);

        // Create chart panel

        ChartPanel chartPanel = new ChartPanel(chart);

        // Add input box panel and chart panel to frame
        add(inputPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public JButton getButton() {
        return createButton;
    }
    public JButton getSortButton() {
        return sortButton;
    }
    public JTextField getTextField() {
        return textField;
    }
    public DefaultCategoryDataset getDataset() {
        return dataset;
    }
    public void createGraph(int[] arr) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < arr.length; i++) {
            dataset.addValue(arr[i], "Data", Integer.toString(i + 1));
        }

        chart = ChartFactory.createLineChart("Array Graph", "Index", "Value", dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }



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


}

