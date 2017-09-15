package com.aero;

import pixelitor.gui.PixelitorWindow;
import pixelitor.gui.utils.GUIUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompositionMetadata  {
    private String source;
    private String date;
    private static JDialog metadataDialog;
//    private String sourceURL;
    private String title;
    private String description;
    private String author;
    private ImageLicence imageLicence;
    private ArrayList<String> categories;
    private String fileName;

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }



    public static void showMetadataDialog(CompositionMetadata metadata) {
        if (metadataDialog == null) {

            metadataDialog = new JDialog(PixelitorWindow.getInstance(), "Metadata", false);
            JPanel p = new MetadataPanel(metadata);
            metadataDialog.getContentPane().add(p);

            metadataDialog.setSize(500, 500);
            GUIUtils.centerOnScreen(metadataDialog);
        }

        if (!metadataDialog.isVisible()) {
            metadataDialog.setVisible(true);
        }
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setSourceFromEncodedSource(String encodedSource)
    {
        source = encodedSource.replace("urn:library-of-congress:ndnp:mets:newspaper:page://", "");
        String[] split = source.split("/");
        String sn = split[0];
        String date = split[1];
        String ed = split[2];
        String seq = split[3];

        ed = String.valueOf(Integer.parseInt(ed));
        seq = String.valueOf(Integer.parseInt(seq));

        Pattern pattern = Pattern.compile("\\D+(\\d+).*");
        Matcher matcher = pattern.matcher(fileName);
        matcher.matches();
        String seqNumber = matcher.group(1);

        source = "http://chroniclingamerica.loc.gov/lccn/"+sn+"/"+date+"/ed-"+ed+"/seq-"+seqNumber;
        // http://chroniclingamerica.loc.gov/lccn/sn84026817/1905-10-25/ed-1/seq-4.jp2
        System.out.println(source);

    }


    public String getMarkupForMetadata()
    {
        String markup = "=={{int:filedesc}}==\n{{Information\n|description=$DESCRIPTION$\n|date=$DATE$\n|source=$SOURCE$\n|author={{author|$AUTHOR$}}\n|permission=\n|other versions=\n}}\n\n=={{int:license-header}}==\n{{$LICENSE$}}\n\n$CATEGORIES$";
        markup = markup.replace("$DESCRIPTION$", ((description==null||description.equals(""))?"":"{{en|1="+description+"}}"));
        markup = markup.replace("$DATE$", (date==null?"":date));
        markup = markup.replace("$SOURCE$", (source==null?"":source));
        markup = markup.replace("$AUTHOR$", ((author == null || author.equals(""))?"unknown":author));
        markup = markup.replace("$LICENSE$", (imageLicence==null?"":imageLicence.getTemplateName()));
        markup = markup.replace("$CATEGORIES$", "");
        return markup;
    }


    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public ImageLicence getImageLicence()
    {
        return imageLicence;
    }

    public void setImageLicence(ImageLicence imageLicence)
    {
        this.imageLicence = imageLicence;
    }

    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public void setCategories(ArrayList<String> categories)
    {
        this.categories = categories;
    }

    public CompositionMetadata copy()
    {
        CompositionMetadata meta = new CompositionMetadata();
        meta.setDescription(description);
        meta.setImageLicence(imageLicence);
        meta.setCategories(categories);
        meta.setAuthor(author);
        meta.setSource(source);
        meta.setTitle(title);
        meta.setDate(date);
        return meta;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;

    }

    public String getFileName()
    {
        return fileName;
    }
}
