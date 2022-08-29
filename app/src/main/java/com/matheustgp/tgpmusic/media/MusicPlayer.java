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

package com.matheustgp.tgpmusic.media;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.Events;
import com.google.android.exoplayer2.util.ListenerSet;
import com.matheustgp.tgpmusic.adapters.MusicList;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer implements ExoPlayer.Listener {
    // variaveis
    private Context mContext;
    private int musicDuration;
    
    // Views e Widgets
    private TextView txtMusicTitle;
    private TextView txtMusicArtist;
    private ImageView albumImage;
    private SeekBar seekBar;
    
    // Player == ExoPlayer
    public ExoPlayer player;
    
    public MusicPlayer(Context context, TextView musicTitle,
    				  TextView musicArtist, ImageView imgAlbum, SeekBar seek) {
        this.mContext = context;
        this.txtMusicTitle = musicTitle;
        this.txtMusicArtist = musicArtist;
        this.albumImage = imgAlbum;
        this.seekBar = seek;
    }
    
    public void setupPlayer() {
        this.player = new ExoPlayer.Builder(mContext).build();
        player.addListener(this);
    }
    
    public void createNewMusic(List<MusicList> musicList, int musicPosition) {
        Uri music = Uri.fromFile(new File(musicList.get(musicPosition).getMusic()));
   	 player.setMediaItem(MediaItem.fromUri(music));
   	 player.prepare();
    }
    
    public void cleanPlayer() {
        if (player != null) {
            player.clearMediaItems();
            player.stop();
            player.release();
        }
    }
    
    public void playMusic() {
        if (player != null && !player.isPlaying()) {
            player.play();
        }
    }
    
    public void pauseMusic() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }
    
    public void seekTo(int value) {
        if (player != null && player.isPlaying()) {
        	player.seekTo(value);
        }    
    }
    
    public int getPosition() {
        return (int) player.getCurrentPosition();
    }
    
    public int getMusicDuration() {
        if (player != null) {
        	return musicDuration;
        } else {
        	return 0;
        }
    }
    
    public ExoPlayer getPlayer() {
        return player;
    }
    
    @Override
    public void onEvents(Player player, Events events) {
    	if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) 
             || events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)) {
                 musicDuration = (int) player.getDuration();
                 seekBar.setMax(musicDuration);
        }
	}
    
    public void onMediaMetadataChanged(MediaMetadata mediaMetadata) {
		if (mediaMetadata.title != null) {
             txtMusicTitle.setText(mediaMetadata.title);
 	   }
        
        if (mediaMetadata.artist != null) {
            txtMusicArtist.setText(mediaMetadata.artist);
        }
        
        if (mediaMetadata.artworkData != null) {
            final byte[] albumArt = mediaMetadata.artworkData;
            albumImage.setImageBitmap(BitmapFactory.decodeByteArray(albumArt, 0, albumArt.length));
        }
    }
}

