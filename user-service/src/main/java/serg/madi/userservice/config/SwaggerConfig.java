package serg.madi.userservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("swagger")
public class SwaggerConfig {

    @Configuration
    @SecurityScheme(
            name = "keycloak",
            type = SecuritySchemeType.OAUTH2,
            flows = @OAuthFlows(
                    authorizationCode = @OAuthFlow(
                            authorizationUrl = "${keycloak.uri}/realms/qonys/protocol/openid-connect/auth",
                            tokenUrl = "${keycloak.uri}/realms/qonys/protocol/openid-connect/token",
                            scopes = {
                                    @OAuthScope(name = "openid"),
                                    @OAuthScope(name = "microprofile-jwt"),
                                    @OAuthScope(name = "edit_catalogue"),
                                    @OAuthScope(name = "view_catalogue")
                            }
                    ))
    )
    public static class SpringDocSecurity {
    }
}
