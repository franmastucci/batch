package com.santander.consumer.westernhub.customer.service.impl;

import com.santander.consumer.westernhub.customer.model.IvrCodification;
import com.santander.consumer.westernhub.customer.repository.IvrCodificationRepository;
import com.santander.consumer.westernhub.customer.service.IvrCodificationDataService;
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
public class IvrCodificationDataServiceImpl implements IvrCodificationDataService {

	@Autowired
	private IvrCodificationRepository ivrCodificationRepository;



	@Override
	public String insertIvrCodificationData(List<IvrCodification> ivrCodifications) {

		var response = ivrCodificationRepository.saveAll(ivrCodifications);

		if (response.isEmpty()) return "no data";
		else return  "data inserted ok";

	}

}
