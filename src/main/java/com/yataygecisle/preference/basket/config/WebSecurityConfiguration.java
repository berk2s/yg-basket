package com.yataygecisle.preference.basket.config;

import com.yataygecisle.preference.basket.web.controllers.BasketController;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests(authz -> authz
                        .mvcMatchers(BasketController.ENDPOINT).hasAuthority("WRITE_BASKET")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt()
                        .jwtAuthenticationConverter(getJwtAuthenticationConverter()));
    }

    private Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            for (String authority :  jwt.getClaimAsStringList("authorities")) {
                grantedAuthorities.add(
                        new SimpleGrantedAuthority(authority)
                );
            }

            return grantedAuthorities;
        });
        return converter;
    }
}
