package com.shopme.teste.user;

import com.shopme.common.entity.Roles;
import com.shopme.user.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole(){
        Roles role = new Roles("Admin","administration system");

        Roles saveRole = roleRepository.save(role);
        assertThat(saveRole.getCode()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Roles roleSalesPerson = new Roles( "SalesPerson","manage product price," +
                " custumers, shipping and sales report.");

        Roles roleEditor = new Roles("Editor","manage categories, brands, " +
                "products, articles and menus.");

        Roles roleShipper = new Roles("Shipper","view products, view orders, " +
                "and update order status.");

        Roles roleAssistant = new Roles("Assistant","manage question and reviews");

        List<Roles> addRoles = Arrays.asList(roleSalesPerson,roleEditor,roleShipper,roleAssistant);
        roleRepository.saveAll(addRoles);
    }
}
