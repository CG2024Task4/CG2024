package com.cgvsu.deletevertex;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

public class PolygonRemover {
    public static void removePolygons(Model model, List<Integer> indices, boolean removeUnusedVertices, boolean removeUnusedNormals, boolean removeUnusedTextures) {
        if (indices == null || indices.isEmpty()) {
            System.out.println("Список индексов пуст или null.");
            return;
        }

        // Удаляем индексы в порядке убывания, чтобы избежать смещения
        indices.sort((a, b) -> Integer.compare(b, a));

        // Список удаленных полигонов для дальнейшей обработки
        List<Polygon> removedPolygons = new ArrayList<>();

        // Удаляем полигоны по индексам
        for (int index : indices) {
            if (index < 0 || index >= model.getPolygons().size()) {
                System.out.println("Индекс вне допустимого диапазона: " + index);
                continue;
            }

            // Удаляем полигон по индексу
            Polygon removedPolygon = model.getPolygons().remove(index);
            removedPolygons.add(removedPolygon);
            System.out.println("Полигон с индексом " + index + " удален.");
        }

        // Удаляем связанные элементы, если это необходимо
        if (removeUnusedVertices) {
            for (Polygon polygon : removedPolygons) {
                removeUnused(model, model.getVertices(), Polygon::getVertexIndices, polygon.getVertexIndices());
            }
        }

        if (removeUnusedNormals) {
            for (Polygon polygon : removedPolygons) {
                removeUnused(model, model.getNormals(), Polygon::getNormalIndices, polygon.getNormalIndices());
            }
        }

        if (removeUnusedTextures) {
            for (Polygon polygon : removedPolygons) {
                removeUnused(model, model.getTextureVertices(), Polygon::getTextureVertexIndices, polygon.getTextureVertexIndices());
            }
        }
    }

    // Удаляет элементы из списка, если они больше не используются ни одним полигоном
    private static <T> void removeUnused(Model model, List<T> list, Function<Polygon, List<Integer>> indexExtractor, List<Integer> removedIndices) {
        Set<Integer> usedIndices = new HashSet<>();
        for (Polygon polygon : model.getPolygons()) {
            usedIndices.addAll(indexExtractor.apply(polygon));
        }

        // Удаляем элементы, которые больше не используются
        for (int i = removedIndices.size() - 1; i >= 0; i--) {
            int index = removedIndices.get(i);
            if (!usedIndices.contains(index)) {
                list.remove(index);
                System.out.println("Удален свободный элемент с индексом: " + index);
            }
        }
    }


}
