package io.github.learnjaxrpc;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.learnjaxrpc.ws.AircraftInterface;

@SpringBootTest
public class LearnJaxRpcClientApplicationTests {

	@Autowired
	private AircraftInterface aircraftInterface;

	@Test
	public void contextLoads() {
		assertNotNull(aircraftInterface);
	}
}
