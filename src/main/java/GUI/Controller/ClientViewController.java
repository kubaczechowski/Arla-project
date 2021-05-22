package gui.Controller;

import be.Screen;
import gui.Model.ClientModel;
import gui.Model.ScreenModel;
import gui.util.CSVLoader;
import gui.util.Observator.ObserverSingle;
import gui.util.PDFLoader;
import gui.util.WebsiteLoader;
import be.ScreenElement;
import be.User;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientViewController extends ObserverSingle implements Initializable{

    public AnchorPane pane;
    private List<ScreenElement> sections;
    private ClientModel model;
    private User user;
    private GridPane gridPane = new GridPane();
    private WebEngine webEngine;
    private Stage stageToSet;

    public void setUser(User user, Stage stage) {
        this.stageToSet =stage;
        this.user = user;
        model = ClientModel.getInstance();
        //sections = model.getSections(user.getID());

        sections = new ArrayList<>();
        ScreenElement s1 = new ScreenElement(0, 0, 1, 1, "dog");
        ScreenElement s2 = new ScreenElement(0, 1, 1, 1, "src/../Data/PDFData/Assignment 1 - Consultative Solutions.pdf");
        ScreenElement s3 = new ScreenElement(1, 0, 1, 2, "src/../Data/CSVData/test.csv");
        sections.add(s1);
        sections.add(s2);
        sections.add(s3);

        loadScreen(stage);
    }

    private void loadScreen(Stage stage) {
        for(ScreenElement section : sections) {

            String filePath = section.getFilepath();
            AnchorPane anchorPane = null;
            if(filePath!=null) {
                String fileType = "";
                if(filePath.length() > 4) fileType = filePath.substring(filePath.length() - 4);
                else fileType = filePath;
                switch (fileType) {
                    case ".png", ".jpg":
                        anchorPane = loadImage(filePath);
                        break;
                    case ".pdf":
                        anchorPane = loadPDF(filePath);
                        break;
                    case ".csv":
                        anchorPane = loadCSV(filePath);
                        break;
                    default:
                        anchorPane = loadWebsite(filePath);
                        break;
                }
            }

            gridPane.add(anchorPane, section.getColIndex(),
                    section.getRowIndex(), section.getColSpan(), section.getRowSpan());
            GridPane.setHgrow(anchorPane, Priority.SOMETIMES);
            GridPane.setVgrow(anchorPane, Priority.SOMETIMES);
        }

        gridPane.setGridLinesVisible(true);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();

    }


    private AnchorPane loadWebsite(String filepath) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(300, 300);
        WebsiteLoader websiteLoader = new WebsiteLoader(webEngine);
        websiteLoader.addWebView(anchorPane);
        websiteLoader.executeQuery(filepath);
        System.out.println("loaded website");
        return anchorPane;
    }


    private AnchorPane loadPDF(String filepath) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(300, 300);
        PDFLoader.setDestinationPathPDF(Path.of(filepath));
        PDFLoader.loadPDFViewer(anchorPane);
        System.out.println("loaded pdf");
        return anchorPane;
    }

    private AnchorPane loadCSV(String filepath) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(300, 300);
        CSVLoader.setDestinationPathCsv(Path.of(filepath));
        CSVLoader.createTable(false, anchorPane);
        System.out.println("loaded csv");
        return anchorPane;
    }

    private AnchorPane loadImage(String filepath) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(300, 300);
        Image image = new Image(filepath);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        anchorPane.getChildren().add(imageView);
        imageView.setFitHeight(anchorPane.getHeight());
        imageView.setFitWidth(anchorPane.getWidth());
        System.out.println("loaded image");
        return anchorPane;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScreenModel.observersSingle.add(this);
    }

    public void setScreenObs(Screen screen){
        setScreen(screen);
    }

    /**
     * if update reload all screen
     */
    @Override
    public void update() {
        pane.getChildren().clear();
        loadScreen(stageToSet);
    }
}
