package org.playerapi.player;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @SequenceGenerator(name ="player_id_seq", sequenceName = "player_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq")
    int id;
    @Column(nullable = false)
    String firstName;
    @Column(nullable = false)
    String lastName;
    @Column(nullable = false)
    int age;
    @Column(nullable = false)
    LocalDate dateOfBirth;
    @Column(nullable = false, unique = true)
    String phoneNumber;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column
    String team;
    @Column(nullable = false)
    boolean termsAccepted;

    // JPA uses proxy instances (e.g. for lazy loading) to interact with entity classes
    // JPA (Java Persistence) is required for reflection to instantiate objects
    public Player() {

    }

    public Player(int id, String firstName, String lastName, int age, LocalDate dateOfBirth, String phoneNumber, String email, Gender gender, String team, boolean termsAccepted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.team = team;
        this.termsAccepted = termsAccepted;
    }

    public Player(String firstName, String lastName, int age, LocalDate dateOfBirth, String phoneNumber, String email, Gender gender, String team, boolean termsAccepted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.team = team;
        this.termsAccepted = termsAccepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isTermsAccept() {
        return termsAccepted;
    }

    public void setTermsAccept(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && age == player.age && Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName) && Objects.equals(email, player.email);
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getAge();
        result = 31 * result + getDateOfBirth().hashCode();
        result = 31 * result + getPhoneNumber().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getGender().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", team='" + team + '\'' +
                ", termsAccepted=" + termsAccepted +
                '}';
    }
}
