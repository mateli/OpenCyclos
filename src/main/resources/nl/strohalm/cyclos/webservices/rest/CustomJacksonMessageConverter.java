/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.webservices.rest;

import java.io.EOFException;
import java.io.IOException;

import com.fasterxml.jackson.JsonParseException;
import com.fasterxml.jackson.JsonParser;
import com.fasterxml.jackson.JsonToken;
import com.fasterxml.jackson.map.DeserializationConfig;
import com.fasterxml.jackson.map.JsonMappingException;
import com.fasterxml.jackson.map.ObjectMapper;
import com.fasterxml.jackson.map.SerializationConfig;
import com.fasterxml.jackson.map.annotation.JsonSerialize.Inclusion;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

/**
 * Custom Jackson converter used to customize the {@link ObjectMapper}
 * @author luis
 */
public class CustomJacksonMessageConverter extends MappingJacksonHttpMessageConverter {

    /**
     * Custom object mapper which handles empty input as null objects
     * @author luis
     */
    private static class CustomObjectMapper extends ObjectMapper {
        @Override
        protected JsonToken _initForReading(final JsonParser jp) throws IOException, JsonParseException, JsonMappingException {
            try {
                return super._initForReading(jp);
            } catch (EOFException e) {
                return JsonToken.VALUE_NULL;
            }
        }
    }

    public CustomJacksonMessageConverter() {
        ObjectMapper objectMapper = new CustomObjectMapper()
                .enable(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .enable(DeserializationConfig.Feature.USE_BIG_DECIMAL_FOR_FLOATS)
                .disable(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
        objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
        setObjectMapper(objectMapper);
    }

}
