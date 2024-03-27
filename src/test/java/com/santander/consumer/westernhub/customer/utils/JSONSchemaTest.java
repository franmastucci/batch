package com.santander.consumer.westernhub.customer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The Class SchemaTest.
 *
 */
@RunWith(JUnit4.class)
class JSONSchemaTest {

    /**
     * Test interveners json schema mock.
     *
     * @throws Exception     the exception
     */
    //@Test
    public void testIntervenersSchema() throws Exception {
        var mapper = new ObjectMapper();
        var factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        var jsonSchema = factory.getSchema(
                JSONSchemaTest.class.getResourceAsStream("/jsonSchemas/intervenersSchema.json"));
        var jsonNode = mapper.readTree(
                JSONSchemaTest.class.getResourceAsStream("/mocks/as400/AS400GetListIntervenersOut-Mockeado.json"));
        var errors = jsonSchema.validate(jsonNode);
        assertThat(errors).isEmpty();
    }

    /**
     * Test operations json schema mock.
     *
     * @throws Exception     the exception
     */
    //@Test
    public void testOperationsSchema() throws Exception {
        var mapper = new ObjectMapper();
        var factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        var jsonSchema = factory.getSchema(
                JSONSchemaTest.class.getResourceAsStream("/jsonSchemas/operationsSchema.json"));
        var jsonNode = mapper.readTree(
                JSONSchemaTest.class.getResourceAsStream("/mocks/as400/AS400ListOperationsOUTDTO-Mockeado.json"));
        var errors = jsonSchema.validate(jsonNode);
        assertThat(errors).isEmpty();
    }

    /**
     * Test KO interveners json schema mock.
     *
     * @throws Exception     the exception
     */
   // @Test
    public void testIntervenersSchema_KO() throws Exception {
        var mapper = new ObjectMapper();
        var factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        var jsonSchema = factory.getSchema(
                JSONSchemaTest.class.getResourceAsStream("/jsonSchemas/intervenersSchema.json"));
        var jsonNode = mapper.readTree(
                JSONSchemaTest.class.getResourceAsStream("/mocks/as400/ko/AS400GetListIntervenersOut-Mockeado-KO.json"));
        var errors = jsonSchema.validate(jsonNode);
        assertThat(errors).isNotEmpty();
    }

    /**
     * Test KO operations json schema mock.
     *
     * @throws Exception     the exception
     */
   // @Test
    public void testOperationsSchema_KO() throws Exception {
        var mapper = new ObjectMapper();
        var factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        var jsonSchema = factory.getSchema(
                JSONSchemaTest.class.getResourceAsStream("/jsonSchemas/operationsSchema.json"));
        var jsonNode = mapper.readTree(
                JSONSchemaTest.class.getResourceAsStream("/mocks/as400/ko/AS400ListOperationsOUTDTO-Mockeado-KO.json"));
        var errors = jsonSchema.validate(jsonNode);
        assertThat(errors).isNotEmpty();
    }
}
