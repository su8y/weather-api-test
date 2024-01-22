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
    private final String WITHOUT_CTPRVN_REG_EXP = "특별시$|광역시$";
    private final String WITHOUT_SIG_REG_EXP = "[시군구]*$";

    public DistrictCodeService(DistrictCodeRepository repository
    ) {
        this.repository = repository;
    }

    public String getLandCode(Region region) {
        if (isNotInKor(region.area0())) throw new IllegalArgumentException("한국내 에서만 사용 가능 합니다.");

        String city = cityWithoutImpurities(region.area1());
        String sig = sigWithoutImpurities(region.area2());

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

        String city = cityWithoutImpurities(region.area1());
        String sig = sigWithoutImpurities(region.area2());

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


    private String cityWithoutImpurities(String city) {
        return city.replaceAll(WITHOUT_CTPRVN_REG_EXP, "");
    }

    private String sigWithoutImpurities(String sig) {
        return sig.replaceAll(WITHOUT_SIG_REG_EXP, "");
    }

    private boolean isNotInKor(String country) {
        return !(country.equals("kor") || country.equals("kr"));
    }
}
