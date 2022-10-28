package com.orient.CrudOperation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {
    // AUTHORIZATION PROSESI Yani KIM HARA MURACIYET EDIR

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer)throws Exception{
        //BURDA RESOURCE elan olunur(FILAN ID ,SECRETKEY,GRANTTYPE ADLI RESOURCE "READ" "WRITE" EDE BILER ADIDA *ACCOUNTAPI*

        //AWAQIDA DEILIRIKI SENIN BU RESOURCE HUQUQUN VARMI ? HUQUQUN VARSA EGER GEDIR ResourceServerConfige
        configurer
                .inMemory()
                .withClient("alma")  //filan Id
                .secret(passwordEncoder.encode("alma")) //filan Secret Key
                .authorizedGrantTypes("password")   //password
                .scopes("read","write") //wertidi oxu yaz da yaza bilerik (sadece bular ne etmek isdediyivin adlaridi (action))
                .resourceIds("accountapi"); // ID,SECRETKEY, GRANTTYPE FILAN OLAN  RESOURCEiD NI TEYIN ETDIM
        /* filan clientId filan secretKey ,filan grantType vasitesi ile 'accountapi-sine'
         "read" "write" muraciyetleri ede biler*/
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)throws Exception{
        //ENDPOINTLERIN AUTHORIZE OLUNMASI UCUN LAZIM OLAN OBYEKLERIN SET OLUNMASI
        TokenEnhancerChain enhancerChain=new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager);
    }
}
