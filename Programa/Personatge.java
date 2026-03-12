package Programa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Personatge {
    
    private String nom;
    private int    edat;
    private String raca;        
 
    private double salut;
    private double salutMaxima;
    private double mana;
    private double manaMaxima;
 
    private double forca;
    private double destresa;
    private double constitucio;
    private double intelligencia;
    private double saviesa;
    private double carisma;
 
    // Atribut inventat: sort
    private double sort;
 
    private List<Arma> inventari;
    private Arma       armaEquipada;
 
    private boolean estaDefenent;
    private boolean critic;  
 
    
    private static final Scanner sc  = new Scanner(System.in);
 
    // Constants per a la creació (7 stats: 6 base + sort)
    public static final int TOTAL_PUNTS = 70;
    public static final int MIN_STAT    =  5;
    public static final int MAX_STAT    = 20;
    public static final int NUM_STATS   =  6;
    public static final int MIN_SORT    =  0;
    public static final int MAX_SORT    =  4;

     public Personatge(String nom, int edat, String raca, double forca, double destresa, double constitucio, double intelligencia, double saviesa, double carisma, double sort) {
        this.nom           = nom;
        this.edat          = edat;
        this.raca          = raca;
        this.forca         = forca;
        this.destresa      = destresa;
        this.constitucio   = constitucio;
        this.intelligencia = intelligencia;
        this.saviesa       = saviesa;
        this.carisma       = carisma;
        this.sort          = sort;
 
        this.salut = constitucio   * 50;
        this.mana  = intelligencia * 30;
 
        this.inventari        = new ArrayList<>();
        this.armaEquipada = null;
        this.estaDefenent = false;
        this.critic       = false;
    }

    // Setters 
    
    public void setNom(String nom)             { this.nom  = nom; }
    public void setEdat(int edat)              { this.edat = edat; }
    public void setRaca(String raca)           { this.raca = raca; }
    public void setSalut(double salut)         { this.salut = Math.max(0, Math.min(salut, getSalutMaxima())); }
    public void setMana(double mana)           { this.mana  = Math.max(0, Math.min(mana,  getManaMaxima())); }
    public void setForca(double forca)         { this.forca         = Math.max(MIN_STAT, Math.min(forca,         MAX_STAT)); }
    public void setDestresa(double destresa)   { this.destresa      = Math.max(MIN_STAT, Math.min(destresa,      MAX_STAT)); }
    public void setConstitucio(double con)     { this.constitucio   = Math.max(MIN_STAT, Math.min(con,           MAX_STAT)); }
    public void setIntelligencia(double intel) { this.intelligencia = Math.max(MIN_STAT, Math.min(intel,         MAX_STAT)); }
    public void setSaviesa(double saviesa)     { this.saviesa       = Math.max(MIN_STAT, Math.min(saviesa,       MAX_STAT)); }
    public void setCarisma(double carisma)     { this.carisma       = Math.max(MIN_STAT, Math.min(carisma,       MAX_STAT)); }
    public void setSort(double sort)           { this.sort          = Math.max(MIN_STAT, Math.min(sort,          MAX_STAT)); }
    

    // Getters 
    public String     getNom()           { return nom; }
    public int        getEdat()          { return edat; }
    public String     getRaca()          { return raca; }
    public double     getSalut()         { return salut; }
    public double     getMana()          { return mana; }
    public double     getForca()         { return forca; }
    public double     getDestresa()      { return destresa; }
    public double     getConstitucio()   { return constitucio; }
    public double     getIntelligencia() { return intelligencia; }
    public double     getSaviesa()       { return saviesa; }
    public double     getCarisma()       { return carisma; }
    public double     getSort()          { return sort; }
    public List<Arma> getArmes()         { return inventari; }
    public Arma       getArmaEquipada()  { return armaEquipada; }
    public double     getSalutMaxima()   { return constitucio   * 50; }
    public double     getManaMaxima()    { return intelligencia * 30; }
    public boolean    estaViu()          { return salut > 0; }
 

    public void defensar(double dany) {
        estaDefenent = true;
    }

    public void canviarArma() {
        if (inventari.isEmpty()) {
            System.out.println("    No tens armes a l'inventari.");
            return;
        }
        System.out.println("    Armes disponibles:");
        for (int i = 0; i < inventari.size(); i++) {
            String eq = (inventari.get(i) == armaEquipada) ? " ◄ equipada" : "";
            System.out.printf("      %d. %s%s%n", i + 1, inventari.get(i), eq);
        }
        System.out.printf("      %d. Sense arma%n", inventari.size() + 1);
        System.out.print("    Opció: ");
 
        int opcio = llegirInt(1, inventari.size() + 1);
        if (opcio == inventari.size() + 1) {
            armaEquipada = null;
            System.out.println("    Arma desequipada.");
        } else {
            Arma triada = inventari.get(opcio - 1);
            if (triada.getMagia() && intelligencia < 10) {
                System.out.println(" ( ✘ ) Cal Intel·ligència >= 10 per equipar una arma màgica.");
            } else {
                armaEquipada = triada;
                System.out.printf(" ( ✔ ) Equipada: %s%n", armaEquipada.getNom());
            }
        }
        
    }
    public void afegirArma(Arma a) {
        inventari.add(a);
    }
    private int llegirInt(int min, int max) {
        int val = Integer.MIN_VALUE;
        while (val < min || val > max) {
            try {
                val = Integer.parseInt(sc.nextLine().trim());
                if (val < min || val > max)
                    System.out.printf("    Introdueix entre %d i %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("    Entrada no vàlida (%d-%d): ", min, max);
            }
        }
        return val;
    }
     public double atacar() {
        double dany;
        if (armaEquipada == null || !armaEquipada.getMagia()) {
            if (armaEquipada == null) {
                dany = forca * (1/100.0);
            }
            else {
            double danyArma = armaEquipada.getDany();
            dany = forca * (1 + danyArma / 100.0);
            }
        } else {
            dany = armaEquipada.getDany() * intelligencia / 100.0;
        }
        return dany;
    
    }

    public boolean esquivar() {
        
        double prob = (destresa - 5) * 3.33 + sort * 4;
        if ((Math.random() * 100 + 1) <= prob) {
            return true;
            
        }
        else {
            return false;
        }
        
    }

    public boolean critic() {
        double prob = (sort + 1) * 0.12;
        if (Math.random()*1 <=prob) {
            return true;
        }
        else {
            return false;
        }
        
        }

    public void regenerarVida() {
        salut = Math.min(salut + constitucio * 3, constitucio * 50);
    }
 
    public void regenerarMana() {
        mana = Math.min(mana + intelligencia * 2, intelligencia * 30);
    }
    
    public void rebreDanyAtac(double dany) {
        if (esquivar()) {
            System.out.printf("   ( ---> ) %s ha ESQUIVAT l'atac!%n", nom);
            return;
        }
        if (estaDefenent) {
            dany         /= 2.0;
            estaDefenent  = false;
            System.out.printf("   ( D ) %s estava en defensa! Dany reduït a %.1f%n", nom, dany);
        }
        salut = Math.max(0, salut - dany);
        System.out.printf("   ( -> ) %s rep %.1f de dany.  Salut: %.1f / %.1f%n",
                nom, dany, salut, constitucio * 50);
    }

@Override
    public String toString() {
        String arma = armaEquipada != null ? armaEquipada.getNom() : "Cap";
        return "Nom: " + nom + " [" + raca + "] " + edat + " anys\n" +
            "Salut: " + salut + "/" + getSalutMaxima() + "\n" +
            "Mana: " + mana + "/" + getManaMaxima() + "\n" +
            "FOR:" + forca + " DES:" + destresa + " CON:" + constitucio +
            " INT:" + intelligencia + " SAV:" + saviesa + 
            " CAR:" + carisma + " SOR:" + sort + "\n" +
            "Arma: " + arma;
}
}
