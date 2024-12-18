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

public class DeleteVertex {
    public static Model changeModel(Model model, List<Integer> verticesToRemoveIndices, boolean flagDelNormal, boolean flagDelTexV) {
        Model model1 = model;
        deleteVertex(model1, verticesToRemoveIndices, flagDelNormal, flagDelTexV);
        return model1;
    }


    public static void deleteVertex(Model model, List<Integer> verticesToRemoveIndices, boolean flagDelNormal, boolean flagDelTexV) {

        //Удаляем грани, связанные с вершинами, которые нужно удалить
        //List<Integer> normalsToRemove = new ArrayList<>();
        //List<Integer> TexVToRemove = new ArrayList<>();
        //List<Vector3f> normalsToRemove = new ArrayList<>();
        //HashSet<Integer> normalsToRemoveIndex = new HashSet<>();
        //HashSet<Integer> TexVToRemoveIndex = new HashSet<>();
        //HashSet<Vector2f> TexVToRemove = new HashSet<>();

        flagDelNormal = flagDelNormal && !model.getNormals().isEmpty();
        flagDelTexV = flagDelTexV && !model.getTextureVertices().isEmpty();
        HashMap<Integer, Vector3f> DelVert = new HashMap<>();
        HashMap<Integer, Vector3f> DelNorml = new HashMap<>();
        HashMap<Integer, Vector2f> DelTexVert = new HashMap<>();


        List<Polygon> polygonsToRemove = new ArrayList<>();
        List<Polygon> polygons = model.getPolygons();
        //List<Integer> hanging = new ArrayList<>();

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

//        if (flagDelNormal || flagDelTexV) {
//            for (Polygon polygon : polygonsToRemove) {
//                if (flagDelNormal) {
//                    for (int n : polygon.getVertexIndices()) {
//                        if (model.getNormals().size() > n && verticesToRemoveIndices.contains(n + 1)) {
//                            System.out.println(1111);
//                            //model.getNormals().remove(n);
//                            normalsToRemoveIndex.add(polygon.getNormalIndices().get(n));
//                        }
//                    }
//                }
//                if (flagDelTexV) {
//                    for (int n = 0; n < polygon.getTextureVertexIndices().size(); n++) {
//                        if (model.getTextureVertices().size() > n) {
//                            model.getTextureVertices().remove(model.getTextureVertices().get(n));
//
//                            TexVToRemoveIndex.add(n);
//
//                        }
//                    }
//                }
//            }
//        }

        model.getPolygons().removeAll(polygonsToRemove);
        //Удаляем вершины
//        for (int i = verticesToRemoveIndices.size() - 1; i >= 0; i--) {
//            if (verticesToRemoveIndices.get(i) < model.getVertices().size()) {
//                model.getVertices().remove( verticesToRemoveIndices.get(i)-1);
//            }
//        }


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


//            for (Vector3f s : normalsToRemove){
//                model.getNormals().remove(s);
//            }
//        }
//        if (flagDelTexV && !model.getTextureVertices().isEmpty()){
//            model.getTextureVertices().removeAll(TexVToRemoveIndex);
//        }

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





/*public class DeleteVertex {
    public static void deleteVertex(Model model, List<Integer> verticesToRemoveIndices, boolean flagDelNormal, boolean flagDelTexV) {
        Model model1 = new Model();
        verticesToRemoveIndices.replaceAll(integer -> integer - 1);
        flagDelNormal = flagDelNormal && !model.getNormals().isEmpty();
        flagDelTexV = flagDelTexV && !model.getTextureVertices().isEmpty();

        SortedSet<Integer> DelVert = new TreeSet<>();
        SortedSet<Integer> DelNormal = new TreeSet<>();
        SortedSet<Integer> DelTextVert = new TreeSet<>();

        for (Polygon polygon : model.getPolygons()) {
            boolean flag = true;
            for (int vertDel: verticesToRemoveIndices) {
                if (polygon.getVertexIndices().contains(vertDel)) {
                    DelVert.addAll(polygon.getVertexIndices());
                    if (flagDelNormal){
                        DelNormal.addAll(polygon.getNormalIndices());
                    }
                    if (flagDelTexV){
                        DelTextVert.addAll(polygon.getTextureVertexIndices());
                    }
                    flag = false;
                    break;
                }
            }
            if (flag) {
                model1.getPolygons().add(polygon);
                for (int vertexIndex : polygon.getVertexIndices()) {
                    model1.getVertices().add(model.getVertices().get(vertexIndex));
                    DelVert.remove(vertexIndex);
                }
                if (flagDelNormal) {
                    for (int normalIndex : polygon.getNormalIndices()) {
                        model1.getNormals().add(model.getNormals().get(normalIndex));
                    }
                }
                if (flagDelTexV) {
                    for (int textVertIndex : polygon.getTextureVertexIndices()) {
                        model1.getTextureVertices().add(model.getTextureVertices().get(textVertIndex));
                    }
                }
            }
        }

        for (Polygon polygon : model1.getPolygons()) {
            polygon.getVertexIndices().replaceAll(integer -> integer - DelVert.headSet(integer).size());
            if (flagDelNormal) {
                polygon.getNormalIndices().replaceAll(integer -> integer - DelNormal.headSet(integer).size());
            }
            if (flagDelTexV){
                polygon.getTextureVertexIndices().replaceAll(integer -> integer - DelTextVert.headSet(integer).size());
            }
        }
        model=model1;
    }
}*/
