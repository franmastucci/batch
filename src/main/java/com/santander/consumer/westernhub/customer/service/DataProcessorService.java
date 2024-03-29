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

		String retrieveCsvDataFromS3();

		void insertCsvDataIntoDB(String csvPath);

	}
