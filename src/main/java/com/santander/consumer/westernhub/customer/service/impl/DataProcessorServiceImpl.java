package com.santander.consumer.westernhub.customer.service.impl;

import com.santander.consumer.westernhub.customer.service.CodificationDataService;
import com.santander.consumer.westernhub.customer.service.DataProcessorService;
import com.santander.consumer.westernhub.customer.service.IvrCodificationDataService;
import com.santander.consumer.westernhub.customer.service.ServicesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
	public List<String> retrieveCsvFilesFromS3() {
		String csvFilePath = "historico_codificacion_pre3.csv";
		var responseList = new ArrayList<String>();
		responseList.add(servicesUtils.getFullPath(csvFilePath));
		return responseList;
	}

	@Override
	public void insertDataIntoDB(List<String> csvFilePathList) {

		var codificationLists = servicesUtils.codificationListsBuilder();
		servicesUtils.loadMapping();

		csvFilePathList.forEach(f -> {
			try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
				reader.readLine();
				servicesUtils.buildListsToInsert(reader, codificationLists);
				ivrCodificationDataService.insertIvrCodificationData(codificationLists.getIvrCodificationList());
				codificationDataService.insertCodificationData(codificationLists.getCodificationList());

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

}
