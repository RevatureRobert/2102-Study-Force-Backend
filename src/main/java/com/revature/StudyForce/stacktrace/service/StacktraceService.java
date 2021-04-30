package com.revature.StudyForce.stacktrace.service;

public class StacktraceService {
/*
    private final StacktraceRepository STACKTRACE_REPO;

    @Autowired
    public StacktraceService(StacktraceRepository STACKTRACE_REPO){
        this.STACKTRACE_REPO = STACKTRACE_REPO;
    }
    private int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    *//**
     *  Ensures a permitted offset valye
     * @param offset The offset number being validated
     * @return A valid offset value
     *//*
    private int validateOffset(int offset){
        if(offset != 5 && offset != 10 && offset != 25 && offset != 50 && offset != 100)
            offset = 25;
        return offset;
    }
    *//**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     *//*
    private String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)){
            case "creator_id":
                return "creatorId";
            case "title":
                return "title";
            case "technology_id":
                return "technologyId";
            case "creation_time":
                return "creationTime";
            default:
                return "stacktraceId";
        }
    }*/
}
