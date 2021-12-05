package com.example.core.config;

import com.example.core.mdc.UniqueIdFilter;
import com.example.core.stublogging.StubLogginFilter;
import lombok.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.example.SpringBootMDCDemo.core.mdc.UniqueIdFilter;

import org.springframework.core.annotation.Order;

@Configuration
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationFilter {

	public static final String DEFAULT_HEADER_TOKEN = "correlationId";
	public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "correlationId";

	private String responseHeader = DEFAULT_HEADER_TOKEN;
	private String mdcKey = DEFAULT_MDC_UUID_TOKEN_KEY;
	private String requestHeader = DEFAULT_HEADER_TOKEN;

	@Order(2)
	@Bean
	public FilterRegistrationBean<UniqueIdFilter> servletRegistrationBean() {
		final FilterRegistrationBean<UniqueIdFilter> registrationBean = new FilterRegistrationBean<>();
		final UniqueIdFilter log4jMDCFilterFilter = new UniqueIdFilter(responseHeader, mdcKey, requestHeader);
		registrationBean.setFilter(log4jMDCFilterFilter);
//		registrationBean.setOrder(2);
		return registrationBean;
	}

	@Order(1)
	@Bean
    public FilterRegistrationBean<StubLogginFilter> stubLogginFilter(){
        FilterRegistrationBean<StubLogginFilter> filterRegistrationBean=new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new StubLogginFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}
