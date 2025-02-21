package org.playerapi.player.integration;

import org.junit.jupiter.api.Test;
import org.playerapi.AbstractTestcontainers;

import static org.assertj.core.api.Assertions.assertThat;

public class TestContainersTest extends AbstractTestcontainers {
    @Test
    void canStartPostgreSQLContainer() {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();

    }
}
