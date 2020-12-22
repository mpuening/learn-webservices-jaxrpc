package io.github.learnjaxrpc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import io.github.learnjaxrpc.ws.AircraftInterface;
import io.github.learnjaxrpc.ws.AircraftServiceLocator;
import io.github.learnjaxrpc.ws.messages.AcknowledgeAircraftType;
import io.github.learnjaxrpc.ws.messages.GetAircraftType;
import io.github.learnjaxrpc.ws.messages.ShowAircraftType;
import io.github.learnjaxrpc.ws.messages.UpdateAircraftType;
import io.github.learnjaxrpc.ws.schemas.AircraftType;
import io.github.learnjaxrpc.ws.schemas.ManufacturerType;

@ActiveProfiles("unittest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LearnJaxRpcServerApplicationTests {

	@LocalServerPort
	private int testServerPort;

	private AircraftInterface aircraftInterface;

	@BeforeEach
	public void setup() throws MalformedURLException, ServiceException {
		String endpoint = String.format("http://localhost:%d/ws/AircraftService", testServerPort);
		this.aircraftInterface = new AircraftServiceLocator().getAircraftInterfaceBinding(new java.net.URL(endpoint));

		// Basic HTTP Authentication, see SecurityConfiguration file for credentials
		((Stub) aircraftInterface).setUsername("service");
		((Stub) aircraftInterface).setPassword("secretpassword");
	}

	@Test
	public void testPing() throws MalformedURLException, ServiceException, RemoteException {

		String response = aircraftInterface.ping("message");
		assertNotNull(response);
		assertEquals("message", response);
	}

	@Test
	public void testGetAircraftService() throws MalformedURLException, ServiceException, RemoteException {

		GetAircraftType request = new GetAircraftType("", "Fighting Falcon");
		ShowAircraftType response = aircraftInterface.getAircraft(request);
		assertNotNull(response.getAircrafts());
		assertEquals(1, response.getAircrafts().length);
		assertEquals("F-16", response.getAircrafts(0).getDesignation());
	}

	@Test
	public void testUpdateAircraftService() throws MalformedURLException, ServiceException, RemoteException {

		ManufacturerType boeing = new ManufacturerType();
		boeing.setName("Boeing");
		boeing.setHeadquarters("Chicago, Illinois");
		Date founded = Date.from(LocalDate.of(1916, Month.JULY, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		boeing.setFounded(founded);

		AircraftType b52 = new AircraftType();
		b52.setDesignation("B-52");
		b52.setName("Stratofortress");
		b52.setNickname("BUFF");
		b52.setManufacturer(boeing);
		Date firstFlight = Date
				.from(LocalDate.of(1952, Month.APRIL, 15).atStartOfDay(ZoneId.systemDefault()).toInstant());
		b52.setFirstFlight(firstFlight);
		b52.setProduced(744);

		UpdateAircraftType request = new UpdateAircraftType(b52);
		AcknowledgeAircraftType response = aircraftInterface.updateAircraft(request);
		assertNotNull(response);
		assertEquals(200, response.getCode());
		assertEquals("SUCCESS", response.getStatus());
	}
}
