package com.example.geomweatherapi.application;

import com.example.geomweatherapi.domain.vo.Region;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 중기예보 코드 조회 서비스
 */
@Service
public class DistrictCodeService {
    private final DistrictCodeRepository repository;
    private String WITHOUT_CTPRVN_REG_EXP = "특별시$|광역시$";
    private String WITHOUT_SIG_REG_EXP = "[시군구]*$";

    public DistrictCodeService(DistrictCodeRepository repository
    ) {
        this.repository = repository;
    }

    public String getLandCode(Region region) {
        if (isNotInKor(region.area0())) return null;

        String city = cityMeter(region.area1());
        String sig = sigMeter(region.area2());
        System.out.println(city + " " + sig);

        Optional<String> codeByCity = repository.findLandForecastAreaCodeByCity(city);
        if (codeByCity.isPresent()) {
            return codeByCity.get();
        }
        Optional<String> codeBySig = repository.findLandForecastAreaCodeByCity(sig);
        if (codeBySig.isPresent()) {
            return codeBySig.get();
        }
        throw new IllegalArgumentException("해당 지역을 육상예보코드에서 찾을 수 없습니다.");
    }

    public String getTemperatureForecastAreaCode(Region region) {
        if (isNotInKor(region.area0())) return null;

        String city = cityMeter(region.area1());
        String sig = sigMeter(region.area2());

        Optional<String> codeByCity = repository.findTemperatureForecastAreaCodeByCity(city);
        if (codeByCity.isPresent()) {
            return codeByCity.get();
        }
        Optional<String> codeBySig = repository.findTemperatureForecastAreaCodeByCity(sig);
        if (codeBySig.isPresent()) {
            return codeBySig.get();
        }
        throw new IllegalArgumentException("해당 지역을 기온예보코드에서 찾을 수 없습니다.");
    }

    private String cityMeter(String city) {
        return city.replaceAll(WITHOUT_CTPRVN_REG_EXP, "");
    }

    private String sigMeter(String sig) {
        return sig.replaceAll(WITHOUT_SIG_REG_EXP, "");
    }

    private boolean isNotInKor(String country) {
        return !(country.equals("kor") || country.equals("kr"));
    }
}
