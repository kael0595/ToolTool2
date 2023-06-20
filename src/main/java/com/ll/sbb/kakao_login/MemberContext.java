package com.ll.sbb.kakao_login;

import com.ll.sbb.User.SiteUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MemberContext extends User implements OAuth2User {
    private final int id;
    private final String email;

    private Map<String, Object> attributes;
    private String userNameAttributeName;

    public MemberContext(SiteUser user, List<GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public MemberContext(SiteUser user, List<GrantedAuthority> authorities, Map<String, Object> attributes, String userNameAttributeName) {
        this(user, authorities);
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return super.getAuthorities().stream().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return this.getAttribute(this.userNameAttributeName).toString();
    }
}
