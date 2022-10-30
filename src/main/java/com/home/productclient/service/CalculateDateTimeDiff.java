package com.home.productclient.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class CalculateDateTimeDiff {

    public static  Map<String,String> getZoneMap(){

        Map<String,String> zoneMap=new HashMap<>();
        /*zoneMap.put("E","America/New_York");
        zoneMap.put("P","America/Los_Angeles");
        zoneMap.put("C","America/Chicago");*/
        zoneMap.put("P","PST8PDT");
        zoneMap.put("E","EST5EDT");
        return zoneMap;
    }


    public static  void  main(String a[]){

        CalculateDateTimeDiff calculateDateTimeDiff=new CalculateDateTimeDiff();
        String departureDate="2022-09-15T13:31:51";
        boolean isWithinSpecifiedTime=calculateDateTimeDiff.isWithInSpecifiedTime("was",departureDate);

       System.out.println(isWithinSpecifiedTime);

    }

    private boolean isWithInSpecifiedTime(String city,String depatureDate) {
        long hours=getCurrentDateTimeOnStation(city,depatureDate);
        if(hours<=24){
            return true;
        }
        return false;
    }

    private long getCurrentDateTimeOnStation(String city,String departureDate) {

        String zoneShortCut=getZoneShortCutByCity(city);
        String zoneCity=getZoneMap().get(zoneShortCut);

        LocalDateTime currentDateTime=LocalDateTime.now(TimeZone.getTimeZone("EST5EDT").toZoneId());
        System.out.println("current date time "+currentDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(TimeZone.getTimeZone("EST5EDT").toZoneId());
        LocalDateTime dateTime = LocalDateTime.parse(departureDate, formatter);
        System.out.println("departuredateTime "+dateTime);
        long hours = currentDateTime.until(dateTime, ChronoUnit.HOURS);
        System.out.println(hours);

        LocalDateTime utcDate =
                Instant.ofEpochMilli(1667715600000l).atZone(TimeZone.getTimeZone("EST5EDT").toZoneId()).toLocalDateTime();
        System.out.println("mill to date: "+ utcDate);

        LocalDateTime edtDate =
                Instant.ofEpochMilli(1667715600000l).atZone(TimeZone.getTimeZone("EST").toZoneId()).toLocalDateTime();
        System.out.println("mill to EDT date: "+ edtDate);

        return hours;



    }

    private String getZoneShortCutByCity(String city) {
        return "E";
    }


    public String getCurrentDateTimeByCityAddHours(String city,int noOfHours,String addType){
        String zoneShortCut=getZoneShortCutByCity(city);
        String zoneCity=getZoneMap().get(zoneShortCut);

        LocalDateTime currentDateTime=LocalDateTime.now(TimeZone.getTimeZone("EST5EDT").toZoneId());
        System.out.println("current date time "+currentDateTime);

        LocalDateTime updatedDateTime=currentDateTime;
        switch (addType){

            case "hours" :
                updatedDateTime= currentDateTime.plusHours(noOfHours);
                break;
            case "minutes" :
                 updatedDateTime=currentDateTime.plusMinutes(noOfHours);
                 break;

            default: updatedDateTime=currentDateTime;


        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(TimeZone.getTimeZone("EST5EDT").toZoneId());

        return formatter.format(updatedDateTime);
    }

    public String getCurrentDateTimeByCityAddMinutes(String city,int noOfMiutes){
        String zoneShortCut=getZoneShortCutByCity(city);
        String zoneCity=getZoneMap().get(zoneShortCut);

        LocalDateTime currentDateTime=LocalDateTime.now(TimeZone.getTimeZone("EST5EDT").toZoneId());
        System.out.println("current date time "+currentDateTime);

        LocalDateTime updatedDateTime=currentDateTime.plusMinutes(noOfMiutes);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(TimeZone.getTimeZone("EST5EDT").toZoneId());



        return formatter.format(updatedDateTime);
    }


    public long differenceBetween2Dates(String from, String to){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssz").withZone(TimeZone.getTimeZone("EST5EDT").toZoneId());
        LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
        Duration duration= Duration.between(fromDateTime,toDateTime);
       return duration.toHours();
    }
}
