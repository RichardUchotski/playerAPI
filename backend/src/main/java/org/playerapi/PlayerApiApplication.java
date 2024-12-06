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
        System.out.println("Just adding a line before running the spring boot, adding a comment");
        SpringApplication.run(PlayerApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(JdbcTemplate repository, PlayerJPARepository playerRepository) {
        return args -> {
            var sql ="INSERT into player(first_name,last_name,age,email) values(?,?,?,?)";
            Player player = CreatePlayer.make();
            repository.update(sql,player.getFirstName(),player.getLastName(),player.getAge(),player.getEmail());
            playerRepository.save(CreatePlayer.make());
        };
    }

}
