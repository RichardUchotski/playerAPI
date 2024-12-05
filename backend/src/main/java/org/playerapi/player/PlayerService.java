package org.playerapi.player;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.playerapi.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @PersistenceContext
    private EntityManager entityManager;

    PlayerDAO playerDAO;

    @Autowired
    public PlayerService(@Qualifier("jdbc") PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    public List<Player> getPlayers() {
        return playerDAO.getPlayers();
    }

    public Player getPlayer(int id) {
        Player p = playerDAO.getPlayer(id).orElse(null);

        if(p == null) {
            throw new PlayerByIdNotInDatabaseException("Player with id " + id + " does not exist by ID");
        }

        return p;

    }

    public Player addPlayer(PlayerRequestObject requestObject) {
        // Add the validation and error handling here
        if(!isValidName(requestObject.firstName())){
            throw new RequestPropertyIsNotValid("Valid first name is required");
        }

        if(!isValidName(requestObject.lastName())) {
            throw new RequestPropertyIsNotValid("Valid last name is required");
        }

        String email = requestObject.email();

        if(!isValidEmail(email)) {
            throw new RequestPropertyIsNotValid("Email is required");
        }

        if(requestObject.age() < 18 || requestObject.age() > 101) {
            throw new RequestPropertyIsNotValid("Age must be between 18 and 101");
        }

        if(existsPlayerByEmail(email)){
            throw new PlayerAlreadyExistsByEmailException("Player with email " + email + " already exists");
        }

        Player newPlayerToAdd = new Player(requestObject.firstName(), requestObject.lastName(),requestObject.age(), email);
        playerDAO.addPlayer(newPlayerToAdd);
        return newPlayerToAdd;
    }

    public Player updatePlayer(PlayerRequestObject requestObject, int id) {

        Player playerToUpdate = getPlayer(id);

        if(playerToUpdate == null) {
            throw new PlayerByIdNotInDatabaseException("Player with id " + id + " does not exist by ID");
        }

        if (requestObject == null ||
                (requestObject.firstName() == null &&
                        requestObject.lastName() == null &&
                        requestObject.age() == 0 &&
                        requestObject.email() == null)) {
            throw new RequestPropertyIsNotValid("All fields are required, fields are currently empty");
        }


        // one way, if we have a series of functions to do is to set a true variable and if there is an update, set that to
        // true, and then if there is not an update throw a custom error
        boolean isUpdate = false;

        if(isValidName(requestObject.firstName()) && !playerToUpdate.getFirstName().equals(requestObject.firstName())) {
            playerToUpdate.setFirstName(requestObject.firstName());
            isUpdate = true;
        }

        if(isValidName(requestObject.lastName()) && !playerToUpdate.getLastName().equals(requestObject.lastName())) {
            playerToUpdate.setLastName(requestObject.lastName());
            isUpdate = true;
        }

        if(requestObject.age() > 18 && requestObject.age() < 101 && playerToUpdate.getAge() != requestObject.age()) {
            playerToUpdate.setAge(requestObject.age());
            isUpdate = true;
        }

        if(isValidEmail(requestObject.email()) && !playerToUpdate.getEmail().equals(requestObject.email())) {
            playerToUpdate.setEmail(requestObject.email());
            isUpdate = true;
        }

        if(!isUpdate) {
            throw new NoChangesMadeOnUpdateException("No Update Required as no changes made on update");
        }

        playerDAO.updatePlayer(playerToUpdate);

        return playerToUpdate;
    };

    private boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }

    private boolean isValidEmail(String email) {
        String strictRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && !email.isBlank() && email.matches(strictRegex);
    }

    public String deletePlayer(int id) {
        boolean b = getPlayer(id) != null;

        if(b){
            playerDAO.deletePlayer(id);
            return "Player with id " + id + " deleted";
        } else {
            throw new PlayerByIdNotInDatabaseException("Player with id " + id + " does not exist by ID");
        }

    }

    @Transactional
    public String deleteAllPlayers() {
        if(getPlayers().isEmpty()) {
            throw new NoPlayersInDatabaseException();
        }

        playerDAO.deleteAllPlayers();
        entityManager.createNativeQuery("ALTER SEQUENCE player_id_seq RESTART WITH 1").executeUpdate();

        return "Deleted All Players";
    }

    public boolean existsPlayerByEmail(String email){
        return playerDAO.existsPlayerByEmail(email);
    }
}
