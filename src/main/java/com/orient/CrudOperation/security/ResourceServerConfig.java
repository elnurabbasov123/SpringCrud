package com.orient.CrudOperation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    // UJE BURDA SERVERIN NASTROYKASI GEDIR  // ESAS CLAS

    @Autowired
    private ResourceServerTokenServices tokenServices;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources)throws Exception{
//BURDA ISE DEIRKI FILAN RESOURSE FILAN TOKEN VASITESI ILE IDARE OLUNSUN
        resources
                .resourceId("accountapi")
                .tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http)throws Exception{
        //BURDA ISE ENDPOINTLERI GEYD EDIRIK (DEIRIYKI BU RESOURCE BU ENDPOINTLERE MURACIYET EDE BILER)
        //DEIR SEN BU RESOURCE DAXIL OLDUN FILAN URL-LERE HUQUQUN VAR

        //AUTHORIZATION OZU (ENDPOINTLER) BURDA TEYIN OLUNUR
        http
                .requestMatchers()
                .and()
                .authorizeRequests()// MURACIYET ELIYIR AUTHORIZATION SERVER CONFIGE (AUTHORIZE REQUEST PROSESLERI ->)
                .antMatchers("/actuator/**","/api-docs/**").permitAll()  //FILAN URL-LERI HAMI GORE BILER
                .antMatchers("/**").authenticated(); //HANSI HAMI? *accountapi*-ye GELIB CATA BILEN HAMI
        //EN AZINDAN SENIN ADAMA clientId ve secretKeyi vi vere bilmen lazimdi (onnan sonra he die bilersenki sen filan filan
        //weyleri gore bilersen ;)
    }
}
