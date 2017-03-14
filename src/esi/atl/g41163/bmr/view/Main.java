package esi.atl.g41163.bmr.view;

import esi.atl.g41163.bmr.model.BmiMath.*;
import static esi.atl.g41163.bmr.model.BmiMath.getBmi;
import static esi.atl.g41163.bmr.model.BmiMath.getCal;
import esi.atl.g41163.bmr.model.Lifestyles;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @version 1.1
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
    
    private Label createTitle(String text)
    {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setUnderline(true);
        
        return label;
    }
    
    private void setupLabels(GridPane pane, String[] labels, String title)
    {
        pane.add(createTitle(title + ":"), 0, 0);

        for (int i = 0; i < labels.length; i++)
        {
            pane.add(new Label(labels[i]), 0, (i + 1));
        }
    }
    
    private List<TextField> setupFields(GridPane pane, String[] labels)
    {
        List<TextField> fields = new ArrayList();
        
        for (int i = 0; i < labels.length; i++)
        {
            TextField field = new TextField();
            field.setPromptText(labels[i]);
            fields.add(field);
            pane.add(field, 1, (i + 1));
        }
        
        return fields;
    }
    
    private RadioButton setupRadio(GridPane data)
    {
        ToggleGroup group = new ToggleGroup();
        
        RadioButton gInput = new RadioButton("Male");
        gInput.setToggleGroup(group);
        gInput.setSelected(true);
        
        RadioButton gInput2 = new RadioButton("Female");
        gInput2.setToggleGroup(group);
        
        HBox radio = new HBox(10);
        radio.getChildren().addAll(gInput, gInput2);
        data.add(radio, 1, 4);
        
        return gInput;
    }
    
    private ChoiceBox setupCBox(GridPane data)
    {
        ChoiceBox ls = new ChoiceBox(FXCollections.observableArrayList(Lifestyles.values()));
        ls.getSelectionModel().selectFirst();
        data.add(ls, 1, 5);
        
        return ls;
    }
    
    private boolean isEmpty(List<TextField> dFields)
    {
        return (dFields.get(0).getText().equals("") ||
                dFields.get(1).getText().equals("") ||
                dFields.get(2).getText().equals(""));
    }
    
    private void fail(List<TextField> rFields)
    {
        for (TextField field : rFields)
        {
            field.setStyle("-fx-text-inner-color: red;");
            field.setText("Failed!");
        }
    }
    
    private void succeed(List<TextField> rFields, double[] out)
    {
        for (int i = 0; i < out.length; i++)
        {
            rFields.get(i).setStyle("-fx-text-inner-color: black;");
            rFields.get(i).setText(String.valueOf(out[i]));
        }
    }
    
    private boolean isGoodInput(List<TextField> dFields)
    {
        for (TextField field : dFields)
        {
            try
            {
                Double.parseDouble(field.getText());
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        VBox root = setupVBox();
        HBox columns = setupHBox();
        GridPane data = setupGrid();
        GridPane results = setupGrid();

        // Left column - Data
        String[] dLabels = {"Size in cm", "Weight in kg", "Age in years",
        "Gender", "Lifestyle"};
        setupLabels(data, dLabels, "Data");
        // Subset of dLabels since Gender and Lifestyle are not TextFields
        List<TextField> dFields = setupFields(data, Arrays.copyOfRange(dLabels, 0, 3));
        RadioButton gender = setupRadio(data);
        ChoiceBox lifestyle = setupCBox(data);

        // Right column - Results
        String[] rLabels = {"BMR Results", "Calories expenses"};
        setupLabels(results, rLabels, "Results");
        List<TextField> rFields = setupFields(results, rLabels);
        
        // Create calculate button
        Button btn = new Button();
        HBox.setHgrow(btn, Priority.ALWAYS);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setText("Calculate BMI");
        
        // We add all the respective elements to their parent layouts
        columns.getChildren().addAll(data, results);
        root.getChildren().addAll(columns, btn);
        
        // We define the button's action
        btn.setOnAction((ActionEvent event) ->
        {
            // We reject empty or non-double input
            if (isEmpty(dFields) || !isGoodInput(dFields.subList(0, 3)))
            {
                fail(rFields);
            }
            else
            {
                // Parse input
                double s = Double.parseDouble(dFields.get(0).getText());
                double w = Double.parseDouble(dFields.get(1).getText());
                double a = Double.parseDouble(dFields.get(2).getText());
                
                Lifestyles ls = (Lifestyles) lifestyle.getValue();
                double multiplier = ls.getMult();
                boolean g = gender.selectedProperty().get();
                
                double bmi = getBmi(s, w, a, g);
                double cal = getCal(bmi, multiplier);
                
                succeed(rFields, new double[] {bmi, cal});
                
            }
        });
        
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
