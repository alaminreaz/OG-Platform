/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.legalentity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.legalentity.Account;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.master.legalentity.ManageableLegalEntity;
import com.opengamma.util.money.Currency;

/**
 * Mock legalEntity.
 */
@BeanDefinition
public class MockLegalEntity extends ManageableLegalEntity {

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The currency.
   */
  @PropertyDefinition(validate = "notNull")
  private Currency _currency;

  /**
   * Creates an instance.
   */
  protected MockLegalEntity() {
  }

  /**
   * Creates an instance.
   *
   * @param name     the name, not null
   * @param bundle   the bundle, not null
   * @param currency the currency, not null
   */
  public MockLegalEntity(String name, ExternalIdBundle bundle, Currency currency) {
    super(name, bundle);
    setCurrency(currency);
  }

  @Override
  public Collection<Account> getAccounts() {
    Account account = new Account();
    return Collections.singleton(account);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code MockLegalEntity}.
   * @return the meta-bean, not null
   */
  public static MockLegalEntity.Meta meta() {
    return MockLegalEntity.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(MockLegalEntity.Meta.INSTANCE);
  }

  @Override
  public MockLegalEntity.Meta metaBean() {
    return MockLegalEntity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  /**
   * Sets the currency.
   * @param currency  the new value of the property, not null
   */
  public void setCurrency(Currency currency) {
    JodaBeanUtils.notNull(currency, "currency");
    this._currency = currency;
  }

  /**
   * Gets the the {@code currency} property.
   * @return the property, not null
   */
  public final Property<Currency> currency() {
    return metaBean().currency().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public MockLegalEntity clone() {
    return (MockLegalEntity) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      MockLegalEntity other = (MockLegalEntity) obj;
      return JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("MockLegalEntity{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("currency").append('=').append(JodaBeanUtils.toString(getCurrency())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code MockLegalEntity}.
   */
  public static class Meta extends ManageableLegalEntity.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofReadWrite(
        this, "currency", MockLegalEntity.class, Currency.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "currency");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return _currency;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends MockLegalEntity> builder() {
      return new DirectBeanBuilder<MockLegalEntity>(new MockLegalEntity());
    }

    @Override
    public Class<? extends MockLegalEntity> beanType() {
      return MockLegalEntity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return ((MockLegalEntity) bean).getCurrency();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          ((MockLegalEntity) bean).setCurrency((Currency) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((MockLegalEntity) bean)._currency, "currency");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
