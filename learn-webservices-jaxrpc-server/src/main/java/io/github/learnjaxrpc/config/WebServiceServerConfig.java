package io.github.learnjaxrpc.config;

import org.apache.axis.transport.http.AxisServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Register the Axis Servlet. The default settings will read the
 * server-config.wsdd file.
 */
@Configuration
public class WebServiceServerConfig {
	@Bean
	public ServletRegistrationBean axisServlet() {
		AxisServlet servlet = new AxisServlet();
		return new ServletRegistrationBean(servlet, "/ws/*");
	}
}