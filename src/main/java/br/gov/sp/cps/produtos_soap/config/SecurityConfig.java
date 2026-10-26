package br.gov.sp.cps.produtos_soap.config;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;

@Configuration
public class SecurityConfig implements WsConfigurer {
    private final String username;
    private final String password;

    public SecurityConfig(@Value("${soap.security.username}") String username,
                          @Value("${soap.security.password}") String password) {
        if (username.isBlank() || password.isBlank()) {
            throw new IllegalArgumentException("Configure SOAP_SECURITY_USERNAME e SOAP_SECURITY_PASSWORD");
        }
        this.username = username;
        this.password = password;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        var callback = new SimplePasswordValidationCallbackHandler();
        callback.setUsersMap(Map.of(username, password));
        var interceptor = new Wss4jSecurityInterceptor();
        interceptor.setValidationActions("UsernameToken");
        interceptor.setValidationCallbackHandler(callback);
        return interceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }
}
