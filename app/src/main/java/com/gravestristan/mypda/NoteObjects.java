package com.gravestristan.mypda;

import java.util.UUID;

/**
 * Created by Tristan on 3/26/2016.
 */
public class NoteObjects implements AppStatics {

    private UUID mId;
    private String mNoteContents;

    /**
     *
     */
    public NoteObjects(){
        mId = UUID.randomUUID();
    }

    /**
     *
     * @return
     */
    public UUID getNoteId(){
        return mId;
    }

    /**
     *
     * @param mId
     */
    public void setNoteId(UUID mId){
        this.mId = mId;
    }

    /**
     *
     * @return
     */
    public String getNoteContents(){
        return mNoteContents;
    }

    /**
     *
     * @param mNoteContents
     */
    public void setNoteContents(String mNoteContents){
        this.mNoteContents = mNoteContents;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        return "note UUID: " + mId;
    }
}
