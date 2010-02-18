/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.engine.view.calcnode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.fudgemsg.FudgeField;
import org.fudgemsg.FudgeFieldContainer;
import org.fudgemsg.MutableFudgeFieldContainer;
import org.fudgemsg.mapping.FudgeDeserializationContext;
import org.fudgemsg.mapping.FudgeSerializationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.value.ValueSpecification;

/**
 * The definition of a particular job that must be performed by
 * a Calculation Node.
 *
 * @author kirk
 */
public class CalculationJob implements Serializable {
  public static final String FUNCTION_UNIQUE_ID_FIELD_NAME = "functionUniqueIdentifier";
  public static final String INPUT_FIELD_NAME = "valueInput";
  
  @SuppressWarnings("unused")
  private static final Logger s_logger = LoggerFactory.getLogger(CalculationJob.class);
  
  private final CalculationJobSpecification _specification;
  private final String _functionUniqueIdentifier;
  private final ComputationTargetSpecification _computationTargetSpecification;
  private final Set<ValueSpecification> _inputs = new HashSet<ValueSpecification>();
  
  /**
   * @param viewName
   * @param iterationTimestamp
   * @param jobId
   */
  public CalculationJob(String viewName, long iterationTimestamp, long jobId,
      String functionUniqueIdentifier,
      ComputationTargetSpecification computationTargetSpecification,
      Collection<ValueSpecification> inputs) {
    this(new CalculationJobSpecification(viewName, iterationTimestamp, jobId),
        functionUniqueIdentifier, computationTargetSpecification, inputs);
  }
  
  protected CalculationJob(
      CalculationJobSpecification specification,
      String functionUniqueIdentifier,
      ComputationTargetSpecification computationTargetSpecification,
      Collection<ValueSpecification> inputs) {
    // TODO kirk 2009-09-29 -- Check Inputs.
    _specification = specification;
    _functionUniqueIdentifier = functionUniqueIdentifier;
    _computationTargetSpecification = computationTargetSpecification;
    _inputs.addAll(inputs);
  }

  /**
   * @return the functionUniqueIdentifier
   */
  public String getFunctionUniqueIdentifier() {
    return _functionUniqueIdentifier;
  }

  /**
   * @return the inputs
   */
  public Set<ValueSpecification> getInputs() {
    return _inputs;
  }

  /**
   * @return the specification
   */
  public CalculationJobSpecification getSpecification() {
    return _specification;
  }
  
  /**
   * @return the computationTargetSpecification
   */
  public ComputationTargetSpecification getComputationTargetSpecification() {
    return _computationTargetSpecification;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
  
  //public FudgeFieldContainer toFudgeMsg(FudgeMessageFactory fudgeMessageFactory) {
  public FudgeFieldContainer toFudgeMsg(FudgeSerializationContext fudgeContext) {
    MutableFudgeFieldContainer msg = fudgeContext.newMessage();
    getSpecification().writeFields(msg);
    getComputationTargetSpecification().toFudgeMsg(fudgeContext, msg);
    msg.add(FUNCTION_UNIQUE_ID_FIELD_NAME, getFunctionUniqueIdentifier());
    
    for(ValueSpecification inputSpecification : getInputs()) {
      msg.add(INPUT_FIELD_NAME, inputSpecification.toFudgeMsg(fudgeContext));
    }
    
    return msg;
  }

  //public static CalculationJob fromFudgeMsg(FudgeMsgEnvelope envelope) {
  public static CalculationJob fromFudgeMsg (FudgeDeserializationContext fudgeContext, FudgeFieldContainer msg) {
    String viewName = msg.getString(CalculationJobSpecification.VIEW_NAME_FIELD_NAME);
    long iterationTimestamp = msg.getLong(CalculationJobSpecification.ITERATION_TIMESTAMP_FIELD_NAME);
    long jobId = msg.getLong(CalculationJobSpecification.JOB_ID_FIELD_NAME);
    String functionUniqueId = msg.getString(FUNCTION_UNIQUE_ID_FIELD_NAME);
    
    ComputationTargetSpecification computationTargetSpecification = ComputationTargetSpecification.fromFudgeMsg(msg);
    
    List<ValueSpecification> inputs = new ArrayList<ValueSpecification>();
    for(FudgeField field : msg.getAllByName(INPUT_FIELD_NAME)) {
      ValueSpecification inputSpecification = ValueSpecification.fromFudgeMsg(fudgeContext, (FudgeFieldContainer)field.getValue());
      inputs.add(inputSpecification);
    }
    
    return new CalculationJob(viewName, iterationTimestamp, jobId, functionUniqueId, computationTargetSpecification, inputs);
  }
}
