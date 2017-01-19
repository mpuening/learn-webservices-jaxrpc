package io.github.learnjaxrpc.config;

import java.net.MalformedURLException;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.axis.AxisProperties;
import org.apache.axis.client.Stub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.learnjaxrpc.ws.AircraftInterface;
import io.github.learnjaxrpc.ws.AircraftServiceLocator;

@Configuration
public class WebServiceClientConfig {

	/**
	 * The end point is defined by the context that the AxisServlet is running
	 * on and the Service Name as defined in the server-config.wsdd file
	 */
	@Value("${ws.endpoint:http://localhost:8080/ws/AircraftService}")
	protected String endpoint;

	@Value("${ws.username:username}")
	protected String username;

	@Value("${ws.password:password}")
	protected String password;

	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AircraftInterface aircraftInterface() throws MalformedURLException, ServiceException {
		// Ignore SSL problems for now
		AxisProperties.setProperty("axis.socketSecureFactory",
				"org.apache.axis.components.net.SunFakeTrustSocketFactory");

		AircraftServiceLocator service = new AircraftServiceLocator();
		HandlerRegistry handlerRegistry = service.getHandlerRegistry();

		QName portName = new QName("https://learnjaxrpc.github.io/learn/webservice/rpc", "AircraftInterfaceBinding");
		List handlerChain = handlerRegistry.getHandlerChain(portName);
		HandlerInfo handlerInfo = new HandlerInfo();
		handlerInfo.setHandlerClass(SOAPLogHandler.class);
		handlerChain.add(handlerInfo);

		AircraftInterface aircraftInterface = service.getAircraftInterfaceBinding(new java.net.URL(endpoint));

		// Basic HTTP Authentication, see application.yml for how the
		// credentials are set in this example
		((Stub) aircraftInterface).setUsername(username);
		((Stub) aircraftInterface).setPassword(password);

		return aircraftInterface;
	}
}