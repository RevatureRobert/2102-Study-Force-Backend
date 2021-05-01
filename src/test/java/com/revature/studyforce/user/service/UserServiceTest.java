package com.revature.studyforce.user.service;


import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

/**
 * Tests for {@link UserService}
 * @author Steven Ceglarek
 */

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:test-application.properties")
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void whenCreateUser_callRepository_returnsCorrectUser(){
        User user = new User("LebronJames@revature.net", "Lebron James");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDTO response = userService.createNewUser(user);
        Assertions.assertNotNull(response);
        Assertions.assertEquals("LebronJames@revature.net", response.getEmail());
        Assertions.assertEquals("Lebron James", response.getName());

        System.out.println(response);
    }
}
