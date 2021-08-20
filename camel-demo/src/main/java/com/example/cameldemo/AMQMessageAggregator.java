package com.example.cameldemo;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class AMQMessageAggregator implements AggregationStrategy 
{

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) 
    {
        if (oldExchange == null) 
        {
            return newExchange;
        }
        
        String message = oldExchange.getIn().getBody(String.class);
        String result = newExchange.getIn().getBody(String.class);
        oldExchange.getIn().setBody(result + message);
        
        return oldExchange;
    }
}