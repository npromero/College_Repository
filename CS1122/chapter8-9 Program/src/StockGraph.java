import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.concurrent.Worker.State;

public class StockGraph extends Application{

    private Stage stage = null;
    private BorderPane borderPane = null;
    private WebView view = null;
    private WebEngine webEngine = null;
    private TextField statusbar = null;
    private TextField searchbar = null;

    //Help Button popup window

    // HELPER METHODS
    /**
     * Retrieves the value of a command line argument specified by the index.
     *
     * @param index - position of the argument in the args list.
     * @return The value of the command line argument.
     */
    private String getParameter( int index ) {
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();
        return !parameters.isEmpty() ? parameters.get(index) : "";
    }


    /**
     * Generates the status bar layout and text field.
     *
     * @return statusbarPane - the HBox layout that contains the statusbar.
     */
    private HBox makeStatusBar( ) {
        HBox statusbarPane = new HBox();
        statusbarPane.setPadding(new Insets(5, 4, 5, 4));
        statusbarPane.setSpacing(10);
        statusbarPane.setStyle("-fx-background-color: #336699;");
        statusbar = new TextField();
        HBox.setHgrow(statusbar, Priority.ALWAYS);
        statusbarPane.getChildren().addAll(statusbar);
        return statusbarPane;
    }

    /**
     * Makes the top bar of the browser, which includes the forward and backward buttons, the searchbar, and the help button
     *
     * @return toolbarPane
     */
    private HBox makeToolBar( ){
        HBox toolbarPane = new HBox( );
        toolbarPane.setPadding(new Insets(5,4,5,4));
        toolbarPane.setSpacing(10);
        toolbarPane.setStyle("-fx-background-color: #336699;");

        //Back Button
        Button backButton = new Button("<-");
        backButton.setOnAction(event -> {
            //webEngine.getHistory().go(-1);
            webEngine.executeScript("history.back()");
        });

        //Forward Button
        Button forwardButton = new Button("->");
        forwardButton.setOnAction(event ->{
            //webEngine.getHistory().go(1);
            webEngine.executeScript("history.forward()");
        });

        //searchbar
        searchbar = new TextField( );
        HBox.setHgrow(searchbar, Priority.ALWAYS);

        searchbar.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                String searchbarText = searchbar.getText();
                if( !searchbarText.startsWith( "https://" ) ){
                    if( !searchbarText.startsWith("www.")){
                        searchbarText = "www." + searchbarText;
                    }
                    searchbarText = "https://" + searchbarText;
                }
                webEngine.load(searchbarText);

                // clear text
                searchbar.setText("");
            }
        });

        //help button
        Button helpButton = new Button("?");
        helpButton.setOnAction(event -> Popup.display());
        toolbarPane.getChildren().addAll(backButton, forwardButton, searchbar, helpButton);

        return toolbarPane;
    }
    // REQUIRED METHODS
    /**
     * The main entry point for all JavaFX applications. The start method is
     * called after the init method has returned, and after the system is ready
     * for the application to begin running.
     *
     * NOTE: This method is called on the JavaFX Application Thread.
     *
     * @param stage - the primary stage for this application, onto which
     * the application scene can be set.
     */

    @Override
    public void start(Stage stage) {
        // Build your window here.
        this.stage = stage;

        String url = "https://www.google.com";
        borderPane = new BorderPane();
        Scene scene = new Scene(borderPane,800,600);

        borderPane.setCenter(makeHtmlView());
        webEngine.load(url);

        borderPane.setTop( makeToolBar() );

        borderPane.setBottom(makeStatusBar());

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

}
