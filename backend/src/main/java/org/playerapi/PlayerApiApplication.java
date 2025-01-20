package org.playerapi;

import org.playerapi.player.Player;
import org.playerapi.player.PlayerJPARepository;
import org.playerapi.utility.CreatePlayer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PlayerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(JdbcTemplate repository, PlayerJPARepository playerRepository) {
        return args -> {
            var sql ="INSERT into player(first_name,last_name,age,date_of_birth,phone_number,email,gender,team,terms_accepted) values(?,?,?,?,?,?,?,?,?)";
            Player player = CreatePlayer.make();
            repository.update(sql,player.getFirstName(),player.getLastName(),player.getAge(),player.getDateOfBirth(), player.getPhoneNumber(),player.getEmail(), player.getGender().name(), player.getTeam(), player.isTermsAccept());

            for(int i=0; i<1; i++){
                playerRepository.save(CreatePlayer.make());
            }
        };
    }

}
