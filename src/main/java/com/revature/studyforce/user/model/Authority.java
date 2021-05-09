package com.revature.studyforce.user.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Model for Authority enum
 * @author Lok Kan Kung
 */
public enum Authority implements GrantedAuthority {

    USER ("ROLE_USER"),
    ADMIN ("ROLE_ADMIN"),
    SUPER_ADMIN ("ROLE_SUPER_ADMIN");

    public final String authorityName;

    Authority(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityName;
    }
}
