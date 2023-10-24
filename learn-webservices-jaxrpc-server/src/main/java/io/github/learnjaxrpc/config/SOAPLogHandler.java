package io.github.learnjaxrpc.config;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This the SOAP Request and Response logger. It is referenced in
 * in the src/main/resources/server-config.wsdd file
 */
public class SOAPLogHandler extends BasicHandler {

	private static final long serialVersionUID = -7087527636726506041L;
	private final Logger LOG = LoggerFactory.getLogger(SOAPLogHandler.class);

	@Override
	public void invoke(MessageContext messageContext) throws AxisFault {
		if (messageContext.getRequestMessage() != null
				&& messageContext.getRequestMessage().getSOAPPartAsString() != null
				&& messageContext.getResponseMessage() == null) {
			LOG.debug("\n===\nREQUEST:\n{}\n===", messageContext.getRequestMessage().getSOAPPartAsString());
		}
		if (messageContext.getResponseMessage() != null
				&& messageContext.getResponseMessage().getSOAPPartAsString() != null) {
			LOG.debug("\n===\nRESPONSE:\n{}\n===", messageContext.getResponseMessage().getSOAPPartAsString());
		}
	}
}
