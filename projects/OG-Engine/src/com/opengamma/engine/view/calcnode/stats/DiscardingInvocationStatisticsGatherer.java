/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 * 
 * Please see distribution for license.
 */
package com.opengamma.engine.view.calcnode.stats;

/**
 * Gatherer implementation that discards all received statistics.
 */
public class DiscardingInvocationStatisticsGatherer implements FunctionInvocationStatisticsGatherer {

  @Override
  public void functionInvoked(
      String configurationName, String functionIdentifier, int invocationCount,
      double executionNanos, double dataInputBytes, double dataOutputBytes) {
    // no action
  }

}
