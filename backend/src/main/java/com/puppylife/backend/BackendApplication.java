package com.puppylife.backend;

import com.puppylife.backend.security.persistance.entity.PermissionEntity;
import com.puppylife.backend.security.persistance.entity.RoleEntity;
import com.puppylife.backend.security.persistance.entity.UserEntity;
import com.puppylife.backend.security.persistance.repository.UserRepository;
import com.puppylife.backend.security.utils.constants.PermissionEnum;
import com.puppylife.backend.security.utils.constants.RoleEnum;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			// create permissions
			PermissionEntity createPermission = PermissionEntity.builder()
					.name(PermissionEnum.WRITE)
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.name(PermissionEnum.READ)
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name(PermissionEnum.UPDATE)
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.name(PermissionEnum.DELETE)
					.build();
			PermissionEntity reportPermission = PermissionEntity.builder()
					.name(PermissionEnum.REPORT)
					.build();

			// create roles
			RoleEntity roleAdmin = RoleEntity.builder()
					.name(RoleEnum.ADMIN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, reportPermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.name(RoleEnum.USER)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, reportPermission))
					.build();

			RoleEntity roleDogWalker = RoleEntity.builder()
					.name(RoleEnum.DOG_WALKER)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, reportPermission))
					.build();

			RoleEntity roleVeterinarian = RoleEntity.builder()
					.name(RoleEnum.VETERINARIAN)
					.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission, reportPermission))
					.build();


			// create users
			UserEntity userNico = UserEntity.builder()
					.username("nico")
					.password("1234")
					.email("nico@gmail.com")
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userIrene = UserEntity.builder()
					.username("irene")
					.password("1234")
					.email("irene@gmail.com")
					.roles(Set.of(roleDogWalker))
					.build();

			UserEntity userDaniel = UserEntity.builder()
					.username("daniel")
					.password("1234")
					.email("daniel@gmail.com")
					.roles(Set.of(roleUser))
					.build();

			UserEntity userYisus = UserEntity.builder()
					.username("yisus")
					.password("1234")
					.email("yisus@gmail.com")
					.roles(Set.of(roleVeterinarian))
					.build();

			userRepository.saveAll(List.of(userNico, userYisus, userIrene, userDaniel));
		};
	}
}
