package com.bizsquare.bizsquare_backend.auth.dao;

import com.bizsquare.bizsquare_backend.user.dto.RoleResponse;
import com.bizsquare.bizsquare_backend.jooq.generated.tables.Roles;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao {

    private final DSLContext dsl;

    public RoleDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer findRoleIdByName(String roleName) {
        return dsl.select(Roles.ROLES.ID)
                .from(Roles.ROLES)
                .where(Roles.ROLES.NAME.eq(roleName))
                .fetchOneInto(Integer.class);
    }

    public List<RoleResponse> findAll() {
        return dsl.selectFrom(Roles.ROLES)
                .fetch()
                .map(record -> new RoleResponse(record.getId(), record.getName()));
    }
}
