package Programa;

public class Arma {
 
   
    private String  nom;
    private String  tipus;    
    private boolean magia;
    private double  dany;
 
    
    public Arma(String nom, String tipus, boolean magia, double dany) {
        this.nom   = nom;
        this.tipus = tipus;
        this.magia = magia;
        this.dany  = Math.max(1, Math.min(100, dany));
    }
 
    
    public double  getDany()   { return dany; }
    public boolean getMagia()  { return magia; }
 
   
    public String getNom()   { return nom; }
    public String getTipus() { return tipus; }

    

}