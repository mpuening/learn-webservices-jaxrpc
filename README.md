Learn RPC-Based SOAP Web Services (JAX-RPC)
===========================================

[![Continuous Integration](https://github.com/mpuening/learn-webservices-jaxrpc/actions/workflows/ci.yml/badge.svg)](https://github.com/mpuening/learn-webservices-jaxrpc/actions/workflows/ci.yml)

This project contains example code to demonstrate proficiency with RPC based, aka JAX-RPC, SOAP web services.

Obviously, building JAX-RPC web services today is definitely not something you want to do. However, it isn't
unusual for a large company to buy some vendor package that has been around for a while and has never been
updated to REST services. So you as a developer get stuck having to integrate with it by using its RPC web services.

But never fear. Axis 1.4 is still quite adept at handling the task even though the Spring Framework has dropped
support for RPC.

This project contain three modules:
* learn-webservices-jaxrpc-stubs
* learn-webservices-jaxrpc-server
* learn-webservices-jaxrpc-client

The subject matter of this example is military aircraft: simply keeping track of the types of aircraft and
their manufacturers.

Stubs Module
------------
You might ask yourself, how do I know if I have an RPC Based WebService? Probably most simply because you see
"RPC" in the wsdl file. Actually, just look at the binding style and use. They should say it is an RPC style
and the message body is encoded. If the WSDL file says "document" and "literal" then it is not RPC, it is JAX-WS
and you should see my other project for an example of that. 
 
The stubs module is responsible for running the WSDL to Java plug-in. When integrating with a vendor package it will
likely have some method for you to get the WSDL file. You will want to properly configure the plug-in mappings
so that you get decent looking code. Don't accept the default "namespace to package name" mapping. Chances are
it will give you a horrible looking package structure. Take the time to generate code in the proper packages. 

As a side note, many WSDL files from the early 2000s were based on OAGIS standards. You may have heard of 
noun and verb pairs. OAGIS is where this comes from. For more information on OAGIS, see this link:
http://www.oagi.org/oagis/9.0/Documentation/Architecture.html

My example WSDL file uses pairs, but not the actual OAGIS schemas. (Too big and bulky for an example).
There are many standards for building an API, and this project isn't about that. I'm not even going to
discuss versioning, even though you see versions my example XSD and WSDL files.

Server Module
-------------
Implementing a JAX-RPC based service is rather stupid today, but I wanted an example to test against, so I had
to implement one. It is interesting to see how well the Axis Servlet still plays well in a Spring Boot App.
The only trouble I had is that Axis doesn't quite understand Java 8 syntax, so just keep Java 8 syntax out of the
Axis Service class and you should be fine. This is why there is both an AxisAircraftService and an AircraftService
class.

The service also has security on it because you will likely face that in an integration.  It wouldn't surprise
me that login credentials actually go into the SOAP message as opposed to using basic authentication. My example
just uses Basic Authentication.

I also included a logging handler to print out the incoming and outgoing SOAP messages. You can use this for
debugging purposes.

Client Module
-------------
The client and stubs module is really what you need for integration. The client module shows how to configure a client
stub to invoke the service on an arbitrary URL with credentials versus what the you see in the WSDL file.

Legacy Jakarta Support
----------------------
With the release of Jakarta EE 9, Axis, as is, no longer works on the modern app servers. However, the
Eclipse Transformer can breathe new life into old frameworks. The `learn-webservices-jaxrpc-legacy-axis`
and `learn-webservices-jaxrpc-legacy-xml-rpc-api` projects produce transformed JAR files that work against
the new Jakarta namespace.

Building and Running the Application
------------------------------------
Simply run "mvn clean package" (or install) to build the applications. There is a Spring Boot App for both the
server and client.

To run the server, run the following command from the top level directory of the project:

> java -jar learn-webservices-jaxrpc-server/target/learn-webservices-jaxrpc-server-0.0.2-SNAPSHOT.war

From another command line, you can run the client with 

> java -jar learn-webservices-jaxrpc-client/target/learn-webservices-jaxrpc-client-0.0.2-SNAPSHOT.jar

You should see the following output:
> =====================
>
> Ping Message
>
> =====================


The server output should also display the REQUEST and RESPONSE messages. Certainly not exciting, so there are more interesting test cases in the server module.

Future Work
-----------
I want to add an example of an attachment.

Attribution
-----------
The `learn-webservices-jaxrpc-server` project contains a patch to the following code:

http://svn.apache.org/viewvc/axis/axis1/java/tags/1.4/src/org/apache/axis/utils/bytecode/ParamNameExtractor.java?revision=1225645&view=markup

This is done to work around a Java 11 compatibility issue.
