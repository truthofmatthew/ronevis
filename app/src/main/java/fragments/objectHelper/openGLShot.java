package fragments.objectHelper;

/**
 * Created by mt.karimi on 20/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class openGLShot {
//    public static void saveBitmap( Bitmap screenshot,  String filePath,  String fileName) {
//        OutputStream outStream = null;
//        filePath = Environment.getExternalStorageDirectory().toString() + "/" + filePath;
//        File dir = new File( filePath );
//        dir.mkdirs();
//        File output = new File( filePath, fileName );
//        try {
//            outStream = new FileOutputStream( output );
//            screenshot.compress( Bitmap.CompressFormat.PNG, 100, outStream );
//            outStream.flush();
//            outStream.close();
//            Logger.v(  "Saving Screenshot [" + filePath + fileName + "]" );
//        } catch ( FileNotFoundException e ) {
//            Logger.e(  "" + e.getMessage() );
//        } catch ( IOException e ) {
//            Logger.e(  "" + e.getMessage() );
//        }
//    }
//
//
//    public Bitmap captureScreenShot() {
//        int width = Gdx.graphics.getWidth(); // use your favorite width
//        int height = Gdx.graphics.getHeight(); // use your favorite height
//        int screenshotSize = width * height;
//        ByteBuffer bb = ByteBuffer.allocateDirect( screenshotSize * 4 );
//        bb.order( ByteOrder.nativeOrder() );
//        // any opengl context will do
//        Gdx.graphics.getGL20().glReadPixels( 0, 0, width, height, Gdx.graphics.getGL20().GL_RGBA, Gdx.graphics.getGL20().GL_UNSIGNED_BYTE, bb );
//        int pixelsBuffer[] = new int[screenshotSize];
//        bb.asIntBuffer().get( pixelsBuffer );
//        bb = null;
//        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.RGB_565 );
//        bitmap.setPixels( pixelsBuffer, screenshotSize - width, -width, 0, 0, width, height );
//        pixelsBuffer = null;
//
//        short sBuffer[] = new short[screenshotSize];
//        ShortBuffer sb = ShortBuffer.wrap( sBuffer );
//        bitmap.copyPixelsToBuffer( sb );
//
//        //Making created bitmap (from OpenGL points) compatible with Android bitmap
//        for ( int i = 0; i &lt; screenshotSize; ++i ) {
//            short v = sBuffer[i];
//            sBuffer[i] = ( short ) ( ( ( v &amp; 0x1f ) &lt;&lt; 11 ) | ( v &amp; 0x7e0 ) | ( ( v &amp; 0xf800 ) &gt;&gt; 11 ) );
//        }
//        sb.rewind();
//        bitmap.copyPixelsFromBuffer( sb );
//        return bitmap;
//    }
//
//
//    public void captureScreenShot( final String path,  final String fileName) {
//        Bitmap screenshot = captureScreenShot();
//        saveBitmap( screenshot, path, fileName );
//        screenshot.recycle();
//    }
}
