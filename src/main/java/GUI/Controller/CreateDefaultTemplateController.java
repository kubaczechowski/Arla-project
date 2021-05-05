package GUI.Controller;

import GUI.Model.ScreenModel;
import GUI.Model.exception.ModelException;
import GUI.util.*;
import GUI.util.charts.CreateHistogramChart;
import be.DefaultScreen;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

/**
 *
 */
public class CreateDefaultTemplateController implements Initializable {
    @FXML
    private AnchorPane spacePDF;
    @FXML
    private JFXTextField nameField;
    @FXML
    private AnchorPane websiteSpace;
    @FXML
    private JFXTextField websiteLink;
    @FXML
    private Label attachment1;
    @FXML
    private Label attachment2;
    @FXML
    private Label attachment3;
    @FXML
    private AnchorPane csvChart;
    private WebEngine webEngine;
    private WebEngine pdfViewerEngine;
    private FileChooser fileChooser = new FileChooser();
    private ScreenModel screenModel = ScreenModel.getInstance();
    private final static String DESTINATION_PATH_PDF = "src/../Data/PDFData/";
    private final static String HOME = "https://www.google.com/webhp";

    //fields to save
    private Path destinationPathCSV;
    private String insertedWebsite;
    private Path destinationPathPDF;
    private String name;

    @Override
    public void initialize(URL url2, ResourceBundle resourceBundle) {

    }


    /**
     * we need to save three paths:
     * - one to website
     * - one to csv
     * - one to PDF
     *
     * @param actionEvent
     */
    public void save(ActionEvent actionEvent) {
        name = nameField.getText();
        if (name != null && destinationPathCSV != null &&
                insertedWebsite != null && destinationPathPDF != null) {
            screenModel.saveDefaultTemplate(new DefaultScreen(name, destinationPathCSV,
                    destinationPathPDF, insertedWebsite));
        }
    }

    /**
     * not save anything.
     * Also delete files that have been stored over there
     * We need to delete PDF HTML, CSV
     *
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        if(destinationPathPDF!=null)
            screenModel.deletePDFfiles(destinationPathPDF);
        if(destinationPathCSV!=null)
            screenModel.deleteCSV(destinationPathCSV);
    }

    /**
     * some actions to load website
     *
     * @param actionEvent
     */
    public void loadWebsite(ActionEvent actionEvent) {
        WebsiteLoader websiteLoader = new WebsiteLoader(webEngine);
        websiteLoader.addWebView(websiteSpace);
        insertedWebsite = websiteLink.getText();
        websiteLoader.executeQuery(insertedWebsite);
        attachment2.setText(insertedWebsite);
    }


    public void loadPDF(ActionEvent actionEvent) {
       attachment3.setText(PDFLoader.loadPDF(actionEvent, fileChooser));
       PDFLoader.loadPDFViewer(spacePDF);
    }


    /**
     * we need file chooser, maybe i should save that file
     * then we need double[] and then
     * we can use CreateHistogramChart
     *
     * @param actionEvent
     */

    public void loadCSV(ActionEvent actionEvent) {
        attachment1.setText( CSVLoader.loadCSV(actionEvent, fileChooser, csvChart));
    }





/*
    private File getSelectedFile(ActionEvent actionEvent, String information) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        fileChooser.setTitle(information);
        return fileChooser.showOpenDialog(stage);
    }

 */

/*
    private void saveFile(File selectedFile, Path destinationPath) {
        try {
            screenModel.saveFile(Path.of(selectedFile.getAbsolutePath()),
                    destinationPath);
        } catch (ModelException e) {
            e.printStackTrace();
            AlertDisplayer.displayInformationAlert("saving",
                    "couldn't save", "");
        }
    }

 */


}
