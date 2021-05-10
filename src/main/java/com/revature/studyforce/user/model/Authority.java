package com.revature.studyforce.user.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Model for Authority enum
 * @author Lok Kan Kung
 */
public enum Authority implements GrantedAuthority {

    USER ("USER"),
    ADMIN ("ADMIN"),
    SUPER_ADMIN ("SUPER_ADMIN");

    public final String authorityName;

    Authority(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }

}
