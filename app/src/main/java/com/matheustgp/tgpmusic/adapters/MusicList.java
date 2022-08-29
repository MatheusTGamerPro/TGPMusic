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

package com.matheustgp.tgpmusic.adapters;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import com.matheustgp.tgpmusic.utils.DataRetriever;
import java.util.ArrayList;

import com.matheustgp.tgpmusic.FileUtil;

public class MusicList {
    
    public ArrayList<String> musics = new ArrayList<String>();
    private String musicName, musicArtist;
    
    public MusicList(String name, String artist) {
        musicName = name;
        musicArtist = artist;
    }
    
    public MusicList() {
        
    }
    
    public String getMusic() {
        return musicName;
    }
    
    public String getMusicArtist() {
        return musicArtist;
    }
    
    public ArrayList<MusicList> createMusicList() {
        ArrayList<MusicList> musicsList = new ArrayList<MusicList>();
        
        DataRetriever.MUSIC_DIRECTORY_SELECTED = "/sdcard/Music/";
        FileUtil.listDir("/sdcard/Music/", musicsList);
        
        return musicsList;
    }
    
    public String getMusic(int position) {
        return musics.get(position);
    }
}
