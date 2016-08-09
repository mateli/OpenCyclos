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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.EOFException;
import java.io.IOException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;



/**
 * Custom Jackson converter used to customize the {@link ObjectMapper}
 * @author luis
 */
public class CustomJacksonMessageConverter extends MappingJackson2HttpMessageConverter{

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
        ObjectMapper customObjectMapper = new CustomObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
        customObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        setObjectMapper(customObjectMapper);
    }

}
