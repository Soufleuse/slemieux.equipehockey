package service;

import model.equipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gererEquipe
 */
public class gererEquipe {

    private String urlBD = "jdbc:mysql://localhost:3306/LigueHockey";

    /**
     * Liste les équipes présentes sur la BD.
     * 
     * @return La liste des équipes lues.
     */
    public List<equipe> listeEquipe() {
        List<equipe> listeEquipe = new ArrayList<equipe>();
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, "lemste", "Misty@00");
            
            Statement st = con.createStatement();
            String sql = ("SELECT * FROM Equipe;");
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                equipe monEquipe = new equipe();
                monEquipe.setId(rs.getInt("Id"));
                monEquipe.setNomEquipe(rs.getString("NomEquipe"));
                monEquipe.setVille(rs.getString("Ville"));
                monEquipe.setAnneeDebut(rs.getInt("AnneeDebut"));
                monEquipe.setAnneeFin(null);
                var monAnneeFin = rs.getInt("AnneeFin");
                if(monAnneeFin != 0) monEquipe.setAnneeFin(monAnneeFin);  // En attendant que je comprenne le Iif Java.
                monEquipe.setEstDevenueEquipe(null);
                var monDevenuEquipe = rs.getInt("EstDevenueEquipe");
                if(monDevenuEquipe != 0) monEquipe.setEstDevenueEquipe(monDevenuEquipe);
                listeEquipe.add(monEquipe);
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    System.out.println(String.format("Erreur sur le close de la connection; message : ", ex.getMessage()));
                }
            }
        }

        return listeEquipe;
    }

    /**
     * Ajoute une équipe à la BD.
     * 
     * @param pEquipe L'équipe à ajouter
     * @return True si l'équipe a été ajoutée; false autrement.
     */
    public boolean ajouterEquipe(equipe pEquipe) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, "lemste", "Misty@00");
            
            String sql = ("INSERT INTO Equipe (Id, NomEquipe, Ville, AnneeDebut, AnneeFin, EstDevenueEquipe) VALUES (?, ?, ?, ?, NULL, NULL);");
            PreparedStatement st = con.prepareStatement(sql);
            
            int idMaxPlusUn = obtenirPlusGrodId() + 1;
            st.setInt(1, idMaxPlusUn);
            st.setString(2, pEquipe.getNomEquipe());
            st.setString(3, pEquipe.getVille());
            st.setInt(4, pEquipe.getAnneeDebut());

            st.executeUpdate();
        }
        catch (SQLException ex) {
            retour = false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    retour = false;
                    System.out.println(String.format("Erreur sur le close de la connection; message : ", ex.getMessage()));
                }
            }
        }

        return retour;
    }

    /**
     * Obtient le plus gros numéro de Id d'équipe.
     * 
     * @return Le plus gros numéro de Id d'équipe.
     */
    private int obtenirPlusGrodId() {
        int retour = -1;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, "lemste", "Misty@00");
            
            String sql = ("SELECT MAX(Id) FROM Equipe;");
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet monResultat = st.executeQuery();
            if (monResultat.next()) {
                retour = monResultat.getInt(1);
            }
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    System.out.println(String.format("Erreur sur le close de la connection; message : ", ex.getMessage()));
                }
            }
        }

        return retour;
    }

    public boolean modifierEquipe (equipe pEquipe) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, "lemste", "Misty@00");
            
            String sql = ("UPDATE Equipe set NomEquipe = ?, Ville = ?, AnneeDebut = ?, AnneeFin = ?, EstDevenueEquipe = ? where Id = ?;");
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, pEquipe.getNomEquipe());
            st.setString(2, pEquipe.getVille());
            st.setInt(3, pEquipe.getAnneeDebut());

            if (pEquipe.getAnneeFin() == null) {
                st.setInt(4, 0);
            } else {
                st.setInt(4, pEquipe.getAnneeFin());
            }

            if (pEquipe.getEstDevenueEquipe() == null) {
                st.setInt(5, 0);
            } else {
                st.setInt(5, pEquipe.getEstDevenueEquipe());
            }

            st.setInt(6, pEquipe.getId());

            st.executeUpdate();
        }
        catch (SQLException ex) {
            retour = false;
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    retour = false;
                    System.out.println(String.format("Erreur sur le close de la connection; message : ", ex.getMessage()));
                }
            }
        }

        return retour;
    }
}
