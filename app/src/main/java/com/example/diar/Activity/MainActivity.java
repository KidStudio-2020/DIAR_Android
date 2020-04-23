package com.example.diar.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.diar.R;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private ModelRenderable andyRenderable;

    private FloatingActionButton main_fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.activity_main);
        main_fab = (FloatingActionButton)findViewById(R.id.fab);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addObject(Uri.parse("CouchRed.sfb")); //sfb 파일명 서버에서 받아오는 변수 만들어서 그걸로 지정해두기
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                startActivity(intent);
            }
        });
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void addObject(Uri model){
//        ModelRenderable.builder()
//                .setSource(this, model)
//                .build()
//                .thenAccept(renderable -> andyRenderable = renderable)
//                .exceptionally(
//                        throwable -> {
//                            Toast toast =
//                                    Toast.makeText(this, "Unable to load andy renderable", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                            return null;
//                        });
//
//        arFragment.setOnTapArPlaneListener(
//                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
////                    if (andyRenderable == null) {
////                        Toast.makeText(this, "Select a model to load", Toast.LENGTH_SHORT).show();
////                        return;
////                    }
//
//                    // Create the Anchor.
//                    Anchor anchor = hitResult.createAnchor();
//                    AnchorNode anchorNode = new AnchorNode(anchor);
//                    anchorNode.setParent(arFragment.getArSceneView().getScene());
//
//                    // Create the transformable andy and add it to the anchor.
//                    TransformableNode andy = new TransformableNode(arFragment.getTransformationSystem());
//                    andy.setParent(anchorNode);
//                    andy.setRenderable(andyRenderable);
//                    andy.select();
//                });
//    }
}