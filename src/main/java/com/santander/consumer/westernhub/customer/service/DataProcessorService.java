package com.santander.consumer.westernhub.customer.service;


/**
 * DataProcessorService interface
 *
 */

public interface DataProcessorService {

	/**
	 * Get a list of Request
	 * @return response message
	 */

		String retrieveCsvFilesFromS3();

		void insertDataIntoDB(String csvPath);

	}
