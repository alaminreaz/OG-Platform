/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.marketdatasnapshot.SnapshotDataBundle;
import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.CompiledFunctionDefinition;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.marketdata.ExternalIdBundleResolver;
import com.opengamma.engine.target.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.OpenGammaCompilationContext;
import com.opengamma.financial.analytics.ircurve.strips.CurveNodeWithIdentifier;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.async.AsynchronousExecution;

/**
 * For a given curve name, returns a {@link SnapshotDataBundle} containing the market data for the nodes
 * of that curve. This function does not require that any or all of the market data is available for
 * it to return the snapshot.
 */
public class CurveMarketDataFunction extends AbstractFunction {
  /** The curve name */
  private final String _curveName;

  /**
   * @param curveName The curve name, not null
   */
  public CurveMarketDataFunction(final String curveName) {
    ArgumentChecker.notNull(curveName, "curve name");
    _curveName = curveName;
  }

  @Override
  public CompiledFunctionDefinition compile(final FunctionCompilationContext context, final Instant atInstant) {
    final ZonedDateTime atZDT = ZonedDateTime.ofInstant(atInstant, ZoneOffset.UTC);
    final ValueProperties properties = createValueProperties()
        .with(ValuePropertyNames.CURVE, _curveName)
        .get();
    final ValueSpecification spec = new ValueSpecification(ValueRequirementNames.CURVE_MARKET_DATA, ComputationTargetSpecification.NULL, properties);
    final ConfigSource configSource = OpenGammaCompilationContext.getConfigSource(context);
    final CurveSpecification specification = CurveUtils.getCurveSpecification(atInstant, configSource, atZDT.toLocalDate(), _curveName);
    return new AbstractInvokingCompiledFunction(atZDT.with(LocalTime.MIDNIGHT), atZDT.plusDays(1).with(LocalTime.MIDNIGHT).minusNanos(1000000)) {

      @Override
      public Set<ComputedValue> execute(final FunctionExecutionContext executionContext, final FunctionInputs inputs, final ComputationTarget target,
          final Set<ValueRequirement> desiredValues) throws AsynchronousExecution {
        final SnapshotDataBundle marketData = new SnapshotDataBundle();
        final ExternalIdBundleResolver resolver = new ExternalIdBundleResolver(context.getComputationTargetResolver());
        for (final ComputedValue value : inputs.getAllValues()) {
          final ExternalIdBundle identifiers = value.getSpecification().getTargetSpecification().accept(resolver);
          final double data = (Double) value.getValue();
          marketData.setDataPoint(identifiers, data);
        }
        return Collections.singleton(new ComputedValue(spec, marketData));
      }

      @Override
      public ComputationTargetType getTargetType() {
        return ComputationTargetType.NULL;
      }

      @Override
      public Set<ValueSpecification> getResults(final FunctionCompilationContext compilationContext, final ComputationTarget target) {
        return Collections.singleton(spec);
      }

      @Override
      public Set<ValueRequirement> getRequirements(final FunctionCompilationContext compilationContext, final ComputationTarget target, final ValueRequirement desiredValue) {
        final Set<ValueRequirement> requirements = new HashSet<>();
        for (final CurveNodeWithIdentifier id : specification.getNodes()) {
          if (id.getDataField() != null) {
            requirements.add(new ValueRequirement(id.getDataField(), ComputationTargetType.PRIMITIVE, id.getIdentifier()));
          } else {
            requirements.add(new ValueRequirement(MarketDataRequirementNames.MARKET_VALUE, ComputationTargetType.PRIMITIVE, id.getIdentifier()));
          }
        }
        return requirements;
      }

      @Override
      public boolean canHandleMissingRequirements() {
        return true;
      }

      @Override
      public boolean canHandleMissingInputs() {
        return true;
      }
    };
  }
}
