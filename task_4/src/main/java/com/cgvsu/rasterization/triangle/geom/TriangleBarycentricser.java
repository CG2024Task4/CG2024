package com.cgvsu.rasterization.triangle.geom;

import java.util.Objects;

import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.rasterization.math.Floats;

/**
 * A class for memory and time efficient {@link TriangleBarycentrics}
 * generation from a {@link Triangle}.
 *
 * <p>
 * Exists for 3 reasons: to not allocate new barycentrics each time, to cache
 * the calculations for a single triangle and to not enforce the implementation
 * of this functionality on user.
 *
 * @since 2.0.0
 *
 * @see Triangle
 * @see TriangleBarycentrics
 */
public final class TriangleBarycentricser {

    private Triangle triangle;
    private final TriangleBarycentrics barycentrics;

    // on update()

    private Vector2f v1;
    private Vector2f v2;
    private Vector2f v3;

    private double x1;
    private double y1;

    private double x2;
    private double y2;

    private double x3;
    private double y3;

    private double dx13;
    private double dy13;

    private double dx23;
    private double dy23;

    private double k; // inverted denominator

    // on calculate()

    private double x;
    private double y;

    private double dx3;
    private double dy3;

    private double numerator1;
    private double numerator2;
    private double numerator3;

    /**
     * Creates a new {@link TriangleBarycentricser} instance.
     *
     * <p>
     * Creates a new {@link TriangleBarycentrics} with zeros as coordinates.
     *
     * @since 2.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentricser() {
        barycentrics = new TriangleBarycentrics(0, 0, 0);
    }

    /**
     * Returns a reference to the triangle barycentrics in this barycentricser
     * object.
     *
     * @return reference to the barycentrics in this barycentricser
     *
     * @since 2.0.0
     *
     * @see TriangleBarycentrics
     */
    public TriangleBarycentrics barycentrics() {
        return barycentrics;
    }

    /**
     * Returns a reference to the current used triangle in this barycentricser
     * object.
     *
     * @return reference to the triangle in this barycentricser
     *
     * @since 2.0.0
     *
     * @see Triangle
     */
    public Triangle triangle() {
        return triangle;
    }

    /**
     * Sets the new triangle to calculate the barycentrics for.
     *
     * <p>
     * Updates the barycentrics in the object automatically on call.
     *
     * @param t the triangle to use
     *
     * @throws NullPointerException if {@code t} is {@code null}
     *
     * @since 2.0.0
     *
     * @see Triangle
     */
    public void setTriangle(final Triangle t) {
        triangle = Objects.requireNonNull(t);
        update();
    }

    /**
     * Updates cached points to the current state of the used triangle.
     *
     * <p>
     * Because the triangle can be modified externally (and just a new one can be
     * set), but the rasterization process considers only a single frame, the update
     * is a separate method.
     *
     * @since 2.0.0
     */
    public void update() {
        v1 = triangle.v1();
        v2 = triangle.v2();
        v3 = triangle.v3();

        x1 = v1.get(0);
        y1 = v1.get(1);

        x2 = v2.get(0);
        y2 = v2.get(1);

        x3 = v3.get(0);
        y3 = v3.get(1);

        dx13 = x1 - x3;
        dy13 = y1 - y3;

        dx23 = x2 - x3;
        dy23 = y2 - y3;

        k = dy23 * dx13 - dx23 * dy13; // denominator
        if (Floats.equals((float) k, 0)) {
            k = 0;
        } else {
            k = 1f / k;
        }
    }

    /**
     * Calculates the barycentrics in this barycenctricser based on the current
     * point data.
     *
     * <p>
     * Updates the triangle barycentrics object accordingly on call.
     *
     * @param p a point to calculate barycentrics for
     *
     * @throws NullPointerException if {@code p} is {@code null}
     *
     * @since 2.0.0
     *
     * @see Vector2f
     */
    public void calculate(final Vector2f p) {
        Objects.requireNonNull(p);

        x = p.get(0);
        y = p.get(1);

        dx3 = x - x3;
        dy3 = y - y3;

        numerator1 = dy23 * dx3 - dx23 * dy3;
        numerator2 = -dy13 * dx3 + dx13 * dy3;
        numerator3 = (y1 - y2) * (x - x1) + (x2 - x1) * (y - y1);

        barycentrics.setLambda1((float) (numerator1 * k));
        barycentrics.setLambda2((float) (numerator2 * k));
        barycentrics.setLambda3((float) (numerator3 * k));
    }
}