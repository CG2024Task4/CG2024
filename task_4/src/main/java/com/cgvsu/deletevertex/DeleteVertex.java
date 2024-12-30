package com.cgvsu.deletevertex;

import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.*;
import java.util.function.Function;

public class DeleteVertex {
    public static void deleteVertex(Model model, List<Integer> verticesToRemoveIndices, boolean flagDelNormal, boolean flagDelTexV) {

        flagDelNormal = flagDelNormal && !model.getNormals().isEmpty();
        flagDelTexV = flagDelTexV && !model.getTextureVertices().isEmpty();
        HashMap<Integer, Vector3f> DelVert = new HashMap<>();
        HashMap<Integer, Vector3f> DelNorml = new HashMap<>();
        HashMap<Integer, Vector2f> DelTexVert = new HashMap<>();


        List<Polygon> polygonsToRemove = new ArrayList<>();
        List<Polygon> polygons = model.getPolygons();

        for (Polygon polygon : polygons)
            for (Integer vertexIndex : verticesToRemoveIndices) {
                if (polygon.getVertexIndices().contains(vertexIndex - 1)) {
                    polygonsToRemove.add(polygon);
                    for (int i = 0; i < polygon.getVertexIndices().size(); i++) {
                        DelVert.put(polygon.getVertexIndices().get(i), model.getVertices().get(polygon.getVertexIndices().get(i)));

                        if (flagDelNormal) {
                            DelNorml.put(polygon.getNormalIndices().get(i), model.getNormals().get(polygon.getNormalIndices().get(i)));
                        }
                        if (flagDelTexV) {
                            DelTexVert.put(polygon.getTextureVertexIndices().get(i), model.getTextureVertices().get(polygon.getTextureVertexIndices().get(i)));
                        }
                    }
                    break;
                }
            }


        model.getPolygons().removeAll(polygonsToRemove);



        HashMap<Integer, Vector3f> DelVert1 = new HashMap<>();
        //висячие
        for (int index : DelVert.keySet()) {
            int flag = 1;
            for (Polygon polygon : polygons) {
                if (polygon.getVertexIndices().contains(index)) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                DelVert1.put(index, DelVert.get(index));
            }
        }

        model.getVertices().removeAll(DelVert1.values());

        HashMap<Integer, Vector3f> DelNorml1 = new HashMap<>();
        HashMap<Integer, Vector2f> DelTexVert1 = new HashMap<>();

        if (flagDelNormal) {

            for (int index : DelVert1.keySet()) {
                int flag = 1;
                for (Polygon polygon : polygons) {
                    if (polygon.getNormalIndices().contains(index)) {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    DelNorml1.put(index, DelNorml.get(index));
                }
            }
            model.getNormals().removeAll(DelNorml1.values());
        }

        if (flagDelTexV) {
            for (int index : DelTexVert.keySet()) {
                int flag = 1;
                for (Polygon polygon : polygons) {
                    if (polygon.getTextureVertexIndices().contains(index)) {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    DelTexVert1.put(index, DelTexVert.get(index));
                }
            }
            model.getTextureVertices().removeAll(DelTexVert1.values());
        }




        for (Polygon polygon : polygons) {
            for (int i = 0; i < polygon.getVertexIndices().size(); i++) {
                int index = polygon.getVertexIndices().get(i);
                int del = index;
                for (int indexDel : DelVert1.keySet()) {
                    if (index > indexDel) {
                        del--;
                    }
                }
                polygon.getVertexIndices().set(i, del);
            }
            if (flagDelNormal) {
                for (int i = 0; i < polygon.getNormalIndices().size(); i++) {
                    int index = polygon.getNormalIndices().get(i);
                    int del = index;
                    for (int indexDel : DelVert1.keySet()) {

                        if (index > indexDel) {
                            del--;
                        }
                    }
                    polygon.getNormalIndices().set(i, del);
                }
            }

            if (flagDelTexV) {
                for (int i = 0; i < polygon.getTextureVertexIndices().size(); i++) {
                    int index = polygon.getTextureVertexIndices().get(i);
                    int del = index;
                    for (int indexDel : DelTexVert1.keySet()) {
                        if (index > indexDel) {
                            del--;
                        }
                    }
                    polygon.getTextureVertexIndices().set(i, del);
                }
            }
        }
    }
}






