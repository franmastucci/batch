package com.santander.consumer.westernhub.customer.service;


import com.santander.consumer.westernhub.customer.model.Codification;

import java.util.List;

/**
 * Action interfaces for Request
 *
 */
public interface CodificationDataService {

	/**
	 * Get a list of Request
	 * @return response message
	 */

	public String insertCodificationData(List<Codification> codification);

}
