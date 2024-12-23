package com.cgvsu.model;

import com.cgvsu.math.ATTransformator;
import com.cgvsu.math.typesVectors.Vector3f;

public class ModelTransformer {

    public static Model translateModel(Model model, double tX, double tY, double tZ) {
        ATTransformator transformator = new ATTransformator.ATBuilder()
                .translateByVector(new Vector3f(tX, tY, tZ))
                .build();
        return transformator.applyTransformationToModel(model);
    }

    public static Model rotateModel(Model model, double angleX, double angleY, double angleZ) {
        // Конвертация градусов в радианы
        double radX = Math.toRadians(angleX);
        double radY = Math.toRadians(angleY);
        double radZ = Math.toRadians(angleZ);

        ATTransformator transformator = new ATTransformator.ATBuilder()
                .rotateByX(radX)
                .rotateByY(radY)
                .rotateByZ(radZ)
                .build();
        return transformator.applyTransformationToModel(model);
    }

    public static Model scaleModel(Model model, double sX, double sY, double sZ) {
        ATTransformator transformator = new ATTransformator.ATBuilder()
                .scaleByVertor(new Vector3f(sX, sY, sZ))
                .build();
        return transformator.applyTransformationToModel(model);
    }

    public static Model uniformScaleModel(Model model, double scale) {
        ATTransformator transformator = new ATTransformator.ATBuilder()
                .scaleByVertor(new Vector3f(scale, scale, scale))
                .build();
        return transformator.applyTransformationToModel(model);
    }
}
