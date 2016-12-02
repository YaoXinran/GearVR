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

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRTexture;
import org.gearvrf.pickandmove.anim.AnimButtonPlay;
import org.gearvrf.pickandmove.focus.ControlSceneObjectBehavior;
import org.gearvrf.pickandmove.focus.TouchAndGestureImpl;
import org.gearvrf.pickandmove.input.TouchPadInput;
import org.gearvrf.scene_objects.GVRSphereSceneObject;

import java.util.concurrent.Future;

public class PickandmoveMain extends GVRMain {


    private GVRContext mGVRContext;
    private GVRScene scene;
    //private GVRSceneObject cross;
    private GVRSphereSceneObject grass;
    private GVRSphereSceneObject world;
    private boolean worldee = true;
    private AnimButtonPlay playButton;
    //private List<GVRSceneObject> mObjects = new ArrayList<GVRSceneObject>();

    Future<GVRTexture> texture;
    Future<GVRTexture> texture1;

    
   @Override
    public void onInit(GVRContext gvrContext) {

       mGVRContext = gvrContext;

       scene = gvrContext.getMainScene();


       createWorld();
       //createCross();
       createPlayButton();
       createGazeCursor();


    }


    private void createWorld(){

        texture = mGVRContext.loadFutureTexture(new GVRAndroidResource(mGVRContext, R.raw.photosphere));

        world = new GVRSphereSceneObject(mGVRContext,72, 144, false, texture);
        scene.addSceneObject(world);
        worldee = true;



    }

    private void createGrass(){

        texture1 = mGVRContext.loadFutureTexture(new GVRAndroidResource(mGVRContext, R.raw.niceday));

        grass = new GVRSphereSceneObject(mGVRContext,72, 144, false, texture1);
        scene.addSceneObject(grass);
        worldee = false;



    }


   /* private void createCross() {

        GVRMesh mesh = mGVRContext.createQuad(0.05f,0.05f);
        GVRTexture texture = mGVRContext.loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.cross));
        cross = new GVRSceneObject(mGVRContext, mesh, texture);

        cross.getTransform().setPosition(0.0f, 0.0f, -1.0f);
        cross.getRenderData().setDepthTest(false);
        //cross.getTransform().setRotationByAxis(90, 1, 0, 0);
        //cross.getTransform().rotateByAxisWithPivot(SUN_ANGLE_POSITION, 1, 0, 0, 0, 0, 0);
        cross.getRenderData().setRenderingOrder(100000);
        scene.addSceneObject(cross);
    }*/

    private void createPlayButton() {

        playButton = new AnimButtonPlay(mGVRContext);
        playButton.getTransform().setPosition(0.0f, 0.0f, -1.0f);
        playButton.getRenderData().setDepthTest(false);

        playButton.setTouchAndGesturelistener(new TouchAndGestureImpl() {

            @Override
            public void singleTap() {
                super.singleTap();


                //scene.removeSceneObject(world);
                if (isWorlding()) {
                    scene.removeSceneObject(world);
                    createGrass();
                    //playButton.getTransform().setPosition(1.0f,0.0f,-1.0f);

                } else {
                    scene.removeSceneObject(grass);
                    createWorld();
                    //playButton.getTransform().setPosition(0.0f, 0.0f, -1.0f);
                }


            }
        });
        scene.addSceneObject(playButton);
    }

    public boolean isWorlding() {
        return worldee;
    }

    private void createGazeCursor() {
        GazeController.setupGazeCursor(mGVRContext);

    }

 @Override
    public void onStep() { FPSCounter.tick();
     TouchPadInput.process();
     ControlSceneObjectBehavior.process(mGVRContext);
    }

    /*public void onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                scene.removeSceneObject(sphereObject);
                sphereObject = new GVRSphereSceneObject(mGVRContext, 72, 144, false, texture1);
                scene.addSceneObject(sphereObject);
                break;
            case MotionEvent.ACTION_DOWN:
                scene.removeSceneObject(sphereObject);
                sphereObject = new GVRSphereSceneObject(mGVRContext, 72, 144, false, texture);
                scene.addSceneObject(sphereObject);
                break;
            default:break;
        }

    }*/




}