package sample;

import com.stiwsquer.logic.Catalog;
import com.stiwsquer.logic.FileInfo;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class Controller {
    public TextArea textArea;
    public ListView listView;
    public Button trackDirectoryButton;
    public Button checkButton;
    public Button chooseDirectoryButton;


    public void chooseDirectory(ActionEvent actionEvent) {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        File selectedDirectory = directoryChooser.showDialog(null);

        if(selectedDirectory != null){
            listView.getItems().add(new Catalog(selectedDirectory.getPath()));
        }

    }


    public void showFiles(MouseEvent mouseEvent) {


        Catalog theCatalog = (Catalog)listView.getSelectionModel().getSelectedItem();
        if(theCatalog != null){
            String textAreaText ="";
            for (FileInfo file:
                    theCatalog.getFiles()) {

                textAreaText += file.getFileName();
                textAreaText += "\n";

            }
            textArea.setText(textAreaText);
        }

    }


    public void haveDirectoryChanged(ActionEvent actionEvent) {
        Catalog theCatalog = (Catalog)listView.getSelectionModel().getSelectedItem();
        if(theCatalog != null){
            boolean hasDirectoryChanged = theCatalog.hasDirectoryChanged();

            textArea.setText(Boolean.toString(hasDirectoryChanged));
        }

    }

    public void trackDirectory(ActionEvent actionEvent) {
        Catalog theCatalog = (Catalog)listView.getSelectionModel().getSelectedItem();
        if(theCatalog != null){
            theCatalog.trackDirectory();
        }
    }
}
