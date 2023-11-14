import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.*;
import java.util.Optional;

public class GUIUnitConverter extends Application {
    private static final String DATA_FILE = "unit_converter_data.txt";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        loadConversionData(); // Load existing conversion data from the file

        // Initialize GUI components
        ComboBox<String> conversionTypeComboBox = new ComboBox<>();
        conversionTypeComboBox.getItems().addAll("Temperature", "Currency", "Weight");
        conversionTypeComboBox.setPromptText("Select Conversion Type");

        ComboBox<String> subConversionComboBox = new ComboBox<>();
        subConversionComboBox.setPromptText("Select Sub-conversion");
        subConversionComboBox.setVisible(false);

        Button convertButton = new Button("Convert");
        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        // Create a layout for the GUI
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(conversionTypeComboBox, subConversionComboBox, convertButton, resultTextArea);

        // Set up the primary stage
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Unit Converter");
        primaryStage.show();

        // Set up event handling
        conversionTypeComboBox.setOnAction(event -> {
            String selectedConversion = conversionTypeComboBox.getValue();
            if (selectedConversion != null) {
                subConversionComboBox.getItems().clear();
                subConversionComboBox.setVisible(true);

                switch (selectedConversion) {
                    case "Temperature":
                        subConversionComboBox.getItems().addAll("Fahrenheit to Celsius", "Celsius to Fahrenheit");
                        break;
                    case "Currency":
                        subConversionComboBox.getItems().addAll("USD to Euro", "Euro to USD", "USD to JPY", "JPY to USD", "INR to Euro", "Euro to INR", "INR to USD", "USD to INR");
                        break;
                    case "Weight":
                        subConversionComboBox.getItems().addAll("Ounces to Pounds", "Pounds to Ounces", "Grams to Pounds", "Pounds to Grams", "Kilograms to Grams", "Grams to Kilograms");
                        break;
                }
            }
        });

        convertButton.setOnAction(event -> {
            String selectedSubConversion = subConversionComboBox.getValue();
            if (selectedSubConversion != null) {
                double result = 0.0;
                String promptMessage = "Enter the value for conversion:";
                double inputValue = showInputPromptDialog(promptMessage);

                switch (selectedSubConversion) {
                    case "Fahrenheit to Celsius":
                        result = (inputValue - 32) * 5.0 / 9.0;
                        break;
                    case "Celsius to Fahrenheit":
                        result = (inputValue * 9.0 / 5.0) + 32;
                        break;
                    case "USD to Euro":
                        result = inputValue * 0.87;
                        break;
                    case "Euro to USD":
                        result = inputValue / 0.87;
                        break;
                    case "USD to JPY":
                        result = inputValue * 111.09;
                        break;
                    case "JPY to USD":
                        result = inputValue / 111.09;
                        break;
                    case "INR to Euro":
                        result = inputValue / 84.39;
                        break;
                    case "Euro to INR":
                        result = inputValue * 84.39;
                        break;
                    case "INR to USD":
                        result = inputValue / 81.64;
                        break;
                    case "USD to INR":
                        result = inputValue * 81.64;
                        break;
                    case "Ounces to Pounds":
                        result = inputValue * 0.0625;
                        break;
                    case "Pounds to Ounces":
                        result = inputValue / 0.0625;
                        break;
                    case "Grams to Pounds":
                        result = inputValue * 0.00220462;
                        break;
                    case "Pounds to Grams":
                        result = inputValue / 0.00220462;
                        break;
                    case "Kilograms to Grams":
                        result = inputValue * 1000;
                        break;
                    case "Grams to Kilograms":
                        result = inputValue / 1000;
                        break;
                }

                String resultMessage = selectedSubConversion + ": " + result;
                saveConversionData(selectedSubConversion, inputValue, result); // Save the conversion data to the file
                showInfoAlert(resultMessage);
                resultTextArea.setText(resultMessage);
            }
        });
    }

    // Helper method to show an input prompt dialog and get a double value from the user
    private double showInputPromptDialog(String promptMessage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Prompt");
        dialog.setHeaderText(null);
        dialog.setContentText(promptMessage);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                return Double.parseDouble(result.get());
            } catch (NumberFormatException e) {
                showWarningAlert("Invalid input. Please enter a valid number.");
            }
        }
        return 0.0; // Default value if there is no valid input
    }

    // Helper method to show an information alert
    private void showInfoAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show a warning alert
    private void showWarningAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to save conversion data to a file
    private void saveConversionData(String conversion, double inputValue, double result) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE, true))) {
            writer.println(conversion + "," + inputValue + "," + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to load conversion data from a file
    private void loadConversionData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String conversion = parts[0];
                    double inputValue = Double.parseDouble(parts[1]);
                    double result = Double.parseDouble(parts[2]);
                    // You can process and display the loaded data if needed
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
