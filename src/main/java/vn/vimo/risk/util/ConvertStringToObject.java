package vn.vimo.risk.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.vimo.risk.entity.Transaction;


public class ConvertStringToObject {
    public static Transaction convertStringToObject(String source) throws JsonProcessingException {
        ObjectMapper objectMapper   = new ObjectMapper();
        Transaction data = objectMapper.readValue(source, Transaction.class);
        return data ;
    }
}
