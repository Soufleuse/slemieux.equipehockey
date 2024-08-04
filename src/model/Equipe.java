package model;

/**
 * equipe
 */
public class Equipe {
    private int Id = 0;
    private String NomEquipe = "";
    private String Ville = "";
    private int AnneeDebut = 0;      // int = non-nullable
    private Integer AnneeFin = null; // Integer = nullable
    private Integer EstDevenueEquipe = null;

    public Equipe() {}

    public Equipe(String pNomEquipe, String pVille, int pAnneeDebut) {
        this.NomEquipe = pNomEquipe;
        this.Ville = pVille;
        this.AnneeDebut = pAnneeDebut;
        this.AnneeFin = null;
        this.EstDevenueEquipe = null;
    }

    public int getId() { return Id; }
    public void setId(int pId) { this.Id = pId; }
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