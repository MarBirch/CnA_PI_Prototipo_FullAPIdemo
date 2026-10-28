package com.example.FullAPIdemo.config;
import com.example.FullAPIdemo.service.LeadService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MarmiteriaDashboardBootstrap {
 @Bean ApplicationRunner importarMarmiterias(LeadService leads){return args->leads.sincronizarMarmiterias();}
}
