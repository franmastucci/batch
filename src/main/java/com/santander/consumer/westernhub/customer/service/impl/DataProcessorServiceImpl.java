package com.santander.consumer.westernhub.customer.service.impl;

import com.santander.consumer.westernhub.customer.service.CodificationDataService;
import com.santander.consumer.westernhub.customer.service.DataProcessorService;
import com.santander.consumer.westernhub.customer.service.IvrCodificationDataService;
import com.santander.consumer.westernhub.customer.service.ServicesUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * DataProcessorServiceImpl clqss
 *
 */
@Service
@Slf4j
public class DataProcessorServiceImpl implements DataProcessorService {

	@Autowired
	private CodificationDataService codificationDataService;

	@Autowired
	private IvrCodificationDataService ivrCodificationDataService;

	@Autowired
	private ServicesUtils servicesUtils;


	@Override
	public String retrieveCsvDataFromS3() {
		String csvFilePath = "historico_codificacion_pre3.csv";
		return servicesUtils.getFullPath(csvFilePath);
	}

	@Override
	public void insertCsvDataIntoDB(String csvFilePath) {

		var codificationLists = servicesUtils.codificationListsBuilder();
		servicesUtils.loadMapping();

		try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
			reader.readLine();
			servicesUtils.buildListsToInsert(reader, codificationLists);
			ivrCodificationDataService.insertIvrCodificationData(codificationLists.getIvrCodificationList());
			codificationDataService.insertCodificationData(codificationLists.getCodificationList());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
