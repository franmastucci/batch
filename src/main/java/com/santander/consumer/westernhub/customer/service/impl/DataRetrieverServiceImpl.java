package com.santander.consumer.westernhub.customer.service.impl;

import com.santander.consumer.westernhub.customer.model.Codification;
import com.santander.consumer.westernhub.customer.model.IvrCodification;
import com.santander.consumer.westernhub.customer.repository.CodificationRepository;
import com.santander.consumer.westernhub.customer.service.CodificationDataService;
import com.santander.consumer.westernhub.customer.service.DataRetrieverService;
import com.santander.consumer.westernhub.customer.service.IvrCodificationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.santander.consumer.westernhub.customer.utils.RepresentativesConstants.*;
import static com.santander.consumer.westernhub.customer.utils.RepresentativesConstants.TIME_PATTERN;


/**
 * Action implementations for Request
 *
 */
@Service
@Slf4j
public class DataRetrieverServiceImpl implements DataRetrieverService {

	@Autowired
	private CodificationDataService codificationDataService;

	@Autowired
	private IvrCodificationDataService ivrCodificationDataService;


	@Override
	public String retrieveCsvData() {
		String csvFilePath = "historico_codificacion_pre3.csv";
		String fullPath = getFullPath(csvFilePath);
		insertDataFromCsv(fullPath);

		return "null";
	}


	private void insertDataFromCsv(String csvFilePath) {

		Map<String, Integer> tipoMapping = loadMapping(getFullPath("tipo.txt"));
		Map<String, Integer> tareaMapping = loadMapping(getFullPath("subtipo.txt"));
		Map<String, Integer> subtareaMapping = loadMapping(getFullPath("razon.txt"));

		//String currentDir = System.getProperty(USER_DIR);

		try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {

			reader.readLine();
			String line;

			List<String> idGrabacionList = new ArrayList<>();
			var codificationList = new ArrayList<Codification>();
			var ivrCodificationList = new ArrayList<IvrCodification>();


			while ((line = reader.readLine()) != null) {

				String[] data = line.split(";");
				boolean recordExists = checkIfRecordExists(idGrabacionList, data[6].trim());

				if (!recordExists) {
					//solo si el registro no existe agrega un objeto a la lista de insertion
					idGrabacionList.add(data[6].trim());
					//construye el objeto ivr
					ivrCodificationList.add(buildIvrCodification(data)); //reemplaza el append

					//countIvrCodificacionRows++; //cuenra de para rows (habria que eliminarla)
				}

				// ### esta parte deberia quedar igual
				String tipo = data[14].trim();
				String tarea = data[14].trim().concat(".").concat(data[15].trim());
				String subtarea =data[15].trim().concat(".").concat(data[16].trim());

				int tipoCode = tipoMapping.getOrDefault(tipo, -1);
				int tareaCode = tareaMapping.getOrDefault(tarea, -1);
				int subtareaCode = subtareaMapping.getOrDefault(subtarea, -1);
				// ###

				//consturye el objeto codification
				codificationList.add(buildCodification(data, tipoCode, tareaCode, subtareaCode));

			}


			//llamar servicios de ivr codif y codif
			ivrCodificationDataService.insertIvrCodificationData(ivrCodificationList);

			codificationDataService.insertCodificationData(codificationList);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String buildIvrCodificacionValues(String[] data) {
		return String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
				data[6].trim(), data[4].trim(), data[5].trim(), NA, NA, NA, AUD_USR_CREATION, getTimestamp());
	}

	private static String buildCodificacionValues(String[] data, int tipoCode, int tareaCode, int subtareaCode) {
		return String.format("('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, %d, %d, '%s', %d, '%s', '%s', '%s')",
				data[0].trim(), data[2].trim(), data[3].trim(), data[6].trim(),
				combineDateTime(data[7].trim(), data[8].trim()), combineDateTime(data[7].trim(), data[8].trim()),
				combineDateTime(data[9].trim(), data[10].trim()), combineDateTime(data[9].trim(), data[10].trim()),
				data[13].trim(), tipoCode, tareaCode, subtareaCode, NA, 6, NA, AUD_USR_CREATION, getTimestamp());
	}

	public  Codification buildCodification(String[] data, int tipoCode, int tareaCode, int subtareaCode) {
		Codification codification = new Codification();
		codification.setIdGestion(data[0].trim());
		codification.setIdGrabacion(data[6].trim());

		codification.setFecEGestion(combineDateTime(data[7].trim(), data[8].trim()));
		codification.setHoraEGestion(combineDateTime(data[7].trim(), data[8].trim()));
		codification.setFecSGestion(combineDateTime(data[9].trim(), data[10].trim()));
		codification.setHoraSGestion(combineDateTime(data[9].trim(), data[10].trim()));

		codification.setOperacion(data[13].trim());
		codification.setDocumentoReal(NA);
		codification.setCodFlujo(6);
		codification.setTipo(tipoCode);
		codification.setSubtipo(tareaCode);
		codification.setRazon(subtareaCode);
		codification.setComentarios(NA);

		codification.setAgente(data[2].trim());
		codification.setLoginAcd(data[3].trim());
		codification.setAudUsrCreation(AUD_USR_CREATION);
		codification.setAudTimCreation(getTimestamp());

		return codification;
	}

	private IvrCodification buildIvrCodification(String[] data) {
		IvrCodification ivrCodification = new IvrCodification();
		ivrCodification.setIdGrabacion(data[6].trim());
		ivrCodification.setViaEntrada(NA);
		ivrCodification.setServicio(data[4].trim());
		ivrCodification.setVdn(data[5].trim());
		ivrCodification.setCodAfiliacion(NA);
		ivrCodification.setTipoLlamante(NA);
		ivrCodification.setAudUsrCreation(AUD_USR_CREATION);
		ivrCodification.setAudTimCreation(getTimestamp());
		return ivrCodification;
	}


	private static Timestamp getTimestamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
		String formattedTimestamp = now.format(formatter);
		return Timestamp.valueOf(formattedTimestamp);
	}

	private static String getFullPath(String relativePath) {
		Path path = FileSystems.getDefault().getPath("src/main/resources", relativePath);
		return path.toAbsolutePath().toString();
	}

	private static Timestamp combineDateTime(String dateString, String timeString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
			SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN);

			Date parsedDate = dateFormat.parse(dateString);
			Date parsedTime = timeFormat.parse(timeString);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(parsedDate);

			Calendar timeCalendar = Calendar.getInstance();
			timeCalendar.setTime(parsedTime);

			// Establecer la hora, minuto y segundo de la fecha
			calendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			calendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			calendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
			calendar.set(Calendar.MILLISECOND, 0);

			return new Timestamp(calendar.getTimeInMillis());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean checkIfRecordExists(List<String> idGrabacionList, String idGrabacion) {
		return idGrabacionList.contains(idGrabacion);
	}

	private static String getNewFilePath(String existingFilePath) {
		int fileCount = 2;
		String newFilePath = existingFilePath.replace("_1.sql", "_" + fileCount + ".sql");
		while (new File(newFilePath).exists()) {
			fileCount++;
			newFilePath = existingFilePath.replace("_1.sql", "_" + fileCount + ".sql");
		}
		return newFilePath;
	}

	private static Map<String, Integer> loadMapping(String fileName) {
		Map<String, Integer> mapping = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length == 2) {
					String value = parts[1].trim();
					int code = Integer.parseInt(parts[0].trim());
					mapping.put(value, code);
				}
			}
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
		return mapping;
	}


}
