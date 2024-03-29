package com.santander.consumer.westernhub.customer.service;


import com.santander.consumer.westernhub.customer.model.Codification;

import java.util.List;

/**
 * CodificationDataService interface
 *
 */
public interface CodificationDataService {

	/**
	 * Get a list of Request
	 * @return response message
	 */

	public String insertCodificationData(List<Codification> codification);

}
