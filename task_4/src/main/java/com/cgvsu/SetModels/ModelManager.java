package com.cgvsu.SetModels;

import com.cgvsu.model.Model;

import java.util.ArrayList;
import java.util.List;

public class ModelManager {
    private final List<Model> models = new ArrayList<>(); // Список всех моделей
    private Model activeModel; // Текущая активная модель

    public void addModel(Model model) {
        models.add(model);
    }

    public void setActiveModel(Model model) {
        this.activeModel = model;
    }

    public Model getActiveModel() {
        return activeModel;
    }

    public List<Model> getModels() {
        return models;
    }
    public void delModels(Model model){
        models.remove(model);
    }
}
