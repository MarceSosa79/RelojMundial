import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class RelojMundial {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("EEEE HH:mm", Locale.ENGLISH);

        while (true) {
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalTime localTime = localDateTime.toLocalTime();
            DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
            System.out.println("Hora local: " + localTime.format(timeFormatter));

            int hour = localTime.getHour();
            if (hour >= 6 && hour <= 11) {
                System.out.println("Saludo: Good morning!");
            } else if (hour >= 12 && hour <= 17) {
                System.out.println("Saludo: Good afternoon!");
            } else if (hour >= 18 && hour <= 21) {
                System.out.println("Saludo: Good evening!");
            } else {
                System.out.println("Saludo: Time to rest...");
            }

            String diaEnEspanol = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
            diaEnEspanol = diaEnEspanol.substring(0,1).toUpperCase() + diaEnEspanol.substring(1);
            System.out.println("Hoy es: " + diaEnEspanol);

            System.out.print("Elegí una ciudad (tokio, londres, nueva_york, ciudad_de_mexico): ");
            String ciudad = sc.nextLine().trim().toLowerCase();
            ZoneId zoneId;

            switch (ciudad) {
                case "tokio":
                    zoneId = ZoneId.of("Asia/Tokyo");
                    break;
                case "londres":
                    zoneId = ZoneId.of("Europe/London");
                    break;
                case "nueva_york":
                    zoneId = ZoneId.of("America/New_York");
                    break;
                case "ciudad_de_mexico":
                    zoneId = ZoneId.of("America/Mexico_City");
                    break;
                default:
                    System.out.println("Ciudad no válida.");
                    continue;
            }

            ZonedDateTime horaCiudad = ZonedDateTime.now(zoneId);
            ZonedDateTime horaLocal = ZonedDateTime.now();

            ZoneOffset offsetLocal = horaLocal.getOffset();
            ZoneOffset offsetCiudad = horaCiudad.getOffset();
            int diferenciaHoras = offsetCiudad.getTotalSeconds() / 3600 - offsetLocal.getTotalSeconds() / 3600;

            System.out.println("Hora en " + capitalizar(ciudad) + ": " + horaCiudad.format(fullFormatter));
            System.out.println("Diferencia horaria: " + (diferenciaHoras >= 0 ? "+" : "") + diferenciaHoras + " horas");

            System.out.print("¿Querés consultar otra ciudad? (si/no): ");
            String continuar = sc.nextLine().trim().toLowerCase();
            if (continuar.equals("no")) {
                System.out.println("¡Hasta luego!");
                break;
            }
        }

        sc.close();
    }

    private static String capitalizar(String ciudad) {
        ciudad = ciudad.replace('_', ' ');
        return ciudad.substring(0,1).toUpperCase() + ciudad.substring(1);
    }
}
