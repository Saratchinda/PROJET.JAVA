import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActionsBDDImpl implements ActionsBDD {
    private Connection connection;

    @Override
    public void openConnection() {
        String url = "jdbc:postgresql://localhost:5432/bdtpjava";
        String user = "postgres";
        String password = "saraclem25";

        try {
            // Charger le driver PostgreSQL
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie à PostgreSQL !");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Programmeur> getAllProgrammeurs() {
        List<Programmeur> listeProgrammeurs = new ArrayList<>();
        String query = "SELECT * FROM programmeur";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Programmeur programmeur = new Programmeur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("an_naissance"), // Vérifier le nom de la colonne
                        rs.getDouble("salaire"),
                        rs.getDouble("prime"),
                        rs.getString("pseudo")
                );
                listeProgrammeurs.add(programmeur);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeProgrammeurs;
    }

    @Override
    public boolean deleteProgrammeurById(int id) {
        String query = "DELETE FROM programmeur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addProgrammeur(Programmeur programmeur) {
        String query = "INSERT INTO programmeur (id, nom, prenom, an_naissance, salaire, prime, pseudo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, programmeur.getId());
            pstmt.setString(2, programmeur.getNom());
            pstmt.setString(3, programmeur.getPrenom());
            pstmt.setString(4, programmeur.getAnNaissance());
            pstmt.setDouble(5, programmeur.getSalaire());
            pstmt.setDouble(6, programmeur.getPrime());
            pstmt.setString(7, programmeur.getPseudo());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateProgrammeur(int id, Programmeur programmeur) {
        String query = "UPDATE programmeur SET nom = ?, prenom = ?, an_naissance = ?, salaire = ?, prime = ?, pseudo = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, programmeur.getNom());
            pstmt.setString(2, programmeur.getPrenom());
            pstmt.setString(3, programmeur.getAnNaissance());
            pstmt.setDouble(4, programmeur.getSalaire());
            pstmt.setDouble(5, programmeur.getPrime());
            pstmt.setString(6, programmeur.getPseudo());
            pstmt.setInt(7, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Programmeur getProgrammeurById(int id) {
        String query = "SELECT * FROM programmeur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Programmeur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("an_naissance"),
                        rs.getDouble("salaire"),
                        rs.getDouble("prime"),
                        rs.getString("pseudo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Programmeur getProgrammeurbyName(String nom) {
        return null;
    }

    @Override
    public Programmeur getProgrammeurbyId(int id) {
        return null;
    }

    @Override
    public boolean updateProgrammeurById(int id, Programmeur programmeur) {
        return false;
    }

}
