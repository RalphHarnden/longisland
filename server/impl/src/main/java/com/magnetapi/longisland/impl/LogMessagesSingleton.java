package com.magnetapi.longisland.impl;

import com.magnet.i18n.PlatformLogMessages;
import com.magnet.i18n.PlatformResources;

public class LogMessagesSingleton {

  private static class SingletonHolder {
    private static final LogMessages LOG = PlatformLogMessages.get(LogMessages.class);
    private static final LogMessages RES = PlatformResources.get(LogMessages.class);
  }

  public static LogMessages get() {
    return SingletonHolder.LOG;
  }

  public static LogMessages getResources() {
    return SingletonHolder.RES;
  }
		  
}
