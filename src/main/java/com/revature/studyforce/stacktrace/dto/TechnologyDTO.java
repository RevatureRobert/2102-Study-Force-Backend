package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Technology;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Function;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TechnologyDTO {
    /**
     * Primary Key for each entry
     */
    private int technologyId;

    /**
     * Name of Technology
     */
    private String technologyName;

    public static Function<Technology, TechnologyDTO> technologyToDTO() {
        return technology -> new TechnologyDTO(technology.getTechnologyId(),
                technology.getTechnologyName());
    }

    public static Function<TechnologyDTO, Technology> dTOtoTechnology() {
        return technologyDTO -> new Technology(technologyDTO.getTechnologyId(),
                technologyDTO.getTechnologyName());
    }
}
