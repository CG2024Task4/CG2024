package com.cgvsu.render_engine;


import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f target;
    private final double fov;
    private double aspectRatio;
    private final double nearPlane;
    private final double farPlane;

    public Camera(Vector3f position,
                  Vector3f target,
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

    public void setPosition(final Vector3f position) {
        this.position = position;
    }

    public void setTarget(final Vector3f target) {
        this.target = target;
    }

    public void setAspectRatio(final double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void movePosition(final Vector3f translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3f translation) {
        this.target = this.target.added(translation);
    }

    public Matrix4f getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4f getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public void mouseCameraZoom(double deltaY) {
        double smoothFactor = 0.02; // Коэффициент для чувствительности (можно его настроить)
        double delta = deltaY * smoothFactor;

        double minDistance = 3.0;

        Vector3f det = target.subtracted(position).normalize();

        double x = position.getX() + det.getX() * delta;
        double y = position.getY() + det.getY() * delta;
        double z = position.getZ() + det.getZ() * delta;
        Vector3f newPosition = new Vector3f(x, y, z);
        if (target.subtracted(newPosition).getLength() > minDistance) {
            position = new Vector3f(x, y, z);
        }
    }
}
