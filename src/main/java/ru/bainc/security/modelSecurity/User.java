package ru.bainc.security.modelSecurity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bainc.security.dtoSecurity.RoleDto;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {

    @Column(name = "username")
    private String userName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name ="password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name ="user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<Role> roles;



    public User(String userName, String firstName, String lastName, String status, List<Role> rolesId, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setStatus(Status.valueOf(status));
        this.setRoles(rolesId);
        this.password = password;

    }

}
