package com.puppylife.backend.security.persistance.entity;

import com.puppylife.backend.security.utils.constants.PermissionEnum;
import com.puppylife.backend.security.utils.constants.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoleEnum name;
    @ManyToMany(
            fetch = FetchType.EAGER, // recive all instant
            cascade = CascadeType.ALL // create permissions if not exist
    )
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<PermissionEntity> permissions;
}
