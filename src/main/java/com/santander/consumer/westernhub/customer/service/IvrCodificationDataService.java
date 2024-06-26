package com.santander.consumer.westernhub.customer.service;


import com.santander.consumer.westernhub.customer.model.Codification;
import com.santander.consumer.westernhub.customer.model.IvrCodification;

import java.util.List;

/**
 * Action interfaces for Request
 *
 */
public interface IvrCodificationDataService {

	/**
	 * Get a list of Request
	 * @return response message
	 */

	public String insertIvrCodificationData(List<IvrCodification> ivrCodifications);

}
