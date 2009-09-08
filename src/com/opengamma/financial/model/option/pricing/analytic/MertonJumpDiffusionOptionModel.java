package com.opengamma.financial.model.option.pricing.analytic;

import java.util.Date;

import com.opengamma.financial.model.option.definition.EuropeanVanillaOptionDefinition;
import com.opengamma.financial.model.option.definition.MertonJumpDiffusionModelOptionDataBundle;
import com.opengamma.financial.model.option.definition.StandardOptionDataBundle;
import com.opengamma.math.function.Function1D;

/**
 * 
 * @author emcleod
 * 
 */

public class MertonJumpDiffusionOptionModel extends AnalyticOptionModel<EuropeanVanillaOptionDefinition, MertonJumpDiffusionModelOptionDataBundle> {
  protected BlackScholesMertonModel _bsm = new BlackScholesMertonModel();

  @Override
  protected Function1D<MertonJumpDiffusionModelOptionDataBundle, Double> getPricingFunction(final EuropeanVanillaOptionDefinition definition) {
    Function1D<MertonJumpDiffusionModelOptionDataBundle, Double> pricingFunction = new Function1D<MertonJumpDiffusionModelOptionDataBundle, Double>() {

      @Override
      public Double evaluate(MertonJumpDiffusionModelOptionDataBundle data) {
        Date date = data.getDate();
        double k = definition.getStrike();
        double t = definition.getTimeToExpiry(date);
        double sigma = data.getVolatility(t, k);
        double lambda = data.getLambda();
        double gamma = data.getGamma();
        double sigmaSq = sigma * sigma;
        double delta = gamma * sigmaSq / lambda;
        double sigmaAdjusted = Math.sqrt(sigmaSq - lambda * delta);
        double lambdaT = lambda * t;
        double mult = Math.exp(-lambdaT);
        // TODO this is not right
        StandardOptionDataBundle bsmData = new StandardOptionDataBundle(data.getDiscountCurve(), data.getCostOfCarry(), data.getVolatilitySurface(), data.getSpot(), date);
        Function1D<StandardOptionDataBundle, Double> bsmFunction = _bsm.getPricingFunction(definition);
        double price = mult * bsmFunction.evaluate(bsmData);
        for (int i = 1; i < 50; i++) {
          sigmaAdjusted = Math.sqrt(sigmaAdjusted * sigmaAdjusted + delta * i / t);
          mult *= lambdaT / i;
          // TODO have to change volatility
          price += mult * bsmFunction.evaluate(bsmData);
        }
        return price;
      }

    };
    return pricingFunction;
  }
}
