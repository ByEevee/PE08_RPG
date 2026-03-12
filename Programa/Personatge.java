package Programa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Personatge {
    
    private String nom;
    private int    edat;
    private String raca;        
 
    private double salut;
    private double mana;
 
    private double forca;
    private double destresa;
    private double constitucio;
    private double intelligencia;
    private double saviesa;
    private double carisma;
 
    // Atribut inventat: sort
    private double sort;
 
    private List<Arma> armes;
    private Arma       armaEquipada;
 
    private boolean estaDefenent;
    private boolean provocat;  //Nova Funcionalitat: Provocació    
 
    
    private static final Scanner sc  = new Scanner(System.in);
 
    // Constants per a la creació (7 stats: 6 base + sort)
    public static final int TOTAL_PUNTS = 70;
    public static final int MIN_STAT    = 5;
    public static final int MAX_STAT    = 20;
    public static final int NUM_STATS   = 7;

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
 
        this.armes        = new ArrayList<>();
        this.armaEquipada = null;
        this.estaDefenent = false;
        this.provocat     = false;
    }
}
