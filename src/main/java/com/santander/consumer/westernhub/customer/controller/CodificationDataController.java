package com.santander.consumer.westernhub.customer.controller;

import com.santander.ademma.common.model.dto.error.ListRestError;
import com.santander.ademma.common.utils.CommonConstants;
import com.santander.consumer.westernhub.customer.service.DataProcessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Codification Data", description = "Calls to retriever and inserter codification sewrvices")
@Slf4j
public class CodificationDataController {

	@Autowired
	private DataProcessorService dataProcessorService;


	/**
	 * Initiates the Codification Data retriever and persisting process.
     *
     * @param contentType contentType
     * @return ResponseEntity
     */
	@Operation(summary = "Posts Codification Data")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_OK, description = "Request found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_NOT_FOUND, description = "Request not found",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" +CommonConstants.HTTP_CODE_BAD_REQUEST, description = "Bad Request. Request is not correct.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_UNAUTHORIZED, description = "Unauthorized.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_FORBIDDEN, description = "Authorization is not correct.",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_UNPROCESSABLE_ENTITY, description = "Unable to convert Request to database entity or viceversa",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_TOO_MANY_REQUESTS, description = "Too many requests",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_INTERNAL_SERVER_ERROR, description = "An exception occurred",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class))),
			@ApiResponse(responseCode = "" + CommonConstants.HTTP_CODE_SERVICE_UNAVAILABLE, description = "Database Unavailable",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListRestError.class)))	})
	@PostMapping("/codification")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> insertCodificationData(
			//@Parameter(hidden = true) WebRequest request,
			@Parameter(hidden = true)
			@RequestBody String input,
			//@RequestHeader(value = CommonConstants.HEADER_AUTHORIZATION) String authorization,
			@Parameter(description = "Content-Type", required = false)
			@RequestHeader(value = "Content-Type", required = false) String contentType)
			/*@Parameter(description = "Client ID header", required = true)
			@RequestHeader(value = "X-Santander-Client-Id") String clientId,
			@Parameter(description = "The server responds", required = false)
			@RequestHeader(value = "Accept", required = false) String accept,
			@Parameter(description = "Context", required = false)
			@RequestHeader(value = "Context", required = false) String context,
			@Parameter(description = "Overall ID of the trace, shared by every span in the trace.", required = false)
			@RequestHeader(value = "X-B3-TraceId", required = false) String xB3TraceId,
			@Parameter(description = "Position of the parent operation in the trace tree", required = false)
			@RequestHeader(value = "X-B3-ParentSpanId", required = false) String xB3ParentSpanId,
			@Parameter(description = "Position of the current operation in the trace tree", required = false)
			@RequestHeader(value = "X-B3-SpanId", required = false) String xB3SpanId,
			@Parameter(description = "Sampling decision", required = false)
			@RequestHeader(value = "X-B3-Sampled", required = false) String xB3Sampled,
			@Parameter(description = "Language", required = true)
			@RequestHeader(value = "language", defaultValue = "en") String language)

			 */
			
	{

		var response = dataProcessorService.retrieveCsvDataFromS3();

		if(!response.isEmpty()) dataProcessorService.insertCsvDataIntoDB(response);
		else {
			log.info("no data found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok().body("ok");

	}

}
