/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gearvrf.pickandmove;

import android.os.Bundle;
import android.view.MotionEvent;

import org.gearvrf.GVRActivity;
import org.gearvrf.pickandmove.focus.VRSamplesTouchPadGesturesDetector;
import org.gearvrf.pickandmove.input.TouchPadInput;
import org.gearvrf.pickandmove.focus.VRSamplesTouchPadGesturesDetector.SwipeDirection;

public class PickandmoveActivity extends GVRActivity implements
        VRSamplesTouchPadGesturesDetector.OnTouchPadGestureListener {

    private VRSamplesTouchPadGesturesDetector mDetector = null;
   private PickandmoveMain main;

    private static final int TAP_INTERVAL = 300;
    private long mLatestTap = 0;


    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        main = new PickandmoveMain();
        setMain(main, "gvr.xml");
        mDetector = new VRSamplesTouchPadGesturesDetector(this, this);
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        main.onTouchEvent(event);
        return super.onTouchEvent(event);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDetector == null) {
            return false;
        }
        TouchPadInput.input(event);
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTap(MotionEvent e) {
        if (System.currentTimeMillis() > mLatestTap + TAP_INTERVAL) {
            mLatestTap = System.currentTimeMillis();
            TouchPadInput.onSingleTap();
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        TouchPadInput.onLongPress();
    }

    @Override
    public boolean onSwipe(MotionEvent e, SwipeDirection swipeDirection, float velocityX,
                           float velocityY) {
        TouchPadInput.onSwipe(swipeDirection);

        return false;
    }

    @Override
    public void onSwiping(MotionEvent e, MotionEvent e2, float velocityX, float velocityY,
                          SwipeDirection swipeDirection) {
    }

    @Override
    public void onSwipeOppositeLastDirection() {
    }
}
