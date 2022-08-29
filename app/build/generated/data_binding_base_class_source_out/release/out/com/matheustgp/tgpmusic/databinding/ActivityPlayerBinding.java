// Generated by view binder compiler. Do not edit!
package com.matheustgp.tgpmusic.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.matheustgp.tgpmusic.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPlayerBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout coordinator;

  @NonNull
  public final ViewPager2 pager;

  @NonNull
  public final TabLayout tablayout;

  private ActivityPlayerBinding(@NonNull LinearLayout rootView, @NonNull LinearLayout coordinator,
      @NonNull ViewPager2 pager, @NonNull TabLayout tablayout) {
    this.rootView = rootView;
    this.coordinator = coordinator;
    this.pager = pager;
    this.tablayout = tablayout;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPlayerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPlayerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_player, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPlayerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout coordinator = (LinearLayout) rootView;

      id = R.id.pager;
      ViewPager2 pager = ViewBindings.findChildViewById(rootView, id);
      if (pager == null) {
        break missingId;
      }

      id = R.id.tablayout;
      TabLayout tablayout = ViewBindings.findChildViewById(rootView, id);
      if (tablayout == null) {
        break missingId;
      }

      return new ActivityPlayerBinding((LinearLayout) rootView, coordinator, pager, tablayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
