package com.revature.studyforce.stacktrace.mapper;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import org.mapstruct.Mappings;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Here we user mapstruct to handle the mapping from our stacktrace to stacktraceDTO so we dont have to
 * manually write out the mapping code
 *
 * author: Noel Shaji
 */

@Mapper(componentModel = "spring")
public abstract class StacktraceMapper {
/*@Mappings({
    @Mapping(target = "creationTime", expression = "java(java.time.Instant.now())"),
    @Mapping(target = "technology", expression = "java(technology)"),
    @Mapping(target = "user", source = "user"),
    @Mapping(target = "body", expression = "java(stacktraceDTO.body)")
})*/
public abstract Stacktrace map(StacktraceDTO stacktraceDTO, Technology technology, User user);
    public abstract Stacktrace map(StacktraceDTO stacktraceDTO, Technology technology);
    /*@Mappings({
    @Mapping(target = "id", source = "java(stacktraceId)"),
    @Mapping(target = "technologyName", source = "java(technologyId.name)"),
    @Mapping(target = "userName", source = "java(user.firstName)")
        })*/
    public abstract  StacktraceDTO mapToDto(Stacktrace stacktrace);



}
