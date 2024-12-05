package org.playerapi.player.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.playerapi.AbstractTestcontainers;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerJPARepository;
import org.playerapi.utility.CreatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerRepositoryTest  extends AbstractTestcontainers {

    @Autowired
    PlayerJPARepository underTest;

//    @Autowired
//    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
//        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail(){
        Player player = CreatePlayer.make();
        underTest.save(player);
        assertThat(underTest.existsByEmail(player.getEmail())).isTrue();
    }


}
