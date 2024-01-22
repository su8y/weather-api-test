package com.example.geomweatherapi.application;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

public class GeoUtils {
    private static Geometry transCRSByGeometry(final Geometry feature, final String _sourceCrs, String targetCrs) throws FactoryException, TransformException {
        CoordinateReferenceSystem target = CRS.decode(targetCrs);
        CoordinateReferenceSystem sourceCrs = CRS.decode(_sourceCrs);

        MathTransform transform = CRS.findMathTransform(sourceCrs, target, false);

        return JTS.transform(feature, transform);
    }
}
