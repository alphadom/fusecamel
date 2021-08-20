package com.example.cameldemo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.stereotype.Component;

@Component
public class ExampleRouter extends RouteBuilder 
{

	@Override
	public void configure() throws Exception 
	{
		//callFileLanguage();
		//callSimpleLang();
		//callFileAndAMQ();
		//callJAXB();
		//callFTPtoAMQ(); //single same file is sent to amq infinite times
		
		//aggregateAndAMQ(); //not working
	}

	private void callSimpleLang() 
	{
		from("file:C:/input")
		.setBody(simple("${body} added again"))
		.log("Content of file is ${body}")
		.to("log:out");
	}

	private void callFTPtoAMQ()
	{
		from("ftp://testuser@127.0.0.1/inFTP?password=testpass")
		.to("activemq:queue:destinationQQ");
	}

	private void callJAXB() throws JAXBException 
	{
		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
		JAXBContext con = JAXBContext.newInstance(Employee.class);
		xmlDataFormat.setContext(con);

		from("file:C:/input")
		.unmarshal(xmlDataFormat)
		.process(new EmployeeProcessor())
		.marshal(xmlDataFormat)
		.to("file:C:/output");
	}

	private void aggregateAndAMQ() 
	{
		from("activemq:queue:sourceQQ")
		.routeId("RouteID:1")
		.log("First Route!")
		.aggregate(body(), new AMQMessageAggregator())
		.completionTimeout(10000)
		.process(new MyProcessor())
		.to("mock:a");
	}

	private void callFileAndAMQ() 
	{
		from("file:C:/input")
		.to("activemq:queue:destinationQQ");
	}

	private void callFileLanguage() 
	{
		from("file:C:/input")
		.to("file:C:/output?fileName=${file:onlyname.noext}-${date:now:yyyyMMdd}.${file:ext}");
	}

}
