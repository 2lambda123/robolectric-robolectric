package org.robolectric.plugins;

import static com.google.common.base.StandardSystemProperty.OS_ARCH;
import static com.google.common.base.StandardSystemProperty.OS_NAME;

import com.google.auto.service.AutoService;
import java.util.Locale;
import java.util.Properties;
import org.robolectric.annotation.ConscryptMode;
import org.robolectric.annotation.ConscryptMode.Mode;
import org.robolectric.pluginapi.config.Configurer;
import org.robolectric.plugins.config.SingleValueConfigurer;

/** Provides configuration to Robolectric for its @{@link ConscryptMode} annotation. */
@AutoService(Configurer.class)
public class ConscryptModeConfigurer
    extends SingleValueConfigurer<ConscryptMode, ConscryptMode.Mode> {

  public ConscryptModeConfigurer(
      Properties systemProperties, PackagePropertiesLoader propertyFileLoader) {
    super(
        ConscryptMode.class,
        ConscryptMode.Mode.class,
        defaultValue(systemProperties),
        propertyFileLoader,
        systemProperties);
  }

  private static ConscryptMode.Mode defaultValue(Properties properties) {
    String os = properties.getProperty(OS_NAME.key(), "").toLowerCase(Locale.US);
    String arch = properties.getProperty(OS_ARCH.key(), "").toLowerCase(Locale.US);
    if (os.contains("mac") && "aarch64".equals(arch)) {
      return Mode.OFF;
    }
    return Mode.ON;
  }
}
