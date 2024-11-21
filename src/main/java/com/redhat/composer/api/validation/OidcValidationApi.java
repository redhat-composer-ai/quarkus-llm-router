package com.redhat.composer.api.validation;

import java.util.Set;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

/**
 * OIDCValidationAPI.
 */
@Path("/auth")
@Authenticated
public class OidcValidationApi {

  @Inject
  SecurityIdentity securityIdentity;


  @GET
  @Path("principal")
  public String getPrincipal() {
    return securityIdentity.getPrincipal().getName();
  }


  @GET
  @Path("roles")
  public Set<String> getRoles() {
    return securityIdentity.getRoles();
  }

}
