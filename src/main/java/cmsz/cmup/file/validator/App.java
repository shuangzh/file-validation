package cmsz.cmup.file.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Pattern pattern = Pattern.compile("b*g");  
        Matcher matcher = pattern.matcher("bbg");  
    }
}
