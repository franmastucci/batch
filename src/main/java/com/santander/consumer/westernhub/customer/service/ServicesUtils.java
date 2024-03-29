package com.santander.consumer.westernhub.customer.service;

import com.santander.consumer.westernhub.customer.model.Codification;
import com.santander.consumer.westernhub.customer.model.IvrCodification;
import com.santander.consumer.westernhub.customer.model.dto.CodificationLists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.santander.consumer.westernhub.customer.utils.CodificationDataConstants.*;

@Service
@Slf4j
public class ServicesUtils {

    @Value("${server.cvs-reader.base-path}")
    private String basePath;

    @Value("${server.cvs-reader.tipo-file}")
    private String tipoFileName;

    @Value("${server.cvs-reader.tarea-file}")
    private String tareaFileName;

    @Value("${server.cvs-reader.subtarea-file}")
    private String subtareaFileName;

    private Map<String, Integer> tipoMapping;

    private Map<String, Integer> tareaMapping;

    private Map<String, Integer> subtareaMapping;


    public void buildListsToInsert(BufferedReader reader, CodificationLists codificationLists) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(";");
            boolean recordExists = checkIfRecordExists(codificationLists.getIdGrabacionList(), data[6].trim());

            if (!recordExists) {
                codificationLists.getIdGrabacionList().add(data[6].trim());
                codificationLists.getIvrCodificationList().add(buildIvrCodification(data));
            }

            var tipo = data[14].trim();
            var tarea = data[14].trim().concat(".").concat(data[15].trim());
            var subtarea = data[15].trim().concat(".").concat(data[16].trim());

            codificationLists.getCodificationList().add(buildCodification(data, tipoMapping.getOrDefault(tipo, -1),
                    tareaMapping.getOrDefault(tarea, -1), subtareaMapping.getOrDefault(subtarea, -1)));
        }

    }

    private Map<String, Integer> loadMapping(String fileName) {
        var mapping = new HashMap<String,Integer>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String value = parts[1].trim();
                    var code = Integer.parseInt(parts[0].trim());
                    mapping.put(value, code);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return mapping;
    }

    public void loadMapping() {
        tipoMapping = loadMapping(this.getFullPath(tipoFileName));
        tareaMapping = loadMapping(this.getFullPath(tareaFileName));
        subtareaMapping = loadMapping(this.getFullPath(subtareaFileName));
    }

    public  String getFullPath(String relativePath) {
        Path path = FileSystems.getDefault().getPath(basePath, relativePath);
        return path.toAbsolutePath().toString();
    }

    private boolean checkIfRecordExists(List<String> idGrabacionList, String idGrabacion) {
        return idGrabacionList.contains(idGrabacion);
    }

    private Timestamp getTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String formattedTimestamp = now.format(formatter);
        return Timestamp.valueOf(formattedTimestamp);
    }

    private Timestamp combineDateTime(String dateString, String timeString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN);

            Date parsedDate = dateFormat.parse(dateString);
            Date parsedTime = timeFormat.parse(timeString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);

            Calendar timeCalendar = Calendar.getInstance();
            timeCalendar.setTime(parsedTime);

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

    public Codification buildCodification(String[] data, int tipoCode, int tareaCode, int subtareaCode) {
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

    public CodificationLists codificationListsBuilder() {
        return CodificationLists.builder()
                .idGrabacionList(new ArrayList<>())
                .codificationList(new ArrayList<>())
                .ivrCodificationList(new ArrayList<>())
                .build();
    }

}
