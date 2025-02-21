package org.playerapi.player;

import java.time.LocalDate;
import java.util.List;

public record PlayerDTO(
        int id,
        String firstName,
        String lastName,
        int age,
        LocalDate dateOfBirth,
        String phoneNumber,
        String email,
        Gender gender,
        String team,
        boolean termsAccepted,
        List<String> roles,
        String username
) {
}
