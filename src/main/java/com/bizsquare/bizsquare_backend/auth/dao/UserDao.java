package com.bizsquare.bizsquare_backend.auth.dao;

import com.bizsquare.bizsquare_backend.auth.dto.UserResponse;
import com.bizsquare.jooq.generated.tables.records.UsersRecord;
import com.bizsquare.jooq.generated.tables.Users;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {

    private final DSLContext dsl;

    public UserDao(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Integer createUser(String name, String email, String hashedPassword, boolean enabled) {
        return dsl.insertInto(Users.USERS)
                .set(Users.USERS.NAME, name)
                .set(Users.USERS.EMAIL, email)
                .set(Users.USERS.PASSWORD, hashedPassword)
                .set(Users.USERS.ENABLED, enabled)
                .returning(Users.USERS.ID)
                .fetchOne()
                .getId();
    }

    public Integer createStaff(String name, String email, String hashedPassword, boolean enabled, Integer ownerId) {
        return dsl.insertInto(Users.USERS)
                .set(Users.USERS.NAME, name)
                .set(Users.USERS.EMAIL, email)
                .set(Users.USERS.PASSWORD, hashedPassword)
                .set(Users.USERS.ENABLED, enabled)
                .set(Users.USERS.OWNER_ID, ownerId)
                .returning(Users.USERS.ID)
                .fetchOne()
                .getId();
    }

    public List<UserResponse> findAll() {
        return dsl.select()
                .from(Users.USERS)
                .fetch()
                .map(record -> new UserResponse(record.get(Users.USERS.ID), record.get(Users.USERS.NAME), record.get(Users.USERS.EMAIL), record.get(Users.USERS.OWNER_ID)));
    }

    public Optional<UsersRecord> findByEmail(String email) {
        return dsl.selectFrom(Users.USERS)
                .where(Users.USERS.EMAIL.eq(email))
                .fetchOptional();
    }

    public Optional<UserResponse> findUserById(Integer userId) {
        return dsl.select()
                .from(Users.USERS)
                .where(Users.USERS.ID.eq(userId))
                .fetchOptional()
                .map(record -> new UserResponse(record.get(Users.USERS.ID), record.get(Users.USERS.NAME), record.get(Users.USERS.EMAIL), record.get(Users.USERS.OWNER_ID)));
    }

    public List<UserResponse> findStaffByOwnerId(Integer ownerId){
        return dsl.select().from(Users.USERS).where(Users.USERS.OWNER_ID.eq(ownerId)).fetch().map(record -> new UserResponse(record.get(Users.USERS.ID), record.get(Users.USERS.NAME), record.get(Users.USERS.EMAIL), record.get(Users.USERS.OWNER_ID)));
    }
}
