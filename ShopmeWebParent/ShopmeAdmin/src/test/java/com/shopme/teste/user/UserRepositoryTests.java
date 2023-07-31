package com.shopme.teste.user;

import com.shopme.common.entity.Roles;
import com.shopme.common.entity.User;
import com.shopme.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUserWithOneRole(){
        Roles adminRole = testEntityManager.find(Roles.class,1);
        User userTest = new User("jenkstest@gmail.com","jenks3432","Jenks","jenks user");
        userTest.addRole(adminRole);
        User savedUser = userRepository.save(userTest);
        assertThat(savedUser.getCode()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles(){

        User userTest = new User("Beemo@gmail.com","beemo123","Beemo","user");

        Roles roleAdmin = new Roles(3);
        Roles roleSalesPerson = new Roles(4);

        userTest.addRole(roleAdmin);
        userTest.addRole(roleSalesPerson);

        User savedUser = userRepository.save(userTest);
        assertThat(savedUser.getCode()).isGreaterThan(0);
    }

    @Test
    public void testUserList(){
        List<User> listUsersTest = userRepository.findAll();
        listUsersTest.forEach(user -> System.out.println(user));
    }

    @Test
    public void testFinUserdByCode(){
        User userTesting = userRepository.findById(1).get();
        System.out.println(userTesting);
        assertThat(userTesting).isNotNull();
    }

    @Test
    public void testUpdateUserDetails(){
        User userTestingUpdate = userRepository.findById(1).get();

        userTestingUpdate.setEmail("Hijaking@gmail.com");
        userTestingUpdate.setFirstName("Hijaking");
        userTestingUpdate.setLastName("J.K");

        userRepository.save(userTestingUpdate);
    }

    @Test
    public void testUpdateUserRoles(){
        User userTestingUpdateRoles = userRepository.findById(1).get();

        Roles roleAdmin = new Roles(1);
        Roles roleShipper = new Roles(2);

        userTestingUpdateRoles.getRoles().remove(roleAdmin);
        userTestingUpdateRoles.getRoles().add(roleShipper);

        userRepository.save(userTestingUpdateRoles);
    }

    @Test
    public void testDeleteUser(){
        Integer userCode = 3;
        userRepository.deleteById(userCode);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "Hijaking@gmail.com";
         User usertesting = userRepository.getUserByEmail(email);
         assertThat(usertesting).isNotNull();
    }

    @Test
    public void testCountByCode(){
        Integer code = 100;
        Long countByCode = userRepository.countBycode(code);
        assertThat(countByCode).isGreaterThan(0);
    }

    @Test
    public void testDisabledUser(){
        Integer code = 13;
        userRepository.updateEnabledStatus(code,false);
    }

    @Test
    public void testEnabledUser(){
        Integer code = 13;
        userRepository.updateEnabledStatus(code,true);
    }

}
