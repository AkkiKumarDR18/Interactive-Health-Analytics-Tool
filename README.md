# 🏥 Interactive COVID-19 Health Analytics Tool
A custom-built Java desktop application for public health analytics. Features interactive JFreeChart visualizations, dynamic CSV parsing with OpenCSV, and a data storytelling module. Built with Maven.

### *Java-Based Data Visualization Dashboard with Maven*

## 🌟 Project Overview
This project is a custom-built analytical tool designed to visualize and interpret the impact of the COVID-19 pandemic across various States and Union Territories in India. Built using **Java Swing** and the **JFreeChart** library, it transforms raw CSV health data into interactive visual insights.

Unlike standard dashboards, this application integrates a **Data Storytelling** module, providing qualitative context to the quantitative metrics, making it a powerful tool for public health communication.

## 🏗️ Architecture & Features
The application follows a modular structure to ensure data accuracy and user interactivity:

* **Dynamic Charting:** Supports 7 different visualization types:
    * **Bar & Pie Charts:** For regional comparisons of confirmed cases and deaths.
    * **Line & Area Charts:** For analyzing recovery vs. infection trends.
    * **Bubble & Scatter Plots:** For multivariate analysis of active cases vs. mortality rates.
    * **Histograms:** For data distribution analysis.
* **Data Storytelling:** A dedicated module that explains the "story" behind the numbers, citing sources like the Ministry of Health and Family Welfare (MoHFW).
* **Flexible Data Ingestion:** Uses **OpenCSV** to parse regional health data, allowing for easy updates as new data becomes available.
* **Interactive UI:** Modernized Java Swing components with custom styling and a responsive layout.

## 🛠️ Tech Stack
* **Language:** Java 8
* **Build Tool:** Apache Maven
* **Libraries:** * `JFreeChart`: For high-performance data visualization.
    * `OpenCSV`: For robust CSV parsing and data handling.
* **UI Framework:** Java Swing / AWT

## 📂 Project Structure
```text
├── pom.xml                     # Maven configuration & dependencies
├── src/
│   ├── main/
│   │   ├── java/com/example/   # Source code (DataVisualizationGUI.java)
│   │   └── resources/          # Application assets (dataset1.csv, images)
│   └── test/java/com/example/  # Unit tests (AppTest.java)
└── README.md                   # Project documentation
