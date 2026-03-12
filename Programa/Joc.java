package Programa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joc {

    private String           Jugador1;
    private String           Jugador2;
    private List<Personatge> Personatges;

    private Scanner sc;


    // ─── Constructor 
    public Joc() {
        Personatges = new ArrayList<>();
        sc          = new Scanner(System.in);
    }
    public void Nou() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║            JOC RPG           ║");
        System.out.println("╚══════════════════════════════╝");
 
        
        System.out.print("\nNom del Jugador 1: ");
        Jugador1 = llegirNom();
        System.out.print("Nom del Jugador 2: ");
        Jugador2 = llegirNomDistint(Jugador1);
 
        System.out.printf("%nBenvinguts, %s i %s!%n", Jugador1, Jugador2);
 
        boolean menu = true;
        do {
            System.out.println();
            System.out.println("══════ MENÚ PRINCIPAL ══════");
            System.out.println("1. Crear personatge");
            System.out.printf ("2. Jugar combat 1vs1  [Personatges: %d]%n", Personatges.size());
            System.out.println("3. Sortir");
            System.out.print("Opció: ");
            int opcio = llegirInt(1, 3);
            switch (opcio) {
                case 1 -> CrearPersonatge();
                case 2 -> {
                    if (Personatges.size() < 2) {
                        System.out.println(" ( ⚠ ) - Calen almenys 2 personatges. Crea'n més!");
                    } else {
                        jugar();
                    }
                }
                case 3 -> menu = false;
            }
        }while (menu);
        System.out.println("\nFins aviat!");
    }
    private void CrearPersonatge() {
        System.out.println();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       CREAR PERSONATGE       ║");
        System.out.println("╚══════════════════════════════╝");
        
    }

    private void jugar() {
        System.out.println();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         COMBAT 1vs1          ║");
        System.out.println("╚══════════════════════════════╝");
 
        // Aquí aniria la lògica del combat entre dos personatges
        System.out.println("⚠ Funcionalitat de combat no implementada encara.");
    }

    private void tornJugador(Personatge atacant, Personatge defensor) {
        // Canviar arma?
        if (!atacant.getArmes().isEmpty()) {
            System.out.println("  Canviar d'arma?  1.Sí  2.No");
            System.out.print  ("  Opció: ");
            if (llegirInt(1, 2) == 1) atacant.canviarArma();
        }
 
        // Acció
        System.out.println("  Acció:");
        System.out.println("    1. Atacar");
        System.out.println("    2. Atacar (sort pot fer crític → doble dany)");
        System.out.println("    3. Defensar-se  (pròxim atac rebut fa ½ dany)");
        System.out.print  ("  Opció: ");
 
        switch (llegirInt(1, 3)) {
            case 1 -> {
                double dany = atacant.atacar();
                System.out.printf("  %s ataca per %.1f de dany!%n", atacant.getNom(), dany);
                defensor.rebreDanyAtac(dany);
            }
            case 2 -> {
                double dany = atacant.atacar();
                if (atacant.critic()) {
                    dany *= 2;
                    System.out.printf(" ( ! ) ATAC CRÍTIC! %s fa %.1f de dany!%n",
                            atacant.getNom(), dany);
                } else {
                    System.out.printf("  %s ataca per %.1f de dany (sense crític).%n",
                            atacant.getNom(), dany);
                }
                defensor.rebreDanyAtac(dany);
            }
            case 3 -> {
                atacant.defensar(0);
                System.out.printf("  %s es defensa! Pròxim atac rebut farà ½ dany.%n",
                        atacant.getNom());
            }
        }
    }

    private String llegirNom() {
        String nom = "";
        while (nom.isEmpty()) {
            nom = sc.nextLine().trim();
            if (nom.isEmpty()) {
                System.out.print(" ( ⚠ ) - El nom no pot estar buit: ");
                
            }
            
        }
        return nom;
    }
 
    private String llegirNomDistint(String altre) {
        String nom;
        do {
            nom = llegirNom();
            if (nom.equalsIgnoreCase(altre)) {
                System.out.print(" ( ⚠ ) - El nom no pot ser igual al del Jugador 1: ");
                nom = "";
            }
        } while (nom.isEmpty());
        return nom;
    }
 
    private int llegirInt(int min, int max) {
        int valor = 0;
        while (valor < min || valor > max) {
            try {
                valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < min || valor > max)
                    System.out.printf(" ( ⚠ ) - Entre %d i %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf(" ( ⚠ ) - Entrada no vàlida (%d-%d): ", min, max);
            }
        }
        return valor;
    }
}

