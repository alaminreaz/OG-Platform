package com.opengamma.financial.model.interestrate.curve;

import java.util.Collections;
import java.util.Date;

import com.opengamma.math.interpolation.Interpolator1D;

/**
 * 
 * @author emcleod
 * 
 *         A DiscountCurve that has a constant interest rate for all times in
 *         the future.
 * 
 */
public class ConstantInterestRateDiscountCurve extends DiscountCurve {
  private final double _rate;

  public ConstantInterestRateDiscountCurve(Date date, Double rate) {
    super(date, Collections.<Double, Double> singletonMap(0., rate), null);
    _rate = rate;
  }

  @Override
  public Interpolator1D getInterpolator() {
    throw new UnsupportedOperationException();
  }

  @Override
  public double getInterestRate(Double t) {
    return _rate;
  }
}
