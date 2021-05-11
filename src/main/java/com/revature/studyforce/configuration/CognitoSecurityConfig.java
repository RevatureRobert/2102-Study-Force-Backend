package com.revature.studyforce.configuration;
import com.revature.studyforce.cognito.CognitoAccessTokenConverter;
import com.revature.studyforce.cognito.CognitoService;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.util.Collections;

/**
 * Spring boot programmatic configuration of OAuth2 resource server security
 * @author Nick Wickham
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, jsr250Enabled = true)
public class CognitoSecurityConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties resource;

    /**
     * Sets default properties through overriding beans passed into
     * resource via autowiring.
     * @param resource the configured resource properties for an OAuth2 resource server.
     */
    @Autowired
    public CognitoSecurityConfig(ResourceServerProperties resource) {
        this.resource = resource;
    }

    /**
     * Sets specific properties through overriding beans passed into
     * resource via autowiring.
     * @param http A Spring Boot helper object of type {@link HttpSecurity}
     *             used to configure the Http security of StudyForce
     *             to require all requests to be authenticated.
     *
     * @throws Exception if incorrect configuration prevents build process.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().authorizeRequests().anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * A JSON web keys token store is needed to comply with OAuth2 guidelines, defining this bean overrides the default behavior
     * of the application's token store to define the key set location/uti, and specify a conversion bean {@link CognitoAccessTokenConverter}
     * to handle the mapping of token claims to the Spring security context.
     * @param cognitoService service for handling Cognito API calls
     * @param userService service for handling User related business logic
     * @return configured TokenStore
     */
    @Bean
    public TokenStore jwkTokenStore(CognitoService cognitoService, UserService userService) {
        return new JwkTokenStore(
                Collections.singletonList(resource.getJwk().getKeySetUri()),
                new CognitoAccessTokenConverter(cognitoService, userService),
                null);
    }

    /**
     * Bean used to configure interaction with AWS Cognito Identity Provider.
     */
    @Primary
    @Bean
    CognitoIdentityProviderClient cognitoIdentityProviderClientBean(){
        return CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }


}
