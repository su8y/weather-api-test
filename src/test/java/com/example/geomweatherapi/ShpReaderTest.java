package com.example.geomweatherapi;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
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
    void 행정구역_교차() throws FactoryException, TransformException, IOException {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        ShpReader shpReader = new ShpReader();

        // kakao 회사 좌표
        double y = 33.450701D, x = 126.570667D;

        Coordinate coordinate = new Coordinate(x, y);
        Point point = geometryFactory.createPoint(coordinate);

        List<String> strings1 = shpReader.intersectMeter(point);
        assertThat(strings1.size()).isEqualTo(1);
        assertThat(strings1.get(0)).isEqualTo("Jeju-do");
    }

    @Test
    void 좌표계변환_테스트() throws FactoryException, TransformException, IOException {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        double y1 = 33.450701D, x1 = 126.570667D;
        ShpReader shpReader = new ShpReader();
        Coordinate coordinate = new Coordinate(x1, y1);

        Point point = geometryFactory.createPoint(coordinate);
        List<String> strings = shpReader.intersectMeter(point);
        assertThat(strings.get(0)).isEqualTo("Jeju-do");

    }

}