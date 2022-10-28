package com.orient.CrudOperation.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
        //USERIN AUTHENTICAT ELEMEY UCUN OLAN OBYEKTI RETURN EDIR
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //MUSTERI EMAILINE ESASEN TAPILMIS OLAN MELUMATI ENCOD ELYIB
        //MUSTERININ HASHLENMIW ENCOD OLUNMUW MELUMATI ILE YOXLUYACAQ
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter=new JwtAccessTokenConverter();
        converter.setSigningKey(signingKey); //BU KEYE ESASSEN TOKEN GENERASIYA OLUNUR VE TOKEN VERIFY OLUNUR
        return converter;
        //CONVERTER TOKENI TESTIGLEMEK (VERIFY) , GENERASIYA , ENCOD ELEMEY UCUN ISTIFADE OLUNUR
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
        //TOKENI HARDA SAXLIYACAM (HAL HAZIRDA RAM-DA SAXLIYACAM VARIANTINA BAXIRIQ)
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices=new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        //TOKEN SERVICE TEQDIM EDIRSEN TOKE STORU(YANI TOKENI NECE SAXLIYACAQSAN)(BUNU SET ELEMEYNEN CONVERTERIDE SET ELIYIRSEN)
        defaultTokenServices.setSupportRefreshToken(true);
        //TOKENIN YAWAMA MUDDETI SOBETI(REGRESH TOKEN)

        return defaultTokenServices;
        //VE BU DEFAULT TOKEN SERVICE VASITE SI ILE ARTIG OAUTH2 SERVERI TOKENE AID OLAN
        //BUTUN PROSESLERI ICRA ELIECEK
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //NU ISE SECURITYNIN TETBIQ OLUNDUGU METHOTDU
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .realmName("myrealm")
                .and()
                .csrf()
                .disable();
    }
}
