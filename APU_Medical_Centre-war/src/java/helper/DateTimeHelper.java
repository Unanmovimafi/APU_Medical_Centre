/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author zihao
 */
public class DateTimeHelper {
    
    public static Date getCurrentDateTime() {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Instant currentInstantLocalDateTime = currentLocalDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date currentDate = Date.from(currentInstantLocalDateTime);
        return currentDate;
    }

}
