package com.santander.consumer.westernhub.customer.service;


import java.util.List;

/**
 * DataProcessorService interface
 *
 */

public interface DataProcessorService {

	/**
	 * Get a list of cvs file paths
	 * @return response message
	 */

	List<String> retrieveCsvFilesFromS3();

	/**
	 * Insert a list of cvs file data objects
	 */

	void insertDataIntoDB(List<String> csvPathList);

}
