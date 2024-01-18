package com.example.geomweatherapi;

import com.example.geomweatherapi.application.WeatherUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ShpReaderTest {

    private GeometryFactory geometryFactory = new GeometryFactory();

    @Test
    @Disabled
    void 행정구역_교차() throws FactoryException, TransformException, IOException {
        System.out.println(System.getProperty("file.encoding"));

        System.setProperty("file.encoding", "UTF-8");
        System.out.println(System.getProperty("file.encoding"));
        System.setProperty("org.geotools.referencing.forceXY", "true");
        ShpReader shpReader = new ShpReader();

        // kakao 회사 좌표
        double y = 33.450701D, x = 126.570667D;

        Coordinate coordinate = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coordinate);

        List<String> strings1 = shpReader.intersectMeter(point);
        assertThat(strings1.size()).isEqualTo(1);
        assertThat(strings1.get(0)).isEqualTo("제주특별자치도");

    }

    @Test
    @Disabled
    void 좌표계변환_테스트() throws FactoryException, TransformException, IOException {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        double x = 126.98000833333333, y = 37.56356944444444;
        double[] 성남시분당구 = {127.12924444444444, 37.39673888888889};


        ShpReader shpReader = new ShpReader();
        Coordinate coordinate = new Coordinate(x, y);

        Point point = geometryFactory.createPoint(coordinate);
        List<String> strings = shpReader.intersectMeter(point);
        assertThat(strings.get(0)).isEqualTo("서울특별시");
        assertThat(strings.size()).isEqualTo(1);

        Coordinate 성남시분당구Coord = new Coordinate(성남시분당구[0], 성남시분당구[1]);
        Geometry geometry = geometryFactory.createPoint(성남시분당구Coord);

        List<String> 성남시분당구str = shpReader.intersectMeter(geometry);
        assertThat(성남시분당구str.get(0)).isEqualTo("경기도");
        double[] doubles = WeatherUtils.transXY(성남시분당구[0], 성남시분당구[1]);
        System.out.println(doubles[0] + " " + doubles[1]);
    }


}