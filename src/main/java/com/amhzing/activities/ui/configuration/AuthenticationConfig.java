package com.amhzing.activities.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Import({AuthenticationConfigOnline.class, AuthenticationConfigOffline.class})
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

}
