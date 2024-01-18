package com.example.geomweatherapi;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.util.Iterator;

@SpringBootApplication
public class GeomWeatherApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(GeomWeatherApiApplication.class, args);
    }
}
