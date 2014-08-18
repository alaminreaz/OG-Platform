/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.model.volatility.discrete;

import com.opengamma.analytics.financial.model.volatility.surface.VolatilitySurface;
import com.opengamma.analytics.financial.model.volatility.surface.VolatilitySurfaceProvider;
import com.opengamma.analytics.math.matrix.DoubleMatrix1D;
import com.opengamma.analytics.math.matrix.DoubleMatrix2D;
import com.opengamma.analytics.math.surface.Surface;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.tuple.DoublesPair;

/**
 * Form a {@link DiscreteVolatilityFunctionProvider} from a {@link VolatilitySurfaceProvider}
 */
public class DiscreteVolatilityFunctionProviderFromVolSurface extends DiscreteVolatilityFunctionProvider {

  private final VolatilitySurfaceProvider _volatilitySurfacePro;

  /**
   * set up a {@link DiscreteVolatilityFunctionProvider}
   * @param volSurfacePro a volatility surface provider 
   */
  public DiscreteVolatilityFunctionProviderFromVolSurface(final VolatilitySurfaceProvider volSurfacePro) {
    ArgumentChecker.notNull(volSurfacePro, "volSurfacePro");
    _volatilitySurfacePro = volSurfacePro;
  }

  @Override
  public DiscreteVolatilityFunction from(final DoublesPair[] expiryStrikePoints) {
    ArgumentChecker.notNull(expiryStrikePoints, "strikeExpiryPoints");
    final int n = expiryStrikePoints.length;

    return new DiscreteVolatilityFunction() {

      @Override
      public DoubleMatrix1D evaluate(final DoubleMatrix1D x) {
        final double[] res = new double[n];
        final VolatilitySurface vs = _volatilitySurfacePro.getVolSurface(x);
        for (int i = 0; i < n; i++) {
          res[i] = vs.getVolatility(expiryStrikePoints[i]);
        }
        return new DoubleMatrix1D(res);
      }

      @Override
      public DoubleMatrix2D calculateJacobian(final DoubleMatrix1D x) {
        final Surface<Double, Double, DoubleMatrix1D> volSurfaceAdjoint = _volatilitySurfacePro.getParameterSensitivitySurface(x);
        final DoubleMatrix2D res = new DoubleMatrix2D(n, _volatilitySurfacePro.getNumModelParameters());
        for (int i = 0; i < n; i++) {
          res.getData()[i] = volSurfaceAdjoint.getZValue(expiryStrikePoints[i]).getData();
        }

        return res;
      }

      @Override
      public int getLengthOfDomain() {
        return _volatilitySurfacePro.getNumModelParameters();
      }

      @Override
      public int getLengthOfRange() {
        return n;
      }

    };
  }

}
