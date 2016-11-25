package org.gearvrf.pickandmove;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.gearvrf.FutureWrapper;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRSphereCollider;
import org.gearvrf.GVRPicker;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRTexture;
import org.gearvrf.GVRTransform;
import org.gearvrf.GVRPicker.GVRPickedObject;
import org.gearvrf.IPickEvents;
import org.gearvrf.pickandmove.R;
import org.gearvrf.scene_objects.GVRSphereSceneObject;

import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by lgicl on 2016/11/25.
 */

public class Multiphoto extends GVRMain {

    private GVRContext mGVRContext = null;
//    private GVRScene mScene = null;
    private List<GVRSceneObject> mObjects = new ArrayList<GVRSceneObject>();
    private IPickEvents mPickHandler;
    private GVRSceneObject mPickedObject = null;
    private GVRPicker   mPicker;

    GVRScene scene;
    GVRSphereSceneObject sphereObject;
    Future<GVRTexture> texture;
    Future<GVRTexture> texture1;


    @Override
    public void onInit(GVRContext gvrContext) {
        mGVRContext = gvrContext;
        scene=mGVRContext.getNextMainScene();
        sphereObject = null;
        texture = mGVRContext.loadFutureTexture(new GVRAndroidResource(mGVRContext, R.raw.abc));
        texture1 = mGVRContext.loadFutureTexture(new GVRAndroidResource(mGVRContext, R.raw.abd));
        sphereObject = new GVRSphereSceneObject(mGVRContext, 72, 144, false, texture);
        scene.addSceneObject(sphereObject);

    }

    @Override
    public void onStep() {
        FPSCounter.tick();
    }

    public void onTouchEvent(MotionEvent event) {
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

    }

}
