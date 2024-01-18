package com.example.geomweatherapi;

import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ShpReader {
    public List<String> intersectMeter(Geometry geometry) throws IOException, FactoryException, TransformException {
        List<String> results = new ArrayList<>();

        // read File
        File file = new File("src/main/resources/ctprvn.shp");
        HashMap<String, Object> map = new HashMap<>();
        map.put("url", file.toURI().toURL());

        // it must be Singleton scope
        ShapefileDataStore shapefileDataStore = new ShapefileDataStore(file.toURI().toURL());
        shapefileDataStore.setCharset(Charset.forName("EUC-KR"));
//        DataStore dataStore = DataStoreFinder.getDataStore(map);


        String typeName = shapefileDataStore.getTypeNames()[0];


        // read shp Features
        FeatureSource<SimpleFeatureType, SimpleFeature> source = shapefileDataStore.getFeatureSource(typeName);
//        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")

        // 좌표값 4326 -> 5179 변경
        Geometry transformedFeature = transformEPSG5157(geometry);

        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
        try (FeatureIterator<SimpleFeature> features = collection.features()) {
            while (features.hasNext()) {
                Optional<String> englishNameIntersects = getEnglishNameIntersects(features.next(), transformedFeature);
                englishNameIntersects.ifPresent(property -> {
                    results.add(property);
                });
            }
        }
        return results;
    }

    private Geometry transformEPSG5157(Geometry feature) throws FactoryException, TransformException {
        CoordinateReferenceSystem target = CRS.decode("EPSG:5179");
        CoordinateReferenceSystem sourceCrs = CRS.decode("EPSG:4326");

        MathTransform transform = CRS.findMathTransform(sourceCrs, target, false);

        return JTS.transform(feature, transform);
    }

    private Optional<String> getEnglishNameIntersects(SimpleFeature feature, Geometry target) throws UnsupportedEncodingException {
        Geometry defaultGeometry = (Geometry) feature.getDefaultGeometry();
        boolean isIntersects = defaultGeometry.intersects(target);

        if (!isIntersects) return Optional.empty();

        Property featureEngNameProperty = feature.getProperty("CTP_KOR_NM");
        String englishName = featureEngNameProperty.getValue().toString();


        return Optional.of(englishName);
    }
}
