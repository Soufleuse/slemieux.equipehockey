package service;

import model.Equipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.io.FileInputStream;

/**
 * Classe gererEquipe
 */
public class gererEquipe {

    private String urlBD = "jdbc:mysql://localhost:3306/LigueHockey";
    private String utilisateur;
    private String motdepasse;

    public gererEquipe() {
        try {
            FileInputStream lireUtilisateur = new FileInputStream("src/equipehockey/utilisateur.ini");
            utilisateur = new String(lireUtilisateur.readAllBytes());
            lireUtilisateur.close();

            FileInputStream lireMotdepasse = new FileInputStream("src/equipehockey/motdepasse.ini");
            motdepasse = new String(lireMotdepasse.readAllBytes());
            lireMotdepasse.close();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Liste les équipes présentes sur la BD.
     * 
     * @return La liste des équipes lues.
     */
    public List<Equipe> listeEquipe() {
        List<Equipe> listeEquipe = new ArrayList<Equipe>();
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            Statement st = con.createStatement();
            String sql = ("SELECT * FROM Equipe;");
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                Equipe monEquipe = new Equipe();
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
    public int ajouterEquipe(Equipe pEquipe) {
        int retour = 0;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            String sql = ("INSERT INTO Equipe (Id, NomEquipe, Ville, AnneeDebut, AnneeFin, EstDevenueEquipe) VALUES (?, ?, ?, ?, NULL, NULL);");
            PreparedStatement st = con.prepareStatement(sql);
            
            int idMaxPlusUn = obtenirPlusGrodId() + 1;
            st.setInt(1, idMaxPlusUn);
            st.setString(2, pEquipe.getNomEquipe());
            st.setString(3, pEquipe.getVille());
            st.setInt(4, pEquipe.getAnneeDebut());

            st.executeUpdate();
            retour = idMaxPlusUn;
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

    /**
     * Obtient le plus gros numéro de Id d'équipe.
     * 
     * @return Le plus gros numéro de Id d'équipe.
     */
    private int obtenirPlusGrodId() {
        int retour = -1;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
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

    /**
     * Modifie le nom de l'équipe.
     * @param pId Le Id de l'équipe.
     * @param pNomEquipe Le nom de l'équipe.
     * @return true si ça a marché; false autrement.
     */
    public boolean modifierNomEquipe (Integer pId, String pNomEquipe) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            String sql = ("UPDATE Equipe set NomEquipe = ? where Id = ?;");
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, pNomEquipe);
            st.setInt(2, pId);

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
     * Modifie la ville de l'équipe.
     * @param pId Le Id de la ville.
     * @param pNomVille Le nom de la ville.
     * @return true si ça a marché; false autrement.
     */
    public boolean modifierVilleEquipe (Integer pId, String pNomVille) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            String sql = ("UPDATE Equipe set Ville = ? where Id = ?;");
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, pNomVille);
            st.setInt(2, pId);

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
     * Modifie l'année de début de l'équipe.
     * @param pId Le Id de l'équipe.
     * @param pAnneeDebut L'année de début de l'équipe.
     * @return true si ça a marché; false autrement.
     */
    public boolean modifierAnneeDebutEquipe (Integer pId, int pAnneeDebut) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            String sql = ("UPDATE Equipe set AnneeDebut = ? where Id = ?;");
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setInt(1, pAnneeDebut);
            st.setInt(2, pId);

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
     * Modifie la date de fin de l'équipe.
     * @param pId Le Id de l'équipe
     * @param pAnneeFin L'année de fin de l'équipe.
     * @return true si ça a marché; false autrement.
     */
    public boolean modifierAnneeFinEquipe (Integer pId, Integer pAnneeFin) {
        boolean retour = true;
        Connection con = null;

        try {
            con = DriverManager.getConnection(urlBD, utilisateur, motdepasse);
            
            String sql = ("UPDATE Equipe set AnneeFin = ? where Id = ?;");
            PreparedStatement st = con.prepareStatement(sql);

            if (pAnneeFin == null) {
                st.setNull(1, java.sql.Types.NULL);
            } else {
                st.setInt(1, pAnneeFin.intValue());
            }
            
            st.setInt(2, pId);

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
