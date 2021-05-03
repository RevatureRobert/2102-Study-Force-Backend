package com.revature.studyforce.stacktrace.mapper;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.stacktrace.model.Stacktrace;

/**
 * Here we user mapstruct to handle the mapping from our stacktrace to stacktraceDTO so we dont have to
 * manually write out the mapping code
 *
 * author: Noel Shaji
 */

@Mapper(componentModel = "spring")
public interface StacktraceMapper {

    @Mapping(target = "creationTime", expression = "java(java.time.Instant.now())")
    @Mapping(target = "technology", expression = "technology")
    @Mapping(target = "user", expression = "user")
    @Mapping(target = "body", expression = "stacktraceDTO.body")
    Stacktrace map(StacktraceDTO stacktraceDTO, Technology technology, User user);
    Stacktrace map(StacktraceDTO stacktraceDTO, Technology technology);

    @Mapping(target = "id", source = "stacktraceId")
    @Mapping(target = "technologyName", source = "technologyId.name")
    @Mapping(target = "userName", source = "user.firstName")
    StacktraceDTO mapToDto(Stacktrace stacktrace);


}
