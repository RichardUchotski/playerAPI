package org.playerapi.utility;

import com.github.javafaker.Faker;
import org.playerapi.player.Player;

public class CreatePlayer {

    public static Player make() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String firstName = Character.toUpperCase(email.charAt(0)) + email.substring(1,email.indexOf("."));
        String secondName = Character.toUpperCase(email.charAt(email.indexOf(".")+1)) + email.substring(email.indexOf(".")+2, email.indexOf("@"));
        int age = faker.number().numberBetween(18,100);
        return new Player(firstName,secondName,age,email);
    }

}
