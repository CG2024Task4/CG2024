package com.cgvsu.render_engine;


import com.cgvsu.math.typesMatrix.Matrix4D;
import com.cgvsu.math.typesVectors.Vector3C;

public class Camera {

    private Vector3C position;
    private Vector3C target;
    private final double fov;
    private double aspectRatio;
    private final double nearPlane;
    private final double farPlane;

    public Camera(Vector3C position,
                  Vector3C target,
                  double fov,
                  double aspectRatio,
                  double nearPlane,
                  double farPlane
    ) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final Vector3C position) {
        this.position = position;
    }

    public void setTarget(final Vector3C target) {
        this.target = target;
    }

    public void setAspectRatio(final double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3C getPosition() {
        return position;
    }

    public Vector3C getTarget() {
        return target;
    }

    public void movePosition(final Vector3C translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3C translation) {
        this.target = this.target.added(translation);
    }

    public Matrix4D getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4D getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }
}
