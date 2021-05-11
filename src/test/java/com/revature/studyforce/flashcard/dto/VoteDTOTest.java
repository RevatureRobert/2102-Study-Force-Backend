package com.revature.studyforce.flashcard.dto;
import com.revature.studyforce.flashcard.model.*;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Test class for the methods in VoteDTO {@link VoteDTO}
 * @author Nick Zimmerman
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class VoteDTOTest {

    @Test
    void givenVote_whenConvertVoteToDTO_shouldReturnVoteDTO(){
        User u = new User(0,"jesus.christ@revature.com","Jesus Christ",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,u,null,"how is your day",1,1,null,null,false);

        Answer a = new Answer(0,u,flashcard,"check stackoverflow",5,false,false,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Vote vote = new Vote(6,1,a,u);
        VoteDTO v = VoteDTO.convertVoteToDto().apply(vote);

        System.out.println(vote);
        System.out.println(v.toString());
        Assertions.assertEquals(v.getValue(),vote.getVoteValue());
        Assertions.assertEquals(v.getAnswerId(),vote.getAnswer().getAnswerId());
        Assertions.assertEquals(v.getUserId(),vote.getUser().getUserId());
    }
}
