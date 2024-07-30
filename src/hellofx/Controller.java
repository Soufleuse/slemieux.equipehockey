package hellofx;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import model.equipe;
import service.lireequipe;

public class Controller {

    @FXML private GridPane maGrille;
    @FXML private TableView<equipe> tvwListeEquipe;
    @FXML private Label lblNomEquipe;
    @FXML private TextField txtNomEquipe;
    @FXML private Label lblVille;
    @FXML private TextField txtVille;
    @FXML private Label lblAnneeDebut;
    @FXML private TextField txtAnneeDebut;
    @FXML private Label lblAnneeFin;
    @FXML private TextField txtAnneeFin;
    @FXML private Label lblEstDevenuEquipe;
    @FXML private ComboBox<equipe> cboEstDevenuEquipe;

    private List<equipe> listeEquipe;

    public void initialize() {
        maGrille.getStyleClass().add("grid");
        maGrille.getColumnConstraints().add(new ColumnConstraints(250));
        maGrille.getColumnConstraints().add(new ColumnConstraints(250));
        tvwListeEquipe.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lireequipe maLecture = new lireequipe();
        listeEquipe = maLecture.lireEquipe();
        maLecture = null;

        ObservableList<equipe> listeEquipeObservable = FXCollections.observableArrayList(listeEquipe);
        tvwListeEquipe.setItems(listeEquipeObservable);
        TableColumn<equipe, String> colonneNomEquipe = new TableColumn<equipe, String>("Nom de l'équipe");
        TableColumn<equipe, String> colonneVille = new TableColumn<equipe, String>("Ville");
        TableColumn<equipe, String> colonneAnneeDebut = new TableColumn<equipe, String>("AnneeDebut");
        TableColumn<equipe, String> colonneAnneeFin = new TableColumn<equipe, String>("AnneeFin");
        colonneNomEquipe.setCellValueFactory(new PropertyValueFactory<>("NomEquipe"));
        colonneVille.setCellValueFactory(new PropertyValueFactory<>("Ville"));
        colonneVille.setMinWidth(75);
        colonneAnneeDebut.setCellValueFactory(new PropertyValueFactory<>("AnneeDebut"));
        colonneAnneeFin.setCellValueFactory(new PropertyValueFactory<>("AnneeFin"));
        tvwListeEquipe.getColumns().setAll(colonneNomEquipe, colonneVille, colonneAnneeDebut, colonneAnneeFin);
    }

    @FXML private void ajouterEquipe(ActionEvent event) {
        event.consume();
        System.out.println("Ajout de l'équipe en construction");
    }

    @FXML private void modifierEquipe(ActionEvent event) {
        event.consume();
        System.out.println("Modification de l'équipe en construction");
    }

    @FXML private void tvwListeEquipe_Clicked(MouseEvent evenement) {
        equipe monEquipe = tvwListeEquipe.getSelectionModel().getSelectedItem();
        txtNomEquipe.setText(monEquipe.getNomEquipe());
        txtVille.setText(monEquipe.getVille());
        txtAnneeDebut.setText(String.format("%d", monEquipe.getAnneeDebut()));
        
        txtAnneeFin.setText("");
        if(monEquipe.getAnneeFin() != null) {
            txtAnneeFin.setText(String.format("%d", monEquipe.getAnneeFin()));
        }

        var listeTemp = obtenirListeEquipePourComboBox(txtNomEquipe.getText(), txtVille.getText());
        cboEstDevenuEquipe.setItems(listeTemp);

        /*cboEstDevenuEquipe.getSelectionModel().select(-1);
        if(monEquipe.getEstDevenueEquipe() != null) {
            cboEstDevenuEquipe.getSelectionModel().select(monEquipe.getEstDevenueEquipe());
        }*/
    }

    private ObservableList<equipe> obtenirListeEquipePourComboBox(String nomEquipe, String ville) {
        ObservableList<equipe> listeLocaleEquipes = FXCollections.observableArrayList();
        
        for (int i = 0; i < listeEquipe.size(); i++) {
            if (!(listeEquipe.get(i).getNomEquipe().equals(nomEquipe) && listeEquipe.get(i).getVille().equals(ville))) {
                listeLocaleEquipes.add(listeEquipe.get(i));
            }
        }

        return listeLocaleEquipes;
    }
}
