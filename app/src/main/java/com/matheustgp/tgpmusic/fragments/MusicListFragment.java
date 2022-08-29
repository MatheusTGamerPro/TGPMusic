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

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.matheustgp.tgpmusic.FileUtil;
import com.matheustgp.tgpmusic.PlayerActivity;
import com.matheustgp.tgpmusic.R;
import com.matheustgp.tgpmusic.adapters.MusicList;
import com.matheustgp.tgpmusic.adapters.MusicListAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicListFragment extends Fragment {
    
    private List<MusicList> musics = new ArrayList<MusicList>();
    public RecyclerView recycler;
    public MusicListAdapter adapter;
    
    public MusicListFragment() {
        
    }
    
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler = view.findViewById(R.id.recycler);
        
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    
    public void setup(MusicListAdapter recyclerAdapter, List<MusicList> musicArray) {
        this.adapter = recyclerAdapter;
        this.musics = musicArray;
    }
    
    public RecyclerView getRecyclerView() {
        return recycler;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_list, parent, false);
    }
}

