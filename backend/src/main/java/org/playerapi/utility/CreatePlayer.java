package org.playerapi.utility;

import com.github.javafaker.Faker;
import org.playerapi.player.Gender;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerRequestObject;

import java.time.LocalDate;
import java.time.Period;
import java.util.Random;

public class CreatePlayer {

    private static final Random random = new Random();

    public static Player makePlayer() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String firstName = Character.toUpperCase(email.charAt(0)) + email.substring(1,email.indexOf("."));
        String secondName = Character.toUpperCase(email.charAt(email.indexOf(".")+1)) + email.substring(email.indexOf(".")+2, email.indexOf("@"));
        String password = faker.internet().password();

        int randYear = random.nextInt(1950,2006);
        int randMonth = random.nextInt(1,13);
        int randDay = random.nextInt(1,28);

        LocalDate birthday = LocalDate.of(randYear, randMonth, randDay);
        int age = Period.between(birthday, LocalDate.now()).getYears();
        Gender gender = Gender.values()[random.nextInt(Gender.values().length)];

        StringBuilder phoneNumber = new StringBuilder("07");
        for(int i=0; i<9; i++){
            phoneNumber.append(random.nextInt(10));
        };

        String[] teamNames =   { "Glasgow Korfball Club" , "Edinburgh City" , "Edinburgh Mavericks" , "Edinburgh University" , "St Andrews University" ,   "Strathclyde University", "Barrowland Bears" , "Dundee Korfball Club"  };
        String team = teamNames[random.nextInt(teamNames.length)];


        return new Player(firstName, secondName, age, birthday, phoneNumber.toString(), email, password, gender, team, true);
    }
    public static PlayerRequestObject makePlayerRequestObject() {
        Player createdPlayer = makePlayer();
        return new PlayerRequestObject(createdPlayer.getFirstName(),
                createdPlayer.getLastName(),
                createdPlayer.getAge(),
                createdPlayer.getDateOfBirth().toString(),
                createdPlayer.getPhoneNumber(),
                createdPlayer.getEmail(),
                createdPlayer.getPassword(),
                createdPlayer.getGender().name(),
                createdPlayer.getTeam(),
                createdPlayer.isTermsAccepted()
            );
    }
    public static Player makePlayerFromRequest(PlayerRequestObject playerRequestObject) {
        return new Player(
                playerRequestObject.firstName(),
                playerRequestObject.lastName(),
                playerRequestObject.age(),
                LocalDate.parse(playerRequestObject.dateOfBirth()),
                playerRequestObject.phoneNumber(),
                playerRequestObject.email(),
                playerRequestObject.password(),
                Gender.valueOf(playerRequestObject.gender()),
                playerRequestObject.team(),
                true
        );
    }

}
