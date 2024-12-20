import java.util.List;
import java.util.Scanner;

public class Menu {
    private final ActionsBDDImpl actionsBDD = new ActionsBDDImpl();

    public Menu() { }

    public void afficherMenu() {
        actionsBDD.openConnection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n__________MENU___________");
                System.out.println("1. Afficher tous les programmeurs");
                System.out.println("2. Afficher un programmeur");
                System.out.println("3. Supprimer un programmeur");
                System.out.println("4. Ajouter un programmeur");
                System.out.println("5. Modifier le salaire");
                System.out.println("6. Quitter le programme");
                System.out.print("Quel est votre choix : ");

                int choix = scanner.nextInt();
                scanner.nextLine(); // Consomme la nouvelle ligne

                switch (choix) {
                    case 1 -> afficherTousLesProgrammeurs();
                    case 2 -> afficherUnProgrammeur(scanner);
                    case 3 -> supprimerProgrammeur(scanner);
                    case 4 -> ajouterProgrammeur(scanner);
                    case 5 -> modifierSalaire(scanner);
                    case 6 -> {
                        actionsBDD.closeConnection();
                        return;
                    }
                    default -> System.out.println("Choix invalide, veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
                scanner.nextLine(); // Nettoie le scanner
            }
        }
    }

    private void afficherTousLesProgrammeurs() {
        List<Programmeur> programmeurs = actionsBDD.getAllProgrammeurs();
        if (programmeurs.isEmpty()) {
            System.out.println("Aucun programmeur trouvé.");
        } else {
            programmeurs.forEach(System.out::println);
        }
    }

    private void afficherUnProgrammeur(Scanner scanner) {
        System.out.print("Entrez le nom du programmeur : ");
        String nom = scanner.nextLine();
        Programmeur programmeur = actionsBDD.getProgrammeurbyName(nom);
        if (programmeur != null) {
            System.out.println(programmeur);
        } else {
            System.out.println("Programmeur introuvable.");
        }
    }

    private void supprimerProgrammeur(Scanner scanner) {
        System.out.print("Entrez l'ID du programmeur à supprimer : ");
        int id = scanner.nextInt();
        if (actionsBDD.deleteProgrammeurById(id)) {
            System.out.println("Programmeur supprimé avec succès.");
        } else {
            System.out.println("Échec de la suppression.");
        }
    }

    private void ajouterProgrammeur(Scanner scanner) {
        System.out.println("Ajout d'un nouveau programmeur :");
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Année de naissance : ");
        String anNaissance = scanner.nextLine();
        System.out.print("Salaire : ");
        double salaire = scanner.nextDouble();
        System.out.print("Prime : ");
        double prime = scanner.nextDouble();
        scanner.nextLine(); // Consomme la nouvelle ligne
        System.out.print("Pseudo : ");
        String pseudo = scanner.nextLine();

        Programmeur programmeur = new Programmeur(0, nom, prenom, anNaissance, salaire, prime, pseudo);
        if (actionsBDD.addProgrammeur(programmeur)) {
            System.out.println("Programmeur ajouté avec succès.");
        } else {
            System.out.println("Erreur lors de l'ajout du programmeur.");
        }
    }

    private void modifierSalaire(Scanner scanner) {
        System.out.print("Entrez l'ID du programmeur : ");
        int id = scanner.nextInt();
        System.out.print("Entrez le nouveau salaire : ");
        double salaire = scanner.nextDouble();
        scanner.nextLine(); // Consomme la nouvelle ligne

        Programmeur programmeur = actionsBDD.getProgrammeurbyId(id);
        if (programmeur != null) {
            programmeur.setSalaire(salaire);
            if (actionsBDD.updateProgrammeurById(id, programmeur)) {
                System.out.println("Salaire mis à jour avec succès.");
            } else {
                System.out.println("Erreur lors de la mise à jour du salaire.");
            }
        } else {
            System.out.println("Programmeur introuvable.");
        }
    }
}
