package fragments.tool;

/**
 * Created by mt.karimi on 9/4/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class RegexFileFilter implements java.io.FileFilter {
    final java.util.regex.Pattern pattern;

    public RegexFileFilter(String regex) {
        pattern = java.util.regex.Pattern.compile(regex);
    }

    public boolean accept(java.io.File f) {
        return pattern.matcher(f.getName()).find();
    }
}