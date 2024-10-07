package com.app.custom_exception;

public class ResourceNotFoundException extends RuntimeException {  //cretaing an unchecked exception so that compiler doesnt force handling of it

	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
	
	
}
