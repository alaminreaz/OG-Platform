/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.math.integration;

import org.apache.commons.lang.Validate;

import com.opengamma.analytics.math.function.DoubleFunction1D;
import com.opengamma.analytics.math.function.special.OrthonormalHermitePolynomialFunction;
import com.opengamma.analytics.math.rootfinding.NewtonRaphsonSingleRootFinder;
import com.opengamma.util.tuple.Pair;

/**
 * Class that generates weights and abscissas for Gauss-Hermite quadrature.
 * Orthonormal Hermite polynomials $H_N$ are used to generate the weights (see
 * {@link OrthonormalHermitePolynomialFunction})
 * using the formula:
 * $$
 * \begin{align*}
 * w_i = \frac{2}{(H_n'(x_i))^2}
 * \end{align*}
 * $$
 * where $x_i$ is the $i^{th}$ root of the orthogonal polynomial and $H_i'$ is
 * the first derivative of the $i^{th}$ polynomial.
 */
public class GaussHermiteWeightAndAbscissaFunction implements QuadratureWeightAndAbscissaFunction {
  /** Weight generator */
  private static final OrthonormalHermitePolynomialFunction HERMITE = new OrthonormalHermitePolynomialFunction();
  /** The root-finder */
  private static final NewtonRaphsonSingleRootFinder ROOT_FINDER = new NewtonRaphsonSingleRootFinder(1e-12);

  @Override
  public GaussianQuadratureData generate(final int n) {
    Validate.isTrue(n > 0);
    final double[] x = new double[n];
    final double[] w = new double[n];
    final boolean odd = n % 2 != 0;
    final int m = (n + 1) / 2 - (odd ? 1 : 0);
    final Pair<DoubleFunction1D, DoubleFunction1D>[] polynomials = HERMITE.getPolynomialsAndFirstDerivative(n);
    final Pair<DoubleFunction1D, DoubleFunction1D> pair = polynomials[n];
    final DoubleFunction1D function = pair.getFirst();
    final DoubleFunction1D derivative = pair.getSecond();
    double root = 0;

    for (int i = 0; i < m; i++) {
      root = getInitialRootGuess(root, i, n, x);
      root = ROOT_FINDER.getRoot(function, derivative, root);
      final double dp = derivative.evaluate(root);
      x[i] = -root;
      x[n - 1 - i] = root;
      w[i] = 2. / (dp * dp);
      w[n - 1 - i] = w[i];
    }
    if (odd) {
      final double dp = derivative.evaluate(0.0);
      w[m] = 2. / dp / dp;
    }
    return new GaussianQuadratureData(x, w);
  }

  private double getInitialRootGuess(final double previousRoot, final int i, final int n, final double[] x) {
    if (i == 0) {
      return Math.sqrt(2 * n + 1) - 1.85575 * Math.pow(2 * n + 1, -1. / 6);
    }
    if (i == 1) {
      return previousRoot - 1.14 * Math.pow(n, 0.426) / previousRoot;
    }
    if (i == 2) {
      return 1.86 * previousRoot + 0.86 * x[0];
    }
    if (i == 3) {
      return 1.91 * previousRoot + 0.91 * x[1];
    }
    return 2 * previousRoot + x[i - 2];
  }
}
