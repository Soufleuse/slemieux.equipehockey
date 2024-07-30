package service;

import model.equipe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * lireequipe
 */
public class lireequipe {

    public List<equipe> lireEquipe() {
        List<equipe> listeEquipe = new ArrayList<equipe>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LigueHockey", "lemste", "Misty@00");
            
            Statement st = con.createStatement();
            String sql = ("SELECT * FROM Equipe;");
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                equipe monEquipe = new equipe();
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
            
            con.close();
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return listeEquipe;
    }
}