package equipehockey;

import javafx.fxml.FXML;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

import model.Equipe;
import service.gererEquipe;

public class Controller {

    @FXML private GridPane maGrille;
    @FXML private TableView<Equipe> tvwListeEquipe;
    @FXML private Label lblNomEquipe;
    @FXML private TextField txtNomEquipe;
    @FXML private Label lblErreurNomEquipe;
    @FXML private Label lblVille;
    @FXML private TextField txtVille;
    @FXML private Label lblErreurVille;
    @FXML private Label lblAnneeDebut;
    @FXML private TextField txtAnneeDebut;
    @FXML private Label lblErreurAnneeDebut;
    @FXML private Label lblAnneeFin;
    @FXML private TextField txtAnneeFin;
    @FXML private Label lblErreurAnneeFin;

    private List<Equipe> listeEquipe;

    /**
     * Effectue les initialisations du contrôleurs.
     */
    public void initialize() {
        lblErreurNomEquipe.setText("");
        lblErreurVille.setText("");
        lblErreurAnneeDebut.setText("");
        lblErreurAnneeFin.setText("");

        maGrille.getStyleClass().add("grid");
        maGrille.getColumnConstraints().add(new ColumnConstraints(150));
        maGrille.getColumnConstraints().add(new ColumnConstraints(250));
        maGrille.getColumnConstraints().add(new ColumnConstraints(250));
        initialiserListeEquipe();
    }

    /**
     * Initialise le TableView de la liste des équipes.
     */
    private void initialiserListeEquipe() {
        gererEquipe maLecture = new gererEquipe();
        listeEquipe = maLecture.listeEquipe();
        maLecture = null;

        ObservableList<Equipe> listeEquipeObservable = FXCollections.observableArrayList(listeEquipe);
        tvwListeEquipe.setItems(listeEquipeObservable);
        tvwListeEquipe.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tvwListeEquipe.setEditable(true);

        TableColumn<Equipe, String> colonneNomEquipe = new TableColumn<Equipe, String>("Nom de l'équipe");
        TableColumn<Equipe, String> colonneVille = new TableColumn<Equipe, String>("Ville");
        TableColumn<Equipe, Integer> colonneAnneeDebut = new TableColumn<Equipe, Integer>("AnneeDebut");
        TableColumn<Equipe, Integer> colonneAnneeFin = new TableColumn<Equipe, Integer>("AnneeFin");
        
        colonneNomEquipe.setCellValueFactory(new PropertyValueFactory<>("NomEquipe"));
        colonneNomEquipe.setCellFactory(TextFieldTableCell.forTableColumn());
        colonneNomEquipe.setOnEditCommit(new EventHandler<CellEditEvent<Equipe, String>>() {
            @Override
            public void handle(CellEditEvent<Equipe, String> t) {
                gererEquipe monEcriture = new gererEquipe();
                boolean estModifieDansBd = monEcriture.modifierNomEquipe(t.getRowValue().getId(), t.getNewValue());
                monEcriture = null;

                if (estModifieDansBd) {
                    ((Equipe) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setNomEquipe(t.getNewValue());
                }
            }
        });
        
        colonneVille.setCellValueFactory(new PropertyValueFactory<>("Ville"));
        colonneVille.setMinWidth(75);
        colonneVille.setCellFactory(TextFieldTableCell.forTableColumn());
        colonneVille.setOnEditCommit(new EventHandler<CellEditEvent<Equipe, String>>() {
            @Override
            public void handle(CellEditEvent<Equipe, String> t) {
                gererEquipe monEcriture = new gererEquipe();
                boolean estModifieDansBd = monEcriture.modifierVilleEquipe(t.getRowValue().getId(), t.getNewValue());
                monEcriture = null;

                if (estModifieDansBd) {
                    ((Equipe) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setVille(t.getNewValue());
                }
            }
        });

        colonneAnneeDebut.setCellValueFactory(new PropertyValueFactory<>("AnneeDebut"));
        colonneAnneeDebut.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }
        
            @Override
            public Integer fromString(String value) {
                return (value == null) ? null : Integer.parseInt(value);
            }
        }));

        colonneAnneeDebut.setOnEditCommit(new EventHandler<CellEditEvent<Equipe, Integer>>() {
            @Override
            public void handle(CellEditEvent<Equipe, Integer> t) {
                boolean estModifieDansBd = false;
                if (!t.getNewValue().equals(null)) {
                    gererEquipe monEcriture = new gererEquipe();
                    estModifieDansBd = monEcriture.modifierAnneeDebutEquipe(t.getRowValue().getId(), t.getNewValue());
                    monEcriture = null;
                }

                if (estModifieDansBd) {
                    ((Equipe) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAnneeDebut(t.getNewValue());
                }
            }
        });

        colonneAnneeFin.setCellValueFactory(new PropertyValueFactory<>("AnneeFin"));
        colonneAnneeFin.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer value) {
                return value == null ? "" : value.toString();
            }
        
            @Override
            public Integer fromString(String value) {
                return (value == null) ? null : Integer.parseInt(value);
            }
        }));

        colonneAnneeFin.setOnEditCommit(new EventHandler<CellEditEvent<Equipe, Integer>>() {
            @Override
            public void handle(CellEditEvent<Equipe, Integer> t) {
                gererEquipe monEcriture = new gererEquipe();
                boolean estModifieDansBd = monEcriture.modifierAnneeFinEquipe(t.getRowValue().getId(), t.getNewValue());
                monEcriture = null;

                if (estModifieDansBd)
                ((Equipe) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
                    ).setAnneeFin(t.getNewValue());
            }
        });

        tvwListeEquipe.getColumns().setAll(colonneNomEquipe, colonneVille, colonneAnneeDebut, colonneAnneeFin);
    }

    /**
     * Ajoute une équipe à la liste des équipe.
     */
    @FXML private void ajouterEquipe(ActionEvent event) {
        event.consume();
        boolean estValide = true;
        int monAnneeDebut = 0;

        lblErreurNomEquipe.setText("");
        lblErreurVille.setText("");
        lblErreurAnneeDebut.setText("");
        lblErreurAnneeFin.setText("");

        if (txtNomEquipe.getText().isBlank()) {
            estValide = false;
            txtNomEquipe.requestFocus();
            lblErreurNomEquipe.setText("Le nom de l'équipe ne doit pas être vide.");
            lblErreurNomEquipe.setTooltip(new Tooltip("Le nom de l'équipe ne doit pas être vide."));
        }

        if (txtVille.getText().isBlank()) {
            estValide = false;
            lblErreurVille.setText("Le nom de la ville ne doit pas être vide.");
            lblErreurVille.setTooltip(new Tooltip("Le nom de la ville ne doit pas être vide."));
        }

        if (txtAnneeDebut.getText().isBlank()) {
            estValide = false;
            txtAnneeDebut.requestFocus();
            lblErreurAnneeDebut.setText("L'année de début doit être numérique.");
            lblErreurAnneeDebut.setTooltip(new Tooltip("L'année de début doit être numérique."));
        } else {
            try {
                monAnneeDebut = Integer.parseInt(txtAnneeDebut.getText());
            }
            catch (NumberFormatException e) {
                estValide = false;
                txtAnneeDebut.requestFocus();
                lblErreurAnneeDebut.setText("L'année de début doit être numérique.");
                lblErreurAnneeDebut.setTooltip(new Tooltip("L'année de début doit être numérique."));
            }
            catch (Exception machin) {
                System.out.println(machin.getMessage());
            }
        }

        if (estValide) {
            Equipe monAjout = new Equipe(txtNomEquipe.getText(), txtVille.getText(), monAnneeDebut);
            gererEquipe monEcriture = new gererEquipe();
            int idEquipe = monEcriture.ajouterEquipe(monAjout);
            monEcriture = null;
            if(idEquipe != 0) {
                monAjout.setId(idEquipe);
                tvwListeEquipe.getItems().add(monAjout);
            }
        }
    }
}
