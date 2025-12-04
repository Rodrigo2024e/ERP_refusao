package com.smartprocessrefusao.erprefusao.services.exceptions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalFlexibleDeserializer extends JsonDeserializer<BigDecimal> {

	@Override
	public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		String value = p.getText();

		if (value == null) {
			return null;
		}

		value = value.trim();

		if (value.isEmpty()) {
			return null;
		}

		// Remove separador de milhar do Brasil ou EUA
		value = value.replace(".", "").replace(",", ".");

		return new BigDecimal(value);
	}
}
