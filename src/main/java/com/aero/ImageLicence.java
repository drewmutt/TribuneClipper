package com.aero;

import java.util.ArrayList;

public class ImageLicence
{
    private static ArrayList<ImageLicence> _cachedArray;
    private final String _templateDescription;
    private final String _templateName;

    public ImageLicence(String templateName, String templateDescription)
    {
        _templateName = templateName;
        _templateDescription = templateDescription;
    }

    public static ArrayList<ImageLicence> getSelfLicenses()
    {
        ArrayList<ImageLicence> array = new ArrayList<>();

        array.add(new ImageLicence("self|cc-by-sa-4.0", "Own work, share alike, attribution required (Creative Commons CC-BY-SA-4.0)"));
        array.add(new ImageLicence("self|cc-by-4.0", "Own work, attribution required (Creative Commons CC-BY-SA-4.0)"));
        array.add(new ImageLicence("self|cc-zero", "Own work, CC0 1.0 Universal Public Domain Dedication"));
        array.add(new ImageLicence("self|GFDL|cc-by-4.0", "Own work, attribution required (Multi-license with GFDL and Creative Commons CC-BY 4.0)"));
        array.add(new ImageLicence("PD-self", "Own work, all rights released (Public domain)"));
        return array;
    }

    public static ArrayList<ImageLicence> getCCLicenses()
    {
        ArrayList<ImageLicence> array = new ArrayList<>();
        array.add(new ImageLicence("cc-by-sa-4.0","Creative Commons Attribution ShareAlike 4.0"));
        array.add(new ImageLicence("cc-by-sa-3.0","Creative Commons Attribution ShareAlike 3.0"));
        array.add(new ImageLicence("cc-by-sa-2.5","Creative Commons Attribution ShareAlike 2.5"));
        array.add(new ImageLicence("cc-by-sa-2.0","Creative Commons Attribution ShareAlike 2.0"));
        array.add(new ImageLicence("cc-by-sa-1.0","Creative Commons Attribution ShareAlike 1.0"));
        array.add(new ImageLicence("cc-by-4.0","Creative Commons Attribution 4.0"));
        array.add(new ImageLicence("cc-by-3.0","Creative Commons Attribution 3.0"));
        array.add(new ImageLicence("cc-by-2.5","Creative Commons Attribution 2.5"));
        array.add(new ImageLicence("cc-by-2.0","Creative Commons Attribution 2.0"));
        array.add(new ImageLicence("cc-by-1.0","Creative Commons Attribution 1.0"));
        array.add(new ImageLicence("cc-sa-1.0","Creative Commons ShareAlike 1.0"));
        return array;
    }

    public static ArrayList<ImageLicence> getPDLicenses()
    {
        ArrayList<ImageLicence> array = new ArrayList<>();
        array.add(new ImageLicence("PD-1923","Published before 1923"));
        array.add(new ImageLicence("PD-old","Author died more than 70 years ago - public domain"));
        array.add(new ImageLicence("Copyrighted free use","Copyrighted but use of the file for any purpose is allowed"));
        array.add(new ImageLicence("PD-Art","Reproduction of a painting that is in the public domain because of its age"));
        array.add(new ImageLicence("PD-AR-Photo","Argentina's Law 11723 for all photographic works 25 years after their first publication"));
        array.add(new ImageLicence("PD-GDR stamps","For German stamps released as Deutsche Post der DDR."));
        array.add(new ImageLicence("PD-GermanGov","For public domain images from Germany statutes or other regulations"));
        array.add(new ImageLicence("PD-US","First published in the United States before 1923 - public domain"));
        array.add(new ImageLicence("PD-USGov","Original work of the US Federal Government - public domain"));
        array.add(new ImageLicence("PD-USGov-Military","For public domain images from the US military or Department of Defense "));
        array.add(new ImageLicence("PD-USGov-NASA","Original work of NASA - public domain"));
        array.add(new ImageLicence("PD-Soviet","Work published in the Soviet Union before May 27, 1973 - public domain"));
        array.add(new ImageLicence("PD","Public domain (generic - please give a reason in the description field!)"));
        array.add(new ImageLicence("patent","Patent material - public domain"));
        return array;
    }

    public static ArrayList<ImageLicence> getGNULicenses()
    {
        ArrayList<ImageLicence> array = new ArrayList<>();
        array.add(new ImageLicence("GFDL","GNU Free Documentation License"));
        array.add(new ImageLicence("GFDL-1.2","for works released under the GNU Free Documentation License version 1.2 specifically."));
        array.add(new ImageLicence("GFDL-en","GFDL content from English Wikipedia"));
        array.add(new ImageLicence("GFDL-OpenGeoDB","GFDL images from http://opengeodb.de"));
        array.add(new ImageLicence("GPL","GNU General Public License"));
        array.add(new ImageLicence("LGPL","GNU Lesser General Public License"));
        return array;
    }

    public static ArrayList<ImageLicence> getOtherLicenses()
    {
        ArrayList<ImageLicence> array = new ArrayList<>();
        array.add(new ImageLicence("Copyright by Wikimedia","Wikimedia logos"));
        array.add(new ImageLicence("FAL","Copyleft: This work of art is free; you can redistribute it and/or modify it according to terms of the Free Art License."));
        return array;
    }

    public static ArrayList<ImageLicence> getAllLicenses()
    {
        if(_cachedArray != null)
            return _cachedArray;

        ArrayList<ImageLicence> array = new ArrayList<>();
        array.addAll(getPDLicenses());
        array.addAll(getCCLicenses());
        array.addAll(getSelfLicenses());
        array.addAll(getGNULicenses());
        array.addAll(getOtherLicenses());
        _cachedArray = array;
        return array;
    }

    public String getTemplateName()
    {
        return _templateName;
    }

    public String getTemplateDescription()
    {
        return _templateDescription;
    }
}
