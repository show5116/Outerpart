package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datecalculate {
   private Datecalculate() {
   }

   private static Datecalculate instance;

   public static Datecalculate getinstance() {
      if (instance == null) {
         instance = new Datecalculate();
      }
      return instance;
   }

   private SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");

   public int dateDifference(String startDate, String endDate) {

      Date secondDate = null;
      Date firstDate = null;

      try {
         firstDate = date.parse(startDate);
         secondDate = date.parse(endDate);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      long calDate = secondDate.getTime() - firstDate.getTime();
      long calDateDays = calDate / (24 * 60 * 60 * 1000);
      calDateDays = Math.abs(calDateDays);

      return (int) calDateDays+1;
   }

}