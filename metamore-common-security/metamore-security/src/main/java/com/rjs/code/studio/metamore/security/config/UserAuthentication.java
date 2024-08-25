package com.rjs.code.studio.metamore.security.config;

import com.rjs.code.studio.metamore.common.security.datamodel.UserDetails;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@EqualsAndHashCode
@ToString
public class UserAuthentication extends UsernamePasswordAuthenticationToken {

    private UserDetails user;

    private boolean authenticated;

    public UserAuthentication(UserDetails user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        this.user = user;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;

    }

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }


}
