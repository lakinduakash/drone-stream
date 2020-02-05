package com.bwin.airtoplay;

import boofcv.abst.feature.detect.interest.ConfigGeneralDetector;
import boofcv.abst.feature.tracker.PointTracker;
import boofcv.alg.sfm.d2.StitchingFromMotion2D;
import boofcv.factory.feature.tracker.FactoryPointTracker;
import boofcv.factory.sfm.FactoryMotion2D;
import boofcv.misc.BoofMiscOps;
import boofcv.struct.image.GrayF32;
import boofcv.struct.image.ImageType;
import boofcv.struct.image.Planar;
import georegression.struct.homography.Homography2D_F64;

public class Stabilizer {

    private void f(){

        StitchingFromMotion2D<Planar<GrayF32>, Homography2D_F64>
                stabilize = FactoryMotion2D.createVideoStitch(0.5, null,ImageType.pl(3,GrayF32.class));

        stabilize.process(null);
    }

}
