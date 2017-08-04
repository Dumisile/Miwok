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
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    //handles audio focus when playin a sound file.
    private AudioManager mAudioManager;

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
            words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
            words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
            words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
            words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
            words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
            words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
            words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
            words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
            words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
            words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


            WordAdapter Adapter = new WordAdapter(this, words, R.color.category_numbers);
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
                        mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                        mMediaPlayer.start();
                        //media player once the sounds has finished playing.
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }

                }

                //@Override
//                protected void onStop() {
//                    super.onStop();
//
//
//                }


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