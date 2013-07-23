package com.magnetapi.longisland.server;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.magnet.context.api.ContextService;
import com.magnet.core.api.Services;
import com.magnet.core.api.Startup;
import com.magnet.common.api.Reference;
import com.magnet.inject.Optional;
import com.magnet.meta.AppMetaService;
import com.magnet.testutils.PlatformRunner;


@RunWith(PlatformRunner.class)
public class ServerSanityTest {

  @Inject
  private Reference<Services> servicesRef;

  @Inject
  @Optional
  private List<Reference<Startup>> startUpList;

  @Inject
  private ContextService contextService;

  @Inject
  private AppMetaService appMetaService;

  @Test
  public void sanity() {
    assertNotNull("the Services Ref should not be null", servicesRef);
    assertNotNull("the Services shouldnot be null", servicesRef.get());
    assertNotNull("the StartUps Ref should not be null", startUpList);
    assertNotNull("the ContextService should not be null", contextService);
    assertNotNull("the AppMetaService shouldnot be null", appMetaService);
  }

}
