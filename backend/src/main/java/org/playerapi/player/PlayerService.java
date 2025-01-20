package org.playerapi.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.playerapi.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String firstName = requestObject.firstName();
        if(!isValidName(firstName)){
            throw new RequestPropertyIsNotValid("Valid first name is required");
        }


        String lastName = requestObject.lastName();
        if(!isValidName(lastName)) {
            throw new RequestPropertyIsNotValid("Valid last name is required");
        }


        String email = requestObject.email();
        if(!isValidEmail(email)) {
            throw new RequestPropertyIsNotValid("Email is required");
        }

        int age = requestObject.age();
        if(age < 18 || age > 101) {
            throw new RequestPropertyIsNotValid("Age must be between 18 and 101");
        }

        if(existsPlayerByEmail(email)){
            throw new PlayerAlreadyExistsByEmailException("Player with email " + email + " already exists");
        }

        LocalDate dateOfBirth = getDateOfBirth(requestObject);
        if(!isValidDateOBirth(dateOfBirth)){
            throw new RequestPropertyIsNotValid("Valid date of birth is required, age can not be over 101");
        }

        String phoneNumber = requestObject.phoneNumber();
        if(!isValidPhoneNumber(phoneNumber)){
            throw new RequestPropertyIsNotValid("Valid phone number is required");
        }

        Gender gender;
        try{
            gender = Gender.valueOf(requestObject.gender().toUpperCase());
        } catch(IllegalArgumentException e){
            throw new RequestPropertyIsNotValid("Valid gender is required");
        }


        String team = requestObject.team();
        if(!isValidTeam(team)){
            throw new RequestPropertyIsNotValid("Valid team is required");
        }

        boolean checked = Objects.equals(requestObject.termsAccepted(), "on");


        if(!checked){
            throw new RequestPropertyIsNotValid("Needs to be checked so terms are agreed to");
        }


        Player newPlayerToAdd = new Player(firstName,lastName,age,dateOfBirth,phoneNumber,email,gender,team, true);
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
                        requestObject.email() == null &&
                        requestObject.dateOfBirth() == null &&
                        requestObject.phoneNumber() == null &&
                        requestObject.gender() == null && requestObject.team() == null
        )) {
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

        LocalDate dateOfBirth = getDateOfBirth(requestObject);
        if(isValidDateOBirth(dateOfBirth) && !playerToUpdate.getDateOfBirth().equals(dateOfBirth)) {
            playerToUpdate.setDateOfBirth(dateOfBirth);
            isUpdate = true;
        }


        if(isValidEmail(requestObject.email()) && !playerToUpdate.getEmail().equals(requestObject.email())) {
            playerToUpdate.setEmail(requestObject.email());
            isUpdate = true;
        }

        if(isValidPhoneNumber(requestObject.phoneNumber()) && !playerToUpdate.getPhoneNumber().equals(requestObject.phoneNumber())) {
            playerToUpdate.setPhoneNumber(requestObject.phoneNumber());
            isUpdate = true;
        }

        if(isValidTeam(requestObject.team()) && !playerToUpdate.getTeam().equals(requestObject.team())) {
            playerToUpdate.setTeam(requestObject.team());
            isUpdate = true;
        }

        boolean changeGender;
        Gender newGender = null;

        try{
            newGender = Gender.valueOf(requestObject.gender());
            changeGender = true;
        } catch (IllegalArgumentException e){
            changeGender = false;
        }

        if(changeGender && !playerToUpdate.getGender().equals(newGender)) {
            playerToUpdate.setGender(newGender);
            isUpdate = true;
        }


        if(!isUpdate) {
            throw new NoChangesMadeOnUpdateException("No Update Required as no changes made on update");
        }

        playerDAO.updatePlayer(playerToUpdate);

        return playerToUpdate;
    };



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

    // Helper methods
    private boolean isValidName(String name) {
        return name != null && !name.isBlank();
    }

    private boolean isValidEmail(String email) {
        String strictRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && !email.isBlank() && email.matches(strictRegex);
    }

    private boolean isValidDateOBirth(LocalDate date) {
        LocalDate today = LocalDate.now();

        if(date.isAfter(today)) {
            return false;
        }

        int age = Period.between(date,today).getYears();
        return age <= 101;

    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {
            return false;

        }

        Pattern pattern = Pattern.compile("^(?:07|\\+44|44)\\d{9}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();

    }

    private boolean isValidTeam(String team){
        String[] teamNames =   { "Glasgow Korfball Club" , "Edinburgh City" , "Edinburgh Mavericks" , "Edinburgh University" , "St Andrews University" ,   "Strathclyde University", "Barrowland Bears" , "Dundee Korfball Club"  };

        return Arrays.asList(teamNames).contains(team);
    }

    private static LocalDate getDateOfBirth(PlayerRequestObject requestObject) {
        String[] dateString = requestObject.dateOfBirth().split("-");
        return LocalDate.of(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]));
    }

}
