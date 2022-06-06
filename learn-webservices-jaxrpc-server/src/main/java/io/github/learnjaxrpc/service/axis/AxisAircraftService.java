package io.github.learnjaxrpc.service.axis;

import java.rmi.RemoteException;

import jakarta.servlet.ServletContext;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.github.learnjaxrpc.service.AircraftService;
import io.github.learnjaxrpc.ws.AircraftInterface;
import io.github.learnjaxrpc.ws.messages.AcknowledgeAircraftType;
import io.github.learnjaxrpc.ws.messages.GetAircraftType;
import io.github.learnjaxrpc.ws.messages.ShowAircraftType;
import io.github.learnjaxrpc.ws.messages.UpdateAircraftType;

/**
 * This is the class that is registered in server-config.wsdd
 * 
 * This class is designed to separate the RPC aspects of the SOAP API from your
 * spring based implementation. You could go even further by mapping schema
 * objects to your business domain objects, but we'll keep things simple for now
 * and just the schema class in our implementation.
 * 
 * This class is meant to demonstrate binding Axis created classes with those
 * created by Spring.
 */
public class AxisAircraftService implements ServiceLifecycle, AircraftInterface {

	protected ServletEndpointContext servletEndpointContext;

	protected WebApplicationContext webApplicationContext;

	protected AircraftService aircraftService;

	@Override
	public void init(Object context) throws ServiceException {
		if (!(context instanceof ServletEndpointContext)) {
			throw new ServiceException("ServletEndpointSupport needs ServletEndpointContext, not [" + context + "]");
		}
		this.servletEndpointContext = (ServletEndpointContext) context;
		ServletContext servletContext = this.servletEndpointContext.getServletContext();
		this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.aircraftService = webApplicationContext.getBean(AircraftService.class);
	}

	@Override
	public void destroy() {
	}

	@Override
	public String ping(String pingRequest) throws RemoteException {
		return pingRequest;
	}

	@Override
	public ShowAircraftType getAircraft(GetAircraftType getAircraftType) throws RemoteException {
		return aircraftService.getAircraft(getAircraftType);
	}

	@Override
	public AcknowledgeAircraftType updateAircraft(UpdateAircraftType updateAircraftType) throws RemoteException {
		return aircraftService.updateAircraft(updateAircraftType);
	}
}
