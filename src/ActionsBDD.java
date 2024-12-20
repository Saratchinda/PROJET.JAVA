import java.util.List;

public interface ActionsBDD {
    void openConnection();
    void closeConnection();
    List<Programmeur> getAllProgrammeurs();
    boolean deleteProgrammeurById(int id);
    boolean addProgrammeur(Programmeur programmeur);

    boolean updateProgrammeur(int id, Programmeur programmeur);

    Programmeur getProgrammeurbyName(String nom);
    Programmeur getProgrammeurbyId(int id);
    boolean updateProgrammeurById(int id, Programmeur programmeur);
}
