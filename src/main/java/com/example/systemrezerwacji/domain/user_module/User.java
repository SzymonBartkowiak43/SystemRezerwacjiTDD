package com.example.systemrezerwacji.domain.user_module;

import com.example.systemrezerwacji.domain.salon_module.Salon;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user")
    List<Salon> salons;

    private User(UserBuilder userBuilder){
        this.email = userBuilder.email;
        this.name = userBuilder.name;
        this.password = userBuilder.password;
        roles = userBuilder.roles;
    }
    public User() {

    }
    User addUserRole(UserRole userRole) {
        roles.add(userRole);
        return this;
    }


    static class UserBuilder {
        private String email;
        private String name;
        private String password;
        private Set<UserRole> roles = new HashSet<>();

        UserBuilder addEmail(String email) {
            this.email = email;
            return this;
        }

        UserBuilder addName(String name) {
            this.name = name;
            return this;
        }

        UserBuilder addPassword(String password) {
            this.password = password;
            return this;
        }

        UserBuilder addUserRole(UserRole userRole) {
            this.roles.add(userRole);
            return this;
        }

        User build() {
            return new User(this);
        }

    }

}
