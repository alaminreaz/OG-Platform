/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.component.factory.master;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.master.portfolio.PortfolioMaster;
import com.opengamma.master.portfolio.impl.RemotePortfolioMaster;
import com.opengamma.masterdb.portfolio.DataDbPortfolioMasterResource;
import com.opengamma.masterdb.portfolio.DbPortfolioMaster;
import com.opengamma.util.rest.AbstractDataResource;

/**
 * Component factory for the database portfolio master.
 */
@BeanDefinition
public class DbPortfolioMasterComponentFactory extends AbstractDocumentDbMasterComponentFactory<DbPortfolioMaster> {

  
  public DbPortfolioMasterComponentFactory() {
    super("prt", PortfolioMaster.class, RemotePortfolioMaster.class);
  }
  

  @Override
  protected DbPortfolioMaster createDbDocumentMaster() {
    return new DbPortfolioMaster(getDbConnector());
  }
  
  @Override
  protected AbstractDataResource createPublishedResource(DbPortfolioMaster dbMaster, Object postProcessedMaster) {
    //note - the db instance is required for this resource
    return new DataDbPortfolioMasterResource(dbMaster);
  }
      

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code DbPortfolioMasterComponentFactory}.
   * @return the meta-bean, not null
   */
  public static DbPortfolioMasterComponentFactory.Meta meta() {
    return DbPortfolioMasterComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(DbPortfolioMasterComponentFactory.Meta.INSTANCE);
  }

  @Override
  public DbPortfolioMasterComponentFactory.Meta metaBean() {
    return DbPortfolioMasterComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  @Override
  public DbPortfolioMasterComponentFactory clone() {
    return (DbPortfolioMasterComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("DbPortfolioMasterComponentFactory{");
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
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DbPortfolioMasterComponentFactory}.
   */
  public static class Meta extends AbstractDocumentDbMasterComponentFactory.Meta<DbPortfolioMaster> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends DbPortfolioMasterComponentFactory> builder() {
      return new DirectBeanBuilder<DbPortfolioMasterComponentFactory>(new DbPortfolioMasterComponentFactory());
    }

    @Override
    public Class<? extends DbPortfolioMasterComponentFactory> beanType() {
      return DbPortfolioMasterComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
