package com.example.cameldemo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EmployeeProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception 
	{
		Employee emp = exchange.getIn().getBody(Employee.class);
		emp.setEmpName("new name");
		exchange.getIn().setBody(emp);
	}

}
