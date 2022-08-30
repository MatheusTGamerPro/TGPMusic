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

package com.matheustgp.tgpmusic;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.MediaSession2;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.Events;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.matheustgp.tgpmusic.R;
import com.matheustgp.tgpmusic.adapters.MusicList;
import com.matheustgp.tgpmusic.adapters.MusicListAdapter;
import com.matheustgp.tgpmusic.fragments.MusicListFragment;
import com.matheustgp.tgpmusic.fragments.MusicsFragment;
import com.matheustgp.tgpmusic.fragments.PlaylistFragment;
import com.matheustgp.tgpmusic.media.MusicPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerActivity extends AppCompatActivity {
   
    public static  int REQ_CD_AUDIOFILE = 101;
    
    // Views
    private TabLayout tab;
    private View bottomSheet;
    private ViewPager2 viewPager;
    private RecyclerView recyclerView;
    private ImageView imageViewAlbum;
    private ImageView imageViewPlay;
    private TextView textViewTitle;
    private TextView textViewArtist;
    private SeekBar seekControl;
    private BottomSheetBehavior bottomSheetBehavior;
   
    public List<MusicList> musics = new ArrayList<MusicList>();
    
    private Timer timer = new Timer();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MusicListFragment musicsFragment;
    private PlaylistFragment playlistFragment;
    private MusicListAdapter recyclerAdapter;
    private MusicsFragment pagerAdapter;
    
    private MusicPlayer player;
    private MediaSessionCompat mediaSession;
    private MediaSessionConnector mediaConnector;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        // Configure IDs
        tab = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);
        textViewTitle = findViewById(R.id.musicTitle);
        textViewArtist = findViewById(R.id.musicArtist);
        imageViewAlbum = findViewById(R.id.musicLogoSheet);
        imageViewPlay = findViewById(R.id.btnPlay);
        seekControl = findViewById(R.id.pgbProgress);
        bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        
        // Setup all adapters
        setupAdapters();
        
        mediaSession = new MediaSessionCompat(this, getPackageName());
        mediaConnector = new MediaSessionConnector(mediaSession);
        mediaConnector.setPlayer(player.getPlayer());
        mediaSession.setActive(true);
        
        player.getPlayer().addListener(new ExoPlayer.Listener() {
            @Override
			public void onIsPlayingChanged(boolean isPlaying) {
 		  	 if (isPlaying) {
 				   TimerTask sleep = new TimerTask() {
                		@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
                   				 seekControl.setProgress(player.getPosition());
               				 }
               			 });
          		 	 }
            		};
           		 timer.scheduleAtFixedRate(sleep, (int)10, (int)10);
 			   }
			}
        });
        
        // Adicionar evento ao TabLayout
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab mTab) {
                viewPager.setCurrentItem(mTab.getPosition());
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab mTab) { } 
            
            @Override
            public void onTabUnselected(TabLayout.Tab  mTab) { }
        });
        
        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.getPlayer().isPlaying()) {
                	player.pauseMusic();
                } else {
                	player.playMusic();
                }
            }
        });
        
        seekControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seeekBar, int value, boolean param3) {
                
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				player.seekTo(seekBar.getProgress());
			}
        });
    }

    // Função para configurar os novos adaptadores
    public void setupAdapters() {
        musics = new MusicList().createMusicList();
        player = new MusicPlayer(getApplicationContext(), textViewTitle, textViewArtist, imageViewAlbum, seekControl);
        player.setupPlayer();
        
        musicsFragment = new MusicListFragment();
        playlistFragment = new PlaylistFragment();
        
        pagerAdapter = new MusicsFragment(fragmentManager, getLifecycle(), musicsFragment, playlistFragment);
        recyclerAdapter = new MusicListAdapter(getApplicationContext(), musics, player);
        
        viewPager.setAdapter(pagerAdapter);
        musicsFragment.setup(recyclerAdapter, musics);
        viewPager.setUserInputEnabled(false);
    }
}
