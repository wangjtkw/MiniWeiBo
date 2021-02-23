package com.facebook.fresco.vito.litho;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.DimenRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.drawable.FadeDrawable;
import com.facebook.fresco.vito.core.FrescoDrawable2;
import com.facebook.fresco.vito.core.VitoImageRequest;
import com.facebook.fresco.vito.listener.ImageListener;
import com.facebook.fresco.vito.options.ImageOptions;
import com.facebook.fresco.vito.source.ImageSource;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.litho.CommonUtils;
import com.facebook.litho.Component;
import com.facebook.litho.ComponentContext;
import com.facebook.litho.ComponentLayout;
import com.facebook.litho.ComponentLifecycle;
import com.facebook.litho.Diff;
import com.facebook.litho.Output;
import com.facebook.litho.Size;
import com.facebook.litho.StateContainer;
import com.facebook.litho.StateValue;
import com.facebook.litho.WorkingRange;
import com.facebook.litho.annotations.Comparable;
import com.facebook.litho.annotations.Prop;
import com.facebook.litho.annotations.PropSetter;
import com.facebook.litho.annotations.ResType;
import com.facebook.litho.annotations.State;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/**
 * Simple Fresco Vito component for Litho <p>
 * @prop-optional callerContext java.lang.Object 
 * @prop-optional imageAspectRatio float 
 * @prop-optional imageListener com.facebook.fresco.vito.listener.ImageListener 
 * @prop-optional imageOptions com.facebook.fresco.vito.options.ImageOptions 
 * @prop-optional imageSource com.facebook.fresco.vito.source.ImageSource 
 * @prop-optional onFadeListener com.facebook.drawee.drawable.FadeDrawable.OnFadeListener 
 * @prop-optional prefetch com.facebook.fresco.vito.litho.FrescoVitoImage2Spec.Prefetch 
 * @prop-optional prefetchRequestListener com.facebook.imagepipeline.listener.RequestListener 
 * @prop-optional uri android.net.Uri 
 *
 * @see com.facebook.fresco.vito.litho.FrescoVitoImage2Spec
 */
public final class FrescoVitoImage2 extends Component {
  @Comparable(
      type = 14
  )
  private FrescoVitoImage2StateContainer mStateContainer;

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
  float imageAspectRatio = FrescoVitoImage2Spec.imageAspectRatio;

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
      type = 13
  )
  ImageOptions imageOptions;

  @Nullable
  @Prop(
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  ImageSource imageSource;

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
  FrescoVitoImage2Spec.Prefetch prefetch = FrescoVitoImage2Spec.prefetch;

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
      resType = ResType.NONE,
      optional = true
  )
  @Comparable(
      type = 13
  )
  Uri uri;

  DataSource<Void> prefetchDataSource;

  Rect viewportDimensions;

  private FrescoVitoImage2() {
    super("FrescoVitoImage2");
    mStateContainer = new FrescoVitoImage2StateContainer();
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
    FrescoVitoImage2 frescoVitoImage2Ref = (FrescoVitoImage2) other;
    if (callerContext != null ? !callerContext.equals(frescoVitoImage2Ref.callerContext) : frescoVitoImage2Ref.callerContext != null) {
      return false;
    }
    if (Float.compare(imageAspectRatio, frescoVitoImage2Ref.imageAspectRatio) != 0) {
      return false;
    }
    if (imageListener != null ? !imageListener.equals(frescoVitoImage2Ref.imageListener) : frescoVitoImage2Ref.imageListener != null) {
      return false;
    }
    if (imageOptions != null ? !imageOptions.equals(frescoVitoImage2Ref.imageOptions) : frescoVitoImage2Ref.imageOptions != null) {
      return false;
    }
    if (imageSource != null ? !imageSource.equals(frescoVitoImage2Ref.imageSource) : frescoVitoImage2Ref.imageSource != null) {
      return false;
    }
    if (onFadeListener != null ? !onFadeListener.equals(frescoVitoImage2Ref.onFadeListener) : frescoVitoImage2Ref.onFadeListener != null) {
      return false;
    }
    if (prefetch != null ? !prefetch.equals(frescoVitoImage2Ref.prefetch) : frescoVitoImage2Ref.prefetch != null) {
      return false;
    }
    if (prefetchRequestListener != null ? !prefetchRequestListener.equals(frescoVitoImage2Ref.prefetchRequestListener) : frescoVitoImage2Ref.prefetchRequestListener != null) {
      return false;
    }
    if (uri != null ? !uri.equals(frescoVitoImage2Ref.uri) : frescoVitoImage2Ref.uri != null) {
      return false;
    }
    if (mStateContainer.workingRangePrefetchData != null ? !mStateContainer.workingRangePrefetchData.equals(frescoVitoImage2Ref.mStateContainer.workingRangePrefetchData) : frescoVitoImage2Ref.mStateContainer.workingRangePrefetchData != null) {
      return false;
    }
    return true;
  }

  @Override
  protected void copyInterStageImpl(Component component) {
    FrescoVitoImage2 frescoVitoImage2Ref = (FrescoVitoImage2) component;
    prefetchDataSource = frescoVitoImage2Ref.prefetchDataSource;
    viewportDimensions = frescoVitoImage2Ref.viewportDimensions;
  }

  @Override
  public FrescoVitoImage2 makeShallowCopy() {
    FrescoVitoImage2 component = (FrescoVitoImage2) super.makeShallowCopy();
    component.prefetchDataSource = null;
    component.viewportDimensions = null;
    component.mStateContainer = new FrescoVitoImage2StateContainer();
    return component;
  }

  @Override
  protected Object onCreateMountContent(Context c) {
    Object _result;
    _result = (Object) FrescoVitoImage2Spec.onCreateMountContent(
      (Context) c);
    return _result;
  }

  @Override
  protected void createInitialState(ComponentContext context) {
    StateValue<AtomicReference<DataSource<Void>>> workingRangePrefetchData = new StateValue<>();
    FrescoVitoImage2Spec.onCreateInitialState(
      (ComponentContext) context,
      (StateValue<AtomicReference<DataSource<Void>>>) workingRangePrefetchData);
    mStateContainer.workingRangePrefetchData = workingRangePrefetchData.get();
  }

  @Override
  protected void onMeasure(ComponentContext c, ComponentLayout layout, int widthSpec,
      int heightSpec, Size size) {
    FrescoVitoImage2Spec.onMeasure(
      (ComponentContext) c,
      (ComponentLayout) layout,
      (int) widthSpec,
      (int) heightSpec,
      (Size) size,
      (float) imageAspectRatio);
  }

  @Override
  protected boolean canMeasure() {
    return true;
  }

  @Override
  protected void onPrepare(ComponentContext c) {
    Output<DataSource<Void>> prefetchDataSourceTmp = new Output<>();
    FrescoVitoImage2Spec.onPrepare(
      (ComponentContext) c,
      (Object) callerContext,
      (FrescoVitoImage2Spec.Prefetch) prefetch,
      (RequestListener) prefetchRequestListener,
      (VitoImageRequest) getImageRequest(c),
      (Output<DataSource<Void>>) prefetchDataSourceTmp);
    prefetchDataSource = prefetchDataSourceTmp.get();
    FrescoVitoImage2Spec.registerWorkingRanges(
      (ComponentContext) c,
      (FrescoVitoImage2Spec.Prefetch) prefetch);
  }

  @Override
  protected void onMount(ComponentContext c, Object frescoDrawable) {
    FrescoVitoImage2Spec.onMount(
      (ComponentContext) c,
      (FrescoDrawable2) frescoDrawable,
      (Object) callerContext,
      (ImageListener) imageListener,
      (VitoImageRequest) getImageRequest(c),
      (DataSource<Void>) prefetchDataSource,
      (Rect) viewportDimensions,
      (AtomicReference<DataSource<Void>>) mStateContainer.workingRangePrefetchData,
      (FadeDrawable.OnFadeListener) onFadeListener);
  }

  @Override
  protected void onBind(ComponentContext c, Object frescoDrawable) {
    FrescoVitoImage2Spec.onBind(
      (ComponentContext) c,
      (FrescoDrawable2) frescoDrawable,
      (Object) callerContext,
      (ImageListener) imageListener,
      (FadeDrawable.OnFadeListener) onFadeListener,
      (VitoImageRequest) getImageRequest(c),
      (DataSource<Void>) prefetchDataSource,
      (Rect) viewportDimensions,
      (AtomicReference<DataSource<Void>>) mStateContainer.workingRangePrefetchData);
  }

  @Override
  protected void onUnbind(ComponentContext c, Object frescoDrawable) {
    FrescoVitoImage2Spec.onUnbind(
      (ComponentContext) c,
      (FrescoDrawable2) frescoDrawable,
      (DataSource<Void>) prefetchDataSource);
  }

  @Override
  protected void onUnmount(ComponentContext c, Object frescoDrawable) {
    FrescoVitoImage2Spec.onUnmount(
      (ComponentContext) c,
      (FrescoDrawable2) frescoDrawable,
      (DataSource<Void>) prefetchDataSource);
  }

  @Override
  protected boolean shouldUpdate(Component _prevAbstractImpl, Component _nextAbstractImpl) {
    FrescoVitoImage2 _prevImpl = (FrescoVitoImage2) _prevAbstractImpl;
    FrescoVitoImage2 _nextImpl = (FrescoVitoImage2) _nextAbstractImpl;
    boolean _result;
    Diff<Uri> uri = new Diff<Uri>(_prevImpl == null ? null : _prevImpl.uri, _nextImpl == null ? null : _nextImpl.uri);
    Diff<ImageSource> imageSource = new Diff<ImageSource>(_prevImpl == null ? null : _prevImpl.imageSource, _nextImpl == null ? null : _nextImpl.imageSource);
    Diff<ImageOptions> imageOptions = new Diff<ImageOptions>(_prevImpl == null ? null : _prevImpl.imageOptions, _nextImpl == null ? null : _nextImpl.imageOptions);
    Diff<Float> imageAspectRatio = new Diff<Float>(_prevImpl == null ? null : _prevImpl.imageAspectRatio, _nextImpl == null ? null : _nextImpl.imageAspectRatio);
    Diff<ImageListener> imageListener = new Diff<ImageListener>(_prevImpl == null ? null : _prevImpl.imageListener, _nextImpl == null ? null : _nextImpl.imageListener);
    _result = (boolean) FrescoVitoImage2Spec.shouldUpdate(
      (Diff<Uri>) uri,
      (Diff<ImageSource>) imageSource,
      (Diff<ImageOptions>) imageOptions,
      (Diff<Float>) imageAspectRatio,
      (Diff<ImageListener>) imageListener);
    return _result;
  }

  @Override
  protected void onPopulateAccessibilityNode(View host, AccessibilityNodeInfoCompat node) {
    FrescoVitoImage2Spec.onPopulateAccessibilityNode(
      (View) host,
      (AccessibilityNodeInfoCompat) node);
  }

  @Override
  public boolean implementsAccessibility() {
    return true;
  }

  @Override
  protected void onBoundsDefined(ComponentContext c, ComponentLayout layout) {
    Output<Rect> viewportDimensionsTmp = new Output<>();
    FrescoVitoImage2Spec.onBoundsDefined(
      (ComponentContext) c,
      (ComponentLayout) layout,
      (Output<Rect>) viewportDimensionsTmp);
    viewportDimensions = viewportDimensionsTmp.get();
  }

  @Override
  public ComponentLifecycle.MountType getMountType() {
    return ComponentLifecycle.MountType.DRAWABLE;
  }

  @Override
  protected int poolSize() {
    return 15;
  }

  @Override
  protected boolean canPreallocate() {
    return true;
  }

  @Override
  protected boolean isMountSizeDependent() {
    return true;
  }

  @Override
  public boolean callsShouldUpdateOnMount() {
    return true;
  }

  @Override
  public boolean isPureRender() {
    return true;
  }

  private void onEnteredWorkingRange(ComponentContext c) {
    FrescoVitoImage2Spec.onEnteredWorkingRange(
      (ComponentContext) c,
      (Object) callerContext,
      (FrescoVitoImage2Spec.Prefetch) prefetch,
      (VitoImageRequest) getImageRequest(c),
      (DataSource<Void>) prefetchDataSource,
      (AtomicReference<DataSource<Void>>) mStateContainer.workingRangePrefetchData);
  }

  private void onExitedWorkingRange(ComponentContext c) {
    FrescoVitoImage2Spec.onExitedWorkingRange(
      (ComponentContext) c,
      (AtomicReference<DataSource<Void>>) mStateContainer.workingRangePrefetchData);
  }

  static void registerImagePrefetchWorkingRange(ComponentContext c, WorkingRange workingRange) {
    if (workingRange == null) {
      return;
    }
    Component component = c.getComponentScope();
    registerWorkingRange("imagePrefetch", workingRange, component, c.getGlobalKey());
  }

  @Override
  public void dispatchOnEnteredRange(ComponentContext c, String name) {
    switch (name) {
      case "imagePrefetch": {
        onEnteredWorkingRange(c);
        return;
      }
    }
  }

  @Override
  public void dispatchOnExitedRange(ComponentContext c, String name) {
    switch (name) {
      case "imagePrefetch": {
        onExitedWorkingRange(c);
        return;
      }
    }
  }

  @Override
  protected boolean hasState() {
    return true;
  }

  @Override
  protected void transferState(StateContainer _prevStateContainer,
      StateContainer _nextStateContainer) {
    FrescoVitoImage2StateContainer prevStateContainer = (FrescoVitoImage2StateContainer) _prevStateContainer;
    FrescoVitoImage2StateContainer nextStateContainer = (FrescoVitoImage2StateContainer) _nextStateContainer;
    nextStateContainer.workingRangePrefetchData = prevStateContainer.workingRangePrefetchData;
  }

  public static Builder create(ComponentContext context) {
    return create(context, 0, 0);
  }

  public static Builder create(ComponentContext context, int defStyleAttr, int defStyleRes) {
    final Builder builder = new Builder();
    FrescoVitoImage2 instance = new FrescoVitoImage2();
    builder.init(context, defStyleAttr, defStyleRes, instance);
    return builder;
  }

  private VitoImageRequest getImageRequest(ComponentContext c) {
    String globalKey = c.getGlobalKey();
    final ImageRequestInputs inputs = new ImageRequestInputs(globalKey,uri,imageSource,imageOptions);
    VitoImageRequest imageRequest = (VitoImageRequest) c.getCachedValue(inputs);
    if (imageRequest == null) {
      imageRequest = FrescoVitoImage2Spec.onCalculateImageRequest(c,uri,imageSource,imageOptions);
      c.putCachedValue(inputs, imageRequest);
    }
    return imageRequest;
  }

  @VisibleForTesting(
      otherwise = 2
  )
  static class FrescoVitoImage2StateContainer extends StateContainer {
    @State
    @Comparable(
        type = 13
    )
    AtomicReference<DataSource<Void>> workingRangePrefetchData;

    @Override
    public void applyStateUpdate(StateContainer.StateUpdate stateUpdate) {
      StateValue<AtomicReference<DataSource<Void>>> workingRangePrefetchData;

      final Object[] params = stateUpdate.params;
      switch (stateUpdate.type) {
      }
    }
  }

  public static final class Builder extends Component.Builder<Builder> {
    FrescoVitoImage2 mFrescoVitoImage2;

    ComponentContext mContext;

    private void init(ComponentContext context, int defStyleAttr, int defStyleRes,
        FrescoVitoImage2 frescoVitoImage2Ref) {
      super.init(context, defStyleAttr, defStyleRes, frescoVitoImage2Ref);
      mFrescoVitoImage2 = frescoVitoImage2Ref;
      mContext = context;
    }

    @Override
    protected void setComponent(Component component) {
      mFrescoVitoImage2 = (FrescoVitoImage2) component;
    }

    @PropSetter(
        value = "callerContext",
        required = false
    )
    public Builder callerContext(@Nullable Object callerContext) {
      this.mFrescoVitoImage2.callerContext = callerContext;
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatio(float imageAspectRatio) {
      this.mFrescoVitoImage2.imageAspectRatio = imageAspectRatio;
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioRes(@DimenRes int resId) {
      this.mFrescoVitoImage2.imageAspectRatio = mResourceResolver.resolveFloatRes(resId);
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioAttr(@AttrRes int attrResId, @DimenRes int defResId) {
      this.mFrescoVitoImage2.imageAspectRatio = mResourceResolver.resolveFloatAttr(attrResId, defResId);
      return this;
    }

    @PropSetter(
        value = "imageAspectRatio",
        required = false
    )
    public Builder imageAspectRatioAttr(@AttrRes int attrResId) {
      this.mFrescoVitoImage2.imageAspectRatio = mResourceResolver.resolveFloatAttr(attrResId, 0);
      return this;
    }

    @PropSetter(
        value = "imageListener",
        required = false
    )
    public Builder imageListener(@Nullable ImageListener imageListener) {
      this.mFrescoVitoImage2.imageListener = imageListener;
      return this;
    }

    @PropSetter(
        value = "imageOptions",
        required = false
    )
    public Builder imageOptions(@Nullable ImageOptions imageOptions) {
      this.mFrescoVitoImage2.imageOptions = imageOptions;
      return this;
    }

    @PropSetter(
        value = "imageSource",
        required = false
    )
    public Builder imageSource(@Nullable ImageSource imageSource) {
      this.mFrescoVitoImage2.imageSource = imageSource;
      return this;
    }

    @PropSetter(
        value = "onFadeListener",
        required = false
    )
    public Builder onFadeListener(FadeDrawable.OnFadeListener onFadeListener) {
      this.mFrescoVitoImage2.onFadeListener = onFadeListener;
      return this;
    }

    @PropSetter(
        value = "prefetch",
        required = false
    )
    public Builder prefetch(@Nullable FrescoVitoImage2Spec.Prefetch prefetch) {
      this.mFrescoVitoImage2.prefetch = prefetch;
      return this;
    }

    @PropSetter(
        value = "prefetchRequestListener",
        required = false
    )
    public Builder prefetchRequestListener(@Nullable RequestListener prefetchRequestListener) {
      this.mFrescoVitoImage2.prefetchRequestListener = prefetchRequestListener;
      return this;
    }

    @PropSetter(
        value = "uri",
        required = false
    )
    public Builder uri(@Nullable Uri uri) {
      this.mFrescoVitoImage2.uri = uri;
      return this;
    }

    @Override
    public Builder getThis() {
      return this;
    }

    @Override
    public FrescoVitoImage2 build() {
      return mFrescoVitoImage2;
    }
  }

  private static class ImageRequestInputs {
    private final String globalKey;

    private final Uri uri;

    private final ImageSource imageSource;

    private final ImageOptions imageOptions;

    ImageRequestInputs(String globalKey, Uri uri, ImageSource imageSource,
        ImageOptions imageOptions) {
      this.globalKey = globalKey;
      this.uri = uri;
      this.imageSource = imageSource;
      this.imageOptions = imageOptions;
    }

    @Override
    public int hashCode() {
      return CommonUtils.hash(globalKey, uri, imageSource, imageOptions, getClass());
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) {
        return true;
      }
      if (other == null || !(other instanceof ImageRequestInputs)) {
        return false;
      }
      ImageRequestInputs cachedValueInputs = (ImageRequestInputs) other;
      if (!com.facebook.litho.CommonUtils.equals(globalKey, cachedValueInputs.globalKey)) {
        return false;
      }
      if (uri != null ? !uri.equals(cachedValueInputs.uri) : cachedValueInputs.uri != null) {
        return false;
      }
      if (imageSource != null ? !imageSource.equals(cachedValueInputs.imageSource) : cachedValueInputs.imageSource != null) {
        return false;
      }
      if (imageOptions != null ? !imageOptions.equals(cachedValueInputs.imageOptions) : cachedValueInputs.imageOptions != null) {
        return false;
      }
      return true;
    }
  }
}
