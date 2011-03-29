// Automatically created - do not modify
///CLOVER:OFF
// CSOFF: Generated File
package com.opengamma.financial.security.option;
public class GapPayoffStyle extends com.opengamma.financial.security.option.PayoffStyle implements java.io.Serializable {
  public <T> T accept (PayoffStyleVisitor<T> visitor) { return visitor.visitGapPayoffStyle(this); }
  private static final long serialVersionUID = -25713078708l;
  private final double _payment;
  public static final String PAYMENT_KEY = "payment";
  public GapPayoffStyle (double payment) {
    _payment = payment;
  }
  protected GapPayoffStyle (final org.fudgemsg.FudgeFieldContainer fudgeMsg) {
    super (fudgeMsg);
    org.fudgemsg.FudgeField fudgeField;
    fudgeField = fudgeMsg.getByName (PAYMENT_KEY);
    if (fudgeField == null) throw new IllegalArgumentException ("Fudge message is not a GapPayoffStyle - field 'payment' is not present");
    try {
      _payment = fudgeMsg.getFieldValue (Double.class, fudgeField);
    }
    catch (IllegalArgumentException e) {
      throw new IllegalArgumentException ("Fudge message is not a GapPayoffStyle - field 'payment' is not double", e);
    }
  }
  protected GapPayoffStyle (final GapPayoffStyle source) {
    super (source);
    if (source == null) throw new NullPointerException ("'source' must not be null");
    _payment = source._payment;
  }
  public org.fudgemsg.FudgeFieldContainer toFudgeMsg (final org.fudgemsg.FudgeMessageFactory fudgeContext) {
    if (fudgeContext == null) throw new NullPointerException ("fudgeContext must not be null");
    final org.fudgemsg.MutableFudgeFieldContainer msg = fudgeContext.newMessage ();
    toFudgeMsg (fudgeContext, msg);
    return msg;
  }
  public void toFudgeMsg (final org.fudgemsg.FudgeMessageFactory fudgeContext, final org.fudgemsg.MutableFudgeFieldContainer msg) {
    super.toFudgeMsg (fudgeContext, msg);
    msg.add (PAYMENT_KEY, null, _payment);
  }
  public static GapPayoffStyle fromFudgeMsg (final org.fudgemsg.FudgeFieldContainer fudgeMsg) {
    final java.util.List<org.fudgemsg.FudgeField> types = fudgeMsg.getAllByOrdinal (0);
    for (org.fudgemsg.FudgeField field : types) {
      final String className = (String)field.getValue ();
      if ("com.opengamma.financial.security.option.GapPayoffStyle".equals (className)) break;
      try {
        return (com.opengamma.financial.security.option.GapPayoffStyle)Class.forName (className).getDeclaredMethod ("fromFudgeMsg", org.fudgemsg.FudgeFieldContainer.class).invoke (null, fudgeMsg);
      }
      catch (Throwable t) {
        // no-action
      }
    }
    return new GapPayoffStyle (fudgeMsg);
  }
  public double getPayment () {
    return _payment;
  }
  public boolean equals (final Object o) {
    if (o == this) return true;
    if (!(o instanceof GapPayoffStyle)) return false;
    GapPayoffStyle msg = (GapPayoffStyle)o;
    if (_payment != msg._payment) return false;
    return super.equals (msg);
  }
  public int hashCode () {
    int hc = super.hashCode ();
    hc = (hc * 31) + (int)_payment;
    return hc;
  }
  public String toString () {
    return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
///CLOVER:ON
// CSON: Generated File
