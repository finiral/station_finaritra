package mg.fini_station.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilitaire {
    public static Timestamp parseToTimestamp(String datetimeLocal){
        try {
            // Define the expected format from the datetime-local input (yyyy-MM-ddTHH:mm)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            
            // Parse the input string to LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(datetimeLocal, formatter);
            
            // Convert LocalDateTime to Timestamp
            return Timestamp.valueOf(localDateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
