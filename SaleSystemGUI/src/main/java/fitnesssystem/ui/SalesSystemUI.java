package fitnesssystem.ui;

import fitnesssystem.dao.InMemorySystemDAO;
import fitnesssystem.dao.SystemDAO;
import fitnesssystem.logic.ActivityLogic;
import fitnesssystem.ui.controllers.DashboardController;
import fitnesssystem.ui.controllers.MyActivitiesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Graphical user interface of the sales system.
 */
public class SalesSystemUI extends Application {

    private static final Logger log = LogManager.getLogger(SalesSystemUI.class);
    private final SystemDAO systemDAO;

    public SalesSystemUI() {
        systemDAO=new InMemorySystemDAO();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("javafx version: " + System.getProperty("javafx.runtime.version"));

        Tab dashboardTab = new Tab();
        dashboardTab.setText("Dashboard");
        dashboardTab.setClosable(false);
        dashboardTab.setContent(loadControls("DashboardTab.fxml", new DashboardController(new ActivityLogic(systemDAO))));

        Tab myActivitiesTab = new Tab();
        myActivitiesTab.setText("My activities");
        myActivitiesTab.setClosable(false);
        myActivitiesTab.setContent(loadControls("MyActivitiesTab.fxml", new MyActivitiesController(new ActivityLogic(systemDAO))));

        Group root = new Group();
        Scene scene = new Scene(root, 600, 500, Color.WHITE);
        //scene.getStylesheets().add(getClass().getResource("DefaultTheme.css").toExternalForm());

        BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(new TabPane(dashboardTab,myActivitiesTab));
        root.getChildren().add(borderPane);

        primaryStage.setTitle("Sales system");
        primaryStage.setScene(scene);
        primaryStage.show();

        log.info("Salesystem GUI started");
    }

    private Node loadControls(String fxml, Initializable controller) throws IOException {
        URL resource = getClass().getResource(fxml);
        if (resource == null)
            throw new IllegalArgumentException(fxml + " not found");

        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }
}


