/*
 * Portions of this code came from frameworks/base/core/java/android/view/ViewConfiguration.java,
 * which contains the following license text:
 *
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.robolectric.shadows;

import static org.robolectric.util.reflector.Reflector.reflector;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewConfiguration;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.util.reflector.Accessor;
import org.robolectric.util.reflector.ForType;

@SuppressWarnings({"UnusedDeclaration"})
@Implements(ViewConfiguration.class)
public class ShadowViewConfiguration {

  private static final int SCROLL_BAR_SIZE = 10;
  private static final int PRESSED_STATE_DURATION = 125;
  private static final int LONG_PRESS_TIMEOUT = 500;
  private static final int TAP_TIMEOUT = 115;
  private static final int DOUBLE_TAP_TIMEOUT = 300;
  private static final int TOUCH_SLOP = 16;
  private static final int PAGING_TOUCH_SLOP = TOUCH_SLOP * 2;
  private static final int DOUBLE_TAP_SLOP = 100;
  private static final int WINDOW_TOUCH_SLOP = 16;
  private static final int MAXIMUM_FLING_VELOCITY = 4000;
  private static final int MAXIMUM_DRAWING_CACHE_SIZE = 320 * 480 * 4;

  private int edgeSlop;
  private int fadingEdgeLength;
  private int minimumFlingVelocity;
  private int maximumFlingVelocity;
  private int scrollbarSize;
  private int touchSlop;
  private int pagingTouchSlop;
  private int doubleTapSlop;
  private int windowTouchSlop;
  private static boolean hasPermanentMenuKey = true;

  private void setup(Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    float density = metrics.density;

    edgeSlop = (int) (density * ViewConfiguration.getEdgeSlop() + 0.5f);
    fadingEdgeLength = (int) (density * ViewConfiguration.getFadingEdgeLength() + 0.5f);
    minimumFlingVelocity = (int) (density * ViewConfiguration.getMinimumFlingVelocity() + 0.5f);
    maximumFlingVelocity = (int) (density * ViewConfiguration.getMaximumFlingVelocity() + 0.5f);
    scrollbarSize = (int) (density * SCROLL_BAR_SIZE + 0.5f);
    touchSlop = (int) (density * TOUCH_SLOP + 0.5f);
    pagingTouchSlop = (int) (density * PAGING_TOUCH_SLOP + 0.5f);
    doubleTapSlop = (int) (density * DOUBLE_TAP_SLOP + 0.5f);
    windowTouchSlop = (int) (density * WINDOW_TOUCH_SLOP + 0.5f);
  }

  @Implementation
  protected static ViewConfiguration get(Context context) {
    ViewConfiguration viewConfiguration = new ViewConfiguration();
    ShadowViewConfiguration shadowViewConfiguration = Shadow.extract(viewConfiguration);
    shadowViewConfiguration.setup(context);

    if (RuntimeEnvironment.getApiLevel() >= android.os.Build.VERSION_CODES.Q) {
      reflector(ViewConfigurationReflector.class, viewConfiguration)
          .setConstructedWithContext(true);
    }

    return viewConfiguration;
  }

  @Implementation
  protected static int getScrollBarSize() {
    return SCROLL_BAR_SIZE;
  }

  @Implementation
  protected int getScaledScrollBarSize() {
    return scrollbarSize;
  }

  @Implementation
  protected int getScaledFadingEdgeLength() {
    return fadingEdgeLength;
  }

  @Implementation
  protected static int getPressedStateDuration() {
    return PRESSED_STATE_DURATION;
  }

  @Implementation
  protected static int getLongPressTimeout() {
    return LONG_PRESS_TIMEOUT;
  }

  @Implementation
  protected static int getTapTimeout() {
    return TAP_TIMEOUT;
  }

  @Implementation
  protected static int getDoubleTapTimeout() {
    return DOUBLE_TAP_TIMEOUT;
  }

  @Implementation
  protected int getScaledEdgeSlop() {
    return edgeSlop;
  }

  @Implementation
  protected static int getTouchSlop() {
    return TOUCH_SLOP;
  }

  @Implementation
  protected int getScaledTouchSlop() {
    return touchSlop;
  }

  @Implementation
  protected int getScaledPagingTouchSlop() {
    return pagingTouchSlop;
  }

  @Implementation
  protected int getScaledDoubleTapSlop() {
    return doubleTapSlop;
  }

  @Implementation
  protected static int getWindowTouchSlop() {
    return WINDOW_TOUCH_SLOP;
  }

  @Implementation
  protected int getScaledWindowTouchSlop() {
    return windowTouchSlop;
  }

  @Implementation
  protected int getScaledMinimumFlingVelocity() {
    return minimumFlingVelocity;
  }

  @Implementation
  protected static int getMaximumFlingVelocity() {
    return MAXIMUM_FLING_VELOCITY;
  }

  @Implementation
  protected int getScaledMaximumFlingVelocity() {
    return maximumFlingVelocity;
  }

  @Implementation
  protected static int getMaximumDrawingCacheSize() {
    return MAXIMUM_DRAWING_CACHE_SIZE;
  }

  @Implementation
  protected boolean hasPermanentMenuKey() {
    return hasPermanentMenuKey;
  }

  public static void setHasPermanentMenuKey(boolean value) {
    hasPermanentMenuKey = value;
  }

  @ForType(ViewConfiguration.class)
  interface ViewConfigurationReflector {
    @Accessor("mConstructedWithContext")
    void setConstructedWithContext(boolean value);
  }
}
