package com.santander.consumer.westernhub.customer.service.impl;

import com.santander.consumer.westernhub.customer.model.Codification;
import com.santander.consumer.westernhub.customer.repository.CodificationRepository;
import com.santander.consumer.westernhub.customer.service.CodificationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Action implementations for Request
 *
 */
@Service
@Slf4j
public class CodificationDataServiceImpl implements CodificationDataService {

	@Autowired
	private CodificationRepository codificationRepository;


	@Override
	public String insertCodificationData(List<Codification> codifications) {

		var response = codificationRepository.saveAll(codifications);

		if (response.isEmpty()) return "no data";
		else return  "data inserted ok";
	}

}
