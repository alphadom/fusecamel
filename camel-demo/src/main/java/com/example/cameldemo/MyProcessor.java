package com.example.cameldemo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		System.out.println("Printing body : " + exchange.getIn().getBody(String.class));
		//exchange.getIn().setBody("edit file content");
	}

}
