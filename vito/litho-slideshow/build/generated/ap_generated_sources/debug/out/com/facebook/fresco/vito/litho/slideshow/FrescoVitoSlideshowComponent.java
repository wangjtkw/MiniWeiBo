package com.facebook.fresco.vito.litho.slideshow;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.VisibleForTesting;
import com.facebook.fresco.vito.options.ImageOptions;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropSetter;
import com.facebook.litho.annotations.RequiredProp;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import javax.annotation.Nullable;

/**
 * @prop-optional callerContext java.lang.Object 
 * @prop-required fadeTransitionMs int 
 * @prop-optional imageOptions com.facebook.fresco.vito.options.ImageOptions 
 * @prop-optional isPlaying boolean 
 * @prop-required photoTransitionMs int 
 * @prop-optional uris java.util.List<android.net.Uri> 
 *
 * @see com.facebook.fresco.vito.litho.slideshow.FrescoVitoSlideshowComponentSpec
 */
public final class FrescoVitoSlideshowComponent extends Component {
  @Comparable(
      type = 14
  )
  private FrescoVitoSlideshowComponentStateContainer mStateContainer;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  Object callerContext;

  @Prop(
      resType = ResType.NONE,
      optional = false
  )
  @Comparable(
      type = 3
  )
  int fadeTransitionMs;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  ImageOptions imageOptions;

  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 3
  )
  boolean isPlaying = FrescoVitoSlideshowComponentSpec.isPlaying;

  @Prop(
      resType = ResType.NONE,
      optional = false
  )
  @Comparable(
      type = 3
  )
  int photoTransitionMs;

  @Prop(
      resType = ResType.NONE,
      optional = true,
      varArg = "uri"
  )
  @Comparable(
      type = 5
  )
  List<Uri> uris = Collections.emptyList();

  private FrescoVitoSlideshowComponent() {
    super("FrescoVitoSlideshowComponent");
    mStateContainer = new FrescoVitoSlideshowComponentStateContainer();
  }

  @Override
  protected StateContainer getStateContainer() {
    return mStateContainer;
  }

  @Override
  public boolean isEquivalentTo(Component other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    FrescoVitoSlideshowComponent frescoVitoSlideshowComponentRef = (FrescoVitoSlideshowComponent) other;
    if (callerContext != null ? !callerContext.equals(frescoVitoSlideshowComponentRef.callerContext) : frescoVitoSlideshowComponentRef.callerContext != null) {
      return false;
    }
    if (fadeTransitionMs != frescoVitoSlideshowComponentRef.fadeTransitionMs) {
      return false;
    }
    if (imageOptions != null ? !imageOptions.equals(frescoVitoSlideshowComponentRef.imageOptions) : frescoVitoSlideshowComponentRef.imageOptions != null) {
      return false;
    }
    if (isPlaying != frescoVitoSlideshowComponentRef.isPlaying) {
      return false;
    }
    if (photoTransitionMs != frescoVitoSlideshowComponentRef.photoTransitionMs) {
      return false;
    }
    if (uris != null ? !uris.equals(frescoVitoSlideshowComponentRef.uris) : frescoVitoSlideshowComponentRef.uris != null) {
      return false;
    }
    if (mStateContainer.currentlyPlaying != null ? !mStateContainer.currentlyPlaying.equals(frescoVitoSlideshowComponentRef.mStateContainer.currentlyPlaying) : frescoVitoSlideshowComponentRef.mStateContainer.currentlyPlaying != null) {
      return false;
    }
    if (mStateContainer.slideshowIndex != null ? !mStateContainer.slideshowIndex.equals(frescoVitoSlideshowComponentRef.mStateContainer.slideshowIndex) : frescoVitoSlideshowComponentRef.mStateContainer.slideshowIndex != null) {
      return false;
    }
    if (mStateContainer.timer != null ? !mStateContainer.timer.equals(frescoVitoSlideshowComponentRef.mStateContainer.timer) : frescoVitoSlideshowComponentRef.mStateContainer.timer != null) {
      return false;
    }
    return true;
  }

  @Override
  protected void createInitialState(ComponentContext c) {
    StateValue<Integer> slideshowIndex = new StateValue<>();
    StateValue<Timer> timer = new StateValue<>();
    StateValue<Boolean> currentlyPlaying = new StateValue<>();
    FrescoVitoSlideshowComponentSpec.createInitialState(
      (ComponentContext) c,
      (StateValue<Integer>) slideshowIndex,
      (StateValue<Timer>) timer,
      (StateValue<Boolean>) currentlyPlaying);
    if (slideshowIndex.get() != null) {
      mStateContainer.slideshowIndex = slideshowIndex.get();
    }
    if (timer.get() != null) {
      mStateContainer.timer = timer.get();
    }
    if (currentlyPlaying.get() != null) {
      mStateContainer.currentlyPlaying = currentlyPlaying.get();
    }
  }

  @Override
  protected Object onCreateMountContent(Context c) {
    Object _result;
    _result = (Object) FrescoVitoSlideshowComponentSpec.onCreateMountContent(
      (Context) c);
    return _result;
  }

  @Override
  protected void onMount(ComponentContext c, Object slideshowDrawable) {
    FrescoVitoSlideshowComponentSpec.onMount(
      (ComponentContext) c,
      (FrescoVitoSlideshowDrawable) slideshowDrawable,
      (List<Uri>) uris,
      (int) photoTransitionMs,
      (int) fadeTransitionMs,
      (boolean) isPlaying,
      (ImageOptions) imageOptions,
      (Object) callerContext,
      (Integer) mStateContainer.slideshowIndex,
      (Timer) mStateContainer.timer,
      (Boolean) mStateContainer.currentlyPlaying);
  }

  @Override
  protected void onUnmount(ComponentContext c, Object slideshowDrawable) {
    FrescoVitoSlideshowComponentSpec.onUnmount(
      (ComponentContext) c,
      (FrescoVitoSlideshowDrawable) slideshowDrawable);
  }

  @Override
  public ComponentLifecycle.MountType getMountType() {
    return ComponentLifecycle.MountType.DRAWABLE;
  }

  @Override
  protected int poolSize() {
    return 3;
  }

  @Override
  protected boolean canPreallocate() {
    return false;
  }

  @Override
  public boolean isPureRender() {
    return true;
  }

  @Override
  protected boolean hasState() {
    return true;
  }

  @Override
  protected void transferState(StateContainer _prevStateContainer,
      StateContainer _nextStateContainer) {
    FrescoVitoSlideshowComponentStateContainer prevStateContainer = (FrescoVitoSlideshowComponentStateContainer) _prevStateContainer;
    FrescoVitoSlideshowComponentStateContainer nextStateContainer = (FrescoVitoSlideshowComponentStateContainer) _nextStateContainer;
    nextStateContainer.currentlyPlaying = prevStateContainer.currentlyPlaying;
    nextStateContainer.slideshowIndex = prevStateContainer.slideshowIndex;
    nextStateContainer.timer = prevStateContainer.timer;
  }

  protected static void lazyUpdateCurrentlyPlaying(ComponentContext c,
      final Boolean lazyUpdateValue) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(-2147483648, lazyUpdateValue);
    c.updateStateLazy(_stateUpdate);
  }

  protected static void lazyUpdateSlideshowIndex(ComponentContext c,
      final Integer lazyUpdateValue) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(-2147483647, lazyUpdateValue);
    c.updateStateLazy(_stateUpdate);
  }

  protected static void lazyUpdateTimer(ComponentContext c, final Timer lazyUpdateValue) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(-2147483646, lazyUpdateValue);
    c.updateStateLazy(_stateUpdate);
  }

  public static Builder create(ComponentContext context) {
    return create(context, 0, 0);
  }

  public static Builder create(ComponentContext context, int defStyleAttr, int defStyleRes) {
    final Builder builder = new Builder();
    FrescoVitoSlideshowComponent instance = new FrescoVitoSlideshowComponent();
    builder.init(context, defStyleAttr, defStyleRes, instance);
    return builder;
  }

  @VisibleForTesting(
      otherwise = 2
  )
  static class FrescoVitoSlideshowComponentStateContainer extends StateContainer {
    @State
    @Comparable(
        type = 13
    )
    Boolean currentlyPlaying;

    @State
    @Comparable(
        type = 13
    )
    Integer slideshowIndex;

    @State
    @Comparable(
        type = 13
    )
    Timer timer;

    @Override
    public void applyStateUpdate(StateContainer.StateUpdate stateUpdate) {
      StateValue<Boolean> currentlyPlaying;
      StateValue<Integer> slideshowIndex;
      StateValue<Timer> timer;

      final Object[] params = stateUpdate.params;
      switch (stateUpdate.type) {
        case -2147483648:
          this.currentlyPlaying = (Boolean) params[0];
          break;

        case -2147483647:
          this.slideshowIndex = (Integer) params[0];
          break;

        case -2147483646:
          this.timer = (Timer) params[0];
          break;
      }
    }
  }

  public static final class Builder extends Component.Builder<Builder> {
    FrescoVitoSlideshowComponent mFrescoVitoSlideshowComponent;

    ComponentContext mContext;

    private final String[] REQUIRED_PROPS_NAMES = new String[] {"fadeTransitionMs", "photoTransitionMs"};

    private final int REQUIRED_PROPS_COUNT = 2;

    private final BitSet mRequired = new BitSet(REQUIRED_PROPS_COUNT);

    private void init(ComponentContext context, int defStyleAttr, int defStyleRes,
        FrescoVitoSlideshowComponent frescoVitoSlideshowComponentRef) {
      super.init(context, defStyleAttr, defStyleRes, frescoVitoSlideshowComponentRef);
      mFrescoVitoSlideshowComponent = frescoVitoSlideshowComponentRef;
      mContext = context;
      mRequired.clear();
    }

    @Override
    protected void setComponent(Component component) {
      mFrescoVitoSlideshowComponent = (FrescoVitoSlideshowComponent) component;
    }

    @PropSetter(
        value = "callerContext",
        required = false
    )
    public Builder callerContext(@Nullable Object callerContext) {
      this.mFrescoVitoSlideshowComponent.callerContext = callerContext;
      return this;
    }

    @PropSetter(
        value = "fadeTransitionMs",
        required = true
    )
    @RequiredProp("fadeTransitionMs")
    public Builder fadeTransitionMs(int fadeTransitionMs) {
      this.mFrescoVitoSlideshowComponent.fadeTransitionMs = fadeTransitionMs;
      mRequired.set(0);
      return this;
    }

    @PropSetter(
        value = "imageOptions",
        required = false
    )
    public Builder imageOptions(@Nullable ImageOptions imageOptions) {
      this.mFrescoVitoSlideshowComponent.imageOptions = imageOptions;
      return this;
    }

    @PropSetter(
        value = "isPlaying",
        required = false
    )
    public Builder isPlaying(boolean isPlaying) {
      this.mFrescoVitoSlideshowComponent.isPlaying = isPlaying;
      return this;
    }

    @PropSetter(
        value = "photoTransitionMs",
        required = true
    )
    @RequiredProp("photoTransitionMs")
    public Builder photoTransitionMs(int photoTransitionMs) {
      this.mFrescoVitoSlideshowComponent.photoTransitionMs = photoTransitionMs;
      mRequired.set(1);
      return this;
    }

    @PropSetter(
        value = "uris",
        required = false
    )
    public Builder uri(Uri uri) {
      if (uri == null) {
        return this;
      }
      if (this.mFrescoVitoSlideshowComponent.uris == Collections.EMPTY_LIST) {
        this.mFrescoVitoSlideshowComponent.uris = new ArrayList<Uri>();
      }
      this.mFrescoVitoSlideshowComponent.uris.add(uri);
      return this;
    }

    @PropSetter(
        value = "uris",
        required = false
    )
    public Builder uris(List<Uri> uris) {
      if (uris == null) {
        return this;
      }
      if (this.mFrescoVitoSlideshowComponent.uris.isEmpty()) {
        this.mFrescoVitoSlideshowComponent.uris = uris;
      } else {
        this.mFrescoVitoSlideshowComponent.uris.addAll(uris);
      }
      return this;
    }

    @Override
    public Builder getThis() {
      return this;
    }

    @Override
    public FrescoVitoSlideshowComponent build() {
      checkArgs(REQUIRED_PROPS_COUNT, mRequired, REQUIRED_PROPS_NAMES);
      return mFrescoVitoSlideshowComponent;
    }
  }
}
