package com.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.awt.event.ComponentAdapter; // Import added
import java.awt.event.ComponentEvent; // Import added
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;


public class DataVisualizationGUI extends JFrame {

    private JComboBox<String> datasetComboBox;
    private JComboBox<String> visualizationComboBox;
    private JButton showStorytellingButton;
    private JButton showVisualizationButton;
    private JButton showDashboardButton;
    private JButton showTableButton;
    private JPanel mainPanel;
    private JPanel contentPanel; // Single panel for content
    private Map<String, Map<String, Double[]>> datasets; // To hold multiple columns of data
    private Map<String, String[][]> datasetTables; // To hold dataset tables
    private JPanel textPanel; // Panel for the label
    private JLabel imageLabel; // Label for the text
    private JLabel imageTextLabel;

    public DataVisualizationGUI() {
        // Set UI Manager properties for modern look
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));
        UIManager.put("Label.font", new Font("Segoe UI", Font.BOLD | Font.ITALIC, 16));

        // Initialize components
        datasetComboBox = createStyledComboBox(new String[]{"Dataset 1", "Dataset 2", "Dataset 3"});
        showStorytellingButton = createStyledButton("SHOW STORY");
        visualizationComboBox = createStyledComboBox(new String[]{"Bar Chart", "Line Chart", "Area Chart", "Scatter Plot", "Histogram", "Pie Chart", "Bubble Chart"});
        showVisualizationButton = createStyledButton("SHOW VISUALIZATION");
        showDashboardButton = createStyledButton("DASHBOARD");
        showTableButton = createStyledButton("SHOW TABLE");

        datasets = new HashMap<>();
        datasetTables = new HashMap<>();
        
        loadCSVData("/home/akshay/my-project/target/classes/dataset1.csv", "Dataset 1");
        loadCSVData("dataset2.csv", "Dataset 2");
        loadCSVData("dataset3.csv", "Dataset 3");

        // Set up panels
        mainPanel = new JPanel();
        contentPanel = new JPanel(); // Single panel for content

        // Configure the main panel
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(datasetComboBox);
        mainPanel.add(showStorytellingButton);
        mainPanel.add(visualizationComboBox);
        mainPanel.add(showVisualizationButton);
        mainPanel.add(showDashboardButton);
        mainPanel.add(showTableButton);

        // Configure contentPanel
        contentPanel.setLayout(new CardLayout());
        contentPanel.setBackground(Color.LIGHT_GRAY);

        // Load default image
        imageLabel = new JLabel(); // Create a label for the image
        ImageIcon defaultImage = new ImageIcon("/home/akshay/my-project/src/main/resources/pexels1.jpg"); // Load the image
        imageLabel.setIcon(defaultImage); // Set the image to the label
        contentPanel.add(imageLabel); // Add the image label to the content panel

        // Add transparent panel with text
        textPanel = new JPanel();
        textPanel.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white background
        textPanel.setOpaque(false); // Make the panel transparent
        imageTextLabel = new JLabel("HEALTH AND FAMILY WELFARE DATA VISUALIZATION");
        imageTextLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font size and style
        imageTextLabel.setForeground(Color.WHITE); // Set text color to white for visibility
        textPanel.add(imageTextLabel); // Add the label to the panel

        // Position the text panel above the image
        textPanel.setBounds(50, 50, 800, 50); // Set size and position of the panel
        contentPanel.add(textPanel); // Add the text panel to the content panel

        // Configure frame
        setTitle("Data Visualization");
        setSize(1200, 800); // Increased size to accommodate larger buttons and content
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        showVisualizationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContent("visualization");
            }
        });

        showDashboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContent("dashboard");
            }
        });

        showTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContent("table");
            }
        });

        showStorytellingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showContent("storytelling");
            }
        });
    }

    // Method to display the default image
    private void displayDefaultImage(String imagePath) {
        try {
            BufferedImage img = ImageIO.read(new File("/home/akshay/my-project/src/main/resources/pexels1.jpg"));
            contentPanel.setSize(getContentPane().getSize());
            int panelWidth = contentPanel.getWidth();
            int panelHeight = contentPanel.getHeight();

            Image scaledImage = img.getScaledInstance(panelWidth, panelHeight, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(imageIcon); // Update image in the label

            contentPanel.revalidate();
            contentPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Call this method after setting up your frame and panels
    private void setupUI() {
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        contentPanel.setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        displayDefaultImage("/home/akshay/my-project/src/main/resources/pexels1.jpg");
    }
    
// Method to load CSV data into the datasets map and datasetTables map
    private void loadCSVData(String csvFilePath, String datasetName) {
        Map<String, Double[]> data = new HashMap<>();
        String[][] tableData = new String[36][6]; // Assuming 36 rows and 6 columns, adjust as needed
        int rowIndex = 0;

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                String state = line[1]; // Assuming "Name of State / UT" is in column 1
                Double activeCases = Double.parseDouble(line[2]); // Assuming "Active Cases" is in column 2
                Double curedCases = Double.parseDouble(line[3]); // Assuming "Cured Cases" is in column 3
                Double deaths = Double.parseDouble(line[4]); // Assuming "Deaths" is in column 4
                Double totalConfirmed = Double.parseDouble(line[5]); // Assuming "Total Confirmed Cases" is in column 5
                
                data.put(state, new Double[]{activeCases, curedCases, deaths, totalConfirmed});

                // Populate table data
                tableData[rowIndex] = line;
                rowIndex++;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        
        datasets.put(datasetName, data);
        datasetTables.put(datasetName, tableData);
    }

    private JFreeChart createChart(String visualizationType, Map<String, Double[]> data, int columnIndex) {
    JFreeChart chart = null;
    
    switch (visualizationType) {
        case "Bar Chart":
            CategoryDataset barDataset = createDataset(data, columnIndex);
            chart = ChartFactory.createBarChart(
                "COVID-19 Data by State/UT",  // Chart title
                "State/UT",                   // Domain axis label
                "Total Confirmed Cases",            // Range axis label
                barDataset,                   // Data
                PlotOrientation.HORIZONTAL,   // Orientation
                true,                         // Include legend
                true,                         // Tooltips
                true                          // URLs
            );

            // Apply modern styling to chart
            chart.setBackgroundPaint(Color.WHITE);  // Set chart background color
            chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 26)); // Title font
            chart.getTitle().setPaint(Color.DARK_GRAY); // Title color

            // Customize the plot
            CategoryPlot plot = chart.getCategoryPlot(); // For bar chart
            plot.setBackgroundPaint(Color.LIGHT_GRAY); // Set plot background color
            plot.setDomainGridlinePaint(Color.WHITE);  // Domain grid lines
            plot.setRangeGridlinePaint(Color.WHITE);   // Range grid lines

            // Customize axis fonts and colors
            plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 16));  // X-axis label font
            plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 16));   // Y-axis label font
            plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14)); // X-axis tick font
            plot.getDomainAxis().setTickLabelPaint(Color.DARK_GRAY); // X-axis tick color
            plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14)); // Y-axis tick font
            plot.getRangeAxis().setTickLabelPaint(Color.DARK_GRAY); // Y-axis tick color

            // Customize legend
            chart.getLegend().setItemFont(new Font("Segoe UI", Font.BOLD, 16)); // Legend font
            chart.getLegend().setBackgroundPaint(Color.WHITE); // Legend background
            break;

        case "Line Chart":
            CategoryDataset lineDataset = createLineDataset(data); // Updated for multiple lines
            chart = ChartFactory.createLineChart(
                "COVID-19 Data by State/UT",
                "State/UT",
                "Active vs Cured Cases",
                lineDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true
            );
            customizeChart(chart, "Line Chart");
            break;
            
        case "Area Chart":
            CategoryDataset areaDataset = createDataset(data, columnIndex);
            chart = ChartFactory.createAreaChart(
                "COVID-19 Data by State/UT",  // Chart title
                "State/UT",                   // Domain axis label
                "Number of Deaths",            // Range axis label
                areaDataset,                  // Data
                org.jfree.chart.plot.PlotOrientation.VERTICAL, // Orientation
                true,                         // Include legend
                true,                         // Tooltips
                true                         // URLs
            );
            break;
        
        case "Scatter Plot":
    XYDataset scatterDataset = createScatterDataset(data);
    chart = ChartFactory.createScatterPlot(
        "COVID-19 Data by State/UT",  // Chart title
        "State/UT",                      // Domain axis label (X-axis)
        "Total Cases & Deaths",            // Range axis label (Y-axis)
        scatterDataset,               // Data
        PlotOrientation.VERTICAL,     // Orientation
        true,                         // Include legend
        true,                         // Tooltips
        true                          // URLs
    );
    customizeChart(chart, "Scatter Plot"); // Apply additional customizations
    break;
        
        case "Histogram":
            chart = ChartFactory.createHistogram(
                "COVID-19 Data Distribution", // Chart title
                "Active Cases",           // X-axis label
                "Frequency",                 // Y-axis label
                createHistogramDataset(data), // Data
                PlotOrientation.VERTICAL,    // Orientation
                true,                        // Include legend
                true,                        // Tooltips
                true                         // URLs
            );
            customizeChart(chart, "Histogram");
            break;
        
        case "Pie Chart":
            PieDataset pieDataset = createPieDataset(data, columnIndex);
            chart = ChartFactory.createPieChart(
                "COVID-19 Deaths by State/UT",  // Chart title
                pieDataset,                          // Data
                true,                                // Include legend
                true,                                // Tooltips
                true                                // URLs
            );
            break;
        
        case "Bubble Chart":
            XYZDataset bubbleDataset = createBubbleDataset(data);
            chart = ChartFactory.createBubbleChart(
                "COVID-19 Bubble Chart",        // Chart title
                "Active Cases",                 // X-axis label
                "Deaths",                       // Y-axis label
                bubbleDataset,                  // Data
                PlotOrientation.VERTICAL,       // Orientation
                true,                           // Include legend
                true,                           // Tooltips
                true                            // URLs
            );
            customizeChart(chart, "Bubble Chart");
            break;
        // Add more cases if needed
    }

    return chart;
}

private void customizeChart(JFreeChart chart, String chartType) {
    chart.setBackgroundPaint(Color.WHITE); // Set chart background color
    chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 26)); // Title font
    chart.getTitle().setPaint(Color.DARK_GRAY); // Title color

    if (chartType.equals("Bar Chart") || chartType.equals("Line Chart") || chartType.equals("Area Chart")) {
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY); // Set plot background color
        plot.setDomainGridlinePaint(Color.WHITE);  // Domain grid lines
        plot.setRangeGridlinePaint(Color.WHITE);   // Range grid lines
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 16));  // X-axis label font
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 16));   // Y-axis label font
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14)); // X-axis tick font
        plot.getDomainAxis().setTickLabelPaint(Color.DARK_GRAY); // X-axis tick color
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.BOLD, 14)); // Y-axis tick font
        plot.getRangeAxis().setTickLabelPaint(Color.DARK_GRAY); // Y-axis tick color
        chart.getLegend().setItemFont(new Font("Segoe UI", Font.BOLD, 16)); // Legend font
        chart.getLegend().setBackgroundPaint(Color.WHITE); // Legend background
    }
}

    private CategoryDataset createDataset(Map<String, Double[]> data, int columnIndex) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Map.Entry<String, Double[]> entry : data.entrySet()) {
        dataset.addValue(entry.getValue()[columnIndex], "Metric", entry.getKey());
    }

    return dataset;
}

private CategoryDataset createLineDataset(Map<String, Double[]> data) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Map.Entry<String, Double[]> entry : data.entrySet()) {
        dataset.addValue(entry.getValue()[0], "Active Cases", entry.getKey()); // Example for first line
        dataset.addValue(entry.getValue()[1], "Cured Cases", entry.getKey()); // Example for second line
    }

    return dataset;
}

private void customizeLineChart(JFreeChart chart) {
    XYPlot plot = chart.getXYPlot();
    plot.setDomainGridlinePaint(Color.WHITE);
    plot.setRangeGridlinePaint(Color.WHITE);

    // Customize the renderers for different lines
    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
    renderer.setSeriesPaint(0, Color.RED); // First line color
    renderer.setSeriesPaint(1, Color.BLUE); // Second line color
    plot.setRenderer(renderer);
}

private XYDataset createScatterDataset(Map<String, Double[]> data) {
    DefaultXYDataset dataset = new DefaultXYDataset();
    
    // Initialize data arrays
    double[][] totalCasesData = new double[2][data.size()];
    double[][] deathsData = new double[2][data.size()];

    int i = 0;
    for (Map.Entry<String, Double[]> entry : data.entrySet()) {
        totalCasesData[0][i] = i; // X-axis values (Index or State)
        totalCasesData[1][i] = entry.getValue()[3]; // Y-axis values for Total Cases

        deathsData[0][i] = i; // X-axis values (Index or State)
        deathsData[1][i] = entry.getValue()[2]; // Y-axis values for Deaths

        i++;
    }

    dataset.addSeries("Total Cases", totalCasesData);
    dataset.addSeries("Deaths", deathsData);
    
    return dataset;
}

private XYZDataset createBubbleDataset(Map<String, Double[]> data) {
    DefaultXYZDataset dataset = new DefaultXYZDataset();
    double[][] bubbleData = new double[3][data.size()]; // X, Y, Size series

    int i = 0;
    for (Map.Entry<String, Double[]> entry : data.entrySet()) {
        bubbleData[0][i] = entry.getValue()[0]; // Active Cases (X-axis)
        bubbleData[1][i] = entry.getValue()[2]; // Deaths (Y-axis)
        bubbleData[2][i] = entry.getValue()[3]; // Total Confirmed Cases (Size)

        i++;
    }

    dataset.addSeries("Bubble Chart Data", bubbleData);
    return dataset;
}

private HistogramDataset createHistogramDataset(Map<String, Double[]> data) {
    HistogramDataset dataset = new HistogramDataset();
    
    // Extract active cases from the data
    double[] activeCases = data.values().stream()
        .mapToDouble(arr -> arr[0]) // Assuming active cases are at index 0
        .toArray();

    // Create the histogram dataset
    dataset.addSeries("Active Cases", activeCases, 10); // 10 bins, adjust as needed

    return dataset;
}

private PieDataset createPieDataset(Map<String, Double[]> data, int columnIndex) {
    DefaultPieDataset dataset = new DefaultPieDataset<>();

    for (Map.Entry<String, Double[]> entry : data.entrySet()) {
        dataset.setValue(entry.getKey(), entry.getValue()[columnIndex]);
    }

    return dataset;
}

    private void showContent(String contentType) {
    contentPanel.removeAll();

    String selectedDataset = (String) datasetComboBox.getSelectedItem();
    Map<String, Double[]> data = datasets.get(selectedDataset);

    switch (contentType) {
        case "storytelling":
            JTextArea storyTextArea = new JTextArea();
            storyTextArea.setText("Story Behind COVID-19 Dataset \n This dataset depicts an overview of the COVID-19 situation within different states and union territories of India. It gives information about active cases, cured/discharged/migrated cases, deaths, and confirmed cases for every region. It underlines the big strike of the pandemic situation among different areas and elaborates on problems different states faced. \n For instance, Kerala and Maharashtra are in the top of states by confirmed and active cases correspondingly, what directly denotes the severity of the pandemic in the mentioned above places. On the other hand, such regions as Dadra and Nagar Haveli and Daman and Diu are completely the opposite, having very small figures, thus revealing regional differences in outcomes of the pandemic. It is data showing a snapshot of the harsh toll-death and active cases-parallel to recoveries. \n Each record in the dataset belongs to a state or union territory that corresponds to the column variable, reflecting the severity as well as the management of the pandemic in that area. These figures show the differential response and outcomes throughout the country, putting together the panoramic view of the COVID-19 crisis in India. \n\n Dataset Source \n  The data is collated from the publicly available health data provided by the Ministry of Health and Family Welfare, Government of India, during the COVID-19 pandemic. The data reflects the updates from state and central governments, it is the strong resource for the understanding of the progress and trend of the pandemic.  \n\n  Author \n This dataset was curated by John Doe, a data analyst specializing in public health. John Doe aggregated information from MoHFW to create a comprehensive resource that supports researchers, public health officials, and policymakers in analyzing the COVID-19 situation, evaluating responses, and planning future health interventions.");
            storyTextArea.setEditable(false);
            storyTextArea.setLineWrap(true);
            storyTextArea.setWrapStyleWord(true);
            storyTextArea.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            storyTextArea.setBackground(Color.WHITE);
            JScrollPane storytellingScrollPane = new JScrollPane(storyTextArea); // Renamed variable
            storytellingScrollPane.setBorder(BorderFactory.createTitledBorder("Storytelling"));
            contentPanel.add(storytellingScrollPane, "storytelling");
            break;
        
        case "visualization":
    String selectedVisualization = (String) visualizationComboBox.getSelectedItem();
    int columnIndex = getColumnIndexForVisualization(selectedVisualization);

    if (data != null) {
        JFreeChart chart = createChart(selectedVisualization, data, columnIndex);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 500));

        // Create a text area for the chart explanation
        JTextArea chartExplanation = new JTextArea();
        chartExplanation.setEditable(false);
        chartExplanation.setLineWrap(true);
        chartExplanation.setWrapStyleWord(true);
        chartExplanation.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        chartExplanation.setBackground(Color.WHITE);

        switch (selectedVisualization) {
            case "Bar Chart":
                chartExplanation.setText("Bar Chart: \n The total reported cases across different States and UTs can be depicted in the form of a bar chart. By doing so, it then becomes easier to compare those states that report maximum and minimum confirmed cases, to paint a better scale of the pandemic being witnessed in the country. \n\n Story: \n States such as Maharashtra, Kerala, and Karnataka were crucial in ensuring that the largest number of cases possibly recorded had been with them. Maharashtra is already leading the chart, towering over others with as many as 6.34 million cases. This would mean that Maharashtra was the most affected state because of the immense pressure on healthcare and resources. After that, Kerala, Karnataka, and Andhra Pradesh are in line, showing quite directly that southern and western states are the ones that are worst hit. \n On the other hand, places like Ladakh and Lakshadweep show less than one percent of confirmed cases probably because of fewer populations and more scattered places. This chart has depicted unequal spread across the nation pointing to those areas that needed health attention. This graph can illustrate the great disparities in how the pandemic's blow was taken by different regions.");
                break;
            case "Line Chart":
                chartExplanation.setText("Line Chart: \n This line graph illustrates an active trend of COVID-19 cases over some time and elaborates exactly on the surges or ebbing of cases across the states/UTs. A line plot between states/UTs on active cases and recovered cases shows a view of how well the regions have been in managing recoveries. \n\n Story: \n Active cases in Kerala cross 178,000 as southern state shows it isn't done with a high caseload yet. But well, with such loads, the recoveries too within the state are way higher, which keeps the pressure on the ongoing cases and might indicate that Kerala is also trying to bring people back to health. And if Maharashtra has fewer active cases-77,905-it represents an even bigger number of recoveries that pronounce a state which has borne the worst of the storm and is making a way towards its recovery. Across states, crisscrossing lines between active and recovered cases tell a human story of lives saved and a battle inside the four walls of hospitals. This contrast of rising active cases and soaring recoveries is considered uni-generis and unmistakably conveys an assuring message that India has, in many of its regions, been through the worst phases of the pandemic.");
                break;
            case "Area Chart":
                chartExplanation.setText("Area Chart: \n Above area chart is just the way number of deaths across different states have been plotted. \n\n Story: \n This is a grim chart but tells us a story wherein some states were overwhelmed at different points in time. e.g., Maharashtra and Delhi - which on different occasions seemed to be contributing maximum to the death rates, reflecting the deadly stroke of the virus. Graph area will show timely response in health care will reduce mortality rates in future health emergencies.");
                break;
            case "Pie Chart":
                chartExplanation.setText("Pie Chart: \n A pie chart would help to easily understand the share of different States/UTs towards the total deaths due to COVID-19 and identify which States have contributed most to the overall death toll in the country.  \n\n Story: \n A pie chart is like a diagram of loss—a sliver of life cut by the pandemic. More than 133,000 recorded deaths are from Maharashtra, again representing the single largest slice—an indication of how badly that state was affected. Hefty shares are taken by Kerala and Karnataka, serving to underline the fact that it was not just a high volume of cases but a formidable death toll as well. Other contributors to the pie, with a lesser value of 50 deaths, were from Lakshadweep and 4 deaths were from Dadra and Nagar Haveli. These represent another dimension in which some areas had lower shares of fatalities probably because of smaller outbreaks or better containment measures. The pie chart is thus an indication of an asymmetric mortality burden across the country, where one state might have faced huge loses while others might have succumbed to similar consequences.");
                break;
            case "Scatter Plot":
                chartExplanation.setText("Scatter Plot: \n A scatter plot would represent a scatter of Total Confirmed Cases v/s Deaths in each of the states. Each point would represent state/UT and it shall be helpful to understand whether High confirmed cases map up to high toll or vice-versa. \n\n Story: \n Each point in this scatter plot is a state or UT, whose position indicates an interesting and useful relation; namely the relation between the total number of confirmed cases and the total number of deaths. That of Maharashtra stands as the most striking outlier: its large number of confirmed cases is associated with a large number of deaths. This would indicate that a pandemic that has caused a large number of infections is related to a large number of deaths. In some other states like Kerala, though the total number of confirmed cases goes on and on, the death count is comparatively lower, which very much reflects better management, probably through timely medical interventions or better-equipped health infrastructures. In other words, this scatter plot indicates that most of the states with a high number of confirmed cases also tend to have more deaths; however, there can always be exceptions—places where likely better health care systems averted fatal consequences despite more infections.");
                break;
            case "Histogram":
                chartExplanation.setText("Histogram: \n A histogram is used to show the distribution of the states against the number of active cases. It can, therefore, be used to see if there is a concentration of states having almost the same number of active cases or they are more spread. \n\n Story: \n The histogram tells a more statistical story. The distribution of states against active cases shows that most of the states cluster at the lower end of the spectrum, indicating that most of the states have been able to keep their active cases below 10,000. Those are also exposed in the histogram; for instance, the state instances include Kerala and Maharashtra. These states now constitute the much heavier current burden of the pandemic, with tens of thousands of cases still active. This becomes important when the histogram is read-for most of India, active cases are under control, while for a few key states, the fight is far from over.");
                break;
            case "Bubble Chart":
                chartExplanation.setText("Bubble Chart: \n A bubble chart can represent states as bubbles, where the bubble size is proportional to total confirmed cases against the active versus death. \n\n Story: \n In the bubble chart, Every state is represented by a bubble that floats in space which is defined by active cases, deaths versus the total confirmed case. So, it is the Maharashtra bubble that leads the giant one, marking high on cases confirmed and deaths. The Kerala bubble, small in size, quite outshines giants in its number of active cases. The likes of Ladakh or Dadra and Nagar Haveli, on the other hand, represent a dwindling existence, reflecting how the pandemic took place at arm's length. The bubble chart visualizes the scale: the bigger the bubble, the higher the impact. This therefore lets us see the diverse tolls of this pandemic across India.");
                break;    
            default:
                chartExplanation.setText("No explanation available for this chart.");
                break;
        }

        JScrollPane explanationScrollPane = new JScrollPane(chartExplanation);
        explanationScrollPane.setBorder(BorderFactory.createTitledBorder("Chart Explanation"));

        // Create a JSplitPane to split the chart and explanation equally
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(chartPanel), explanationScrollPane);
        splitPane.setResizeWeight(0.4);  // This makes both chart and explanation have equal space
        splitPane.setDividerLocation(500); // Set an initial divider location
        splitPane.setOneTouchExpandable(true); // Allow user to toggle pane sizes easily

        JPanel visualizationPanel = new JPanel(new BorderLayout());
        visualizationPanel.add(splitPane, BorderLayout.CENTER);

        contentPanel.add(visualizationPanel, "Visualization");
    } else {
        contentPanel.add(new JLabel("No data found for " + selectedDataset), "Visualization");
    }
    break;
        
        case "dashboard":
    if (data != null) {
        // Create each chart with the appropriate data
        JFreeChart barChart = createChart("Bar Chart", data, 3); // Active Cases
        JFreeChart lineChart = createChart("Line Chart", data, 1); // Cured Cases
        JFreeChart areaChart = createChart("Area Chart", data, 2); // Adjust column index as needed
        JFreeChart scatterplotChart = createChart("Scatter Plot", data, 2);
        JFreeChart histogramChart = createChart("Histogram", data, 1);
        JFreeChart pieChart = createChart("Pie Chart", data, 2); // Adjust column index for Pie Chart

        // Create ChartPanels for each chart
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new Dimension(800, 600));

        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setPreferredSize(new Dimension(800, 600));

        ChartPanel areaChartPanel = new ChartPanel(areaChart);
        areaChartPanel.setPreferredSize(new Dimension(800, 600));
        
        ChartPanel scatterplotChartPanel = new ChartPanel(scatterplotChart);
        scatterplotChartPanel.setPreferredSize(new Dimension(800, 600));
        
        ChartPanel histogramChartPanel = new ChartPanel(histogramChart);
        histogramChartPanel.setPreferredSize(new Dimension(800, 600));

        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new Dimension(800, 600));

        // Create a JPanel with a GridLayout to hold the charts
        JPanel dashboardContentPanel = new JPanel(new GridLayout(3, 3)); // Adjust layout as needed
        dashboardContentPanel.add(barChartPanel);
        dashboardContentPanel.add(lineChartPanel);
        dashboardContentPanel.add(areaChartPanel);
        dashboardContentPanel.add(scatterplotChartPanel);
        dashboardContentPanel.add(histogramChartPanel);
        dashboardContentPanel.add(pieChartPanel);

        // Add the dashboardContentPanel to the contentPanel
        contentPanel.add(dashboardContentPanel, "Dashboard");
    } else {
        contentPanel.add(new JLabel("No data found for " + selectedDataset), "Dashboard");
    }
    break;

        case "table":
                String[][] tableData = datasetTables.get(selectedDataset);

                if (tableData != null) {
                    String[] columnNames = {"S. No.", "Name of State / UT", "Active Cases", "Cured/Discharged", "Deaths", "Total Confirmed cases"};
                    JTable table = new JTable(tableData, columnNames);
                    JScrollPane scrollPane = new JScrollPane(table);

                    JPanel tableContentPanel = new JPanel(new BorderLayout());
                    tableContentPanel.add(scrollPane, BorderLayout.CENTER);

                    contentPanel.add(tableContentPanel, "Table");
                } else {
                    contentPanel.add(new JLabel("No data found for " + selectedDataset), "Table");
                }
                break;
        }

    // Refresh the content panel to display the new content
    CardLayout cl = (CardLayout) contentPanel.getLayout();
    cl.show(contentPanel, contentType);
    
     contentPanel.revalidate();
     contentPanel.repaint();
}

// Method to get column index based on visualization type
    private int getColumnIndexForVisualization(String visualizationType) {
    switch (visualizationType) {
        case "Bar Chart":
            return 3;
        case "Line Chart":
            return 1; // Cured Cases
        case "Area Chart":
            return 2; // Deaths
        case "Pie Chart":
            return 2; // Deaths
        case "Scatter Plot":
            return 2;    
        case "Histogram":
            return 0; 
        case "Bubble Chart":
            return 0; 
        default:
            return 0;
    }
}


    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#008CBA"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JComboBox<String> createStyledComboBox(String[] options) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setPreferredSize(new Dimension(200, 40)); // Set combo box size
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 18));
        comboBox.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return comboBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataVisualizationGUI gui = new DataVisualizationGUI();
            gui.setVisible(true);
        });
    }
}

