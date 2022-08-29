/*
    TGPMusic - MusicPlayer
    Copyright (C) 2022  @MatheusTGP

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/
*/

package com.matheustgp.tgpmusic.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.matheustgp.tgpmusic.adapters.MusicList;
import com.matheustgp.tgpmusic.fragments.MusicListFragment;
import com.matheustgp.tgpmusic.fragments.MusicsFragment;
import com.matheustgp.tgpmusic.fragments.PlaylistFragment;
import java.util.ArrayList;


public class MusicsFragment extends FragmentStateAdapter {
    public Fragment fragmentMusicList;
    public Fragment fragmentPlaylist;
    private boolean frag1, frag2 = false;
    
    public MusicsFragment(@NonNull FragmentManager fragment, Lifecycle lifeCycle) {
        super(fragment, lifeCycle);
    }
    
    public MusicsFragment(@NonNull FragmentManager fragment, Lifecycle lifeCycle, 
    					Fragment fragment1, Fragment fragment2) {
        super(fragment, lifeCycle);
        this.fragmentMusicList = fragment1;
        this.fragmentPlaylist = fragment2;
    }
    
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
      	  case 0:
        		return fragmentMusicList;
            case 1:
            	return fragmentPlaylist;
       	 default:
      	  	return new Fragment();
   	 }
    }
    
    @Override
    public int getItemCount() {
        return 100;
    }
}
