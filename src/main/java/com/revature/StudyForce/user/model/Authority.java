package com.revature.StudyForce.user.model;

/**
 * Model for Authority enum
 * @author Lok Kan Kung
 */
public enum Authority {

    USER ("USER"),
    ADMIN ("ADMIN"),
    SUPER_ADMIN ("SUPER_ADMIN");

    public final String authorityName;

    Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}
