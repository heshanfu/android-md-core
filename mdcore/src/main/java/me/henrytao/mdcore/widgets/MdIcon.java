/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henrytao.mdcore.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import me.henrytao.mdcore.R;
import me.henrytao.mdcore.utils.ResourceUtils;

/**
 * Created by henrytao on 11/1/15.
 */
public class MdIcon extends AppCompatImageView {

  private ColorStateList mImageTintList;

  private PorterDuff.Mode mImageTintMode;

  public MdIcon(Context context) {
    this(context, null);
  }

  public MdIcon(Context context, AttributeSet attrs) {
    this(context, attrs, R.attr.MdIconStyle);
  }

  public MdIcon(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initFromAttributes(attrs, defStyleAttr);
  }

  @Override
  public void setImageDrawable(Drawable drawable) {
    super.setImageDrawable(drawable);
    invalidateImageTint();
  }

  @Override
  public void setImageResource(int resId) {
    super.setImageResource(resId);
    invalidateImageTint();
  }

  @Override
  public void setImageTintList(ColorStateList tint) {
    mImageTintList = tint;
    invalidateImageTint();
  }

  @Override
  public void setImageTintMode(PorterDuff.Mode tintMode) {
    mImageTintMode = tintMode;
    invalidateImageTint();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    invalidateImageTint();
  }

  protected void initFromAttributes(AttributeSet attrs, int defStyleAttr) {
    Context context = getContext();

    boolean enabled = true;
    ColorStateList imageTintList;
    TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MdIcon, defStyleAttr, 0);
    enabled = a.getBoolean(R.styleable.MdIcon_enabled, enabled);
    mImageTintMode = ResourceUtils.parseTintMode(a.getInt(R.styleable.MdIcon_tintMode, -1), PorterDuff.Mode.SRC_IN);
    try {
      imageTintList = ResourceUtils.createColorStateListFromResId(context, a.getResourceId(R.styleable.MdIcon_tint, 0));
    } catch (Exception ignore) {
      imageTintList = a.getColorStateList(R.styleable.MdIcon_tint);
    }
    a.recycle();
    setEnabled(enabled);
    if (imageTintList != null) {
      setImageTintList(imageTintList);
    }
  }

  private void invalidateImageTint() {
    super.setImageDrawable(ResourceUtils.createDrawableTint(getDrawable(), getDrawableState(), mImageTintList, mImageTintMode));
  }
}
