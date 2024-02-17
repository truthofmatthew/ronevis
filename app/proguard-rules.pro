-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 10
-allowaccessmodification
-dontpreverify
-printmapping ronevis_map_outputfile.txt
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose


-keepclassmembers class * implements com.fondesa.lyra.coder.StateCoder {
    <init>(...);
}
-keepclassmembers class ** {
    @com.fondesa.lyra.annotation.SaveState <fields>;
}

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

-keepclassmembers class * extends com.stephentuso.welcome.ui.WelcomeActivity {
    public static java.lang.String welcomeKey();
}

-repackageclasses 'mt.karimi.ronevis'
-allowaccessmodification

-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#-useuniqueclassmembernames
#-keeppackagenames doNotKeepAThing

#-keepnames class fragments.textEffects.**

-keep class fragments.db.** { *; }
-keepclassmembers class fragments.db.** { *; }
-keep class multithreaddownload.**.** { *; }
-keepclassmembers class multithreaddownload.**.** { *; }
-keep class fragments.download.model.** { *; }
-keepclassmembers class fragments.download.model.** { *; }
-keep class fragments.objects.** { *; }
-keepclassmembers class fragments.objects.** { *; }

-keep class org.sqlite.** { *; }
-keep class org.sqlite.database.** { *; }

#-keepclassmembers class fragments.objects.** {
#    private <fields>;
#}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}


-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-dontwarn android.support.**

-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

-assumenosideeffects class android.util.Log {
     public static *** d(...);
     public static *** w(...);
     public static *** v(...);
     public static *** i(...);
     public static *** e(...);
 }

-keepattributes *Annotation*,EnclosingMethod,Signature,Exceptions,InnerClasses,EnclosingMethod,SourceFile,LineNumberTable
-keepnames class com.fasterxml.jackson.** { *; }
 -dontwarn com.fasterxml.jackson.databind.**
 -keep class org.codehaus.** { *; }
 -keepclassmembers public final enum org.codehaus.jackson.annotate.JsonAutoDetect$Visibility {
 public static final org.codehaus.jackson.annotate.JsonAutoDetect$Visibility *; }

-dontwarn com.squareup.okhttp.**

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontnote com.google.vending.licensing.ILicensingService

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**



-keep class com.google.gson.** { *; }



-keep class org.acra.** { *; }

-dontwarn android.support.v4.app.NotificationCompat*

-keep public class * implements org.acra.sender.ReportSenderFactory { public <methods>; }

-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}

-keep class android.support.v4.** { *; }
-dontnote android.support.v4.**

-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep public class android.support.v7.** { *; }





-keep class com.google.android.gms.**
-keep class com.google.analytics.tracking.**
-dontwarn com.google.android.gms.**
-dontwarn com.google.analytics.tracking.**

-keep class com.google.android.** { *; }
-keep interface com.google.android.** { *; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.** { *; }


-dontwarn android.support.**

-dontwarn com.google.**

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}


 -keepclasseswithmembernames class * {
    native <methods>;
}



-keep class android.support.v8.** { *; }
-keep interface android.support.v8.** { *; }
-keep public class android.support.v8.** {*;}
-dontwarn android.support.v8.**

-keep class com.daimajia.** { *; }
-keep interface com.daimajia.** { *; }
-keep public class com.daimajia.** {*;}

-keep class com.nineoldandroids.** { *; }
-keep interface com.nineoldandroids.** { *; }
-keep public class com.nineoldandroids.** {*;}

-keep class com.jakewharton.disklrucache.** { *; }
-keep interface com.jakewharton.disklrucache.** { *; }
-dontskipnonpubliclibraryclassmembers

 -keep class android.webkit.** { *; }
 -dontwarn android.webkit.**


-keepclassmembers enum * {
   public static **[] values();
     public static ** valueOf(java.lang.String);
 }

























 # Remove - System method calls. Remove all invocations of System
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.System {
     public static long currentTimeMillis();
     static java.lang.Class getCallerClass();
     public static int identityHashCode(java.lang.Object);
     public static java.lang.SecurityManager getSecurityManager();
     public static java.util.Properties getProperties();
     public static java.lang.String getProperty(java.lang.String);
     public static java.lang.String getenv(java.lang.String);
     public static java.lang.String mapLibraryName(java.lang.String);
     public static java.lang.String getProperty(java.lang.String,java.lang.String);
 }

 # Remove - Math method calls. Remove all invocations of Math
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.Math {
     public static double sin(double);
     public static double cos(double);
     public static double tan(double);
     public static double asin(double);
     public static double acos(double);
     public static double atan(double);
     public static double toRadians(double);
     public static double toDegrees(double);
     public static double exp(double);
     public static double log(double);
     public static double log10(double);
     public static double sqrt(double);
     public static double cbrt(double);
     public static double IEEEremainder(double,double);
     public static double ceil(double);
     public static double floor(double);
     public static double rint(double);
     public static double atan2(double,double);
     public static double pow(double,double);
     public static int round(float);
     public static long round(double);
     public static double random();
     public static int abs(int);
     public static long abs(long);
     public static float abs(float);
     public static double abs(double);
     public static int max(int,int);
     public static long max(long,long);
     public static float max(float,float);
     public static double max(double,double);
     public static int min(int,int);
     public static long min(long,long);
     public static float min(float,float);
     public static double min(double,double);
     public static double ulp(double);
     public static float ulp(float);
     public static double signum(double);
     public static float signum(float);
     public static double sinh(double);
     public static double cosh(double);
     public static double tanh(double);
     public static double hypot(double,double);
     public static double expm1(double);
     public static double log1p(double);
 }

 # Remove - Number method calls. Remove all invocations of Number
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.* extends java.lang.Number {
     public static java.lang.String toString(byte);
     public static java.lang.Byte valueOf(byte);
     public static byte parseByte(java.lang.String);
     public static byte parseByte(java.lang.String,int);
     public static java.lang.Byte valueOf(java.lang.String,int);
     public static java.lang.Byte valueOf(java.lang.String);
     public static java.lang.Byte decode(java.lang.String);
     public int compareTo(java.lang.Byte);
     public static java.lang.String toString(short);
     public static short parseShort(java.lang.String);
     public static short parseShort(java.lang.String,int);
     public static java.lang.Short valueOf(java.lang.String,int);
     public static java.lang.Short valueOf(java.lang.String);
     public static java.lang.Short valueOf(short);
     public static java.lang.Short decode(java.lang.String);
     public static short reverseBytes(short);
     public int compareTo(java.lang.Short);
     public static java.lang.String toString(int,int);
     public static java.lang.String toHexString(int);
     public static java.lang.String toOctalString(int);
     public static java.lang.String toBinaryString(int);
     public static java.lang.String toString(int);
     public static int parseInt(java.lang.String,int);
     public static int parseInt(java.lang.String);
     public static java.lang.Integer valueOf(java.lang.String,int);
     public static java.lang.Integer valueOf(java.lang.String);
     public static java.lang.Integer valueOf(int);
     public static java.lang.Integer getInteger(java.lang.String);
     public static java.lang.Integer getInteger(java.lang.String,int);
     public static java.lang.Integer getInteger(java.lang.String,java.lang.Integer);
     public static java.lang.Integer decode(java.lang.String);
     public static int highestOneBit(int);
     public static int lowestOneBit(int);
     public static int numberOfLeadingZeros(int);
     public static int numberOfTrailingZeros(int);
     public static int bitCount(int);
     public static int rotateLeft(int,int);
     public static int rotateRight(int,int);
     public static int reverse(int);
     public static int signum(int);
     public static int reverseBytes(int);
     public int compareTo(java.lang.Integer);
     public static java.lang.String toString(long,int);
     public static java.lang.String toHexString(long);
     public static java.lang.String toOctalString(long);
     public static java.lang.String toBinaryString(long);
     public static java.lang.String toString(long);
     public static long parseLong(java.lang.String,int);
     public static long parseLong(java.lang.String);
     public static java.lang.Long valueOf(java.lang.String,int);
     public static java.lang.Long valueOf(java.lang.String);
     public static java.lang.Long valueOf(long);
     public static java.lang.Long decode(java.lang.String);
     public static java.lang.Long getLong(java.lang.String);
     public static java.lang.Long getLong(java.lang.String,long);
     public static java.lang.Long getLong(java.lang.String,java.lang.Long);
     public static long highestOneBit(long);
     public static long lowestOneBit(long);
     public static int numberOfLeadingZeros(long);
     public static int numberOfTrailingZeros(long);
     public static int bitCount(long);
     public static long rotateLeft(long,int);
     public static long rotateRight(long,int);
     public static long reverse(long);
     public static int signum(long);
     public static long reverseBytes(long);
     public int compareTo(java.lang.Long);
     public static java.lang.String toString(float);
     public static java.lang.String toHexString(float);
     public static java.lang.Float valueOf(java.lang.String);
     public static java.lang.Float valueOf(float);
     public static float parseFloat(java.lang.String);
     public static boolean isNaN(float);
     public static boolean isInfinite(float);
     public static int floatToIntBits(float);
     public static int floatToRawIntBits(float);
     public static float intBitsToFloat(int);
     public static int compare(float,float);
     public boolean isNaN();
     public boolean isInfinite();
     public int compareTo(java.lang.Float);
     public static java.lang.String toString(double);
     public static java.lang.String toHexString(double);
     public static java.lang.Double valueOf(java.lang.String);
     public static java.lang.Double valueOf(double);
     public static double parseDouble(java.lang.String);
     public static boolean isNaN(double);
     public static boolean isInfinite(double);
     public static long doubleToLongBits(double);
     public static long doubleToRawLongBits(double);
     public static double longBitsToDouble(long);
     public static int compare(double,double);
     public boolean isNaN();
     public boolean isInfinite();
     public int compareTo(java.lang.Double);
     public <init>(byte);
     public <init>(short);
     public <init>(int);
     public <init>(long);
     public <init>(float);
     public <init>(double);
     public <init>(java.lang.String);
     public byte byteValue();
     public short shortValue();
     public int intValue();
     public long longValue();
     public float floatValue();
     public double doubleValue();
     public int compareTo(java.lang.Object);
     public boolean equals(java.lang.Object);
     public int hashCode();
     public java.lang.String toString();
 }

 # Remove - String method calls. Remove all invocations of String
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.String {
     public <init>();
     public <init>(byte);
     public <init>(byte,int);
     public <init>(byte,int,int);
     public <init>(byte,int,int,int);
     public <init>(byte,int,int,java.lang.String);
     public <init>(byte,java.lang.String);
     public <init>(char);
     public <init>(char,int,int);
     public <init>(java.lang.String);
     public <init>(java.lang.StringBuffer);
     public static java.lang.String copyValueOf(char);
     public static java.lang.String copyValueOf(char,int,int);
     public static java.lang.String valueOf(boolean);
     public static java.lang.String valueOf(char);
     public static java.lang.String valueOf(char);
     public static java.lang.String valueOf(char,int,int);
     public static java.lang.String valueOf(double);
     public static java.lang.String valueOf(float);
     public static java.lang.String valueOf(int);
     public static java.lang.String valueOf(java.lang.Object);
     public static java.lang.String valueOf(long);
     public boolean contentEquals(java.lang.StringBuffer);
     public boolean endsWith(java.lang.String);
     public boolean equalsIgnoreCase(java.lang.String);
     public boolean equals(java.lang.Object);
     public boolean matches(java.lang.String);
     public boolean regionMatches(boolean,int,java.lang.String,int,int);
     public boolean regionMatches(int,java.lang.String,int,int);
     public boolean startsWith(java.lang.String);
     public boolean startsWith(java.lang.String,int);
     public byte getBytes();
     public byte getBytes(java.lang.String);
     public char charAt(int);
     public char toCharArray();
     public int compareToIgnoreCase(java.lang.String);
     public int compareTo(java.lang.Object);
     public int compareTo(java.lang.String);
     public int hashCode();
     public int indexOf(int);
     public int indexOf(int,int);
     public int indexOf(java.lang.String);
     public int indexOf(java.lang.String,int);
     public int lastIndexOf(int);
     public int lastIndexOf(int,int);
     public int lastIndexOf(java.lang.String);
     public int lastIndexOf(java.lang.String,int);
     public int length();
     public java.lang.CharSequence subSequence(int,int);
     public java.lang.String concat(java.lang.String);
     public java.lang.String replaceAll(java.lang.String,java.lang.String);
     public java.lang.String replace(char,char);
     public java.lang.String replaceFirst(java.lang.String,java.lang.String);
     public java.lang.String split(java.lang.String);
     public java.lang.String split(java.lang.String,int);
     public java.lang.String substring(int);
     public java.lang.String substring(int,int);
     public java.lang.String toLowerCase();
     public java.lang.String toLowerCase(java.util.Locale);
     public java.lang.String toString();
     public java.lang.String toUpperCase();
     public java.lang.String toUpperCase(java.util.Locale);
     public java.lang.String trim();
 }

 # Remove - StringBuffer method calls. Remove all invocations of StringBuffer
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.StringBuffer {
     public <init>();
     public <init>(int);
     public <init>(java.lang.String);
     public <init>(java.lang.CharSequence);
     public java.lang.String toString();
     public char charAt(int);
     public int capacity();
     public int codePointAt(int);
     public int codePointBefore(int);
     public int indexOf(java.lang.String,int);
     public int lastIndexOf(java.lang.String);
     public int lastIndexOf(java.lang.String,int);
     public int length();
     public java.lang.String substring(int);
     public java.lang.String substring(int,int);
 }
 # Remove - StringBuilder method calls. Remove all invocations of StringBuilder
 # methods without side effects whose return values are not used.
 -assumenosideeffects public class java.lang.StringBuilder {
     public <init>();
     public <init>(int);
     public <init>(java.lang.String);
     public <init>(java.lang.CharSequence);
     public java.lang.String toString();
     public char charAt(int);
     public int capacity();
     public int codePointAt(int);
     public int codePointBefore(int);
     public int indexOf(java.lang.String,int);
     public int lastIndexOf(java.lang.String);
     public int lastIndexOf(java.lang.String,int);
     public int length();
     public java.lang.String substring(int);
     public java.lang.String substring(int,int);
 }






















 #Ronash proguard
 -keep public class co.ronash.pushe.Pushe {
 public static void initialize(android.content.Context, boolean);
 public static void subscribe(android.content.Context, java.lang.String);
 public static void unsubscribe(android.content.Context, java.lang.String);
 }

 -keep public class co.ronash.pushe.PusheListenerService {
 public void onMessageReceived(org.json.JSONObject, org.json.JSONObject);
 }

 -keep public class co.ronash.pushe.receiver.UpdateReceiver


 # google gms proguard
 -keep public class com.google.android.gms.**
 -dontwarn com.google.android.gms.**

 # google paly services proguard
 -keep class * extends java.util.ListResourceBundle {
 protected Object[][] getContents();
 }

 -keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
 public static final *** NULL;
 }

 -keepnames @com.google.android.gms.common.annotation.KeepName class *
 -keepclassmembernames class * {
 @com.google.android.gms.common.annotation.KeepName *;
 }

 -keepnames class * implements android.os.Parcelable {
 public static fi/Volumes/Files/Developments Final/Ronevis/Ronevis/Ronevis/app/google-services.jsonnal ** CREATOR;}

 -keep public class co.ronash.pushe.log.handlers.** { *; }
 -keep class org.json.** { *; }
 -keep interface org.json.** { *; }

 -keep class **.R
 -keep class **.R$* {
 <fields>;
 }