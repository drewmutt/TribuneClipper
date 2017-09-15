package pixelitor.filters;

import com.jhlabs.image.LaplaceFilter;
import pixelitor.filters.lookup.LuminanceLookup;
import pixelitor.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class AutoAdjust extends Filter {
    public static final String NAME = "Auto Adjust";


    public int span(int in, int min, int max)
    {
        double span = max - min;
        double ratio = ((double)in - (double)min) / span;
        double value = ratio * 255;
        return (int)value;
    }

    @Override
    public BufferedImage transform(BufferedImage src, BufferedImage dest) {

        int[] srcData = ImageUtils.getPixelsAsArray(src);
        int[] destData = ImageUtils.getPixelsAsArray(dest);

        int length = srcData.length;

        int lowest = 0;
        int highest = 0;

        long[] historgram = new long[256];

        for(int i = 0; i < 255; i++)
        {
            historgram[i] = 0;
        }

        for(int i = 0; i < length; i++)
        {
            int rgb = srcData[i];
            int r = (rgb >>> 16) & 0xFF;
            int g = (rgb >>> 8) & 0xFF;
            int b = rgb & 0xFF;
            int lum = LuminanceLookup.getLuminosity(rgb);
//            if(lum < lowest)
//                lowest = lum;
            if(lum > highest)
                highest = lum;

            historgram[lum]++;
        }

        System.out.println("Pre - Lowest: "+lowest);


        for(int i = 1; i < 255; i++)
        {
            long spread = historgram[i] - historgram[i - 1];
            double growth = ((double)historgram[i]) / ((double)historgram[i - 1]);

            double totalPixelPercentage = ((double) spread) / ((double) length);
//            System.out.println(i + ": " + spread + "\t" + growth +  "\t\t"+ totalPixelPercentage);
            System.out.println(i + ": " + historgram[i]);
            if(totalPixelPercentage > 0.0001 && lowest == 0) {
                lowest = i;
//                break;
            }

        }

        int span =  highest - lowest;
        int b = 255;

        System.out.println("Lowest: "+lowest);
        System.out.println("Highest: "+highest);

        double mult = 255.0 / 200.0;
//        int thres =
        for(int i = 0; i < length; i++)
        {
            int rgb = srcData[i];
//            int r1 = (rgb >>> 16) & 0xFF;
//            int g1 = (rgb >>> 8) & 0xFF;
//            int b1 = rgb & 0xFF;
//            int alpha = rgb & 0xFF000000;

            int luminosity = LuminanceLookup.getLuminosity(rgb);


//            int smashed = (int) (((double)luminosity) / 50.0);


//            destLum = (int) (((double)destLum) * mult);
            int destLum = span(luminosity, lowest, highest);
//            if(i > 10000 && i < 10100) {
//                System.out.println("In: " + luminosity + " Out: " + destLum);
//            }
            destLum = (int) (((double)destLum) * mult);

            if (destLum > 255) {
                destLum = 255;
            }
            if (destLum < 0) {
                destLum = 0;
            }


            int finalRGB =(0xFF000000 | (destLum << 16) | (destLum << 8) | destLum);


            destData[i] = finalRGB;


        }

        /*

        for(int i = 0; i < 255; i++)
        {
            historgram[i] = 0;
        }

        System.out.println("--- After");
        for(int i = 0; i < length; i++)
        {
            int rgb = destData[i];
            int lum = LuminanceLookup.getLuminosity(rgb);
            historgram[lum]++;
        }


        for(int i = 0; i < 255; i++)
        {
            System.out.println(i + ": " + historgram[i]);
        }

*/
        return dest;
    }

    @Override
    public void randomizeSettings() {
        // nothing to randomize
    }

    @Override
    public boolean supportsGray() {
        return true;
    }
}