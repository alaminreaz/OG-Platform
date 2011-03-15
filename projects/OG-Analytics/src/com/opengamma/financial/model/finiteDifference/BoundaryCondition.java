/**
 * Copyright (C) 2009 - 2011 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.model.finiteDifference;

/**
 * 
 */
public interface BoundaryCondition {
  
  double[] getLeftMatrixCondition(final PDEDataBundle data, final double t);
  
  double[] getRightMatrixCondition(final PDEDataBundle data, final double t);

  double getConstant(final PDEDataBundle data, final double t);
  
  double getLevel();
  
}
