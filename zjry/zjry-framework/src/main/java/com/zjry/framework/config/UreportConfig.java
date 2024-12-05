package com.zjry.framework.config;

import javax.servlet.Servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.bstek.ureport.console.UReportServlet;

/**
 * @Author: mingc
 * @DateTime: 2021/7/1
 */
@ImportResource("classpath:ureport-console-context.xml")
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.zjry.framework")
public class UreportConfig {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean // 定义ureport的启动servlet
	public ServletRegistrationBean<Servlet> buildUreportServlet() {
		return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");// /ureport/* 不可更改
	}

}
