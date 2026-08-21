package com.bizsquare.bizsquare_backend.security;

import com.bizsquare.bizsquare_backend.auth.dao.UserDao;
import com.bizsquare.bizsquare_backend.jooq.generated.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersRecord user = userDao.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // TODO: Replace with actual role fetching logic
        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
