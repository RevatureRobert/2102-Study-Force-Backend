package com.revature.StudyForce.flashcard.dto;

import com.revature.StudyForce.flashcard.model.*;
import com.revature.StudyForce.user.model.Authority;
import com.revature.StudyForce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class VoteDTOTest {

    @Test
    void DTOFunctionTest(){
        User u = new User(0,"jesus.christ@revature.com","password","Jesus","Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Answer a = new Answer(0,0,0,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);
        VoteDTO v = VoteDTO.voteDTOFunction().apply(vote);

        Assertions.assertEquals(6,v.getVoteId());
        Assertions.assertEquals(1,v.getVoteValue());
        Assertions.assertEquals(0,v.getAnswerId());
        Assertions.assertEquals(0,v.getUserId());
    }
}
