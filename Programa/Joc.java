package Programa;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joc {

     private String           jugador1;
    private String           jugador2;
    private List<Personatge> personatges;
 
    private Scanner sc;
 
    private static final Arma[] POOL_ARMES = {
        new Arma("Espasa de ferro",   "Espasa", false, 70),
        new Arma("Espasa llarga",     "Espasa", false, 85),
        new Arma("Excalivur",         "Espasa", true,  95),
        new Arma("Bastó de foc",      "Bastó",  true,  80),
        new Arma("Bastó de gel",      "Bastó",  true,  75),
        new Arma("Bastó del llamp",   "Bastó",  true,  95),
        new Arma("Arc de fusta",      "Arc",    false, 60),
        new Arma("Arc Magic",         "Arc",    true,  70),
        new Arma("Arc de caça",       "Arc",    false, 55),
    };
 
    // Getters i Setters 
    public String getJugador1()              { return jugador1; }
    public String getJugador2()              { return jugador2; }
    public List<Personatge> getPersonatges() { return personatges; }
    public void setJugador1(String jugador1) { this.jugador1 = jugador1; }
    public void setJugador2(String jugador2) { this.jugador2 = jugador2; }
    public static void main(String[] args) {
        Joc joc = new Joc();
        joc.Nou();
    }

    // ─── Constructor 
    public Joc() {
        personatges = new ArrayList<>();
        sc          = new Scanner(System.in);
    }
    public void Nou() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║            JOC RPG           ║");
        System.out.println("╚══════════════════════════════╝");
 
        
        System.out.print("\nNom del Jugador 1: ");
        jugador1 = llegirNom();
        System.out.print("Nom del Jugador 2: ");
        jugador2 = llegirNomDistint(jugador1);
 
        System.out.printf("%nBenvinguts, %s i %s!%n", jugador1, jugador2);
 
        boolean menu = true;
        do {
            System.out.println();
            System.out.println("══════ MENÚ PRINCIPAL ══════");
            System.out.println("1. Crear personatge");
            System.out.printf ("2. Jugar combat 1vs1  [Personatges: %d]%n", personatges.size());
            System.out.println("3. Sortir");
            System.out.print("Opció: ");
            int opcio = llegirInt(1, 3);
            switch (opcio) {
                case 1 -> CrearPersonatge();
                case 2 -> {
                    if (personatges.size() < 2) {
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
        System.out.print("Nom: ");
        String nom  = llegirNom();
        System.out.print("Edat (1-1000): ");
        int    edat = llegirInt(1, 1000);
 
        // Triar raça amb l'Enum Raca
        String[] races = {"Elf", "Humà", "Orc", "Nan"};
        System.out.println("Races disponibles:");
        for (int i = 0; i < races.length; i++)
            System.out.printf("  %d. %s%n", i + 1, races[i]);
        System.out.print("Tria: ");
        String raca = races[llegirInt(1, races.length) - 1];
 
        
        System.out.println("Mode: 1. Manual   2. Automàtic");
        System.out.print  ("Opció: ");
        int mode = llegirInt(1, 2);
        double[] stats;

        switch (mode) {
            case 1:
                stats = crearManual();
                break;
            case 2:
                stats = crearAutomatic();
                break;
            default:
                stats = crearAutomatic();
                break;
        }

 
        Personatge p = new Personatge(nom, edat, raca, stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], stats[6]);
 
        // Assignar 3 armes aleatòries
        List<Integer> usats = new ArrayList<>();
        while (usats.size() < 3) {
            int armaRand = (int)(Math.random() * POOL_ARMES.length);
            if (!usats.contains(armaRand)) {
                usats.add(armaRand);
                Arma o = POOL_ARMES[armaRand];
                p.afegirArma(new Arma(o.getNom(), o.getTipus(), o.getMagia(), o.getDany()));
            }
        }
 
        personatges.add(p);
        System.out.println("\n ( ✔ ) Personatge creat!");
        System.out.println(p);
        System.out.println("Armes assignades:");
        for (int i = 0; i < p.getArmes().size(); i++)
            System.out.printf("  %d. %s%n", i + 1, p.getArmes().get(i));
    }
    //   Estadistiques manuals
    private double[] crearManual() {
        String[] noms = {"Força", "Destresa", "Constitució",
                         "Intel·ligència", "Saviesa", "Carisma"};
        double[] stats         = new double[7]; // 6 stats + sort
        int      puntsRestants = Personatge.TOTAL_PUNTS;
 
        System.out.printf("%nRepartiràs %d punts entre 6 stats (min %d, max %d)%n", Personatge.TOTAL_PUNTS, Personatge.MIN_STAT, Personatge.MAX_STAT);
 
        for (int i = 0; i < Personatge.NUM_STATS; i++) {
            if (i == Personatge.NUM_STATS - 1) {
                stats[i] = puntsRestants;
                System.out.printf("  %-18s → assignat: %.0f%n", noms[i], stats[i]);
                break;
            }
            int reserva   = (Personatge.NUM_STATS - i - 1) * Personatge.MIN_STAT;
            int maxPermès = Math.min(Personatge.MAX_STAT, puntsRestants - reserva);
            System.out.printf("  %-18s (restants: %2d | %d-%d): ",
                    noms[i], puntsRestants, Personatge.MIN_STAT, maxPermès);
            int val        = llegirInt(Personatge.MIN_STAT, maxPermès);
            stats[i]       = val;
            puntsRestants -= val;
        }
        // Sort: stat independent, rang 0-4
        System.out.print ("    Tria sort: ");
        stats[6] = llegirInt(Personatge.MIN_SORT, Personatge.MAX_SORT);
        return stats;
    
    }

    //  Estadistiques automàtiques
    private double[] crearAutomatic() {
        double[] stats = new double[7]; // 6 stats + sort
        int extra      = Personatge.TOTAL_PUNTS
                         - Personatge.NUM_STATS * Personatge.MIN_STAT;
        for (int i = 0; i < Personatge.NUM_STATS; i++) stats[i] = Personatge.MIN_STAT;
        while (extra > 0) {
            int idx = (int)(Math.random() * Personatge.NUM_STATS);
            if (stats[idx] < Personatge.MAX_STAT) { stats[idx]++; extra--; }
        }
        // Sort aleatòria 0-4
        stats[6] = (int)(Math.random() * (Personatge.MAX_SORT + 1));
        System.out.printf(
            "%nDistribució automàtica:%n" +
            "  FOR:%.0f  DES:%.0f  CON:%.0f  INT:%.0f  SAV:%.0f  CAR:%.0f  SOR:%.0f%n",
            stats[0], stats[1], stats[2], stats[3], stats[4], stats[5], stats[6]);
        return stats;
    }


    public Personatge escollirPersonatge(String nomJugador, Personatge excloure) {
        List<Personatge> disponibles = new ArrayList<>();
        for (int i = 0; i < personatges.size(); i++) {
            if (personatges.get(i) != excloure) disponibles.add(personatges.get(i));
        }
 
        System.out.printf("%n%s, tria el teu personatge:%n", nomJugador);
        for (int i = 0; i < disponibles.size(); i++) {
            Personatge p = disponibles.get(i);
            System.out.printf("  %d. %-12s [%-4s]  ( HP ) %.0f/%.0f%n",
                    i + 1, p.getNom(), p.getRaca(),
                    p.getSalut(), p.getSalutMaxima());
        }
        System.out.print("Opció: ");
        return disponibles.get(llegirInt(1, disponibles.size()) - 1);
    }


    private void jugar() {
        System.out.println();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         COMBAT 1vs1          ║");
        System.out.println("╚══════════════════════════════╝");
 
        Personatge p1 = escollirPersonatge(jugador1, null);
        Personatge p2 = escollirPersonatge(jugador2, p1);
 
        // Restaurar vida i maná al màxim
        for (int i = 0; i < 500; i++) { p1.regenerarVida(); p1.regenerarMana(); }
        for (int i = 0; i < 500; i++) { p2.regenerarVida(); p2.regenerarMana(); }
 
        System.out.printf("%n⚔  %s (%s)  VS  %s (%s)  ⚔%n",
                jugador1, p1.getNom(), jugador2, p2.getNom());
 
        int torn = 1;
        while (p1.estaViu() && p2.estaViu()) {
            System.out.printf("%n========= TORN %d =========%n", torn);
            mostrarBarra(p1, p2);
 
            System.out.printf("%n( -> ) Torn de %s (%s)%n", jugador1, p1.getNom());
            tornJugador(p1, p2);
            if (!p2.estaViu()) break;
 
            System.out.printf("%n( -> ) Torn de %s (%s)%n", jugador2, p2.getNom());
            tornJugador(p2, p1);
            if (!p1.estaViu()) break;
 
            p1.regenerarVida(); p1.regenerarMana();
            p2.regenerarVida(); p2.regenerarMana();
            System.out.println("  [Fi de torn - vida i maná recuperats parcialment]");
            torn++;
        }
        System.out.println();
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       FI DEL COMBAT          ║");
        System.out.println("╚══════════════════════════════╝");
        if (p1.estaViu())
            System.out.printf(" ( ! )  Guanyador: %s (%s)%n", jugador1, p1.getNom());
        else
            System.out.printf(" ( ! )  Guanyador: %s (%s)%n", jugador2, p2.getNom());
    
    }
    
    private void mostrarBarra(Personatge p1, Personatge p2) {
        System.out.printf("  %-12s  ( HP ) %6.1f/%-6.1f  ( M ) %6.1f/%-6.1f%n",
                p1.getNom(), p1.getSalut(), p1.getSalutMaxima(),
                p1.getMana(),  p1.getManaMaxima());
        System.out.printf("  %-12s  ( HP ) %6.1f/%-6.1f  ( M ) %6.1f/%-6.1f%n",
                p2.getNom(), p2.getSalut(), p2.getSalutMaxima(),
                p2.getMana(),  p2.getManaMaxima());
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
        System.out.println("    2. Defensar-se  (pròxim atac rebut fa ½ dany)");
        System.out.println();
        System.out.print  ("  Opció: ");

        switch (llegirInt(1, 2)) {
            case 1 -> {
                double dany = atacant.atacar();
                if (atacant.critic()) {
                    dany *= 1.5;
                    System.out.printf(" ( ! ) ATAC CRÍTIC! %s fa %.1f de dany!%n",
                            atacant.getNom(), dany);
                } else {
                    System.out.printf("  %s ataca per %.1f de dany!%n",
                            atacant.getNom(), dany);
                }
                defensor.rebreDanyAtac(dany);
            }
            case 2 -> {
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

