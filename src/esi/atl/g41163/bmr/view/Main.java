package esi.atl.g41163.bmr.view;

import esi.atl.g41163.bmr.model.Lifestyles;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import javafx.stage.Stage;

/**
 * BMR Application Main class
 * @author Adrian Torres
 * @version 1.0
 * @since 2017-02-28
 */
public class Main extends Application
{   
    private VBox setupVBox()
    {
        // VBox is the root of all elements in the UI
        VBox layout = new VBox();
        layout.setPadding(new Insets(10));
        layout.setSpacing(8);
        
        return layout;
    }
    
    private HBox setupHBox()
    {
        // HBox will contain the two columns for data and results
        HBox layout = new HBox();
        layout.setPadding(new Insets(15, 12, 15, 12));
        
        return layout;
    }
    
    private GridPane setupGrid()
    {
        // GridPane to the left of the HBox, represents user-inputted data
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(0, 10, 0, 10));
        
        return layout;
    }
    
    private Label createDataLabel()
    {
        Label label = new Label("Data:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setUnderline(true);
        
        return label;
    }
    
    private void setupDataLabels(GridPane data)
    {
        data.add(createDataLabel(), 0, 0);
        
        String[] labelTxt = {"Size in cm", "Weight in kg", "Age in years",
                             "Gender", "Lifestyle"};
        
        for (int i = 0; i < labelTxt.length; i++)
        {
            data.add(new Label(labelTxt[i]), 0, (i + 1));
        }
    }
    
    private List<TextField> setupDataFields(GridPane data)
    {
        List<TextField> fields = new ArrayList();
        String[] fieldTxt = {"Size (cm)", "Weight(kg)", "Age (years)"};
        
        for (int i = 0; i < fieldTxt.length; i++)
        {
            TextField field = new TextField();
            field.setPromptText(fieldTxt[i]);
            fields.add(field);
            data.add(field, 1, (i + 1));
        }
        
        return fields;
    }
    

    
    @Override
    public void start(Stage primaryStage)
    {
        VBox root = setupVBox();
        HBox columns = setupHBox();
        GridPane data = setupGrid();
        
        setupDataLabels(data);
        
        List<TextField> fields = setupDataFields(data);


        ToggleGroup group = new ToggleGroup();
        RadioButton gInput = new RadioButton("Male");
        gInput.setToggleGroup(group);
        gInput.setSelected(true);
        
        RadioButton gInput2 = new RadioButton("Female");
        gInput2.setToggleGroup(group);
        
        HBox radio = new HBox(10);
        radio.getChildren().addAll(gInput, gInput2);
        data.add(radio, 1, 4);
        

        ChoiceBox lsInput = new ChoiceBox(FXCollections.observableArrayList(
        Lifestyles.values()));
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
            if ("".equals(fields.get(0).getText()) ||
                "".equals(fields.get(1).getText()) ||
                "".equals(fields.get(2).getText()) ||
                lsInput.getValue() == null)
            {
                bmrInput.setText("Failed!");
                calInput.setText("Failed!");
                System.out.println(lsInput.getValue());
            }
            else
            {
                // Parse input
                double s = Double.parseDouble(fields.get(0).getText());
                double w = Double.parseDouble(fields.get(1).getText());
                double a = Double.parseDouble(fields.get(2).getText());
                
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
