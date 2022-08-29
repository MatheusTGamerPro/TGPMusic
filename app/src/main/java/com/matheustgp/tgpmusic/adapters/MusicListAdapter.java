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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.google.android.exoplayer2.ExoPlayer;
import com.matheustgp.tgpmusic.adapters.MusicList;
import com.matheustgp.tgpmusic.adapters.MusicListAdapter;
import com.matheustgp.tgpmusic.media.MusicPlayer;
import com.matheustgp.tgpmusic.R;

import com.matheustgp.tgpmusic.utils.DataRetriever;
import java.util.ArrayList;
import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{
    
    private List<MusicList> musicList = new ArrayList<MusicList>();
    private Context mContext;
    private int itemSelected;
    private MusicPlayer playerInstance;
    
    public MusicListAdapter(Context context, List<MusicList> array, MusicPlayer player) {
        this.mContext = context;
        this.musicList = array;
        this.playerInstance = player;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtMusicName;
        private View listLayout;
        
        public ViewHolder(@NonNull View view) {
            super(view);
            
            txtMusicName = view.findViewById(R.id.txtMusicName);
            listLayout = view.findViewById(R.id.relativeLayout);
            
            listLayout.setOnClickListener(this);
        }
        
        @Override
        public void onClick(View v) {
            itemSelected = getAdapterPosition();
            notifyDataSetChanged();
            Toast.makeText(mContext, "Posição: " + itemSelected, Toast.LENGTH_SHORT).show();
            playerInstance.createNewMusic(musicList, itemSelected);
            playerInstance.playMusic();
        }
    } 
    
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
        	.inflate(R.layout.layout_music_list, parent, false);
        
        return new ViewHolder(inflater);
    }
    
    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.ViewHolder holder, int position) {
        MusicList music = musicList.get(position);
        holder.txtMusicName.setText(music.getMusic().replace(DataRetriever.MUSIC_DIRECTORY_SELECTED, ""));
        
        if (itemSelected == position) {
            holder.txtMusicName.setTextColor(Color.RED);
        } else {
        	holder.txtMusicName.setTextColor(Color.WHITE);
        }
    }
    
    public int getMusicSelected() {
        return itemSelected;
    }
    
    public void updateList() {
        notifyDataSetChanged();
    }
    
    public List<MusicList> getMusicList() {
        return musicList;
    }
    
    public boolean removeMusic(int position) {
        try {
        	musicList.remove(position);
        	notifyItemRemoved(position);
       	 notifyItemRangeRemoved(position, musicList.size());
            return true;
        } catch (IndexOutOfBoundsException e) {
        	return false;
        }
    }
    
    public int getItemCount() {
        return musicList.size();
    }
}

