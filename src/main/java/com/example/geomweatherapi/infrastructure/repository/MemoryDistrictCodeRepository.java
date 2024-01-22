package com.example.geomweatherapi.infrastructure.repository;

import com.example.geomweatherapi.application.DistrictCodeRepository;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.OrderBy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Order(-1)
@Repository
public class MemoryDistrictCodeRepository implements DistrictCodeRepository {
    private Map<String, String> temperCode = new ConcurrentHashMap<>();

    private Map<String, String> landCode = new ConcurrentHashMap<>();

    @PostConstruct
    void setup() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("mid_district_code.xlsx");
        ZipSecureFile.setMinInflateRatio(0);

        String filePath = classPathResource.getURI().getPath();
        XSSFWorkbook sheets = new XSSFWorkbook(filePath);

        XSSFSheet sheetAt = sheets.getSheetAt(0);

        for (Row cells : sheetAt) {
            Cell addressName = cells.getCell(0);
            Cell code = cells.getCell(1);
            temperCode.put(addressName.getStringCellValue(), code.getStringCellValue());
        }

        // 육상코드 read start
        ClassPathResource ldcRrc = new ClassPathResource("landDistrictCode.csv");
        File ldcFile = ldcRrc.getFile();
        BufferedReader br;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(ldcFile, StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                String code = split[0].trim();
                String name = split[1].trim();
                System.out.println(name + ":" + code);
                landCode.put(name, code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public Optional<String> findLandForecastAreaCodeByCity(String city) {
        return Optional.ofNullable(landCode.get(city));
    }

    @Override
    public Optional<String> findTemperatureForecastAreaCodeByCity(String city) {
        return Optional.ofNullable(temperCode.get(city));
    }
}
