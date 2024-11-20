package com.example.ecommerce.Users;

import java.util.List;

import com.example.ecommerce.Users.Address.AddressModel;
import com.example.ecommerce.Users.UserRole.CustomUserQuery.UserAssignedRolesById;
import com.example.ecommerce.Users.UserRole.Role.UserRole;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")

@SqlResultSetMapping(name = "UserRoleDTOMapping", classes = @ConstructorResult(targetClass = UserAssignedRolesById.class, columns = {
        @ColumnResult(name = "user_id", type = Long.class),
        @ColumnResult(name = "user_name", type = String.class),
        @ColumnResult(name = "user_email", type = String.class),
        @ColumnResult(name = "sync_role_id", type = Long.class),
        @ColumnResult(name = "role_id", type = Long.class),
        @ColumnResult(name = "role_name", type = String.class)
}))
@NamedNativeQuery(name = "User.findUserRolesByEmail", query = "SELECT Users.id AS user_id, Users.name AS user_name, Users.email AS user_email, Users_Role_Sync.role_id AS sync_role_id, User_Role.id AS role_id, User_Role.name AS role_name "
        +
        "FROM Users " +
        "INNER JOIN Users_Role_Sync ON Users.id = Users_Role_Sync.user_id " +
        "INNER JOIN User_Role ON Users_Role_Sync.role_id = User_Role.id " +
        "WHERE Users.email = :email", resultSetMapping = "UserRoleDTOMapping")
@Entity(name = "Users")
@Table(name = "Users") // this tells hibernate to make table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    private String name;
    private String email;
    private String password;
    // private Boolean active;

    public User() {

    }

    public void setUserId(long Id) {
        this.id = Id;
    }

    public long getUserId() {
        return this.id;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    @ManyToMany
    @JoinTable(name = "Users_Role_Sync", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRole> userRoles;

    public void setRoles(List<UserRole> roles) {
        this.userRoles = roles;
    }

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    List<AddressModel> address;

    public void setAddress(List<AddressModel> address) {
        this.address = address;
    }

    public List<AddressModel> getAddress() {
        return this.address;
    }
}
