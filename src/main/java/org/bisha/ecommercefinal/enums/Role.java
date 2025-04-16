package org.bisha.ecommercefinal.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

}