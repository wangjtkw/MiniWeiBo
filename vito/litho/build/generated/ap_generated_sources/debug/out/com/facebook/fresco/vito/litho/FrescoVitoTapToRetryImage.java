package com.facebook.fresco.vito.litho;

import android.graphics.drawable.Drawable;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.VisibleForTesting;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.fresco.vito.listener.ImageListener;
import com.facebook.fresco.vito.options.ImageOptions;
import com.facebook.fresco.vito.source.ImageSource;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.litho.ClickEvent;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.EventHandler;
import com.facebook.litho.HasEventDispatcher;
import com.facebook.litho.LongClickEvent;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.TouchEvent;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropSetter;
import com.facebook.litho.annotations.RequiredProp;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import java.util.BitSet;
import javax.annotation.Nullable;

/**
 * @prop-optional callerContext java.lang.Object 
 * @prop-optional imageAspectRatio float 
 * @prop-optional imageClickHandler com.facebook.litho.EventHandler<com.facebook.litho.ClickEvent> 
 * @prop-optional imageListener com.facebook.fresco.vito.listener.ImageListener 
 * @prop-optional imageLongClickHandler com.facebook.litho.EventHandler<com.facebook.litho.LongClickEvent> 
 * @prop-optional imageOptions com.facebook.fresco.vito.options.ImageOptions 
 * @prop-required imageSource com.facebook.fresco.vito.source.ImageSource 
 * @prop-optional imageTouchHandler com.facebook.litho.EventHandler<com.facebook.litho.TouchEvent> 
 * @prop-optional isInitialTapToLoad boolean 
 * @prop-optional maxTapCount int 
 * @prop-optional onFadeListener com.facebook.drawee.drawable.FadeDrawable.OnFadeListener 
 * @prop-optional prefetch com.facebook.fresco.vito.litho.FrescoVitoImage2Spec.Prefetch 
 * @prop-optional prefetchRequestListener com.facebook.imagepipeline.listener.RequestListener 
 * @prop-optional retryImage android.graphics.drawable.Drawable 
 * @prop-optional retryImageScaleType com.facebook.drawee.drawable.ScalingUtils.ScaleType 
 *
 * @see com.facebook.fresco.vito.litho.FrescoVitoTapToRetryImageSpec
 */
public final class FrescoVitoTapToRetryImage extends Component {
  @Comparable(
      type = 14
  )
  private FrescoVitoTapToRetryImageStateContainer mStateContainer;

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
      resType = ResType.FLOAT,
      optional = true
  )
  @Comparable(
      type = 0
  )
  float imageAspectRatio = FrescoVitoTapToRetryImageSpec.imageAspectRatio;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 12
  )
  EventHandler<ClickEvent> imageClickHandler;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  ImageListener imageListener;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 12
  )
  EventHandler<LongClickEvent> imageLongClickHandler;

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
      optional = false
  )
  @Comparable(
      type = 13
  )
  ImageSource imageSource;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 12
  )
  EventHandler<TouchEvent> imageTouchHandler;

  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 3
  )
  boolean isInitialTapToLoad;

  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 3
  )
  int maxTapCount = FrescoVitoTapToRetryImageSpec.maxTapCount;

  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  FadeDrawable.OnFadeListener onFadeListener;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  FrescoVitoImage2Spec.Prefetch prefetch = FrescoVitoTapToRetryImageSpec.prefetch;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  RequestListener prefetchRequestListener;

  @Nullable
  @Prop(
      resType = ResType.DRAWABLE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  Drawable retryImage;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  ScalingUtils.ScaleType retryImageScaleType;

  private FrescoVitoTapToRetryImage() {
    super("FrescoVitoTapToRetryImage");
    mStateContainer = new FrescoVitoTapToRetryImageStateContainer();
  }

  @Override
  protected StateContainer getStateContainer() {
    return mStateContainer;
  }

  @Override
  public FrescoVitoTapToRetryImage makeShallowCopy() {
    FrescoVitoTapToRetryImage component = (FrescoVitoTapToRetryImage) super.makeShallowCopy();
    component.mStateContainer = new FrescoVitoTapToRetryImageStateContainer();
    return component;
  }

  @Override
  protected void createInitialState(ComponentContext c) {
    StateValue<Boolean> isTapToRetry = new StateValue<>();
    StateValue<Integer> tapCount = new StateValue<>();
    FrescoVitoTapToRetryImageSpec.onCreateInitialState(
      (ComponentContext) c,
      (StateValue<Boolean>) isTapToRetry,
      (StateValue<Integer>) tapCount,
      (boolean) isInitialTapToLoad);
    mStateContainer.isTapToRetry = isTapToRetry.get();
    mStateContainer.tapCount = tapCount.get();
  }

  @Override
  protected Component onCreateLayout(ComponentContext c) {
    Component _result;
    _result = (Component) FrescoVitoTapToRetryImageSpec.onCreateLayout(
      (ComponentContext) c,
      (ImageSource) imageSource,
      (Object) callerContext,
      (float) imageAspectRatio,
      (EventHandler<ClickEvent>) imageClickHandler,
      (EventHandler<LongClickEvent>) imageLongClickHandler,
      (EventHandler<TouchEvent>) imageTouchHandler,
      (ImageListener) imageListener,
      (ImageOptions) imageOptions,
      (int) maxTapCount,
      (FadeDrawable.OnFadeListener) onFadeListener,
      (FrescoVitoImage2Spec.Prefetch) prefetch,
      (RequestListener) prefetchRequestListener,
      (Drawable) retryImage,
      (ScalingUtils.ScaleType) retryImageScaleType,
      (boolean) mStateContainer.isTapToRetry,
      (int) mStateContainer.tapCount);
    return _result;
  }

  private void onRetryClickEvent(HasEventDispatcher _abstract, ComponentContext c) {
    FrescoVitoTapToRetryImage _ref = (FrescoVitoTapToRetryImage) _abstract;
    FrescoVitoTapToRetryImageSpec.onRetryClickEvent(
      c);
  }

  public static EventHandler<ClickEvent> onRetryClickEvent(ComponentContext c) {
    return newEventHandler(FrescoVitoTapToRetryImage.class, "FrescoVitoTapToRetryImage", c, -221901093, new Object[] {
          c,
        });
  }

  @Override
  public Object dispatchOnEvent(final EventHandler eventHandler, final Object eventState) {
    int id = eventHandler.id;
    switch (id) {
      case -221901093: {
        ClickEvent _event = (ClickEvent) eventState;
        onRetryClickEvent(
              eventHandler.mHasEventDispatcher,
              (ComponentContext) eventHandler.params[0]);
        return null;
      }
      case -1048037474: {
        dispatchErrorEvent((com.facebook.litho.ComponentContext) eventHandler.params[0], (com.facebook.litho.ErrorEvent) eventState);
        return null;
      }
      default:
          return null;
    }
  }

  @Override
  protected boolean hasState() {
    return true;
  }

  @Override
  protected void transferState(StateContainer _prevStateContainer,
      StateContainer _nextStateContainer) {
    FrescoVitoTapToRetryImageStateContainer prevStateContainer = (FrescoVitoTapToRetryImageStateContainer) _prevStateContainer;
    FrescoVitoTapToRetryImageStateContainer nextStateContainer = (FrescoVitoTapToRetryImageStateContainer) _nextStateContainer;
    nextStateContainer.isTapToRetry = prevStateContainer.isTapToRetry;
    nextStateContainer.tapCount = prevStateContainer.tapCount;
  }

  protected static void onImageFailure(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(0);
    c.updateStateAsync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onImageFailure");
  }

  protected static void onImageFailureAsync(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(0);
    c.updateStateAsync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onImageFailure");
  }

  protected static void onImageFailureSync(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(0);
    c.updateStateSync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onImageFailure");
  }

  protected static void onTapToRetry(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(1);
    c.updateStateAsync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onTapToRetry");
  }

  protected static void onTapToRetryAsync(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(1);
    c.updateStateAsync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onTapToRetry");
  }

  protected static void onTapToRetrySync(ComponentContext c) {
    Component _component = c.getComponentScope();
    if (_component == null) {
      return;
    }
    StateContainer.StateUpdate _stateUpdate = new StateContainer.StateUpdate(1);
    c.updateStateSync(_stateUpdate, "updateState:FrescoVitoTapToRetryImage.onTapToRetry");
  }

  public static Builder create(ComponentContext context) {
    return create(context, 0, 0);
  }

  public static Builder create(ComponentContext context, int defStyleAttr, int defStyleRes) {
    final Builder builder = new Builder();
    FrescoVitoTapToRetryImage instance = new FrescoVitoTapToRetryImage();
    builder.init(context, defStyleAttr, defStyleRes, instance);
    return builder;
  }

  @VisibleForTesting(
      otherwise = 2
  )
  static class FrescoVitoTapToRetryImageStateContainer extends StateContainer {
    @State
    @Comparable(
        type = 3
    )
    boolean isTapToRetry;

    @State
    @Comparable(
        type = 3
    )
    int tapCount;

    @Override
    public void applyStateUpdate(StateContainer.StateUpdate stateUpdate) {
      StateValue<Boolean> isTapToRetry;
      StateValue<Integer> tapCount;

      final Object[] params = stateUpdate.params;
      switch (stateUpdate.type) {
        case 0:
          isTapToRetry = new StateValue<Boolean>();
          isTapToRetry.set(this.isTapToRetry);
          tapCount = new StateValue<Integer>();
          tapCount.set(this.tapCount);
          FrescoVitoTapToRetryImageSpec.onImageFailure(isTapToRetry, tapCount);
          this.isTapToRetry = isTapToRetry.get();
          this.tapCount = tapCount.get();
          break;

        case 1:
          isTapToRetry = new StateValue<Boolean>();
          isTapToRetry.set(this.isTapToRetry);
          FrescoVitoTapToRetryImageSpec.onTapToRetry(isTapToRetry);
          this.isTapToRetry = isTapToRetry.get();
          break;
      }
    }
  }

  public static final class Builder extends Component.Builder<Builder> {
    FrescoVitoTapToRetryImage mFrescoVitoTapToRetryImage;

    ComponentContext mContext;

    private final String[] REQUIRED_PROPS_NAMES = new String[] {"imageSource"};

    private final int REQUIRED_PROPS_COUNT = 1;

    private final BitSet mRequired = new BitSet(REQUIRED_PROPS_COUNT);

    private void init(ComponentContext context, int defStyleAttr, int defStyleRes,
        FrescoVitoTapToRetryImage frescoVitoTapToRetryImageRef) {
      super.init(context, defStyleAttr, defStyleRes, frescoVitoTapToRetryImageRef);
      mFrescoVitoTapToRetryImage = frescoVitoTapToRetryImageRef;
      mContext = context;
      mRequired.clear();
    }

    @Override
    protected void setComponent(Component component) {
      mFrescoVitoTapToRetryImage = (FrescoVitoTapToRetryImage) component;
    }

    @PropSetter(
        value = "callerContext",
        required = false
    )
    public Builder callerContext(@Nullable Object callerContext) {
      this.mFrescoVitoTapToRetryImage.callerContext = callerContext;
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatio(float imageAspectRatio) {
      this.mFrescoVitoTapToRetryImage.imageAspectRatio = imageAspectRatio;
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioRes(@DimenRes int resId) {
      this.mFrescoVitoTapToRetryImage.imageAspectRatio = mResourceResolver.resolveFloatRes(resId);
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioAttr(@AttrRes int attrResId, @DimenRes int defResId) {
      this.mFrescoVitoTapToRetryImage.imageAspectRatio = mResourceResolver.resolveFloatAttr(attrResId, defResId);
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioAttr(@AttrRes int attrResId) {
      this.mFrescoVitoTapToRetryImage.imageAspectRatio = mResourceResolver.resolveFloatAttr(attrResId, 0);
      return this;
    }

    @PropSetter(
        value = "imageClickHandler",
        required = false
    )
    public Builder imageClickHandler(@Nullable EventHandler<ClickEvent> imageClickHandler) {
      this.mFrescoVitoTapToRetryImage.imageClickHandler = imageClickHandler;
      return this;
    }

    @PropSetter(
        value = "imageListener",
        required = false
    )
    public Builder imageListener(@Nullable ImageListener imageListener) {
      this.mFrescoVitoTapToRetryImage.imageListener = imageListener;
      return this;
    }

    @PropSetter(
        value = "imageLongClickHandler",
        required = false
    )
    public Builder imageLongClickHandler(@Nullable EventHandler<LongClickEvent> imageLongClickHandler) {
      this.mFrescoVitoTapToRetryImage.imageLongClickHandler = imageLongClickHandler;
      return this;
    }

    @PropSetter(
        value = "imageOptions",
        required = false
    )
    public Builder imageOptions(@Nullable ImageOptions imageOptions) {
      this.mFrescoVitoTapToRetryImage.imageOptions = imageOptions;
      return this;
    }

    @PropSetter(
        value = "imageSource",
        required = true
    )
    @RequiredProp("imageSource")
    public Builder imageSource(ImageSource imageSource) {
      this.mFrescoVitoTapToRetryImage.imageSource = imageSource;
      mRequired.set(0);
      return this;
    }

    @PropSetter(
        value = "imageTouchHandler",
        required = false
    )
    public Builder imageTouchHandler(@Nullable EventHandler<TouchEvent> imageTouchHandler) {
      this.mFrescoVitoTapToRetryImage.imageTouchHandler = imageTouchHandler;
      return this;
    }

    @PropSetter(
        value = "isInitialTapToLoad",
        required = false
    )
    public Builder isInitialTapToLoad(boolean isInitialTapToLoad) {
      this.mFrescoVitoTapToRetryImage.isInitialTapToLoad = isInitialTapToLoad;
      return this;
    }

    @PropSetter(
        value = "maxTapCount",
        required = false
    )
    public Builder maxTapCount(int maxTapCount) {
      this.mFrescoVitoTapToRetryImage.maxTapCount = maxTapCount;
      return this;
    }

    @PropSetter(
        value = "onFadeListener",
        required = false
    )
    public Builder onFadeListener(FadeDrawable.OnFadeListener onFadeListener) {
      this.mFrescoVitoTapToRetryImage.onFadeListener = onFadeListener;
      return this;
    }

    @PropSetter(
        value = "prefetch",
        required = false
    )
    public Builder prefetch(@Nullable FrescoVitoImage2Spec.Prefetch prefetch) {
      this.mFrescoVitoTapToRetryImage.prefetch = prefetch;
      return this;
    }

    @PropSetter(
        value = "prefetchRequestListener",
        required = false
    )
    public Builder prefetchRequestListener(@Nullable RequestListener prefetchRequestListener) {
      this.mFrescoVitoTapToRetryImage.prefetchRequestListener = prefetchRequestListener;
      return this;
    }

    @PropSetter(
        value = "retryImage",
        required = false
    )
    public Builder retryImage(@Nullable Drawable retryImage) {
      this.mFrescoVitoTapToRetryImage.retryImage = retryImage;
      return this;
    }

    @PropSetter(
        value = "retryImage",
        required = false
    )
    public Builder retryImageRes(@DrawableRes int resId) {
      this.mFrescoVitoTapToRetryImage.retryImage = mResourceResolver.resolveDrawableRes(resId);
      return this;
    }

    @PropSetter(
        value = "retryImage",
        required = false
    )
    public Builder retryImageAttr(@AttrRes int attrResId, @DrawableRes int defResId) {
      this.mFrescoVitoTapToRetryImage.retryImage = mResourceResolver.resolveDrawableAttr(attrResId, defResId);
      return this;
    }

    @PropSetter(
        value = "retryImage",
        required = false
    )
    public Builder retryImageAttr(@AttrRes int attrResId) {
      this.mFrescoVitoTapToRetryImage.retryImage = mResourceResolver.resolveDrawableAttr(attrResId, 0);
      return this;
    }

    @PropSetter(
        value = "retryImageScaleType",
        required = false
    )
    public Builder retryImageScaleType(@Nullable ScalingUtils.ScaleType retryImageScaleType) {
      this.mFrescoVitoTapToRetryImage.retryImageScaleType = retryImageScaleType;
      return this;
    }

    @Override
    public Builder getThis() {
      return this;
    }

    @Override
    public FrescoVitoTapToRetryImage build() {
      checkArgs(REQUIRED_PROPS_COUNT, mRequired, REQUIRED_PROPS_NAMES);
      return mFrescoVitoTapToRetryImage;
    }
  }
}
