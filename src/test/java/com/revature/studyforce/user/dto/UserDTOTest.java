package com.revature.studyforce.user.dto;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.util.function.Function;

/**
 * UserDTO testing to confirm the functionality for the conversion {@link UserDTO}
 * @author Lok Kan Kung
 */

@SpringBootTest
class UserDTOTest {

    @Test
    void whenConvertingUserToUserDTO_FieldsMatchOriginalObject(){

        Timestamp registrationTime = Timestamp.valueOf ("2021-04-30 10:58:01");
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        Authority authority = Authority.USER;
        User user = new User(0,"testing@gmail.com","password","test",
                true,true,true,
                authority,registrationTime,lastLoginTime);

        UserDTO userDTO = UserDTO.userToDTO ().apply (user);

        Assertions.assertEquals (0,userDTO.getUserId ());
        Assertions.assertEquals ("testing@gmail.com",userDTO.getEmail ());
        Assertions.assertEquals ("",userDTO.getPassword ());
        Assertions.assertEquals ("test",userDTO.getName ());
        Assertions.assertTrue (userDTO.isActive ());
        Assertions.assertTrue (userDTO.isSubscribedFlashcard ());
        Assertions.assertTrue (userDTO.isSubscribedStacktrace ());
        Assertions.assertEquals (authority,userDTO.getAuthority ());
        Assertions.assertEquals (registrationTime,userDTO.getRegistrationTime ());
        Assertions.assertEquals (lastLoginTime,userDTO.getLastLogin ());
    }

    @Test
    void whenConvertingUserDTOToUser_FieldsMatchOriginalObject(){
        Timestamp registrationTime = Timestamp.valueOf ("2021-04-30 10:58:01");
        Timestamp lastLoginTime = Timestamp.valueOf ("2021-04-30 11:00:01");
        Authority authority = Authority.USER;
        UserDTO userDTO = new UserDTO (0,"testing@gmail.com","password","test",
                true,true,true,
                authority,registrationTime,lastLoginTime);
        User user = UserDTO.dtoToUser ().apply (userDTO);

        Assertions.assertEquals (0,user.getUserId ());
        Assertions.assertEquals ("testing@gmail.com",user.getEmail ());
        Assertions.assertEquals ("password",user.getPassword ());
        Assertions.assertEquals ("test",user.getName ());
        Assertions.assertTrue (user.isActive ());
        Assertions.assertTrue (user.isSubscribedFlashcard ());
        Assertions.assertTrue (user.isSubscribedStacktrace ());
        Assertions.assertEquals (authority,user.getAuthority ());
        Assertions.assertEquals (registrationTime,user.getRegistrationTime ());
        Assertions.assertEquals (lastLoginTime,user.getLastLogin ());
    }

    @Test
    void attemptToConvertNullObjectToUserDTO_ThrowsIllegalArgumentException(){
        Function<User,UserDTO> function = UserDTO.userToDTO();
        Assertions.assertThrows (IllegalArgumentException.class,() ->{
            function.apply (null);
        });
    }

    @Test
    void attemptToConvertNullObjectToUser_ThrowsIllegalArgumentException(){
        Function<UserDTO,User> function = UserDTO.dtoToUser ();
        Assertions.assertThrows (IllegalArgumentException.class,() ->{
            function.apply (null);
        });
    }
}