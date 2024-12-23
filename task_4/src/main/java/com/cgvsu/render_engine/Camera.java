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
        double smoothFactor = 0.02; // Коэффициент для чувствительности
        double delta = deltaY * smoothFactor;

        double minDistance = 3.0;

        Vector3f direction = target.subtracted(position).normalize();

        double x = position.getX() + direction.getX() * delta;
        double y = position.getY() + direction.getY() * delta;
        double z = position.getZ() + direction.getZ() * delta;

        Vector3f newPosition = new Vector3f(x, y, z);
        if (target.subtracted(newPosition).getLength() > minDistance) {
            position = newPosition;
        }
    }

    public void mouseOrbit(double deltaX, double deltaY) {
        double rotationSpeed = 0.01; // Чувствительность вращения
        Vector3f direction = position.subtracted(target);
        double radius = direction.getLength();

        // Полярные координаты
        double theta = Math.atan2(direction.getZ(), direction.getX());
        double phi = Math.acos(direction.getY() / radius);

        // Изменение углов
        theta += deltaX * rotationSpeed;
        phi += deltaY * rotationSpeed;

        // Ограничение по вертикали
        phi = Math.max(0.1, Math.min(Math.PI - 0.1, phi));

        // Перевод обратно в декартовы координаты
        double x = radius * Math.sin(phi) * Math.cos(theta);
        double y = radius * Math.cos(phi);
        double z = radius * Math.sin(phi) * Math.sin(theta);

        position = target.added(new Vector3f(x, y, z));
    }

    public void mousePan(double deltaX, double deltaY) {
        double panSpeed = 0.01; // Чувствительность панорамирования
        Vector3f right = target.subtracted(position).normalize().crossProduct(new Vector3f(0, 1, 0)).normalize();
        Vector3f up = right.crossProduct(target.subtracted(position).normalize()).normalize();

        Vector3f panRight = right.multiplied(-deltaX * panSpeed);
        Vector3f panUp = up.multiplied(-deltaY * panSpeed);

        Vector3f panTranslation = panRight.added(panUp);

        position = position.added(panTranslation);
        target = target.added(panTranslation);
    }
}
