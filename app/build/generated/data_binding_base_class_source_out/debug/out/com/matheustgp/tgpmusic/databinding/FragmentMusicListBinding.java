// Generated by view binder compiler. Do not edit!
package com.matheustgp.tgpmusic.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.matheustgp.tgpmusic.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMusicListBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout coordinator;

  @NonNull
  public final RecyclerView recycler;

  private FragmentMusicListBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout coordinator, @NonNull RecyclerView recycler) {
    this.rootView = rootView;
    this.coordinator = coordinator;
    this.recycler = recycler;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMusicListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMusicListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_music_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMusicListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout coordinator = (LinearLayout) rootView;

      id = R.id.recycler;
      RecyclerView recycler = ViewBindings.findChildViewById(rootView, id);
      if (recycler == null) {
        break missingId;
      }

      return new FragmentMusicListBinding((LinearLayout) rootView, coordinator, recycler);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
