package mina.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.sqrt;

public class QuadraticEquation {
    private final Logger log = LoggerFactory.getLogger(QuadraticEquation.class);

    public List<Double> solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        log.debug("discriminant: {}", discriminant);

        if (discriminant < 0) {
            return Collections.emptyList();
        } else {
            List<Double> roots = new ArrayList<>();

            if (discriminant > 0) {
                roots.add((-b - sqrt(discriminant)) / (2 * a));
                roots.add((-b + sqrt(discriminant)) / (2 * a));
            } else {
                roots.add(-b / (2 * a));
            }

            return roots;
        }
    }
}
