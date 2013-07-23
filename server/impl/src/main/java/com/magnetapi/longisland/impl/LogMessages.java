package com.magnetapi.longisland.impl;

import static com.magnet.i18n.Level.FINE;
import static com.magnet.i18n.Level.FINER;
import static com.magnet.i18n.Level.INFO;
import static com.magnet.i18n.Level.SEVERE;
import static com.magnet.i18n.Level.WARNING;

import com.magnet.i18n.Code;
import com.magnet.i18n.LogBundle;
import com.magnet.i18n.Message;
import com.magnet.i18n.Resource;
import com.magnet.i18n.Severity;
import com.magnet.i18n.Thrown;

@SuppressWarnings("unused")
@LogBundle
public interface LogMessages {

  @Code(1)
  @Severity(SEVERE)
  @Thrown
  @Message("Fatal error: {0}")
  void fatalError(Exception e);

  @Code(2)
  @Severity(WARNING)
  @Thrown
  @Message("Error: {0}")
  void error(Exception e);

  @Code(3)
  @Severity(WARNING)
  @Thrown
  @Message("Warning: {0}")
  void warning(Exception e);

  @Code(5)
  @Severity(INFO)
  @Thrown
  @Message("Info: {0}")
  void info(Exception e);

  @Code(6)
  @Severity(FINE)
  @Thrown
  @Message("Fine: {0}")
  void fine(Exception e);

  @Code(7)
  @Severity(FINE)
  @Message("Swallowing: {0}")
  void swallowing(Throwable t);

  @Resource("Location not specified")
  String locationNotSpecified();

  @Resource("Address not specified")
  String addressNotSpecified();

  @Resource("Invalid category index: {0}")
  String invalidCategoryIndex(int index);

  @Resource("Search query not specified")
  String searchQueryNotSpecified();
	  
}
