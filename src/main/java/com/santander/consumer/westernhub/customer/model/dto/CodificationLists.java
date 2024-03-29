package com.santander.consumer.westernhub.customer.model.dto;

import com.santander.consumer.westernhub.customer.model.Codification;
import com.santander.consumer.westernhub.customer.model.IvrCodification;
import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Component
public class CodificationLists {

    private List<String> idGrabacionList;

    private List<Codification> codificationList;

    private List<IvrCodification> ivrCodificationList;

}
