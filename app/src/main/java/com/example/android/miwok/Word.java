package com.example.android.miwok;

/**
 * Created by DUMISILE on 2017/07/11.
 */

public class Word {
      //Default translation for the word
    private String mDefaultTranslation;
    //Miwok translation for the word
    private  String mMiwokTranslation;
    //image resource ID
    private int mImageResourcesId= NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    //Audio resource ID for the word
    private int mAudioResourceId;


    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourcesId,int audioResourceId){
        mDefaultTranslation=defaultTranslation;
        mMiwokTranslation=miwokTranslation;
        mImageResourcesId=imageResourcesId;
        mAudioResourceId=audioResourceId;
    }

//Get default translation of the word
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    //Get miwok translation of the word
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    //return the image resource ID
    public int getmImageResourcesId(){
        return mImageResourcesId;
    }
    //returns wether or not there is an image for this word
    public boolean hasImage(){
       return mImageResourcesId!=NO_IMAGE_PROVIDED;
    }

    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}
