package org.dakik.sweetseekbar.interfaces;

public interface SweetSeekbarListener {
    public void onStart(int value);
    public void onMove(int value);
    public void onEnd(int value);
}
