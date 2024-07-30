package model;

/**
 * equipe
 */
public class equipe {
    private String NomEquipe = "";
    private String Ville = "";
    private int AnneeDebut = 0;   // int = non-nullable
    private Integer AnneeFin = 0; // Integer = nullable
    private Integer EstDevenueEquipe = 0;

    public String getNomEquipe() { return NomEquipe; }
    public void setNomEquipe(String nomEquipe) { this.NomEquipe = nomEquipe; }
    public String getVille() { return Ville; }
    public void setVille(String ville) { this.Ville = ville; }
    public int getAnneeDebut() { return AnneeDebut; }
    public void setAnneeDebut(int anneeDebut) { this.AnneeDebut = anneeDebut; }
    public Integer getAnneeFin() { return AnneeFin; }
    public void setAnneeFin(Integer anneeFin) { this.AnneeFin = anneeFin; }
    public Integer getEstDevenueEquipe() { return EstDevenueEquipe; }
    public void setEstDevenueEquipe(Integer estDevenueEquipe) { this.EstDevenueEquipe = estDevenueEquipe; }
}