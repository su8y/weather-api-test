package com.example.geomweatherapi;

import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.shapefile.shp.ShapefileException;
import org.geotools.data.shapefile.shp.ShapefileReader;
import org.locationtech.jts.geom.GeometryFactory;

import java.io.IOException;
import java.net.MalformedURLException;

public class ShpReader {
    public static void main(String[] args) throws IOException {
//        ClassPathResource shpFile = new ClassPathResource("ctprvn.shp");
//        shpFile.getFile();
//        File file = new File("ctprvn.shp");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("url", file.toURI().toURL());
//
//        DataStore dataStore = DataStoreFinder.getDataStore(map);
//        String typeName = dataStore.getTypeNames()[0];
//
//        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
//        Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
//
//        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
//        try (FeatureIterator<SimpleFeature> features = collection.features()) {
//            while (features.hasNext()) {
//                SimpleFeature feature = features.next();
//                System.out.print(feature.getID());
//                System.out.print(": ");
//                System.out.println(feature.getDefaultGeometryProperty().getValue());
//            }
//        }
        ShapefileReader shapefileReader  = null;
        try{
            ShpFiles shpFiles = new ShpFiles("C:\\Users\\bsa05\\workspaces\\geom-weather-api\\src\\main\\resources\\ctprvn.shp");
            GeometryFactory geometryFactory = new GeometryFactory();
            shapefileReader = new ShapefileReader(shpFiles, true, false, geometryFactory);

            while(shapefileReader.hasNext()){
                ShapefileReader.Record record = shapefileReader.nextRecord();
                System.out.println(record.toString());
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ShapefileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
