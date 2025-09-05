package com.smartprocessrefusao.erprefusao.formatBigDecimal;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class IntegerBrazilianSerializerWithoutDecimal extends JsonSerializer<Integer> {

    private static final DecimalFormat df;

    static {
        @SuppressWarnings("deprecation")
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        df = new DecimalFormat("#,##0", symbols);
    }

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(df.format(value));
        } else {
            gen.writeNull();
        }
    }
}

