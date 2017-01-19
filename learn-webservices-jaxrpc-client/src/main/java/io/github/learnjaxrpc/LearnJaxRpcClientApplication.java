package io.github.learnjaxrpc;

import java.rmi.RemoteException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.learnjaxrpc.ws.AircraftInterface;

@SpringBootApplication
public class LearnJaxRpcClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LearnJaxRpcClientApplication.class, args);
		invokeService(ctx.getBean(AircraftInterface.class));
	}

	/**
	 * Just a silly example to ping to the service
	 * 
	 * @param aircraftInterface
	 */
	protected static void invokeService(AircraftInterface aircraftInterface) {
		try {
			String response = aircraftInterface.ping("Ping Message");
			System.out.println("=====================");
			System.out.println(response);
			System.out.println("=====================");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
