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

package org.gearvrf.pickandmove.focus;

import org.gearvrf.GVRContext;
import org.gearvrf.GVREyePointeeHolder;
import org.gearvrf.GVRPicker;
import org.gearvrf.pickandmove.GazeController;
import org.gearvrf.pickandmove.focus.VRSamplesTouchPadGesturesDetector.SwipeDirection;
import org.gearvrf.pickandmove.input.TouchPadInput;

import java.util.ArrayList;

public class ControlSceneObjectBehavior {

    public static ArrayList<ControlSceneObject> interactiveObjects = new ArrayList<ControlSceneObject>();

    public static void process(GVRContext context) {

        GVREyePointeeHolder[] eyePointeeHolders = GVRPicker.pickScene(context.getMainScene());
        ArrayList<ControlSceneObject> needToDisableFocus = new ArrayList<ControlSceneObject>();
        for (ControlSceneObject obj : interactiveObjects) {
            obj.onStep();
            needToDisableFocus.add(obj);
        }

        if (eyePointeeHolders.length == 0) {
            GazeController.disableInteractiveCursor();
        } else {
            for (GVREyePointeeHolder eph : eyePointeeHolders) {
                for (ControlSceneObject object : interactiveObjects) {
                    if (eph.getOwnerObject().equals(object)) {
                        object.setFocus(true);
                        needToDisableFocus.remove(object);
                    }
                }
            }
        }

        for (ControlSceneObject obj : needToDisableFocus) {
            obj.setFocus(false);
        }

        processTap(context);
    }

    private static void processTap(GVRContext context) {

        for (ControlSceneObject object : interactiveObjects) {
            if (object.hasFocus()) {

                checkInput(object);
            }
        }
    }

    private static void checkInput(ControlSceneObject object) {
        if (TouchPadInput.getCurrent().buttonState.isSingleTap()) {
            object.singleTap();

        }
        handleTouchPad(object);

    }


    private static void handleTouchPad(ControlSceneObject object) {
        if (object.touchAndGesturelistener != null) {

            if (TouchPadInput.getCurrent().buttonState.isLongPressed()) {
                object.touchAndGesturelistener.longPressed();
            }
            if (TouchPadInput.getCurrent().buttonState.isDown()) {
                object.touchAndGesturelistener.down();
            }
            if (TouchPadInput.getCurrent().buttonState.isUp()) {
                object.touchAndGesturelistener.up();
            }
            if (TouchPadInput.getCurrent().buttonState.isPressed()) {
                object.touchAndGesturelistener.pressed();
            }
            if (TouchPadInput.getCurrent().swipeDirection != SwipeDirection.Ignore) {
                object.touchAndGesturelistener.swipe(TouchPadInput.getCurrent().swipeDirection);
            }
        }

    }
}
