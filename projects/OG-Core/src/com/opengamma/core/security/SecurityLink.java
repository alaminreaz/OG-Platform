/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.core.security;

import com.opengamma.DataNotFoundException;
import com.opengamma.core.Link;
import com.opengamma.id.VersionCorrection;
import com.opengamma.util.PublicAPI;

/**
 * A flexible link between an object and a security.
 * <p>
 * The security link represents a connection from an entity to a security.
 * The connection can be held by an {@code ObjectId} or an {@code ExternalIdBundle}.
 * The link also holds a resolved reference to the security itself.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicAPI
public interface SecurityLink extends Link<Security> {

  /**
   * Resolves the security for the latest version-correction using a security source.
   * 
   * @param source  the source to use to resolve, not null
   * @return the resolved security, not null
   * @throws DataNotFoundException if the security could not be resolved
   * @throws RuntimeException if an error occurs while resolving
   */
  Security resolve(SecuritySource source);

  /**
   * Resolves the security using a security source.
   * 
   * @param source  the source to use to resolve, not null
   * @param versionCorrection  the version-correction, not null
   * @return the resolved security, not null
   * @throws DataNotFoundException if the security could not be resolved
   * @throws RuntimeException if an error occurs while resolving
   */
  Security resolve(SecuritySource source, VersionCorrection versionCorrection);

  /**
   * Resolves the security using a security source,
   * logging any exception and returning null.
   * 
   * @param source  the source to use to resolve, not null
   * @return the resolved security, null if unable to resolve
   */
  Security resolveQuiet(SecuritySource source);

}
