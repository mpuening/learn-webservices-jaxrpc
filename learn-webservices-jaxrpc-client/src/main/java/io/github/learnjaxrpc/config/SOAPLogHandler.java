package io.github.learnjaxrpc.config;

import java.io.ByteArrayOutputStream;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SOAPLogHandler extends GenericHandler {

	private final Logger LOG = LoggerFactory.getLogger(SOAPLogHandler.class);

	protected HandlerInfo handlerInfo;

	public void init(HandlerInfo info) {
		handlerInfo = info;
	}

	public QName[] getHeaders() {
		return handlerInfo.getHeaders();
	}

	public boolean handleRequest(MessageContext context) {
		logMessage("REQUEST", context);
		return true;
	}

	public boolean handleResponse(MessageContext context) {
		logMessage("RESPONSE", context);
		return true;
	}

	private void logMessage(String prefix, MessageContext context) throws TransformerFactoryConfigurationError {
		if (LOG.isDebugEnabled()) {
			try {

				SOAPMessageContext soapMessageContext = (SOAPMessageContext) context;
				SOAPMessage message = soapMessageContext.getMessage();

				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer();
				Source source = message.getSOAPPart().getContent();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				StreamResult result = new StreamResult(baos);
				transformer.transform(source, result);
				LOG.debug("\n===\n{}:\n{}\n===", prefix, baos.toString());
			} catch (Exception e) {
				throw new JAXRPCException(e);
			}
		}
	}
}