package com.cgvsu.rasterization.triangle.geom;

import java.util.Objects;

import com.cgvsu.math.typesVectors.Vector2f;

/**
 * A default {@link Triangle} implementation.
 * 
 * <p>
 * Does store the references to the original points on creation. Getters of the
 * vertices return the same reference everytime.
 *
 * @since 2.0.0
 *
 * @see Triangle
 */
public final class Polygon3 implements Triangle {

    private final Vector2f v1;
    private final Vector2f v2;
    private final Vector2f v3;

    /**
     * Creates a new {@code Polygon3} instance.
     *
     * @param v1 the first vertex
     * @param v2 the second vertex
     * @param v3 the third vertex
     *
     * @throws NullPointerException if at least one parameter is {@code null}
     *
     * @since 2.0.0
     */
    public Polygon3(final Vector2f v1, final Vector2f v2, final Vector2f v3) {
        this.v1 = Objects.requireNonNull(v1);
        this.v2 = Objects.requireNonNull(v2);
        this.v3 = Objects.requireNonNull(v3);
    }

    @Override
    public Vector2f v1() {
        return v1;
    }

    @Override
    public Vector2f v2() {
        return v2;
    }

    @Override
    public Vector2f v3() {
        return v3;
    }
}
