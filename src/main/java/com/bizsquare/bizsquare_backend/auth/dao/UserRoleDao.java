package com.bizsquare.bizsquare_backend.auth.dao;

import com.bizsquare.jooq.generated.tables.UserRoles;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleDao {

    private final DSLContext dsl;

    public UserRoleDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void assignRoleToUser(Integer userId, Integer roleId) {
        dsl.insertInto(UserRoles.USER_ROLES)
                .set(UserRoles.USER_ROLES.USER_ID, userId)
                .set(UserRoles.USER_ROLES.ROLE_ID, roleId)
                .execute();
    }
}
