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
import android.app.Notification;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.Events;
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
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
    
    // Constantes
    public final int REQ_CD_AUDIOFILE = 101;
    
    // Views e Widgets
    private TabLayout tab;
    private View bottomSheet;
    private ViewPager2 viewPager;
    private RecyclerView recyclerView;
    private ImageView imgAlbum;
    private ImageView imgPlay;
    private TextView txtTitle;
    private TextView txtArtist;
    private SeekBar pgb;
    private BottomSheetBehavior bottomSheetBehavior;
    
    // Lista de Musicas
    public List<MusicList> musics = new ArrayList<MusicList>();
    
    // Variaveis de Classes
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
        
        // Referenciar todos as Views e Widgets
        tab = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.pager);
        bottomSheet = findViewById(R.id.bottomSheet);
        txtTitle = findViewById(R.id.musicTitle);
        txtArtist = findViewById(R.id.musicArtist);
        imgAlbum = findViewById(R.id.musicLogoSheet);
        imgPlay = findViewById(R.id.btnPlay);
        pgb = findViewById(R.id.pgbProgress);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        
        // configura o ViewPager e RecyclerView
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
                   				 pgb.setProgress(player.getPosition());
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
            public void onTabReselected(TabLayout.Tab tab) {}
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
        });
        
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.createNewMusic(musics, recyclerAdapter.getMusicSelected());
                player.playMusic();
                
            }
        });
        
        pgb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        player = new MusicPlayer(getApplicationContext(), txtTitle, txtArtist, imgAlbum, pgb);
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
