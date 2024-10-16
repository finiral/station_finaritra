package mg.fini_station.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class Utilitaire {
    public static Timestamp parseToTimestamp(String datetimeLocal) {
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

    public static Date convertTimestampToDate(Timestamp timestamp) {
        // Conversion en java.sql.Date
        return new Date(timestamp.getTime());
    }

    public static Date htmlTimestampToSqlDate(String htmlTimestamp) {
        // Format du timestamp HTML (exemple : "2024-10-04T15:30:45")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        // Conversion du timestamp HTML en LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(htmlTimestamp, formatter);

        // Extraction de la partie date (sans l'heure)
        LocalDate localDate = localDateTime.toLocalDate();

        // Conversion en java.sql.Date
        return Date.valueOf(localDate);
    }

    public static double convertToCm(String value) throws Exception {
        // Vérifier si la chaîne est vide ou nulle
        if (value == null || value.isEmpty()) {
            throw new Exception("La valeur ne peut pas être nulle ou vide.");
        }
    
        // Suppression des espaces blancs avant ou après
        value = value.trim();
    
        // Les différents multiplicateurs pour chaque unité
        double multiplier = 1.0;
    
        // Vérification des unités et extraction de la partie numérique
        if (value.endsWith("mm")) {
            multiplier = 0.1; // 1 mm = 0.1 cm
            value = value.substring(0, value.length() - 2).trim();
        } else if (value.endsWith("cm")) {
            multiplier = 1.0; // 1 cm = 1 cm
            value = value.substring(0, value.length() - 2).trim();
        } else if (value.endsWith("m")) {
            multiplier = 100.0; // 1 m = 100 cm
            value = value.substring(0, value.length() - 1).trim();
        } else if (value.endsWith("km")) {
            multiplier = 100000.0; // 1 km = 100,000 cm
            value = value.substring(0, value.length() - 2).trim();
        } else {
            throw new Exception("Unité non reconnue, veuillez fournir une valeur valide avec une unité métrique ('mm', 'cm', 'm', 'km').");
        }
    
        // Conversion de la partie numérique en double et application du multiplicateur
        try {
            return Double.parseDouble(value) * multiplier;
        } catch (NumberFormatException e) {
            throw new Exception("Format numérique invalide.");
        }
    }
    public static Timestamp convertDateToTimestamp(Date date) {
        if (date == null) {
            return null; // Gestion des valeurs nulles
        }
        // Conversion de java.sql.Date en java.sql.Timestamp
        return new Timestamp(date.getTime());
    }
    
     public static Date addDays(Date date, int days) {
        // Utiliser un objet Calendar pour manipuler la date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date); // Initialiser la date du Calendar avec la date SQL
        cal.add(Calendar.DAY_OF_MONTH, days); // Ajouter les jours

        // Retourner une nouvelle java.sql.Date avec la date mise à jour
        return new Date(cal.getTimeInMillis());
    }

     public static String getLastDayOfMonth(String monthString) {
        // Crée un LocalDate au premier jour du mois fourni
        LocalDate firstDayOfMonth = LocalDate.parse(monthString + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Trouve le dernier jour du mois
        LocalDate lastDayOfMonth = firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
        
        // Formate la date en string au format yyyy-MM-dd
        return lastDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
