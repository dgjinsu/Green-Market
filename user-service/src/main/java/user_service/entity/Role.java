package user_service.entity;

import lombok.Getter;

@Getter
public enum Role {

    SELLER("판매자"),
    BUYER("구매자"),
    ADMIN("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
