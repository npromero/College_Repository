/*
Name: Niklas Romero
Date: 03/05/20
Class CS1122 L02
Description: The code graphs the data from the stock analyzer class based on the data fields open, close, high & low
for each data sent in through the command line. The program has a drop down menu for each data put into the command
 line to select the data file the user wishes to look at. If the user clicks a location on the graph the status bar on
 the bottom of the window displays the corresponding point on the graph.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

public class StockGraph extends Application {
    private String name = "AAPL";
    private String filename = this.name + ".csv";
    private File file = new File(filename);
    private  CheckBox check1 = new CheckBox("open");
    private CheckBox check2 = new CheckBox("high");
   private CheckBox check3 = new CheckBox("low");
   private CheckBox check4 = new CheckBox("close");
   private StockAnalyzer stocks = new StockAnalyzer();
   private XYChart.Series< String, Number> series1 = new XYChart.Series<>();
   private XYChart.Series<String, Number> series2 = new XYChart.Series<>();
   private XYChart.Series<String, Number> series3 = new XYChart.Series<>();
   private XYChart.Series<String, Number> series4 = new XYChart.Series<>();
   private final CategoryAxis xAxis = new CategoryAxis();
   private final NumberAxis yAxis = new NumberAxis();
   private  final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    private Stage stage = null;
    private BorderPane borderPane = null;
    private String stockSymbol = null;
    private TextField statusbar = null;

    /*
    Method Header: The method below creates the top menu of the interactive graph and updates the graph when items are
    used
     */


    private HBox makeToolBar( ) {
        ComboBox<String> comboBox = new ComboBox<> (  );
        comboBox.getItems().add ( this.getParameters().getRaw().get(0) );
        comboBox.getItems().add ( this.getParameters().getRaw().get(1));
        comboBox.getItems().add ( this.getParameters().getRaw().get(2));
        comboBox.getItems().add ( this.getParameters().getRaw().get(3));
        comboBox.getSelectionModel().select(0);
        comboBox.setOnAction ( event -> {
            stockSymbol = comboBox.getSelectionModel().getSelectedItem();
            if(stockSymbol.equals( this.getParameters().getRaw().get(0)))
            {
                this.name =  this.getParameters().getRaw().get(0);
                filename = this.name + ".csv";
                file = new File(filename);
                updateOpen(series1,file,stocks);
                updateHigh(series2,file,stocks);
                updateLow(series3,file,stocks);
                updateClose(series4,file,stocks);

            }
            else if(stockSymbol.equals( this.getParameters().getRaw().get(1)))
            {
                this.name =  this.getParameters().getRaw().get(1);
                filename = this.name + ".csv";
                file = new File(filename);
                updateOpen(series1,file,stocks);
                updateHigh(series2,file,stocks);
                updateLow(series3,file,stocks);
                updateClose(series4,file,stocks);
            }
            else if(stockSymbol.equals( this.getParameters().getRaw().get(2)))
            {
                this.name =  this.getParameters().getRaw().get(2);
                filename = this.name + ".csv";
                file = new File(filename);
                updateOpen(series1,file,stocks);
                updateHigh(series2,file,stocks);
                updateLow(series3,file,stocks);
                updateClose(series4,file,stocks);
            }
            else if(stockSymbol.equals( this.getParameters().getRaw().get(3)))
            {
               this.name =  this.getParameters().getRaw().get(3);
                filename = this.name + ".csv";
                file = new File(filename);
                updateOpen(series1,file,stocks);
                updateHigh(series2,file,stocks);
                updateLow(series3,file,stocks);
                updateClose(series4,file,stocks);
            }


        });


        HBox hbox = new HBox();
        HBox.setHgrow(comboBox, Priority.ALWAYS);
        HBox.setHgrow(check1, Priority.ALWAYS);
        HBox.setHgrow(check2, Priority.ALWAYS);
        HBox.setHgrow(check3, Priority.ALWAYS);
        HBox.setHgrow(check4, Priority.ALWAYS);
        hbox.setSpacing(120);
        hbox.getChildren().addAll(comboBox,check1,check2,check3,check4);




        return hbox;

    }
    /*
    Method Header: The method updates the displayed data to the corresponding data the user wishes to show.
     */
    private void updateOpen(XYChart.Series<String,Number> series, File file, StockAnalyzer stocks)
    {
        lineChart.getData().remove(series);

        series1 = new XYChart.Series<>();

        try
        {
            ArrayList<AbstractStock> list = stocks.loadStockData(file);
            for(int i =0; i < list.size();i++)
            {
                String month = list.get(i).getTimestamp().toString();
                Instant instant = Instant.ofEpochSecond (Long.parseLong(month));
                LocalDate date = instant.atZone ( ZoneId.systemDefault () ).toLocalDate ();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date = LocalDate.parse ( date.toString(), formatter );
                double data = list.get(i).getOpen();
                xAxis.setAutoRanging(true);
                xAxis.setAnimated(false);
             //   series1.
                series1.getData().add(new XYChart.Data<>(Date.toString(),data));
                series1.setName("Open");
            }
        }
        catch (FileNotFoundException e)
        {

        }
        lineChart.setTitle("Stock Monitoring " + this.name);
        if(check1.isSelected()) {
            lineChart.getData().add(series1);
        }
    }
    /*
    Method Header: The method updates the displayed data to the corresponding data the user wishes to show.
     */
    private void updateLow(XYChart.Series<String,Number> series, File file, StockAnalyzer stocks)
    {
        lineChart.getData().remove(series);

        series3 = new XYChart.Series<>();
        try
        {
            ArrayList<AbstractStock> list = stocks.loadStockData(file);
            for(int i =0; i < list.size();i++)
            {
                String month = list.get(i).getTimestamp().toString();
                Instant instant = Instant.ofEpochSecond (Long.parseLong(month));
                LocalDate date = instant.atZone ( ZoneId.systemDefault () ).toLocalDate ();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date = LocalDate.parse ( date.toString(), formatter );
                double data = list.get(i).getLow();
                xAxis.setAnimated(false);
                series3.getData().add(new XYChart.Data<>(Date.toString(),data));
                series3.setName("Low");
            }
        }
        catch (FileNotFoundException e)
        {

        }
        if(check3.isSelected()) {
            lineChart.getData().add(series3);
        }
    }
    /*
        Method Header: The method updates the displayed data to the corresponding data the user wishes to show.
         */
    private void updateHigh(XYChart.Series<String,Number> series, File file, StockAnalyzer stocks)
    {
        lineChart.getData().remove(series);

        series2 = new XYChart.Series<>();
        try
        {
            ArrayList<AbstractStock> list = stocks.loadStockData(file);
            for(int i =0; i < list.size();i++)
            {
                String month = list.get(i).getTimestamp().toString();
                Instant instant = Instant.ofEpochSecond (Long.parseLong(month));
                LocalDate date = instant.atZone ( ZoneId.systemDefault () ).toLocalDate ();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date = LocalDate.parse ( date.toString(), formatter );
                double data = list.get(i).getHigh();
                xAxis.setAnimated(false);
                series2.getData().add(new XYChart.Data<>(Date.toString(),data));
                series2.setName("High");
            }
        }
        catch (FileNotFoundException e)
        {

        }
        if(check2.isSelected()) {
            lineChart.getData().add(series2);
        }
    }
    /*
    Method Header: The method updates the displayed data to the corresponding data the user wishes to show.
     */
    private void updateClose(XYChart.Series<String,Number> series, File file, StockAnalyzer stocks)
    {
        lineChart.getData().remove(series);

        series4 = new XYChart.Series<>();
        try
        {
            ArrayList<AbstractStock> list = stocks.loadStockData(file);
            for(int i =0; i < list.size();i++)
            {
                String month = list.get(i).getTimestamp().toString();
                Instant instant = Instant.ofEpochSecond (Long.parseLong(month));
                LocalDate date = instant.atZone ( ZoneId.systemDefault () ).toLocalDate ();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date = LocalDate.parse ( date.toString(), formatter );
                double data = list.get(i).getClose();
                xAxis.setAnimated(false);
                series4.getData().add(new XYChart.Data<>(Date.toString(),data));
                series4.setName("Close");
            }
        }
        catch (FileNotFoundException e)
        {

        }
        if(check4.isSelected()) {
            lineChart.getData().add(series4);
        }
    }
    /*
        Method Header: The method updates the data when a check box is updated and adds the graph to the interactive menu
         */
    private VBox graph() {
        VBox vbox = new VBox();
        lineChart.setTitle("Stock Monitoring " +  this.getParameters().getRaw().get(0));
        lineChart.setCreateSymbols(false);

        check1.setOnAction((event2) -> {
            if(check1.isSelected())
            {
               updateOpen(series1,file,stocks);
            }
            if(!check1.isSelected()){
                lineChart.getData().remove(series1);
            }

        });
        check2.setOnAction((event2) -> {
            if(check2.isSelected())
            {
                updateHigh(series2,file,stocks);
            }
            if(!check2.isSelected()){
                lineChart.getData().remove(series2);
            }

        });
        check3.setOnAction((event2) -> {
            if(check3.isSelected())
            {
                updateLow(series3,file,stocks);
            }
            if(!check3.isSelected()){
                lineChart.getData().remove(series3);
            }

        });
        check4.setOnAction((event2) -> {
            if(check4.isSelected())
            {
                updateClose(series4,file,stocks);
            }
            if(!check4.isSelected()){
                lineChart.getData().remove(series4);
            }

        });

        vbox.getChildren().addAll(lineChart);
        vbox.setAlignment(Pos.CENTER);


        return vbox;
    }
    /*
        Method Header: The method creates the status bar and finds the appropriate data point the user is clicking
         */
    public HBox statusBar()
    {
        HBox statusbarPane = new HBox();
        statusbarPane.setPadding(new Insets(0, 0, 0, 0));
        statusbarPane.setSpacing(10);
        statusbarPane.setStyle("-fx-background-color: #336699;");
        statusbar = new TextField();
        HBox.setHgrow(statusbar, Priority.ALWAYS);
        statusbarPane.getChildren().addAll(statusbar);

        statusbar.setText("");
        return statusbarPane;
    }


    /**
     * Makes the top bar of the browser, which includes the forward and backward buttons, the searchbar, and the help button
     *
     * @return toolbarPane
     */

    @Override
    public void start(Stage stage) {

        // Build your window here.
        this.stage = stage;
        stage.setTitle("Stock Monitoring");

        borderPane = new BorderPane();
        Scene scene = new Scene(borderPane,800,600);
       borderPane.setCenter(graph());

        borderPane.setTop( makeToolBar());
        borderPane.setBottom(statusBar());

     scene.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
         @Override
         public void handle(javafx.scene.input.MouseEvent event) {
             Point2D mouseSceneCoords =
                     new Point2D( event.getSceneX ( ), event.getSceneY ());
             String x = xAxis.getValueForDisplay ( 	xAxis.sceneToLocal(mouseSceneCoords).getX() );
             Number y = yAxis.getValueForDisplay ( 	yAxis.sceneToLocal(mouseSceneCoords).getY() );
             statusbar.setText((x == null) ? "" : String.format ( "(%s, %f)", x, y ));


         }
     });
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main( ) method is ignored in JavaFX applications.
     * main( ) serves only as fallback in case the application is launched
     * as a regular Java application, e.g., in IDEs with limited FX
     * support.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}



