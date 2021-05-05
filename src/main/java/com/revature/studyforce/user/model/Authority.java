package com.revature.studyforce.user.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.function.Function;

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

    public static Function<Authority, SimpleGrantedAuthority> toAuthorities() {
        return authority -> new SimpleGrantedAuthority(authority.authorityName);
    }
}
