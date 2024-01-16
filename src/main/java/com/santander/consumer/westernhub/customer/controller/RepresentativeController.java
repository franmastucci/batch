package com.santander.consumer.westernhub.customer.controller;

import com.santander.ademma.common.model.dto.ResponseDTO;
import com.santander.ademma.common.model.dto.context.ExecutionContext;
import com.santander.ademma.common.utils.logging.LoggingUtils;
import com.santander.consumer.westernhub.customer.model.dto.RepresentativeInDTO;
import com.santander.consumer.westernhub.customer.model.dto.out.ListRepresentativeOut;
import com.santander.consumer.westernhub.customer.model.dto.out.error.ErrorListMessageDTO;
import com.santander.consumer.westernhub.customer.service.clients.RepresentativeService;
import com.santander.consumer.westernhub.customer.service.utils.ServicesUtils;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.santander.consumer.westernhub.customer.utils.RepresentativesConstants.*;


/**
 * The Class RepresentativeController.
 */

@Slf4j
@Tag(name = "representatives", description = "List representatives to Genesys")
@RateLimiter(name = "representatives")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping(REQUEST_MAPPING_PATH_REPRESENTATIVES)
public class RepresentativeController {

    @Autowired
    private RepresentativeService representativeService;


    /**
     * Gets the Representatives .
     *
     * @param request         the request
     * @param context         the context
     * @param language        the language
     * @param xB3TraceId      the x B 3 trace id
     * @param xB3ParentSpanId the x B 3 parent span id
     * @param xB3SpanId       the x B 3 span id
     * @param xB3Sampled      the x B 3 sampled
     * @param clientId        the client id
     * @param society        the society
     * @param office        the office
     * @param area            the area
     * @param operationId the operationid
     * @param documentType the documentType
     * @param documentId the documentId
     * @return the Representatives
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the illegal argument exception
     */

    @GetMapping
    @Operation(summary = "List representatives")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HTTP_CODE_OK, description = "Class found",
                    content = @Content(schema = @Schema(implementation = ListRepresentativeOut.class))),
            @ApiResponse(responseCode = HTTP_CODE_NO_CONTENT, description = "No content"),
            @ApiResponse(responseCode = HTTP_CODE_BAD_REQUEST, description = "Bad Request. Class is not correct.",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_UNAUTHORIZED, description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_FORBIDDEN, description = "Authorization is not correct.",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_NOT_FOUND, description = "Class not found",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_UNPROCESSABLE_ENTITY, description = "Unable to convert Class to database entity or vice versa",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_TOO_MANY_REQUESTS, description = "Too many requests",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_INTERNAL_SERVER_ERROR, description = "An exception occurred",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class))),
            @ApiResponse(responseCode = HTTP_CODE_SERVICE_UNAVAILABLE, description = "Database Unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorListMessageDTO.class)))
    })
    public ResponseEntity<ListRepresentativeOut> getListRepresentatives(
            @Parameter(hidden = true) WebRequest request,
            @Parameter(description = "Context", required = false)
            @RequestHeader(value = "Context", required = false) String context,
            @Parameter(description = "Language", required = false)
            @RequestHeader(value = "Language", defaultValue = "en") String language,
            @Parameter(description = "X-B3-ParentSpanId", required = false)
            @RequestHeader(value = "X-B3-ParentSpanId", required = false) String xB3ParentSpanId,
            @Parameter(description = "X-B3-Sampled", required = false)
            @RequestHeader(value = "X-B3-Sampled", required = false) String xB3Sampled,
            @Parameter(description = "X-B3-SpanId", required = false)
            @RequestHeader(value = "X-B3-SpanId", required = false) String xB3SpanId,
            @Parameter(description = "X-B3-TraceId", required = false)
            @RequestHeader(value = "X-B3-TraceId", required = false) String xB3TraceId,
            @Parameter(description = "X-Santander-Client-Id", required = false)
            @RequestHeader(value = "X-Santander-Client-Id", required = false) String clientId,
            @Parameter(description = "x-santander-area", required = true)
            @RequestHeader(value = "x-santander-area", required = true) String area,
            @Parameter(description = "x-santander-office", required = true)
            @RequestHeader(value = "x-santander-office", required = true) String office,
            @Parameter(description = "x-santander-society", required = true)
            @RequestHeader(value = "x-santander-society", required = true) String society,
            @Parameter(description = "Operation ID", required = true)
            @RequestParam String operationId,
            @Parameter(description = "Document Type", required = true)
            @RequestParam String documentType,
            @Parameter(description = "Document ID", required = true)
            @RequestHeader String documentId) throws URISyntaxException, IOException {

        // Build execution context
        var executionContext = ExecutionContext.buildExecutionContext(request);

        LoggingUtils.logControllerInit(log, GET_REPRESENTATIVES_INFO, language);

        RepresentativeInDTO representativeInDTO = new RepresentativeInDTO(ServicesUtils.normalizeSpace(society),
                ServicesUtils.normalizeSpace(office), ServicesUtils.normalizeSpace(area),
                ServicesUtils.normalizeSpace(documentId), ServicesUtils.normalizeSpace(documentType),
                ServicesUtils.normalizeSpace(operationId));

        ListRepresentativeOut representativeOut = representativeService.getRepresentative(executionContext, representativeInDTO);

        if (ObjectUtils.isEmpty(representativeOut)) {
            LoggingUtils.logControllerResponse(log, request, ResponseDTO.buildResponseDTO(HttpStatus.NO_CONTENT, null));
            return ResponseEntity.noContent().build();
        }

        if (!ObjectUtils.isEmpty(representativeOut.getErrors())) {
            var representativeErrorsOut = new ListRepresentativeOut();
            representativeErrorsOut.setErrors(representativeOut.getErrors());
            LoggingUtils.logControllerResponse(log, request, ResponseDTO.buildResponseDTO(HttpStatus.BAD_REQUEST, representativeErrorsOut));
            return ResponseEntity.badRequest().body(representativeErrorsOut);
        }

        LoggingUtils.logControllerResponse(log, request, ResponseDTO.buildResponseDTO(HttpStatus.OK, null));
        return ResponseEntity.ok(representativeOut);
    }

}
