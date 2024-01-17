package com.example.geomweatherapi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherUtilsTest {
    @Test
    void 위경도_변환(){
        WeatherUtils weatherUtils = new WeatherUtils();

        double[] doubles = weatherUtils.transXY(37.579871128849334, 126.98935225645432);
        Assertions.assertThat(doubles[0]).isEqualTo(60);

        double[] doubles1 = weatherUtils.transLL(60, 126);
        System.out.println(doubles1[0]);
        System.out.println(doubles1[1]);

    }


}
