package utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

public class LoggerHelper {

    // 🔷 Info → Console only
    public static void info(Logger log, String message) {
        log.info(message);
    }

    // 🔷 Error → Console + Allure
    public static void error(Logger log, String message) {
        log.error(message);
        attachToAllure("ERROR", message);
    }

    // 🔷 Assertion Logs → Console + Allure (IMPORTANT)
    public static void assertion(Logger log, String message) {
        log.info(message);
        attachToAllure("ASSERTION", message);
    }

    // 🔷 Debug → Console only
    public static void debug(Logger log, String message) {
        log.debug(message);
    }

    private static void attachToAllure(String title, String message) {
        Allure.addAttachment(
                title,
                "text/plain",
                message,
                StandardCharsets.UTF_8.name()
        );
    }
}