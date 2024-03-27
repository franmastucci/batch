package com.santander.consumer.westernhub.customer.service;


import com.santander.consumer.westernhub.customer.model.IvrCodification;

import java.util.List;

/**
 * Action interfaces for Request
 *
 */

//este servicioo deberia fiuncionar para recuperar los ficheros de s3 , habria que
//crear otro para insertar
public interface DataRetrieverService {

	/**
	 * Get a list of Request
	 * @return response message
	 */

	public String retrieveCsvData();

}
