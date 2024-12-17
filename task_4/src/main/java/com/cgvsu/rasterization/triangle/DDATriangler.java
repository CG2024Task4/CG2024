package com.cgvsu.rasterization.triangle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import com.cgvsu.rasterization.math.Floats;
import com.cgvsu.rasterization.triangle.color.MonotoneTriangleFiller;
import com.cgvsu.rasterization.triangle.color.TriangleFiller;
import com.cgvsu.rasterization.triangle.geom.Triangle;
import com.cgvsu.rasterization.triangle.geom.TriangleBarycentrics;
import com.cgvsu.rasterization.triangle.geom.TriangleBarycentricser;
import com.cgvsu.rasterization.color.HTMLColorf;
import com.cgvsu.math.typesVectors.Vector2f;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;

/**
 * A {@link Triangler Triangler} implementation using the digital differential
 * analyzer algorithm for drawing slopes and drawing pixels one by one.
 *
 * <p>
 * The algorithm separates the triangle into two (with flat side on max and/or
 * min Y level), and then draws the slopes pixel by pixel with the triangle
 * itself, using the scanline approach.
 * <p>
 * Uses floats for all calculations and converts the coordinates for
 * rasterization to integers at the last stage - drawing horizontal lines. All
 * floats at the convertion are floored (not rounded) for consistency.
 * <p>
 * Because of that, the triangles can be displaced to the start of the
 * coordinate plane, and the gaps can be seen between the triangles, if this
 * rasterization is used to draw triangulated polygons.
 * <p>
 * Sets pixels one by one with the
 * {@link PixelWriter#setColor(int, int, javafx.scene.paint.Color)} call (the
 * rasterization is not buffered). It's very slow, so this implementation is not
 * recommended for fast rendering.
 * <p>
 * Algorithm documentation: <a href=
 * "https://en.wikipedia.org/wiki/Digital_differential_analyzer_(graphics_algorithm)">Wikipedia</a>.
 * <p>
 * The implementation is heavily based on <a href=
 * "https://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html">this
 * article</a>.
 *
 * @since 2.0.0
 */
public final class DDATriangler implements Triangler {

    // miscellaneous

    private final PixelWriter writer;
    private TriangleFiller filler = new MonotoneTriangleFiller(HTMLColorf.BLACK);

    private final TriangleBarycentricser barycentricser = new TriangleBarycentricser();
    private final TriangleBarycentrics barycentrics = barycentricser.barycentrics();
    private final List<Vector2f> vertices = new ArrayList<>(3);
    private final Vector2f point = new Vector2f(0, 0);

    // vertices

    private Vector2f v1;
    private double v1x;
    private double v1y;

    private Vector2f v2;
    private double v2x;
    private double v2y;

    private Vector2f v3;
    private double v3x;
    private double v3y;

    private Vector2f v4 = new Vector2f(0, 0);
    private double v4x;

    // drawing

    private double x1;
    private double x2;

    private double dx1;
    private double dx2;

    private int x;
    private int y;

    private double x0;
    private double y0;

    private double limY;

    /**
     * Creates a new {@code DDATriangler} instance.
     *
     * <p>
     * Anchores itself to the passed {@link GraphicsContext}: contains the reference
     * to the {@link PixelWriter} from the context.
     * <p>
     * It's not a singleton class, so instances should be created and used
     * separately.
     *
     * @param ctx graphics context to use for the rasterization
     *
     * @since 2.0.0
     *
     * @see GraphicsContext
     */
    public DDATriangler(final GraphicsContext ctx) {
        writer = Objects.requireNonNull(ctx).getPixelWriter();
    }

    @Override
    public TriangleFiller filler() {
        return filler;
    }

    @Override
    public void setFiller(final TriangleFiller f) {
        filler = Objects.requireNonNull(f);
    }

    private void update(final Triangle t) {
        vertices.clear();

        vertices.add(t.v1());
        vertices.add(t.v2());
        vertices.add(t.v3());

        vertices.sort(new Comparator<Vector2f>() {
            @Override
            public int compare(Vector2f a, Vector2f b) {
                if (Math.abs(a.getY()-b.getY()) <= 0.000001){
                    if (Math.abs(a.getX()-b.getX()) <= 0.000001){
                        return 0;
                    } else if (a.getX() > b.getX()) {
                        return 1;
                    } else {return -1;}
                } else if (a.getY() > b.getY()) {
                    return 1;
                } else {return -1;}
            }
        });

        v1 = vertices.get(0);
        v2 = vertices.get(1);
        v3 = vertices.get(2);

        v1x = v1.getX();
        v1y = v1.getY();

        v2x = v2.getX();
        v2y = v2.getY();

        v3x = v3.getX();
        v3y = v3.getY();
    }

    private void drawFlat(final Vector2f p0, final Vector2f p1, final Vector2f p2) {
        x0 = p0.getX();
        y0 = p0.getY();

        dx1 = (p1.getX() - x0) / (p1.getY() - y0);
        dx2 = (p2.getX() - x0) / (p2.getY() - y0);

        limY = p1.getY();
        if (Floats.moreThan((float) y0, (float) limY)) {
            drawFlatAtMinY();
        } else {
            drawFlatAtMaxY();
        }
    }

    private void drawFlatAtMaxY() {
        x1 = x0;
        x2 = x1;

        for (y = (int) y0; y <= limY; y++) {
            drawHLine();

            x1 += dx1;
            x2 += dx2;
        }
    }

    private void drawFlatAtMinY() {
        x1 = x0;
        x2 = x1;

        for (y = (int) y0; y > limY; y--) {
            drawHLine();

            x1 -= dx1;
            x2 -= dx2;
        }
    }

    private void drawHLine() {
        point.set(1, y);

        for (x = (int) x1; x <= x2; x++) {
            point.set(0, x);

            barycentricser.calculate(point);

            if (!barycentrics.normalized()) {
                continue;
            }

            if (!barycentrics.inside()) {
                continue;
            }

            writer.setColor(x, y, filler.color(barycentrics).jfxColor());
        }
    }

    @Override
    public void draw(final Triangle t) {
        Objects.requireNonNull(t);

        barycentricser.setTriangle(t);
        update(t);

        if (Floats.equals((float) v2y, (float) v3y)) {
            drawFlat(v1, v2, v3);
            return;
        }

        if (Floats.equals((float) v1y, (float) v2y)) {
            drawFlat(v3, v1, v2);
            return;
        }

        v4x = v1x + ((v2y - v1y) / (v3y - v1y)) * (v3x - v1x);

        v4.set(0, v4x);
        v4.set(1, v2y);

        if (Floats.moreThan((float) v4x, (float) v2x)) {
            drawFlat(v1, v2, v4);
            drawFlat(v3, v2, v4);
        } else {
            drawFlat(v1, v4, v2);
            drawFlat(v3, v4, v2);
        }
    }
}
