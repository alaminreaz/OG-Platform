/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.marketdata.manipulator.dsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.PropertyDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.marketdata.manipulator.function.StructureManipulator;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.analytics.ircurve.FixedIncomeStripWithSecurity;
import com.opengamma.financial.analytics.ircurve.StripInstrumentType;
import com.opengamma.financial.analytics.ircurve.YieldCurveData;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.util.ArgumentChecker;
import java.util.NoSuchElementException;
import java.util.Set;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.Bean;
import org.joda.beans.impl.direct.DirectMetaProperty;


/**
 * A {@link StructureManipulator} which performs a list of point shifts on {@link YieldCurveData}.
 */
@BeanDefinition
public final class YieldCurveDataPointShiftsManipulator implements StructureManipulator<YieldCurveData>, ImmutableBean {

  private static final Logger s_logger = LoggerFactory.getLogger(YieldCurveDataPointShiftsManipulator.class);

  @PropertyDefinition(validate = "notNull")
  private final ScenarioShiftType _shiftType;

  @PropertyDefinition(validate = "notNull")
  private final List<YieldCurvePointShift> _shifts;

  @ImmutableConstructor
  public YieldCurveDataPointShiftsManipulator(ScenarioShiftType shiftType, List<YieldCurvePointShift> shifts) {
    _shiftType = ArgumentChecker.notNull(shiftType, "shiftType");
    _shifts = ImmutableList.copyOf(ArgumentChecker.notEmpty(shifts, "shiftList"));
  }

  @Override
  public YieldCurveData execute(YieldCurveData curveData,
                                ValueSpecification valueSpecification,
                                FunctionExecutionContext executionContext) {
    ZonedDateTime valuationTime = ZonedDateTime.now(executionContext.getValuationClock());
    Map<ExternalIdBundle, Double> data = Maps.newHashMap(curveData.getDataPoints());
    Map<ExternalId, ExternalIdBundle> index = curveData.getIndex();
    for (YieldCurvePointShift shift : _shifts) {
      for (FixedIncomeStripWithSecurity strip : curveData.getCurveSpecification().getStrips()) {
        Period stripPeriod = strip.getTenor().getPeriod();
        Period shiftPeriod = shift.getTenor();
        ZonedDateTime stripTime = valuationTime.plus(stripPeriod);
        ZonedDateTime shiftStartTime = valuationTime.plus(shiftPeriod);

        if (stripTime.compareTo(shiftStartTime) == 0) {
          ExternalIdBundle bundle = index.get(strip.getSecurityIdentifier());
          boolean future = (strip.getInstrumentType() == StripInstrumentType.FUTURE);
          Double originalData = data.get(bundle);
          Double stripData;

          // futures are quoted the other way round from other instruments
          if (future) {
            stripData = 1 - originalData;
          } else {
            stripData = originalData;
          }
          Double shiftedData;

          if (_shiftType == ScenarioShiftType.RELATIVE) {
            // add shift amount to 1. i.e. 10.pc actualy means 'value * 1.1' and -10.pc means 'value * 0.9'
            shiftedData = stripData * (shift.getShift() + 1);
          } else {
            shiftedData = stripData + shift.getShift();
          }
          Double shiftedStripData;

          if (future) {
            shiftedStripData = 1 - shiftedData;
          } else {
            shiftedStripData = shiftedData;
          }
          data.put(bundle, shiftedStripData);
          s_logger.debug("Shifting data {}, tenor {} by {} from {} to {}",
                         strip.getSecurityIdentifier(), strip.getTenor(), shift.getShift(), originalData, shiftedStripData);
        }
      }
    }
    return new YieldCurveData(curveData.getCurveSpecification(), data);
  }

  @Override
  public Class<YieldCurveData> getExpectedType() {
    return YieldCurveData.class;
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code YieldCurveDataPointShiftsManipulator}.
   * @return the meta-bean, not null
   */
  public static YieldCurveDataPointShiftsManipulator.Meta meta() {
    return YieldCurveDataPointShiftsManipulator.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(YieldCurveDataPointShiftsManipulator.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static YieldCurveDataPointShiftsManipulator.Builder builder() {
    return new YieldCurveDataPointShiftsManipulator.Builder();
  }

  @Override
  public YieldCurveDataPointShiftsManipulator.Meta metaBean() {
    return YieldCurveDataPointShiftsManipulator.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the shiftType.
   * @return the value of the property, not null
   */
  public ScenarioShiftType getShiftType() {
    return _shiftType;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the shifts.
   * @return the value of the property, not null
   */
  public List<YieldCurvePointShift> getShifts() {
    return _shifts;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public YieldCurveDataPointShiftsManipulator clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      YieldCurveDataPointShiftsManipulator other = (YieldCurveDataPointShiftsManipulator) obj;
      return JodaBeanUtils.equal(getShiftType(), other.getShiftType()) &&
          JodaBeanUtils.equal(getShifts(), other.getShifts());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getShiftType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getShifts());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("YieldCurveDataPointShiftsManipulator{");
    buf.append("shiftType").append('=').append(getShiftType()).append(',').append(' ');
    buf.append("shifts").append('=').append(JodaBeanUtils.toString(getShifts()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code YieldCurveDataPointShiftsManipulator}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code shiftType} property.
     */
    private final MetaProperty<ScenarioShiftType> _shiftType = DirectMetaProperty.ofImmutable(
        this, "shiftType", YieldCurveDataPointShiftsManipulator.class, ScenarioShiftType.class);
    /**
     * The meta-property for the {@code shifts} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<YieldCurvePointShift>> _shifts = DirectMetaProperty.ofImmutable(
        this, "shifts", YieldCurveDataPointShiftsManipulator.class, (Class) List.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "shiftType",
        "shifts");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return _shiftType;
        case -903338959:  // shifts
          return _shifts;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public YieldCurveDataPointShiftsManipulator.Builder builder() {
      return new YieldCurveDataPointShiftsManipulator.Builder();
    }

    @Override
    public Class<? extends YieldCurveDataPointShiftsManipulator> beanType() {
      return YieldCurveDataPointShiftsManipulator.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code shiftType} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ScenarioShiftType> shiftType() {
      return _shiftType;
    }

    /**
     * The meta-property for the {@code shifts} property.
     * @return the meta-property, not null
     */
    public MetaProperty<List<YieldCurvePointShift>> shifts() {
      return _shifts;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          return ((YieldCurveDataPointShiftsManipulator) bean).getShiftType();
        case -903338959:  // shifts
          return ((YieldCurveDataPointShiftsManipulator) bean).getShifts();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code YieldCurveDataPointShiftsManipulator}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<YieldCurveDataPointShiftsManipulator> {

    private ScenarioShiftType _shiftType;
    private List<YieldCurvePointShift> _shifts = new ArrayList<YieldCurvePointShift>();

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(YieldCurveDataPointShiftsManipulator beanToCopy) {
      this._shiftType = beanToCopy.getShiftType();
      this._shifts = new ArrayList<YieldCurvePointShift>(beanToCopy.getShifts());
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 893345500:  // shiftType
          this._shiftType = (ScenarioShiftType) newValue;
          break;
        case -903338959:  // shifts
          this._shifts = (List<YieldCurvePointShift>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public YieldCurveDataPointShiftsManipulator build() {
      return new YieldCurveDataPointShiftsManipulator(
          _shiftType,
          _shifts);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code shiftType} property in the builder.
     * @param shiftType  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder shiftType(ScenarioShiftType shiftType) {
      JodaBeanUtils.notNull(shiftType, "shiftType");
      this._shiftType = shiftType;
      return this;
    }

    /**
     * Sets the {@code shifts} property in the builder.
     * @param shifts  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder shifts(List<YieldCurvePointShift> shifts) {
      JodaBeanUtils.notNull(shifts, "shifts");
      this._shifts = shifts;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("YieldCurveDataPointShiftsManipulator.Builder{");
      buf.append("shiftType").append('=').append(JodaBeanUtils.toString(_shiftType)).append(',').append(' ');
      buf.append("shifts").append('=').append(JodaBeanUtils.toString(_shifts));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
