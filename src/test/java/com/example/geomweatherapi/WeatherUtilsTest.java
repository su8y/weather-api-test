package com.example.geomweatherapi;

import com.example.geomweatherapi.application.WeatherUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class WeatherUtilsTest {

    @Test
    void 위경도_변환(){
        WeatherUtils weatherUtils = new WeatherUtils();

//        double[] doubles = weatherUtils.transXY(37.579871128849334, 126.98935225645432);
        double[] doubles = weatherUtils.transXY(126.98935225645432, 37.579871128849334);
        Assertions.assertThat(doubles[0]).isEqualTo(60);
//        double[] 성남시분당구 = {127.12924444D,37.396738888D};
//        double[] doubles2 = weatherUtils.transXY(127.12924444444444,37.39673888888889);
//        System.out.println(doubles2[0]);
//
//        double[] doubles1 = weatherUtils.transLL(60, 126);
//        System.out.println(doubles1[0]);
//        System.out.println(doubles1[1]);

    }


}
