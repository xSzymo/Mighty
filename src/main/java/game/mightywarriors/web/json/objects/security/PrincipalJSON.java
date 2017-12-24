package game.mightywarriors.web.json.objects.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PrincipalJSON {
    public Collection<? extends GrantedAuthority> authorities;

    public PrincipalJSON(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
