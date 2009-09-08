package com.opengamma.financial.model.option.definition;

import java.util.Date;

import com.opengamma.math.function.Function;
import com.opengamma.util.DateUtil;

public abstract class OptionDefinition {
  protected final double DAYS_IN_YEAR = 365.25;
  private double _strike;
  private Date _expiry;
  private boolean _isCall;
  protected Function<Double, Double> _payoffFunction;
  protected Function<Double, Boolean> _exerciseFunction;

  public OptionDefinition(Double strike, Date expiry, Boolean isCall) {
    _strike = strike;
    _expiry = expiry;
    _isCall = isCall;
    initPayoffAndExerciseFunctions();
  }

  protected abstract void initPayoffAndExerciseFunctions();

  public Double getStrike() {
    return _strike;
  }

  public Date getExpiry() {
    return _expiry;
  }

  public double getTimeToExpiry(Date date) {
    return DateUtil.subtract(getExpiry(), date) / DAYS_IN_YEAR;
  }

  public Boolean isCall() {
    return _isCall;
  }

  public Function<Double, Boolean> getExerciseFunction() {
    if (_exerciseFunction == null) throw new IllegalArgumentException("Exercise function was not initialised");
    return _exerciseFunction;
  }

  public Function<Double, Double> getPayoffFunction() {
    if (_payoffFunction == null) throw new IllegalArgumentException("Payoff function was not initialised");
    return _payoffFunction;
  }
}
