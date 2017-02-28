package esi.atl.g41163.bmr;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * BMR Application Main class
 * @author Adrian Torres
 * @version 1.0
 * @since 2017-02-28
 */
public class Main extends Application
{
    
    @Override
    public void start(Stage primaryStage)
    {
        // VBox is the root of all elements in the UI
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(8);
        
        // HBox will contain the two columns for data and results
        HBox columns = new HBox();
        columns.setPadding(new Insets(15, 12, 15, 12));
        
        // GridPane to the left of the HBox, represents user-inputted data
        GridPane data = new GridPane();
        data.setHgap(10);
        data.setVgap(10);
        data.setPadding(new Insets(0, 10, 0, 10));
        
        // We create the data's title
        Label title = new Label("Data:");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        title.setUnderline(true);
        data.add(title, 0, 0);
        
        // We add the size label and text field
        String sizeString = "Size (cm)";
        Label sizeLabel = new Label(sizeString);
        data.add(sizeLabel, 0, 1);
        TextField sizeInput = new TextField();
        sizeInput.setPromptText(sizeString);
        data.add(sizeInput, 1, 1);
        
        // We add the weight label and text field
        String weightString = "Weight (kg)";
        Label weightLabel = new Label(weightString);
        data.add(weightLabel, 0, 2);
        TextField weightInput = new TextField();
        weightInput.setPromptText(weightString);
        data.add(weightInput, 1, 2);
        
        // We add the age label and text field
        String ageString = "Age (years)";
        Label ageLabel = new Label(ageString);
        data.add(ageLabel, 0, 3);
        TextField ageInput = new TextField();
        ageInput.setPromptText(ageString);
        data.add(ageInput, 1, 3);
        
        // We add the gender label and radio button
        String gString = "Gender";                              // I'm hilarious
        Label gLabel = new Label(gString);
        data.add(gLabel, 0, 4);
        
        ToggleGroup group = new ToggleGroup();
        RadioButton gInput = new RadioButton("Male");
        gInput.setToggleGroup(group);
        gInput.setSelected(true);
        
        RadioButton gInput2 = new RadioButton("Female");
        gInput2.setToggleGroup(group);
        
        HBox radio = new HBox(10);
        radio.getChildren().addAll(gInput, gInput2);
        data.add(radio, 1, 4);
        
        // We add the Lifestyle label and Lifestyle ChoiceBox
        String lifestyleString = "Lifestyle";
        Label lifestyleLabel = new Label(lifestyleString);
        data.add(lifestyleLabel, 0, 5);
        ChoiceBox lsInput = new ChoiceBox(FXCollections.observableArrayList(
            Lifestyles.SEDENTARY.getValue(), Lifestyles.L_ACTIVE.getValue(),
            Lifestyles.ACTIVE.getValue(), Lifestyles.V_ACTIVE.getValue(),
            Lifestyles.E_ACTIVE.getValue()));
        data.add(lsInput, 1, 5);
        
        // GridPane to the right of the HBox, represents the results
        GridPane results = new GridPane();
        results.setHgap(10);
        results.setVgap(10);
        results.setPadding(new Insets(0, 10, 0, 10));
        
        // We create the results's title
        Label title2 = new Label("Results:");
        title2.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        title2.setUnderline(true);
        results.add(title2, 0, 0);
        
        // We add the BMR label and text field
        String bmrString = "BMR Results";
        Label bmrLabel = new Label(bmrString);
        results.add(bmrLabel, 0, 1);
        TextField bmrInput = new TextField();
        bmrInput.setPromptText(bmrString);
        results.add(bmrInput, 1, 1);
        
        // We add the Calories label and text field
        String calString = "Calories expenses";
        Label calLabel = new Label(calString);
        results.add(calLabel, 0, 2);
        TextField calInput = new TextField();
        calInput.setPromptText(calString);
        results.add(calInput, 1, 2);
        
        // Add both grids to the hbox
        columns.getChildren().addAll(data, results);
        
        // Create calculate button
        Button btn = new Button();
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setMaxWidth(Double.MAX_VALUE);
        
        btn.setText("Calculate BMI");
        btn.setOnAction((ActionEvent event) ->
        {
            if ("".equals(sizeInput.getText()) ||
                "".equals(weightInput.getText()) ||
                "".equals(ageInput.getText()) ||
                lsInput.getValue() == null)
            {
                bmrInput.setText("Failed!");
                calInput.setText("Failed!");
                System.out.println(lsInput.getValue());
            }
            else
            {
                // Parse input
                double w = Double.parseDouble(weightInput.getText());
                double s = Double.parseDouble(sizeInput.getText());
                double a = Double.parseDouble(ageInput.getText());
                
                double multiplier = 1;
                    
                    switch (lsInput.getValue().toString())
                    {
                        case "Sedentary":
                            multiplier = 1.2;
                            break;
                        case "Little active":
                            multiplier = 1.375;
                            break;
                        case "Active":
                            multiplier = 1.55;
                            break;
                        case "Very active":
                            multiplier = 1.725;
                            break;
                        case "Extremely active":
                            multiplier = 1.9;
                            break;
                    }
                
                    double bmi;
                    double cal;
                
                if (gInput.selectedProperty().get())
                {
                    //Male
                    bmi = 13.7 * w + 5 * s - 6.8 * a + 66;
                    cal = bmi * multiplier;
                }
                else
                {
                    //Female
                    bmi = 9.6 * w + 1.8 * s - 4.7 * a + 655;
                    cal = bmi * multiplier;
                }
                
                bmrInput.setText(String.valueOf(bmi));
                calInput.setText(String.valueOf(cal));
                
            }
        });
        
        root.getChildren().addAll(columns, btn);
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("BMR Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
