package com.revature.StudyForce.stacktrace.dto;

import com.revature.StudyForce.stacktrace.model.Technology;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.function.Function;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyDTO {
    /**
     * Primary Key for each entry
     */
    private int technologyId;
    /**
     * Name of the programming language.
     */
    private String technologyName;

    public static Function<Technology, TechnologyDTO> technologyToDTO() {
        return technology -> {
            return new TechnologyDTO(technology.getTechnologyId(),
                    technology.getTechnologyName());
        };
    }

    public static Function<TechnologyDTO, Technology> dtoToTechnology() {
        return technologyDTO -> {
            return new Technology(technologyDTO.getTechnologyId(),
                    technologyDTO.getTechnologyName());
        };
    }
}
