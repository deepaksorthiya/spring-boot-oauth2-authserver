package com.example;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.DefaultAddressStandardClaim;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@SpringBootApplication
@ImportRuntimeHints(AuthorizationServerApplication.ExamplesRuntimeHints.class)
public class AuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

    /**
     * For native build below config is required for serializing {@link org.springframework.security.core.Authentication}.
     * Test api/authentication. It will break without this config.
     * {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
     * which use the {@link WebAuthenticationDetails}
     */
    static class ExamplesRuntimeHints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.reflection()
                    .registerType(WebAuthenticationDetails.class, MemberCategory.values())
                    .registerType(OidcUserInfo.class, MemberCategory.values())
                    .registerType(DefaultAddressStandardClaim.class, MemberCategory.values())
                    .registerType(SimpleGrantedAuthority.class, MemberCategory.values())
                    .registerType(Jwt.class, MemberCategory.values());
        }

    }

}
