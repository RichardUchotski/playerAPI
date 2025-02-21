package org.playerapi.player;

public record PlayerRequestObject(
        String firstName,
        String lastName,
        int age,
        String dateOfBirth,
        String phoneNumber,
        String email,
        String password, String gender,
        String team,
        boolean termsAccepted
) {
    @Override
    public String toString() {
        return "%s %s %d %s %s %s %s %s %s".formatted(firstName, lastName, age, dateOfBirth, phoneNumber, email, gender, team, termsAccepted);
    }
}
