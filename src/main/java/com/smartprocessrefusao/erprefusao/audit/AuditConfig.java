package com.smartprocessrefusao.erprefusao.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditConfig {

	@Bean
	AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}
}
