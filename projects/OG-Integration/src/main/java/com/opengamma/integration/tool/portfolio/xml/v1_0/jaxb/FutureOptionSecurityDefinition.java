/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.tool.portfolio.xml.v1_0.jaxb;

import java.math.BigDecimal;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.YearMonth;

import com.opengamma.financial.security.option.OptionType;
import com.opengamma.integration.tool.portfolio.xml.v1_0.conversion.ListedFutureOptionSecurityExtractor;
import com.opengamma.integration.tool.portfolio.xml.v1_0.conversion.ListedSecurityExtractor;

@BeanDefinition
@XmlRootElement(name = "futureOptionSecurity")
public class FutureOptionSecurityDefinition extends ListedSecurityDefinition {

  public enum ListedFutureOptionType {
    @XmlEnumValue(value = "equityIndexFutureOption")
    EQUITY_INDEX_FUTURE_OPTION,
    @XmlEnumValue(value = "equityDividendFutureOption")
    EQUITY_DIVIDEND_FUTURE_OPTION
  }

  @XmlAttribute(name = "type", required = true)
  @PropertyDefinition
  private ListedFutureOptionType _listedFutureOptionType;

  @XmlElement(name = "optionType", required = true)
  @PropertyDefinition
  private OptionType _optionType;

  @XmlElement(name = "strike", required = true)
  @PropertyDefinition
  private BigDecimal _strike;

  @XmlElement(name = "futureExpiry", required = true)
  @PropertyDefinition
  private YearMonth _futureExpiry;

  /**
   * At some point we may want to allow mid curve future options where
   * option expiry is significantly earlier than the underlying future
   */
  @XmlElement(name = "optionExpiry")
  @PropertyDefinition
  private YearMonth _optionExpiry;

  @XmlElement(name = "exerciseType", required = true)
  @PropertyDefinition
  private ExerciseType _exerciseType;

  @XmlElement(name = "margined", required = true)
  @PropertyDefinition
  private boolean _isMargined;

  public ListedSecurityExtractor getSecurityExtractor() {
    return new ListedFutureOptionSecurityExtractor(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FutureOptionSecurityDefinition}.
   * @return the meta-bean, not null
   */
  public static FutureOptionSecurityDefinition.Meta meta() {
    return FutureOptionSecurityDefinition.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(FutureOptionSecurityDefinition.Meta.INSTANCE);
  }

  @Override
  public FutureOptionSecurityDefinition.Meta metaBean() {
    return FutureOptionSecurityDefinition.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1200280399:  // listedFutureOptionType
        return getListedFutureOptionType();
      case 1373587791:  // optionType
        return getOptionType();
      case -891985998:  // strike
        return getStrike();
      case 797235414:  // futureExpiry
        return getFutureExpiry();
      case 1032553992:  // optionExpiry
        return getOptionExpiry();
      case -466331342:  // exerciseType
        return getExerciseType();
      case -549878249:  // isMargined
        return isIsMargined();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1200280399:  // listedFutureOptionType
        setListedFutureOptionType((ListedFutureOptionType) newValue);
        return;
      case 1373587791:  // optionType
        setOptionType((OptionType) newValue);
        return;
      case -891985998:  // strike
        setStrike((BigDecimal) newValue);
        return;
      case 797235414:  // futureExpiry
        setFutureExpiry((YearMonth) newValue);
        return;
      case 1032553992:  // optionExpiry
        setOptionExpiry((YearMonth) newValue);
        return;
      case -466331342:  // exerciseType
        setExerciseType((ExerciseType) newValue);
        return;
      case -549878249:  // isMargined
        setIsMargined((Boolean) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FutureOptionSecurityDefinition other = (FutureOptionSecurityDefinition) obj;
      return JodaBeanUtils.equal(getListedFutureOptionType(), other.getListedFutureOptionType()) &&
          JodaBeanUtils.equal(getOptionType(), other.getOptionType()) &&
          JodaBeanUtils.equal(getStrike(), other.getStrike()) &&
          JodaBeanUtils.equal(getFutureExpiry(), other.getFutureExpiry()) &&
          JodaBeanUtils.equal(getOptionExpiry(), other.getOptionExpiry()) &&
          JodaBeanUtils.equal(getExerciseType(), other.getExerciseType()) &&
          JodaBeanUtils.equal(isIsMargined(), other.isIsMargined()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getListedFutureOptionType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getOptionType());
    hash += hash * 31 + JodaBeanUtils.hashCode(getStrike());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFutureExpiry());
    hash += hash * 31 + JodaBeanUtils.hashCode(getOptionExpiry());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExerciseType());
    hash += hash * 31 + JodaBeanUtils.hashCode(isIsMargined());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the listedFutureOptionType.
   * @return the value of the property
   */
  public ListedFutureOptionType getListedFutureOptionType() {
    return _listedFutureOptionType;
  }

  /**
   * Sets the listedFutureOptionType.
   * @param listedFutureOptionType  the new value of the property
   */
  public void setListedFutureOptionType(ListedFutureOptionType listedFutureOptionType) {
    this._listedFutureOptionType = listedFutureOptionType;
  }

  /**
   * Gets the the {@code listedFutureOptionType} property.
   * @return the property, not null
   */
  public final Property<ListedFutureOptionType> listedFutureOptionType() {
    return metaBean().listedFutureOptionType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the optionType.
   * @return the value of the property
   */
  public OptionType getOptionType() {
    return _optionType;
  }

  /**
   * Sets the optionType.
   * @param optionType  the new value of the property
   */
  public void setOptionType(OptionType optionType) {
    this._optionType = optionType;
  }

  /**
   * Gets the the {@code optionType} property.
   * @return the property, not null
   */
  public final Property<OptionType> optionType() {
    return metaBean().optionType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the strike.
   * @return the value of the property
   */
  public BigDecimal getStrike() {
    return _strike;
  }

  /**
   * Sets the strike.
   * @param strike  the new value of the property
   */
  public void setStrike(BigDecimal strike) {
    this._strike = strike;
  }

  /**
   * Gets the the {@code strike} property.
   * @return the property, not null
   */
  public final Property<BigDecimal> strike() {
    return metaBean().strike().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the futureExpiry.
   * @return the value of the property
   */
  public YearMonth getFutureExpiry() {
    return _futureExpiry;
  }

  /**
   * Sets the futureExpiry.
   * @param futureExpiry  the new value of the property
   */
  public void setFutureExpiry(YearMonth futureExpiry) {
    this._futureExpiry = futureExpiry;
  }

  /**
   * Gets the the {@code futureExpiry} property.
   * @return the property, not null
   */
  public final Property<YearMonth> futureExpiry() {
    return metaBean().futureExpiry().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the optionExpiry.
   * @return the value of the property
   */
  public YearMonth getOptionExpiry() {
    return _optionExpiry;
  }

  /**
   * Sets the optionExpiry.
   * @param optionExpiry  the new value of the property
   */
  public void setOptionExpiry(YearMonth optionExpiry) {
    this._optionExpiry = optionExpiry;
  }

  /**
   * Gets the the {@code optionExpiry} property.
   * @return the property, not null
   */
  public final Property<YearMonth> optionExpiry() {
    return metaBean().optionExpiry().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the exerciseType.
   * @return the value of the property
   */
  public ExerciseType getExerciseType() {
    return _exerciseType;
  }

  /**
   * Sets the exerciseType.
   * @param exerciseType  the new value of the property
   */
  public void setExerciseType(ExerciseType exerciseType) {
    this._exerciseType = exerciseType;
  }

  /**
   * Gets the the {@code exerciseType} property.
   * @return the property, not null
   */
  public final Property<ExerciseType> exerciseType() {
    return metaBean().exerciseType().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the isMargined.
   * @return the value of the property
   */
  public boolean isIsMargined() {
    return _isMargined;
  }

  /**
   * Sets the isMargined.
   * @param isMargined  the new value of the property
   */
  public void setIsMargined(boolean isMargined) {
    this._isMargined = isMargined;
  }

  /**
   * Gets the the {@code isMargined} property.
   * @return the property, not null
   */
  public final Property<Boolean> isMargined() {
    return metaBean().isMargined().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FutureOptionSecurityDefinition}.
   */
  public static class Meta extends ListedSecurityDefinition.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code listedFutureOptionType} property.
     */
    private final MetaProperty<ListedFutureOptionType> _listedFutureOptionType = DirectMetaProperty.ofReadWrite(
        this, "listedFutureOptionType", FutureOptionSecurityDefinition.class, ListedFutureOptionType.class);
    /**
     * The meta-property for the {@code optionType} property.
     */
    private final MetaProperty<OptionType> _optionType = DirectMetaProperty.ofReadWrite(
        this, "optionType", FutureOptionSecurityDefinition.class, OptionType.class);
    /**
     * The meta-property for the {@code strike} property.
     */
    private final MetaProperty<BigDecimal> _strike = DirectMetaProperty.ofReadWrite(
        this, "strike", FutureOptionSecurityDefinition.class, BigDecimal.class);
    /**
     * The meta-property for the {@code futureExpiry} property.
     */
    private final MetaProperty<YearMonth> _futureExpiry = DirectMetaProperty.ofReadWrite(
        this, "futureExpiry", FutureOptionSecurityDefinition.class, YearMonth.class);
    /**
     * The meta-property for the {@code optionExpiry} property.
     */
    private final MetaProperty<YearMonth> _optionExpiry = DirectMetaProperty.ofReadWrite(
        this, "optionExpiry", FutureOptionSecurityDefinition.class, YearMonth.class);
    /**
     * The meta-property for the {@code exerciseType} property.
     */
    private final MetaProperty<ExerciseType> _exerciseType = DirectMetaProperty.ofReadWrite(
        this, "exerciseType", FutureOptionSecurityDefinition.class, ExerciseType.class);
    /**
     * The meta-property for the {@code isMargined} property.
     */
    private final MetaProperty<Boolean> _isMargined = DirectMetaProperty.ofReadWrite(
        this, "isMargined", FutureOptionSecurityDefinition.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "listedFutureOptionType",
        "optionType",
        "strike",
        "futureExpiry",
        "optionExpiry",
        "exerciseType",
        "isMargined");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1200280399:  // listedFutureOptionType
          return _listedFutureOptionType;
        case 1373587791:  // optionType
          return _optionType;
        case -891985998:  // strike
          return _strike;
        case 797235414:  // futureExpiry
          return _futureExpiry;
        case 1032553992:  // optionExpiry
          return _optionExpiry;
        case -466331342:  // exerciseType
          return _exerciseType;
        case -549878249:  // isMargined
          return _isMargined;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends FutureOptionSecurityDefinition> builder() {
      return new DirectBeanBuilder<FutureOptionSecurityDefinition>(new FutureOptionSecurityDefinition());
    }

    @Override
    public Class<? extends FutureOptionSecurityDefinition> beanType() {
      return FutureOptionSecurityDefinition.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code listedFutureOptionType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ListedFutureOptionType> listedFutureOptionType() {
      return _listedFutureOptionType;
    }

    /**
     * The meta-property for the {@code optionType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<OptionType> optionType() {
      return _optionType;
    }

    /**
     * The meta-property for the {@code strike} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<BigDecimal> strike() {
      return _strike;
    }

    /**
     * The meta-property for the {@code futureExpiry} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<YearMonth> futureExpiry() {
      return _futureExpiry;
    }

    /**
     * The meta-property for the {@code optionExpiry} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<YearMonth> optionExpiry() {
      return _optionExpiry;
    }

    /**
     * The meta-property for the {@code exerciseType} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExerciseType> exerciseType() {
      return _exerciseType;
    }

    /**
     * The meta-property for the {@code isMargined} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> isMargined() {
      return _isMargined;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
