package ma.gestion;

import ma.gestion.classes.*;
import ma.gestion.repository.ReservationRepository;
import ma.gestion.repository.ReservationRepositoryImpl;
import ma.gestion.repository.SalleRepository;
import ma.gestion.repository.SalleRepositoryImpl;
import ma.gestion.service.ReservationService;
import ma.gestion.service.ReservationServiceImpl;
import ma.gestion.service.SalleService;
import ma.gestion.service.SalleServiceImpl;
import ma.gestion.test.TestScenarios;
import ma.gestion.util.DataInitializer;
import ma.gestion.util.DatabaseMigrationTool;
import ma.gestion.util.PerformanceReport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        System.out.println("=== Systeme de gestion des reservations de salles ===");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-reservations");
        EntityManager em = emf.createEntityManager();

        try {

            SalleRepository salleRepository = new SalleRepositoryImpl();
            SalleService salleService = new SalleServiceImpl(em, salleRepository);

            ReservationRepository reservationRepository = new ReservationRepositoryImpl();
            ReservationService reservationService = new ReservationServiceImpl(em, reservationRepository);

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {

                System.out.println("\n===== MENU =====");
                System.out.println("1 - Initialiser les donnees");
                System.out.println("2 - Executer les tests");
                System.out.println("3 - Simuler une migration de base de donnees");
                System.out.println("4 - Lancer les tests de performance");
                System.out.println("5 - Quitter le programme");
                System.out.print("Choisissez une option : ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        System.out.println("Initialisation des donnees...");
                        DataInitializer dataInitializer = new DataInitializer(emf);
                        dataInitializer.initializeData();
                        System.out.println("Donnees initialisees avec succes.");
                        break;

                    case 2:
                        System.out.println("Execution des scenarios de test...");
                        TestScenarios testScenarios = new TestScenarios(emf, salleService, reservationService);
                        testScenarios.runAllTests();
                        break;

                    case 3:
                        System.out.print("Voulez-vous lancer une simulation de migration ? (o/n) : ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("o")) {
                            System.out.println("Simulation de migration en cours...");
                            System.out.println("La migration est terminee.");
                        } else {
                            System.out.println("Operation annulee.");
                        }
                        break;

                    case 4:
                        System.out.println("Generation du rapport de performance...");
                        PerformanceReport performanceReport = new PerformanceReport(emf);
                        performanceReport.runPerformanceTests();
                        System.out.println("Rapport de performance genere.");
                        break;

                    case 5:
                        exit = true;
                        System.out.println("Fermeture de l'application...");
                        break;

                    default:
                        System.out.println("Option invalide, veuillez recommencer.");
                }
            }

        } finally {

            em.close();
            emf.close();
        }
    }
}