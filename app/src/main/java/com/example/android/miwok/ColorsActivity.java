/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    //handles audio focus when playin a sound file.
    private AudioManager mAudioManager, aoudio;
    //playing the audio file

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //play the word from the beggining when we resume playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
                //pause playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //stop playback and cleanup resources.
                releaseMediaPlayer();
            }
        }
    };

    //playing the audio file
    private MediaPlayer.OnCompletionListener mCompletionListener = (new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    });

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.word_list);
            //request audio focus
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            //creating an array of words
            final ArrayList<Word> words = new ArrayList<Word>();
            words.add(new Word("red", "Wetetti", R.drawable.color_red, R.raw.color_red));
            words.add(new Word("mustard yellow", "chiwiite", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
            words.add(new Word("dusty yellow", "topiise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
            words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
            words.add(new Word("brown", "takaakki", R.drawable.color_brown, R.raw.color_brown));
            words.add(new Word("gray", "topoppi", R.drawable.color_gray, R.raw.color_gray));
            words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
            words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));


            WordAdapter Adapter = new WordAdapter(this, words, R.color.category_colors);
            ListView listView = (ListView) findViewById(R.id.list);
            listView.setAdapter(Adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Word word = words.get(position);
                    //release the media player if it currently exists because we are about.
                    //play a different sound file.
                    releaseMediaPlayer();
                    //request audio focus for playback
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            //use the music stream.
                            AudioManager.STREAM_MUSIC,
                            //request permanent focus
                            AudioManager.AUDIOFOCUS_GAIN);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                        mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceId());
                        mMediaPlayer.start();
                        //media player once the sounds has finished playing.
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }

                }

            });
        }

    private void releaseMediaPlayer() {
        //when media player is not null then it may currently play a sound.
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}


